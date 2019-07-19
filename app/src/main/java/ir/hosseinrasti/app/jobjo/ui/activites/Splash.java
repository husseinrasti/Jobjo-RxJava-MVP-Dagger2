package ir.hosseinrasti.app.jobjo.ui.activites;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 6/2/2018.
 */

public class Splash extends AppCompatActivity implements View.OnClickListener {

    private Unbinder unbinder;
    @BindView(R.id.txtGoal)
    TextView txtGoal;
    @BindView(R.id.txtVersion)
    TextView txtVersion;
    @BindView(R.id.txtTitleNotConnection)
    TextView txtTitleNotConnection;
    @BindView(R.id.txtTitleTry)
    TextView txtTitleTry;
    @BindView(R.id.containerStateInternet)
    ViewGroup containerStateInternet;
    @BindView(R.id.imgTryConnect)
    ImageView imgTryConnect;

    @Inject
    Font font;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.splash );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        font.titr( txtGoal );
        font.titr( txtTitleNotConnection );
        font.koodak( txtVersion );
        font.koodak( txtTitleTry );

        imgTryConnect.setOnClickListener( this );
        txtTitleTry.setOnClickListener( this );

        new Handler().postDelayed( this::start, 2000 );
    }

    private void start() {
        if ( isNetworkAvailable() ) {
            startActivity( new Intent( Splash.this, BaseActivity.class ) );
            finish();
        } else {
            containerStateInternet.setVisibility( View.VISIBLE );
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = ( ConnectivityManager ) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo
                = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onClick( View v ) {
        switch ( v.getId() ) {
            case R.id.imgTryConnect:
            case R.id.txtTitleTry:
                start();
                break;
        }
    }
}
