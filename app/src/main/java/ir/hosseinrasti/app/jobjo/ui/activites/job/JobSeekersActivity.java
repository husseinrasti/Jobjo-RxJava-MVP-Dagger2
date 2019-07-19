package ir.hosseinrasti.app.jobjo.ui.activites.job;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobSeekerAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.ui.user.BottomSheetUsersFragment;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersActivity;
import ir.hosseinrasti.app.jobjo.utils.Config;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.Util;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/28/2018.
 */

public class JobSeekersActivity extends AppCompatActivity implements OnItemUserClickListener {

    private JobModel jobModel;

    private Unbinder unbinder;
    private CompositeDisposable disposable = new CompositeDisposable();

    @BindView(R.id.coordinatorFollower)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerFollower)
    public RecyclerView recyclerFollower;
    @BindView(R.id.toolbarFollower)
    public Toolbar toolbar;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;

    private List<UserModel> userList = new ArrayList<>();

    @Inject
    JobSeekerAdapter jobSeekerAdapter;
    @Inject
    DataSource.Profile profileRepository;
    @Inject
    DataSource.Job repository;
    @Inject
    SessionPref pref;
    @Inject
    Font font;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_job_seekers );
        App.getComponent().inject( this );

        unbinder = ButterKnife.bind( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( "درخواست کنندگان آگهی" );

        try {
            Bundle bundle = getIntent().getExtras();
            jobModel = ( JobModel ) bundle.getSerializable( "job" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        jobSeekerAdapter.setContext( this );
        jobSeekerAdapter.setUserModels( userList );
        jobSeekerAdapter.setOnItemClickListener( this );
        jobSeekerAdapter.setFont( font );

        recyclerFollower.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerFollower.setItemAnimator( new DefaultItemAnimator() );
        recyclerFollower.setAdapter( jobSeekerAdapter );

        fetchFollowers();
    }

    private void fetchFollowers() {
        disposable.add(
                repository.fetchUserModelByIdJobApply( jobModel.getId() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<UserModel>>() {

                            @Override
                            public void onSuccess( List<UserModel> userModels ) {
                                if ( userModels.get( 0 ).isError() ) {
                                    showSnack( userModels.get( 0 ).getErrorMessage() );
                                    recyclerFollower.setVisibility( View.GONE );
                                    txtEmpty.setVisibility( View.VISIBLE );
                                    txtEmpty.setText( userModels.get( 0 ).getErrorMessage() );
                                } else {
                                    showSnack( userModels.get( 0 ).getErrorMessage() );
                                    recyclerFollower.setVisibility( View.VISIBLE );
                                    txtEmpty.setVisibility( View.GONE );
                                    userList.clear();
                                    userList.addAll( userModels );
                                    jobSeekerAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                Util.showError( coordinator, e );
                            }
                        } )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinator, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void onItemClick( UserModel userModel, Action action ) {
        switch ( action ) {
            case CLICK:
                Intent intent = new Intent( this, ProfileOthersActivity.class );
                intent.putExtra( "idUser", userModel.getId() );
                startActivity( intent );
                break;
            case SHARE:
                BottomSheetUsersFragment usersFragment = new BottomSheetUsersFragment();
                Bundle bundle = new Bundle();
                bundle.putLong( "idWorkerUser", userModel.getId() );
                bundle.putLong( "idCurrentUser", pref.getId() );
                usersFragment.setArguments( bundle );
                usersFragment.show( getSupportFragmentManager(), Config.TAG_SHEET_WORKER_RESUME );
                break;
        }

    }

}
