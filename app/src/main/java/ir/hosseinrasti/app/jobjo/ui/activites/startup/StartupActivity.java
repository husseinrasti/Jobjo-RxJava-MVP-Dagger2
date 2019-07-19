package ir.hosseinrasti.app.jobjo.ui.activites.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.ui.activites.BaseActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category.CategoryFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home.HomeFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview.InterviewFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post.PostFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search.SearchFragment;
import ir.hosseinrasti.app.jobjo.ui.user.profile.ProfileActivity;
import ir.hosseinrasti.app.jobjo.ui.user.users.UsersActivity;
import ir.hosseinrasti.app.jobjo.utils.ActivityUtils;
import ir.hosseinrasti.app.jobjo.utils.Config;

/**
 * Created by Hossein on 5/17/2018.
 */

public class StartupActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private Unbinder unbinder;

    private static final int UPDATE = 300;

    @BindView(R.id.navigationBottom)
    public BottomNavigationView bottomNavigation;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.imgProfileToolbar)
    public ImageView imgProfileToolbar;
    @BindView(R.id.imgUsersToolbar)
    public ImageView imgUsersToolbar;

    @Inject
    HomeFragment homeFragment;
    @Inject
    SearchFragment searchFragment;
    @Inject
    CategoryFragment categoryFragment;
    @Inject
    InterviewFragment interviewFragment;
    @Inject
    PostFragment postFragment;

    @Inject
    SessionPref pref;

    // Default selected fragment
    private static final int DEFAULT = -1;

    private int menuPosition = 0; // it should be zero otherwise #selectItem won't be called
    private Bundle saveStateFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        saveStateFragment = savedInstanceState;
        setContentView( R.layout.activity_startup );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        setSupportActionBar( toolbar );

        registerServiceLogout();

        loadProfilePic();

        imgProfileToolbar.setOnClickListener( v ->
                startActivityForResult(
                        new Intent( StartupActivity.this, ProfileActivity.class ), UPDATE ) );
        imgUsersToolbar.setOnClickListener( v ->
                startActivity( new Intent( StartupActivity.this, UsersActivity.class ) ) );

        bottomNavigation.setOnNavigationItemSelectedListener( this );

        loadFragment( homeFragment, Config.TAG_FRAGMENT_HOME );
        menuPosition = DEFAULT;
    }

    private void loadProfilePic() {
        if ( !pref.getPic().isEmpty() ) {
            Picasso.with( this ).load( pref.getPic() )
                    .placeholder( getApplicationContext().getResources().getDrawable( R.drawable.ic_profile ) )
                    .error( getApplicationContext().getResources().getDrawable( R.drawable.ic_profile ) )
                    .into( imgProfileToolbar );
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if ( requestCode == UPDATE && resultCode == RESULT_OK ) {
            try {
                boolean isUpdate = data.getExtras().getBoolean( "update" );
                if ( isUpdate ) {
                    loadProfilePic();
                }
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive( Context context, Intent intent ) {
            startActivity( new Intent( StartupActivity.this, BaseActivity.class ) );
            finish();
        }
    };

    private void registerServiceLogout() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction( "ir.hosseinrasti.app.jobjo.ACTION_LOGOUT" );
        registerReceiver( broadcastReceiver, intentFilter );
    }

    @Override
    public boolean onNavigationItemSelected( @NonNull MenuItem item ) {
        getListFragment();
        switch ( item.getItemId() ) {
            case R.id.navigation_home:
                setListHomeFragment();
                loadFragment( homeFragment, Config.TAG_FRAGMENT_HOME );
                menuPosition = DEFAULT;
                return true;
            case R.id.navigation_search:
                loadFragment( searchFragment, Config.TAG_FRAGMENT_SEARCH );
                menuPosition = 0;
                return true;
            case R.id.navigation_post:
                setListPostFragment();
                loadFragment( postFragment, Config.TAG_FRAGMENT_POST );
                menuPosition = 1;
                return true;
            case R.id.navigation_category:
                setListCategoryFragment();
                loadFragment( categoryFragment, Config.TAG_FRAGMENT_CATEGORY );
                menuPosition = 2;
                return true;
            case R.id.navigation_interview:
                setListInterviewFragment();
                loadFragment( interviewFragment, Config.TAG_FRAGMENT_INTERVIEW );
                menuPosition = 3;
                return true;
        }
        return false;
    }

    private List<JobModel> jobModelsHome = new ArrayList<>();
    private List<JobModel> jobModelsPost = new ArrayList<>();
    private List<WorkGroupModel> workGroupModels = new ArrayList<>();
    private List<UserModel> userModels = new ArrayList<>();

    private void getListFragment() {
        try {
            jobModelsHome.clear();
            jobModelsPost.clear();
            workGroupModels.clear();
            userModels.clear();
            jobModelsHome = homeFragment.getList();
            jobModelsPost = postFragment.getList();
            workGroupModels = categoryFragment.getList();
            userModels = interviewFragment.getList();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void setListHomeFragment() {
        homeFragment.setList( jobModelsHome );
    }

    private void setListPostFragment() {
        postFragment.setList( jobModelsPost );
    }

    private void setListCategoryFragment() {
        categoryFragment.setList( workGroupModels );
    }

    private void setListInterviewFragment() {
        interviewFragment.setList( userModels );
    }

    private void loadFragment( Fragment fragment, String tag ) {
        if ( fragment.isAdded() ) {
            return;
        }
        ActivityUtils.addFragmentToActivity( getSupportFragmentManager(),
                fragment, R.id.frame_container, tag );
    }


    @Override
    public void onBackPressed() {
        if ( menuPosition != DEFAULT ) {
            try {
                loadFragment( homeFragment, Config.TAG_FRAGMENT_HOME );
                bottomNavigation.setSelectedItemId( R.id.navigation_home );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            menuPosition = DEFAULT;
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        homeFragment.dropData();
        postFragment.dropData();
        searchFragment.dropData();
        categoryFragment.dropData();
        unbinder.unbind();
        unregisterReceiver( broadcastReceiver );
        super.onDestroy();
    }
}