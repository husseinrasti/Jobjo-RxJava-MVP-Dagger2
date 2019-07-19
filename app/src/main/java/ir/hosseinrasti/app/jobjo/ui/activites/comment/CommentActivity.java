package ir.hosseinrasti.app.jobjo.ui.activites.comment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.ui.adapter.CommentAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemCommentClickListener;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

public class CommentActivity extends AppCompatActivity implements
        CommentContract.View,
        OnItemCommentClickListener {

    private Unbinder unbinder;
    @BindView(R.id.container)
    public CoordinatorLayout container;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.edtComments)
    public EditText edtComments;
    @BindView(R.id.btnSendComment)
    public ImageView btnSend;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private List<CommentModel> commentModelList = new ArrayList<>();

    @Inject
    CommentContract.Presenter presenter;
    @Inject
    CommentAdapter adapter;
    @Inject
    Font font;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_comment );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );
        presenter.takeView( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );

        font.iran( edtComments );
        font.titr( txtEmpty );

        adapter.setContext( this );
        adapter.setCommentModels( commentModelList );
        adapter.setOnItemClickListener( this );
        adapter.setFont( font );

        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( adapter );

        try {
            Bundle bundle = getIntent().getExtras();
            JobModel jobModel = ( JobModel ) bundle.getSerializable( "job" );
            presenter.setJobModel( jobModel );
            getSupportActionBar().setTitle( jobModel.getNameJob() );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        presenter.fetchComments();

        btnSend.setOnClickListener( v -> {
            if ( edtComments.getText().toString().isEmpty() ) {
                showSnack( "نظر خود را وارد کنید!" );
                return;
            }
            presenter.sendComment();
        } );
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        presenter.rxUnsubscribe();
        presenter.dropView();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void notifyAdapter( List<CommentModel> commentModels ) {
        commentModelList.clear();
        commentModelList.addAll( commentModels );
        adapter.notifyDataSetChanged();
        edtComments.getText().clear();
    }

    @Override
    public void notifyAdapter( CommentModel commentModels ) {
        adapter.addCommentModel( commentModels );
        edtComments.getText().clear();
    }

    @Override
    public void setTextEmpty( String message ) {
        txtEmpty.setText( message );
    }

    @Override
    public void hideTextEmpty() {
        txtEmpty.setVisibility( View.GONE );
    }

    @Override
    public void showTextEmpty() {
        txtEmpty.setVisibility( View.VISIBLE );
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
    public String getComment() {
        return edtComments.getText().toString();
    }

    @Override
    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( container, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void onItemClick( CommentModel commentModel, Action action ) {
        Intent intent = new Intent( this, ProfileOthersActivity.class );
        intent.putExtra( "idUser", commentModel.getIdUser() );
        startActivity( intent );
    }
}
