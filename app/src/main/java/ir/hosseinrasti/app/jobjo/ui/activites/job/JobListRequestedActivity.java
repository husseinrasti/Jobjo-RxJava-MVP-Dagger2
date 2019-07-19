package ir.hosseinrasti.app.jobjo.ui.activites.job;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 6/2/2018.
 */

public class JobListRequestedActivity extends AppCompatActivity implements OnItemJobClickListener {

    private Unbinder unbinder;

    @BindView(R.id.coordinatorMyJob)
    CoordinatorLayout coordinator;
    @BindView(R.id.recyclerMyJob)
    public RecyclerView recyclerJob;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.toolbarMyJob)
    public Toolbar toolbar;


    private CompositeDisposable disposable = new CompositeDisposable();

    private List<JobModel> jobList = new ArrayList<>();

    @Inject
    SessionPref pref;
    @Inject
    ApiService apiService;
    @Inject
    Font font;
    @Inject
    JobAdapter jobAdapter;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_job_requested );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( "کارهای درخواستی شما" );

        jobAdapter.setContext( this );
        jobAdapter.setJobList( jobList );
        jobAdapter.setOnItemClickListener( this );
        jobAdapter.setApplyJob( true );
        jobAdapter.setFont( font );

        recyclerJob.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerJob.setItemAnimator( new DefaultItemAnimator() );
        recyclerJob.setAdapter( jobAdapter );

        fetchMyApplyJobs();
    }

    private void fetchMyApplyJobs() {
        disposable.add(
                apiService.fetchJobApplyById( pref.getId() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<JobModel>>() {

                            @Override
                            public void onSuccess( List<JobModel> jobModels ) {
                                if ( jobModels.get( 0 ).isError() ) {
                                    showSnack( jobModels.get( 0 ).getErrorMessage() );
                                    recyclerJob.setVisibility( View.GONE );
                                    txtEmpty.setVisibility( View.VISIBLE );
                                    txtEmpty.setText( jobModels.get( 0 ).getErrorMessage() );
                                } else {
                                    showSnack( jobModels.get( 0 ).getErrorMessage() );
                                    recyclerJob.setVisibility( View.VISIBLE );
                                    txtEmpty.setVisibility( View.GONE );
                                    jobList.clear();
                                    jobList.addAll( jobModels );
                                    jobAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                Util.showError( coordinator, e );
                            }
                        } )
        );
    }

    private void removeJobApply( final JobModel job ) {
        disposable.add( apiService.removeJobApply( job.getId(), job.getIdUserCreator(), pref.getId() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel jobModel ) {
                        showSnack( jobModel.getErrorMessage() );
                        jobList.remove( job );
                        jobAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError( Throwable e ) {
                        Util.showError( coordinator, e );
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
    }

    private void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinator, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void onItemClick( JobModel jobModel, Action action ) {
        switch ( action ) {
            case APPLY:
                removeJobApply( jobModel );
                break;
            case CLICK: {
                Intent intent = new Intent( this, JobDetailsActivity.class );
                intent.putExtra( "job", jobModel );
                startActivity( intent );
                break;
            }
            case DELETE:
                break;
            case SHARE:
                break;
            case COMMENT:
                Intent intent = new Intent( this, CommentActivity.class );
                intent.putExtra( "job", jobModel );
                startActivity( intent );
                break;
        }
    }
}
