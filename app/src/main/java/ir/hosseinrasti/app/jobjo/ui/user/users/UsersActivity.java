package ir.hosseinrasti.app.jobjo.ui.user.users;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.ui.adapter.UsersAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class UsersActivity extends AppCompatActivity implements OnItemUserClickListener {

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
    @BindView(R.id.edtSearchUser)
    public EditText edtSearchUser;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private List<UserModel> userList = new ArrayList<>();

    @Inject
    UsersAdapter usersAdapter;
    @Inject
    DataSource.Users repository;
    @Inject
    Font font;


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( "کاربران" );

        fetchAllUsers();

        font.titr( txtEmpty );
        font.iran( edtSearchUser );

        usersAdapter.setContext( this );
        usersAdapter.setOnItemClickListener( this );
        usersAdapter.setUserModels( userList );
        usersAdapter.setFont( font );

        recyclerWorker.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerWorker.setItemAnimator( new DefaultItemAnimator() );
        recyclerWorker.setAdapter( usersAdapter );

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( this::fetchAllUsers );

        disposable.add( RxTextView.textChangeEvents( edtSearchUser )
                .skipInitialValue()
                .debounce( 500, TimeUnit.MILLISECONDS )
                .distinctUntilChanged()
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( doSearch() ) );
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
        super.onDestroy();
    }

    private DisposableObserver<TextViewTextChangeEvent> doSearch() {
        return new DisposableObserver<TextViewTextChangeEvent>() {

            @Override
            public void onNext( TextViewTextChangeEvent textViewTextChangeEvent ) {
                String word = textViewTextChangeEvent.text().toString();
                if ( word.isEmpty() ) {
                    fetchAllUsers();
                    return;
                }
                txtEmpty.setVisibility( View.GONE );
                progressBar.setVisibility( View.VISIBLE );
                repository.searchUser( word )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<UserModel>>() {

                            @Override
                            public void onSuccess( List<UserModel> userModels ) {
                                progressBar.setVisibility( View.GONE );
                                if ( userModels.get( 0 ).isError() ) {
                                    txtEmpty.setVisibility( View.VISIBLE );
                                    recyclerWorker.setVisibility( View.GONE );
                                    txtEmpty.setText( "کاربری یافت نشد" );
                                } else {
                                    txtEmpty.setVisibility( View.GONE );
                                    recyclerWorker.setVisibility( View.VISIBLE );
                                    userList.clear();
                                    userList.addAll( userModels );
                                    usersAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                txtEmpty.setVisibility( View.VISIBLE );
                                recyclerWorker.setVisibility( View.GONE );
                                txtEmpty.setText( "مشکلی در دریافت اطلاعات پیش آمده!" );
                                progressBar.setVisibility( View.GONE );
                            }
                        } );
            }

            @Override
            public void onError( Throwable e ) {
                progressBar.setVisibility( View.GONE );
                txtEmpty.setVisibility( View.VISIBLE );
                recyclerWorker.setVisibility( View.GONE );
                txtEmpty.setText( "مشکلی در دریافت اطلاعات پیش آمده!" );
            }

            @Override
            public void onComplete() {
                txtEmpty.setVisibility( View.GONE );
                recyclerWorker.setVisibility( View.GONE );
                progressBar.setVisibility( View.GONE );
            }
        };
    }

    private void fetchAllUsers() {
        if ( !swipeRefresh.isRefreshing() ) {
            progressBar.setVisibility( View.VISIBLE );
        }
        disposable.add(
                repository.fetchAllUsers()
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<UserModel>>() {

                            @Override
                            public void onSuccess( List<UserModel> UserModel ) {
                                if ( swipeRefresh.isRefreshing() ) {
                                    swipeRefresh.setRefreshing( false );
                                }
                                progressBar.setVisibility( View.GONE );
                                if ( UserModel.get( 0 ).isError() ) {
                                    txtEmpty.setText( UserModel.get( 0 ).getErrorMessage() );
                                    recyclerWorker.setVisibility( View.GONE );
                                    txtEmpty.setVisibility( View.VISIBLE );
                                } else {
                                    recyclerWorker.setVisibility( View.VISIBLE );
                                    txtEmpty.setVisibility( View.GONE );
                                    userList.clear();
                                    userList.addAll( UserModel );
                                    usersAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( swipeRefresh.isRefreshing() ) {
                                    swipeRefresh.setRefreshing( false );
                                }
                                progressBar.setVisibility( View.GONE );
                                txtEmpty.setText( "اتصال به اینترنت را چک کنید." );
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

}
