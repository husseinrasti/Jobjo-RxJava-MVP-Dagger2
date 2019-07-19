package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import androidx.core.app.Fragment;
import androidx.core.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import ir.hosseinrasti.app.jobjo.ui.adapter.UserSenderAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class InterviewFragment extends Fragment implements OnItemUserClickListener {

    private Unbinder unbinder;
    private CompositeDisposable disposable = new CompositeDisposable();

    @BindView(R.id.coordinatorMsgFrag)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerSender)
    public RecyclerView recyclerSender;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    private List<UserModel> userList = new ArrayList<>();

    @Inject
    DataSource.Message messageRepository;
    @Inject
    UserSenderAdapter userSenderAdapter;
    @Inject
    Font font;
    private boolean isRefresh;

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_interview, container, false );
        unbinder = ButterKnife.bind( this, view );
        App.getComponent().inject( this );

        font.titr( txtEmpty );

        userSenderAdapter.setContext( getContext() );
        userSenderAdapter.setUserModels( userList );
        userSenderAdapter.setOnItemClickListener( this );
        userSenderAdapter.setFont( font );

        recyclerSender.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerSender.setItemAnimator( new DefaultItemAnimator() );
        recyclerSender.setAdapter( userSenderAdapter );

        try {
            if ( userList.isEmpty() ) {
                fetchAllSender();
            } else {
                userSenderAdapter.notifyDataSetChanged();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( () -> {
            isRefresh = true;
            fetchAllSender();
        } );


        return view;
    }

    public void setList( List<UserModel> userModels ) {
        userList = userModels;
    }

    public List<UserModel> getList() {
        return userList;
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.clear();
        disposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    private void fetchAllSender() {
        if ( isRefresh ) {
            progressBar.setVisibility( View.VISIBLE );
        }
        disposable.add(
                messageRepository.fetchSenderMessage( messageRepository.getIdCurrentUser() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<UserModel>>() {

                            @Override
                            public void onSuccess( List<UserModel> UserModel ) {
                                progressBar.setVisibility( View.GONE );
                                if ( swipeRefresh.isRefreshing() ) {
                                    swipeRefresh.setRefreshing( false );
                                }
                                if ( UserModel.get( 0 ).isError() ) {
                                    txtEmpty.setText( UserModel.get( 0 ).getErrorMessage() );
                                    recyclerSender.setVisibility( View.GONE );
                                    txtEmpty.setVisibility( View.VISIBLE );

                                } else {
                                    recyclerSender.setVisibility( View.VISIBLE );
                                    txtEmpty.setVisibility( View.GONE );
                                    userList.clear();
                                    userList.addAll( UserModel );
                                    userSenderAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( swipeRefresh.isRefreshing() ) {
                                    swipeRefresh.setRefreshing( false );
                                }
                                progressBar.setVisibility( View.GONE );
                                txtEmpty.setText( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
                                recyclerSender.setVisibility( View.GONE );
                                txtEmpty.setVisibility( View.VISIBLE );
                            }
                        } )
        );
    }

    @Override
    public void onItemClick( final UserModel userModel, Action action ) {
        Intent intent = new Intent( getActivity(), ChatActivity.class );
        intent.putExtra( "userModel", userModel );
        startActivity( intent );
    }


}
