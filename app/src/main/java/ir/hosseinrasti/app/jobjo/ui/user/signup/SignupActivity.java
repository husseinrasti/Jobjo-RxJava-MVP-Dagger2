package ir.hosseinrasti.app.jobjo.ui.user.signup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/26/2018.
 */

public class SignupActivity extends AppCompatActivity implements SignupContract.View, View.OnFocusChangeListener {

    private Unbinder unbinder;

    @BindView(R.id.container)
    ScrollView container;

    @BindView(R.id.edtUsernameSignup)
    public EditText edtUsernameSignup;
    @BindView(R.id.txtErrorUsernameSignup)
    public AppCompatTextView txtErrorUsernameSignup;
    @BindView(R.id.edtEmailSignup)
    public EditText edtEmailSignup;
    @BindView(R.id.edtFullnameSignup)
    public EditText edtFullnameSignup;
    @BindView(R.id.edtPasswordSignup)
    public EditText edtPasswordSignup;
    @BindView(R.id.edtPasswordConfirmSignup)
    public EditText edtPasswordConfirmSignup;
    @BindView(R.id.txtErrorPasswordSignup)
    public AppCompatTextView txtErrorPasswordSignup;
    @BindView(R.id.btnRegister)
    public Button btnRegister;
    @BindView(R.id.btnLoginSignup)
    public Button btnLoginSignup;
    @BindView(R.id.progressBarSignup)
    public ProgressBar progressBarSignup;

    @Inject
    public SignupContract.Presenter presenter;
    @Inject
    Font font;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );
        App.getComponent().inject( this );
        unbinder = ButterKnife.bind( this );
        presenter.takeView( this );

        btnRegister.setOnClickListener( v -> presenter.clickedRegister() );
        btnLoginSignup.setOnClickListener( v -> finish() );

        presenter.checkUsername();

        edtUsernameSignup.setOnFocusChangeListener( this );
        edtPasswordSignup.setOnFocusChangeListener( this );

        font.iran( edtEmailSignup );
        font.iran( edtFullnameSignup );
        font.iran( edtPasswordConfirmSignup );
        font.iran( edtPasswordSignup );
        font.iran( edtUsernameSignup );
        font.iran( txtErrorPasswordSignup );
        font.iran( txtErrorUsernameSignup );
        font.iran( btnRegister );
        font.iran( btnLoginSignup );
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        presenter.rxUnsubscribe();
        presenter.dropView();
        super.onDestroy();
    }

    @Override
    public String getEmail() {
        return edtEmailSignup.getText().toString();
    }

    @Override
    public String getFullname() {
        return edtFullnameSignup.getText().toString();
    }

    @Override
    public String getUsername() {
        return edtUsernameSignup.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPasswordSignup.getText().toString();
    }

    @Override
    public String getPasswordConfirm() {
        return edtPasswordConfirmSignup.getText().toString();
    }

    @Override
    public void showErrorInputUsername( String message ) {
        txtErrorUsernameSignup.setVisibility( View.VISIBLE );
        txtErrorUsernameSignup.setText( message );
    }

    @Override
    public void showErrorInputPassword( String message ) {
        txtErrorPasswordSignup.setVisibility( View.VISIBLE );
        txtErrorPasswordSignup.setText( message );
    }

    @Override
    public void showSuccessRegister( String message ) {
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
        finish();
    }

    @Override
    public void showErrorRegister( String message ) {
        showSnack( message );
    }

    @Override
    public void showProgress() {
        progressBarSignup.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        progressBarSignup.setVisibility( View.GONE );
    }

    @Override
    public EditText getEdtUserName() {
        return edtUsernameSignup;
    }

    private void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( container, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void onFocusChange( View v, boolean hasFocus ) {
        switch ( v.getId() ) {
            case R.id.txtErrorPasswordSignup:
                if ( txtErrorPasswordSignup.getVisibility() == View.VISIBLE ) {
                    txtErrorPasswordSignup.setVisibility( View.GONE );
                }
                break;
            case R.id.txtErrorUsernameSignup:
                if ( txtErrorUsernameSignup.getVisibility() == View.VISIBLE ) {
                    txtErrorUsernameSignup.setVisibility( View.GONE );
                }
                break;
        }
    }
}
