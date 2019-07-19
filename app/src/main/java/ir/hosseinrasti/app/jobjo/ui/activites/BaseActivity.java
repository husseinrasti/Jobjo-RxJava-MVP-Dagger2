package ir.hosseinrasti.app.jobjo.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.StartupActivity;
import ir.hosseinrasti.app.jobjo.ui.user.login.LoginActivity;

public class BaseActivity extends AppCompatActivity {

    @Inject
    public SessionPref pref;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        ( ( App ) getApplicationContext() ).getComponent().inject( this );

        if ( !pref.isLoggedIn() ) {
            startActivity( new Intent( BaseActivity.this, LoginActivity.class ) );
            finish();
        } else {
            startActivity( new Intent( BaseActivity.this, StartupActivity.class ) );
            finish();
        }
    }

}
