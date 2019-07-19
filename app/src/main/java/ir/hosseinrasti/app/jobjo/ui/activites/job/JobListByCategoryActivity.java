package ir.hosseinrasti.app.jobjo.ui.activites.job;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.SwipeRefreshLayout;
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
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentActivity;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.Util;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 7/16/2018.
 */

public class JobListByCategoryActivity extends AppCompatActivity implements OnItemJobClickListener {

    private Unbinder unbinder;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.txtEmptyHome)
    public TextView txtEmptyHome;
    @BindView(R.id.txtToolbarNameCategory)
    public TextView txtToolbarNameCategory;
    @BindView(R.id.recyclerJob)
    public RecyclerView recyclerJob;
    @BindView(R.id.coordinatorHome)
    public CoordinatorLayout coordinatorHome;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private List<JobModel> jobList = new ArrayList<>();

    @Inject
    DataSource.Home repository;
    @Inject
    Font font;
    @Inject
    JobAdapter jobAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();

    private long idCategory;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_job_by_category );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );


        try {
            Bundle bundle = getIntent().getExtras();
            idCategory = bundle.getLong( "idCategory" );
            String nameCategory = bundle.getString( "nameCategory" );
            int color = bundle.getInt( "color" );
            Util.whiteNotificationBar( this, toolbar, color );
            toolbar.setBackgroundColor( color );
            txtToolbarNameCategory.setText( nameCategory );
            fetchJob( idCategory );
        } catch ( Exception e ) {
            e.printStackTrace();
        }


        font.iran( txtToolbarNameCategory );
        font.titr( txtEmptyHome );

        jobAdapter.setContext( this );
        jobAdapter.setJobList( jobList );
        jobAdapter.setIdCurrentUser( repository.getIdCurrentUser() );
        jobAdapter.setGrantManager( repository.isGrantManager() );
        jobAdapter.setOnItemClickListener( this );
        jobAdapter.setFont( font );

        recyclerJob.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerJob.setItemAnimator( new DefaultItemAnimator() );
        recyclerJob.setAdapter( jobAdapter );

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( () -> {
            fetchJob( idCategory );
        } );

    }

    private void fetchJob( long idCategory ) {
        if ( !swipeRefresh.isRefreshing() ) {
            progressBar.setVisibility( View.VISIBLE );
        }
        txtEmptyHome.setVisibility( View.GONE );
        disposable.add( repository.fetchJobByCategory( idCategory )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<List<JobModel>>() {

                    @Override
                    public void onSuccess( List<JobModel> jobModels ) {
                        progressBar.setVisibility( View.GONE );
                        if ( swipeRefresh.isRefreshing() ) {
                            swipeRefresh.setRefreshing( false );
                        }
                        if ( jobModels.get( 0 ).isError() ) {
                            txtEmptyHome.setVisibility( View.VISIBLE );
                            txtEmptyHome.setText( jobModels.get( 0 ).getErrorMessage() );
                            showSnack( jobModels.get( 0 ).getErrorMessage() );
                        } else {
                            jobList.clear();
                            jobList.addAll( jobModels );
                            jobAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError( Throwable e ) {
                        progressBar.setVisibility( View.GONE );
                        if ( swipeRefresh.isRefreshing() ) {
                            swipeRefresh.setRefreshing( false );
                        }
                        txtEmptyHome.setVisibility( View.VISIBLE );
                        txtEmptyHome.setText( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
                        showSnack( "مشکلی در دریافت اطلاعات پیش آمده!" );
                    }
                } )
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
        super.onDestroy();
    }

    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinatorHome, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void onItemClick( JobModel jobModel, Action action ) {
        switch ( action ) {
            case CLICK: {
                Intent intent = new Intent( this, JobDetailsActivity.class );
                intent.putExtra( "job", jobModel );
                startActivity( intent );
                break;
            }
            case APPLY:
            case DELETE:
            case SHARE:
                break;
            case COMMENT: {
                Intent intent = new Intent( this, CommentActivity.class );
                intent.putExtra( "job", jobModel );
                startActivity( intent );
                break;
            }
        }
    }
}
