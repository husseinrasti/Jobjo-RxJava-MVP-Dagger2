package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;

public class SearchFragment extends Fragment implements OnItemJobClickListener
        , SearchContract.View {

    private Unbinder unbinder;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.coordinatorSearch)
    public CoordinatorLayout coordinator;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.recyclerSearch)
    public RecyclerView recyclerSearch;
    @BindView(R.id.edtSearch)
    public EditText edtSearch;

    private List<JobModel> jobModelList = new ArrayList<>();

    @Inject
    SearchContract.Presenter presenter;
    @Inject
    Font font;
    @Inject
    JobAdapter jobAdapter;

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_search, container, false );
        unbinder = ButterKnife.bind( this, view );
        App.getComponent().inject( this );


        jobAdapter.setContext( getContext() );
        jobAdapter.setJobList( jobModelList );
        jobAdapter.setOnItemClickListener( this );
        jobAdapter.setIdCurrentUser( presenter.getCurrentId() );
        jobAdapter.setGrantManager( presenter.isGrantManager() );
        jobAdapter.setFont( font );

        recyclerSearch.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerSearch.setItemAnimator( new DefaultItemAnimator() );
        recyclerSearch.setAdapter( jobAdapter );

        presenter.takeView( this );
        try {
            presenter.search();
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }

        font.titr( txtEmpty );
        font.iran( edtSearch );

        return view;
    }

    public void dropData() {
        presenter.rxUnsubscribe();
        unbinder.unbind();
        presenter.dropView();
    }

    @Override
    public EditText getEditText() {
        return edtSearch;
    }

    @Override
    public void notifyAdapter( List<JobModel> JobModels ) {
        jobModelList.clear();
        jobModelList.addAll( JobModels );
        jobAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinator, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void showSnackDelete( final JobModel jobModel ) {
        Snackbar snackbar = Snackbar.make( coordinator
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
    public void setTextEmpty( String message ) {
        txtEmpty.setText( message );
    }

    @Override
    public void hideTextEmpty() {
        txtEmpty.setVisibility( View.GONE );
        recyclerSearch.setVisibility( View.VISIBLE );
    }

    @Override
    public void showTextEmpty() {
        txtEmpty.setVisibility( View.VISIBLE );
        recyclerSearch.setVisibility( View.GONE );
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility( View.GONE );
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
