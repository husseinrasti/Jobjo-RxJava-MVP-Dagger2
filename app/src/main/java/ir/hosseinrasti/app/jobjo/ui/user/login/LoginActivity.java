package ir.hosseinrasti.app.jobjo.ui.user.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.activites.BaseActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/26/2018.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View,
        View.OnFocusChangeListener {

    private Unbinder unbinder;

    @BindView(R.id.edtUsernameLogin)
    public EditText edtUsername;
    @BindView(R.id.edtPasswordLogin)
    public EditText edtPassword;
    @BindView(R.id.btnLogin)
    public Button btnLogin;
    @BindView(R.id.btnResetPassword)
    public Button btnResetPassword;
    @BindView(R.id.btnSignupLogin)
    public Button btnSignup;
    @BindView(R.id.txtErrorPasswordLogin)
    public AppCompatTextView txtErrorPassword;
    @BindView(R.id.txtErrorUsernameLogin)
    public AppCompatTextView txtErrorUsername;
    @BindView(R.id.progressBarLogin)
    public ProgressBar progressBar;
    @BindView(R.id.container)
    ConstraintLayout container;

    @Inject
    public LoginContract.Presenter presenter;

    @Inject
    Font font;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        unbinder = ButterKnife.bind( this );

        App.getComponent().inject( this );

        btnLogin.setOnClickListener( v -> presenter.clickedLogin() );
        btnSignup.setOnClickListener( v -> presenter.clickedSignup( LoginActivity.this ) );

        edtPassword.setOnFocusChangeListener( this );
        edtUsername.setOnFocusChangeListener( this );

        font.iran( txtErrorPassword );
        font.iran( txtErrorUsername );
        font.iran( edtUsername );
        font.iran( edtPassword );
        font.iran( btnSignup );
        font.iran( btnResetPassword );
        font.iran( btnLogin );
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView( this );
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        presenter.rxUnsubscribe();
        super.onDestroy();
    }

    @Override
    public String getUsername() {
        return edtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString();
    }

    @Override
    public void showErrorUsername( String message ) {
        txtErrorUsername.setText( message );
        txtErrorUsername.setVisibility( View.VISIBLE );
    }

    @Override
    public void showErrorPassword( String message ) {
        txtErrorPassword.setText( message );
        txtErrorPassword.setVisibility( View.VISIBLE );
    }

    @Override
    public void showSuccessLogin( String message ) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
        startActivity( new Intent( this, BaseActivity.class ) );
        finish();
    }

    @Override
    public void showErrorLogin( String message ) {
        Snackbar snackbar = Snackbar.make( container, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
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
    public void onFocusChange( View v, boolean hasFocus ) {
        switch ( v.getId() ) {
            case R.id.txtErrorPasswordLogin:
                if ( txtErrorPassword.getVisibility() == View.VISIBLE ) {
                    txtErrorPassword.setVisibility( View.GONE );
                }
                break;
            case R.id.txtErrorUsernameLogin:
                if ( txtErrorUsername.getVisibility() == View.VISIBLE ) {
                    txtErrorUsername.setVisibility( View.GONE );
                }
                break;
        }
    }
}
