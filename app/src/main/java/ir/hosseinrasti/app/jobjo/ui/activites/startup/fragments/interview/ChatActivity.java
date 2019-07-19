package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import ir.hosseinrasti.app.jobjo.data.entity.MessageModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.adapter.ChatAdapter;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 5/27/2018.
 */

public class ChatActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private CompositeDisposable disposable = new CompositeDisposable();

    private List<MessageModel> messageList = new ArrayList<>();

    private UserModel userModel;

    @BindView(R.id.container)
    public ConstraintLayout container;
    @BindView(R.id.recyclerMessage)
    public RecyclerView recyclerMessage;
    @BindView(R.id.toolbarMessage)
    public Toolbar toolbar;
    @BindView(R.id.edtMessage)
    public EditText edtMessage;
    @BindView(R.id.btnSend)
    public ImageView btnSend;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    @Inject
    ApiService apiService;
    @Inject
    SessionPref pref;
    @Inject
    ChatAdapter chatAdapter;

    @Inject
    Font font;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        font.iran( edtMessage );
        font.titr( txtEmpty );

        try {
            Bundle bundle = getIntent().getExtras();
            userModel = ( UserModel ) bundle.getSerializable( "userModel" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( userModel.getFullname() );

        chatAdapter.setContext( this );
        chatAdapter.setMessageModelList( messageList );
        chatAdapter.setIdCurrentUser( pref.getId() );
        chatAdapter.setFont( font );

        recyclerMessage.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerMessage.setItemAnimator( new DefaultItemAnimator() );
        recyclerMessage.setAdapter( chatAdapter );

        fetchMessage();

        btnSend.setOnClickListener( v -> sendMessage() );

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( () -> fetchMessage() );
    }

    private void sendMessage() {
        final String message = edtMessage.getText().toString();
        disposable.add( apiService.insertMessage(
                pref.getId(),
                userModel.getId(),
                Util.getCurrentDate(),
                message )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<MessageModel>() {

                    @Override
                    public void onSuccess( MessageModel messageModels ) {
                        recyclerMessage.setVisibility( View.VISIBLE );
                        txtEmpty.setVisibility( View.GONE );
                        chatAdapter.insertMessage( messageModels );
                        edtMessage.setText( "" );
                        recyclerMessage.smoothScrollToPosition( messageList.size() );
                    }

                    @Override
                    public void onError( Throwable e ) {
                        showSnack( "ارتباط با مرکز برقرار نیست!" );
                    }
                } ) );
    }

    private void fetchMessage() {
        disposable.add( apiService.fetchMessage( userModel.getId(), pref.getId() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<List<MessageModel>>() {

                    @Override
                    public void onSuccess( List<MessageModel> messageModels ) {
                        if ( swipeRefresh.isRefreshing() ) {
                            swipeRefresh.setRefreshing( false );
                        }
                        if ( messageModels.get( 0 ).isError() ) {
                            recyclerMessage.setVisibility( View.GONE );
                            txtEmpty.setVisibility( View.VISIBLE );
                            txtEmpty.setText( "پیام موجود نیست" );
                        } else {
                            recyclerMessage.setVisibility( View.VISIBLE );
                            txtEmpty.setVisibility( View.GONE );
                            updateChat( messageModels );
                        }
                    }

                    @Override
                    public void onError( Throwable e ) {
                        showSnack( "ارتباط با مرکز برقرار نیست!" );
                    }
                } ) );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( container, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    private void updateChat( List<MessageModel> messageModels ) {
        Collections.sort( messageModels, new Comparator<MessageModel>() {

            @Override
            public int compare( MessageModel o1, MessageModel o2 ) {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
                    return Long.compare( o1.getId(), o2.getId() );
                }
                return 0;
            }

        } );
        messageList.clear();
        messageList.addAll( messageModels );
        chatAdapter.notifyDataSetChanged();
        recyclerMessage.smoothScrollToPosition( messageList.size() );
    }
}
