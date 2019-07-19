package ir.hosseinrasti.app.jobjo.ui.user.intruduce;

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
import android.widget.ProgressBar;
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
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.adapter.UsersAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 6/2/2018.
 */

public class UsersIntroducedActivity extends AppCompatActivity implements OnItemUserClickListener {

    private Unbinder unbinder;
    private CompositeDisposable disposable = new CompositeDisposable();

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.coordinatorWorker)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerWorker)
    public RecyclerView recyclerWorker;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private List<UserModel> userList = new ArrayList<>();

    @Inject
    UsersAdapter usersAdapter;
    @Inject
    Font font;
    @Inject
    DataSource.UsersIntroduced repository;
    @Inject
    DataSource.Profile profileRepository;
    @Inject
    SessionPref pref;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_introduce );
        App.getComponent().inject( this );
        unbinder = ButterKnife.bind( this );

        font.titr( txtEmpty );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( "کارجویان" );

        usersAdapter.setContext( this );
        usersAdapter.setOnItemClickListener( this );
        usersAdapter.setUserModels( userList );
        usersAdapter.setFont( font );

        recyclerWorker.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerWorker.setItemAnimator( new DefaultItemAnimator() );
        recyclerWorker.setAdapter( usersAdapter );

        fetchAllUsersIntroduced();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
    }


    private void fetchAllUsersIntroduced() {
        progressBar.setVisibility( View.VISIBLE );
        disposable.add(
                repository.fetchAllUsersIntroduced( pref.getId() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<UserModel>>() {

                            @Override
                            public void onSuccess( List<UserModel> UserModel ) {
                                progressBar.setVisibility( View.GONE );
                                if ( UserModel.get( 0 ).isError() ) {
                                    txtEmpty.setText( UserModel.get( 0 ).getErrorMessage() );
                                    recyclerWorker.setVisibility( View.GONE );
                                    txtEmpty.setVisibility( View.VISIBLE );
                                } else {
                                    showSnack( UserModel.get( 0 ).getErrorMessage() );
                                    recyclerWorker.setVisibility( View.VISIBLE );
                                    txtEmpty.setVisibility( View.GONE );
                                    userList.clear();
                                    userList.addAll( UserModel );
                                    usersAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                progressBar.setVisibility( View.GONE );
                                txtEmpty.setText( "امکان برقراری ارتباط وجود ندارد،اینترنت را چک کنید!" );
                                recyclerWorker.setVisibility( View.GONE );
                                txtEmpty.setVisibility( View.VISIBLE );
                            }
                        } )
        );
    }

    @Override
    public void onItemClick( UserModel userModel, Action action ) {
        Intent intent = new Intent( this, ProfileOthersActivity.class );
        intent.putExtra( "idUser", userModel.getId() );
        startActivity( intent );
    }

    private void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinator, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
