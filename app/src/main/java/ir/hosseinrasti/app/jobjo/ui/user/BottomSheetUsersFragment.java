package ir.hosseinrasti.app.jobjo.ui.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.ui.adapter.UsersEmployerAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.Util;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class BottomSheetUsersFragment extends BottomSheetDialogFragment implements OnItemUserClickListener {

    private Unbinder unbinder;

    private long idCurrentUser;
    private long idWorkerUser;

    private CompositeDisposable disposable = new CompositeDisposable();

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.coordinatorWorker)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerUsers)
    public RecyclerView recyclerUsers;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;

    private List<UserModel> userList = new ArrayList<>();

    @Inject
    UsersEmployerAdapter employerAdapter;
    @Inject
    DataSource.Job repository;
    @Inject
    Font font;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        App.getComponent().inject( this );

        try {
            Bundle bundle = getArguments();
            idWorkerUser = bundle.getLong( "idWorkerUser" );
            idCurrentUser = bundle.getLong( "idCurrentUser" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.bottom_sheet_users, container, false );
        unbinder = ButterKnife.bind( this, view );

        font.titr( txtEmpty );

        employerAdapter.setContext( getContext() );
        employerAdapter.setOnItemClickListener( this );
        employerAdapter.setUserModels( userList );
        employerAdapter.setFont( font );

        recyclerUsers.setLayoutManager( new GridLayoutManager( getActivity(), 2 ) );
        recyclerUsers.setItemAnimator( new DefaultItemAnimator() );
        recyclerUsers.setAdapter( employerAdapter );

        fetchAllUsers();

        return view;
    }

    private void fetchAllUsers() {
        progressBar.setVisibility( View.VISIBLE );
        disposable.add(
                repository.fetchAllUsers()
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<UserModel>>() {

                            @Override
                            public void onSuccess( List<UserModel> UserModel ) {
                                progressBar.setVisibility( View.GONE );
                                if ( UserModel.get( 0 ).isError() ) {
                                    txtEmpty.setText( UserModel.get( 0 ).getErrorMessage() );
                                    recyclerUsers.setVisibility( View.GONE );
                                    txtEmpty.setVisibility( View.VISIBLE );
                                } else {
                                    recyclerUsers.setVisibility( View.VISIBLE );
                                    txtEmpty.setVisibility( View.GONE );
                                    userList.clear();
                                    userList.addAll( UserModel );
                                    employerAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                progressBar.setVisibility( View.GONE );
                                txtEmpty.setText( "اتصال به اینترنت را چک کنید!" );
                                recyclerUsers.setVisibility( View.GONE );
                                txtEmpty.setVisibility( View.VISIBLE );
                            }
                        } )
        );
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onItemClick( final UserModel userModel, Action action ) {
        if ( userModel.getId() == idCurrentUser ) {
            showSnack( "خطا! کارجو را نمی توان به خودتان معرفی کنید." );
            return;
        }
        if ( userModel.getId() == idWorkerUser ) {
            showSnack( "خطا! کارجو با کارفرما یک نفر هستند." );
            return;
        }
        disposable.add( repository.insertUserIntroduced( idCurrentUser,
                userModel.getId(),
                idWorkerUser )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<UserModel>() {

                    @Override
                    public void onSuccess( UserModel UserModel ) {
                        showSnack( UserModel.getErrorMessage() );
                        dismiss();
                    }

                    @Override
                    public void onError( Throwable e ) {
                        Util.showError( coordinator, e );
                    }
                } ) );
    }

    private void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinator, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

}
