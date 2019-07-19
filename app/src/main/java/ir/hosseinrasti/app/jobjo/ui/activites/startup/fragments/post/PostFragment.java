package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobDetailsActivity;
import ir.hosseinrasti.app.jobjo.ui.adapter.PostAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.job.ModifyJobActivity;
import ir.hosseinrasti.app.jobjo.utils.Config;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/28/2018.
 */

public class PostFragment extends Fragment implements
        OnItemJobClickListener,
        PostContract.View {

    private Unbinder unbinder;

    @BindView(R.id.coordinatorMyJob)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerMyJob)
    public RecyclerView recyclerJob;
    @BindView(R.id.fabAddMyJob)
    public FloatingActionButton fabAddJob;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    private List<JobModel> jobList = new ArrayList<>();

    @Inject
    PostAdapter jobAdapter;
    @Inject
    PostContract.Presenter presenter;
    @Inject
    Font font;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_post , container , false );
        unbinder = ButterKnife.bind( this , view );
        App.getComponent().inject( this );
        presenter.takeView( this );

        font.titr( txtEmpty );

        jobAdapter.setContext( getContext() );
        jobAdapter.setJobList( jobList );
        jobAdapter.setOnItemClickListener( this );
        jobAdapter.setFont( font );

        recyclerJob.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerJob.setItemAnimator( new DefaultItemAnimator() );
        recyclerJob.setAdapter( jobAdapter );

        fabAddJob.setOnClickListener( v -> {
            Intent intent = new Intent( getActivity() , ModifyJobActivity.class );
            intent.putExtra( "mustUpdate" , false );
            startActivityForResult( intent , Config.RESULT_UPDATE );
        } );

        try {
            if ( jobList.isEmpty() ) {
                presenter.fetchPosts();
            } else {
                jobAdapter.notifyDataSetChanged();
            }
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( () -> {
            presenter.refresh();
            presenter.fetchPosts();
        } );


        return view;
    }

    public void dropData() {
        presenter.rxUnsubscribe();
        unbinder.unbind();
        presenter.dropView();
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
        Snackbar snackbar = Snackbar.make( coordinator , message , Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( com.google.android.material.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void showSnackDelete( final JobModel jobModel ) {
        Snackbar snackbar = Snackbar.make( coordinator
                , "آگهی " + jobModel.getNameJob() + " پاک شود؟"
                , Snackbar.LENGTH_LONG );
        snackbar.setAction( "بله" , v -> {
            try {
                presenter.removePost( jobModel.getId() );
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        } );
        snackbar.setActionTextColor( Color.GREEN );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( com.google.android.material.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void setTextEmpty( String message ) {
        txtEmpty.setText( message );
    }

    @Override
    public void hideTextEmpty() {
        recyclerJob.setVisibility( View.VISIBLE );
        txtEmpty.setVisibility( View.GONE );

    }

    @Override
    public void showTextEmpty() {
        recyclerJob.setVisibility( View.GONE );
        txtEmpty.setVisibility( View.VISIBLE );
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
    public void onItemClick( JobModel jobModel , Action action ) {
        switch ( action ) {
            case CLICK: {
                Intent intent = new Intent( getContext() , JobDetailsActivity.class );
                intent.putExtra( "job" , jobModel );
                startActivity( intent );
                break;
            }
            case MODIFY: {
                Intent intent = new Intent( getContext() , ModifyJobActivity.class );
                intent.putExtra( "jobModel" , jobModel );
                intent.putExtra( "mustUpdate" , true );
                startActivityForResult( intent , Config.RESULT_UPDATE );
                break;
            }
            case DELETE: {
                showSnackDelete( jobModel );
                break;
            }
        }

    }

    @Override
    public void onActivityResult( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult( requestCode , resultCode , data );
        if ( requestCode == Config.RESULT_UPDATE && resultCode == Activity.RESULT_OK ) {
            try {
                boolean isUpdate = data.getExtras().getBoolean( "update" );
                if ( isUpdate ) {
                    presenter.fetchPosts();
                }
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
    }
}
