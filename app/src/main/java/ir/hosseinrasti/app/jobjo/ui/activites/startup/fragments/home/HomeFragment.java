package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
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
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobDetailsActivity;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobAdapter;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

public class HomeFragment extends Fragment implements
        OnItemJobClickListener
        , HomeContract.View {

    private Unbinder unbinder;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.txtEmptyHome)
    public TextView txtEmptyHome;
    @BindView(R.id.recyclerJob)
    public RecyclerView recyclerJob;
    @BindView(R.id.coordinatorHome)
    public CoordinatorLayout coordinatorHome;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    private List<JobModel> jobList = new ArrayList<>();

    @Inject
    HomeContract.Presenter presenter;
    @Inject
    Font font;
    @Inject
    JobAdapter jobAdapter;

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        unbinder = ButterKnife.bind( this, view );
        App.getComponent().inject( this );

        presenter.takeView( this );

        font.titr( txtEmptyHome );

        jobAdapter.setContext( getContext() );
        jobAdapter.setJobList( jobList );
        jobAdapter.setOnItemClickListener( this );
        jobAdapter.setIdCurrentUser( presenter.getCurrentId() );
        jobAdapter.setGrantManager( presenter.isGrantManager() );
        jobAdapter.setFont( font );

        recyclerJob.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerJob.setItemAnimator( new DefaultItemAnimator() );
        recyclerJob.setAdapter( jobAdapter );

        try {
            if ( jobList.isEmpty() ) {
                presenter.fetchJobs();
            } else {
                jobAdapter.notifyDataSetChanged();
            }
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( () -> {
            presenter.refresh();
            presenter.fetchJobs();
        } );

        return view;
    }

    public void dropData() {
        presenter.rxUnsubscribe();
        presenter.dropView();
        unbinder.unbind();
    }

    @Override
    public void showSnackDelete( final JobModel jobModel ) {
        Snackbar snackbar = Snackbar.make( coordinatorHome
                , "آگهی " + jobModel.getNameJob() + " پاک شود؟"
                , Snackbar.LENGTH_LONG );
        snackbar.setAction( "بله", v -> {
            try {
                presenter.removeJob( jobModel );
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        } );
        snackbar.setActionTextColor( Color.GREEN );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void notifyAdapter( List<JobModel> JobModels ) {
        jobList.clear();
        jobList.addAll( JobModels );
        jobAdapter.notifyDataSetChanged();
        if ( swipeRefresh.isRefreshing() ) {
            swipeRefresh.setRefreshing( false );
        }
    }

    @Override
    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinatorHome, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void setTextEmpty( String message ) {
        txtEmptyHome.setText( message );
    }

    @Override
    public void hideTextEmpty() {
        recyclerJob.setVisibility( View.VISIBLE );
        txtEmptyHome.setVisibility( View.GONE );
    }

    @Override
    public void showTextEmpty() {
        recyclerJob.setVisibility( View.GONE );
        txtEmptyHome.setVisibility( View.VISIBLE );
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        if ( swipeRefresh.isRefreshing() ) {
            swipeRefresh.setRefreshing( false );
        }
        progressBar.setVisibility( View.GONE );
    }

    @Override
    public void setList( List<JobModel> jobModels ) {
        jobList = jobModels;
    }

    @Override
    public List<JobModel> getList() {
        return jobList;
    }

    @Override
    public void onItemClick( JobModel jobModel, Action action ) {
        switch ( action ) {
            case CLICK: {
                Intent intent = new Intent( getActivity(), JobDetailsActivity.class );
                intent.putExtra( "job", jobModel );
                startActivity( intent );
                break;
            }
            case APPLY:
            case DELETE:
            case SHARE:
                presenter.onItemClick( jobModel, action );
                break;
            case COMMENT: {
                Intent intent = new Intent( getActivity(), CommentActivity.class );
                intent.putExtra( "job", jobModel );
                startActivity( intent );
                break;
            }
        }

    }
}
