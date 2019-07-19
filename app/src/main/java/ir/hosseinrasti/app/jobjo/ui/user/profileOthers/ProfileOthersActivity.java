package ir.hosseinrasti.app.jobjo.ui.user.profileOthers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.utils.CircleImageView;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/27/2018.
 */

public class ProfileOthersActivity extends AppCompatActivity implements ProfileOthersContract.View {

    @BindView(R.id.container)
    public CoordinatorLayout container;
    @BindView(R.id.imgProfile)
    public CircleImageView imgProfile;
    @BindView(R.id.txtProfileFullname)
    public TextView txtProfileFullname;
    @BindView(R.id.txtProfileMyExpertise)
    public TextView txtProfileMyExpertise;
    @BindView(R.id.txtProfileAccess)
    public TextView txtProfileAccess;
    @BindView(R.id.txtProfileAge)
    public TextView txtProfileAge;
    @BindView(R.id.txtTitleProfileAge)
    public TextView txtTitleProfileAge;
    @BindView(R.id.txtTitleProfileEducation)
    public TextView txtTitleProfileEducation;
    @BindView(R.id.txtProfileEducation)
    public TextView txtProfileEducation;
    @BindView(R.id.txtTitleProfileSchool)
    public TextView txtTitleProfileSchool;
    @BindView(R.id.txtProfileSchool)
    public TextView txtProfileSchool;
    @BindView(R.id.txtProfileWorkExperience)
    public TextView txtProfileWorkExperience;
    @BindView(R.id.txtTitleWorkExperience)
    public TextView txtTitleWorkExperience;
    @BindView(R.id.txtTitleSkill)
    public TextView txtTitleSkill;
    @BindView(R.id.txtProfileSkill)
    public TextView txtProfileSkill;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.progressBarServer)
    public ProgressBar progressBarServer;

    private Unbinder unbinder;

    @Inject
    ProfileOthersContract.Presenter presenter;
    @Inject
    Font font;

    private ActionBar actionBar;
    private long idUser;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_others );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );
        presenter.takeView( this );

        setSupportActionBar( toolbar );
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );
        actionBar.setTitle( getApplicationContext().getString( R.string.title_profile ) );

        setFont();

        try {
            Bundle bundle = getIntent().getExtras();
            idUser = bundle.getLong( "idUser" );
        } catch ( NullPointerException e ) {
            e.printStackTrace();
            showSnack( "هیچ اطلاعاتی از مرکز دریافت نشد!" );
        }
        presenter.fetchUserModel( idUser );
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        presenter.dropView();
        presenter.rxUnsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void setUserProfile( UserModel userModel ) {
        actionBar.setTitle( getApplicationContext().getString( R.string.title_profile ) + " " + userModel.getFullname() );
        txtProfileFullname.setText( userModel.getFullname() );
        txtProfileMyExpertise.setText( userModel.getMyExpertise() );
        txtProfileAccess.setText( userModel.getAccessibility() );
        txtProfileAge.setText( userModel.getAge() );
        txtProfileEducation.setText( userModel.getEducation() );
        txtProfileSchool.setText( userModel.getSchool() );
        txtProfileWorkExperience.setText( userModel.getWorkExperience() );
        txtProfileSkill.setText( userModel.getProfessionalKnowledge() );
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
    public void showProgress() {
        progressBarServer.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        progressBarServer.setVisibility( View.GONE );
    }

    @Override
    public void showThumbnail( String url ) {
        if ( !url.isEmpty() ) {
            Picasso.with( this )
                    .load( url )
                    .into( imgProfile );
        }
    }

    public void setFont() {
        font.titr( txtProfileFullname );
        font.iran( txtProfileAccess );
        font.iran( txtProfileMyExpertise );
        font.koodak( txtTitleSkill );
        font.koodak( txtTitleWorkExperience );
        font.iran( txtProfileAge );
        font.iran( txtProfileEducation );
        font.iran( txtProfileSchool );
        font.iran( txtProfileSkill );
        font.iran( txtProfileWorkExperience );
        font.yekan( txtTitleProfileAge );
        font.yekan( txtTitleProfileEducation );
        font.yekan( txtTitleProfileSchool );
    }
}
