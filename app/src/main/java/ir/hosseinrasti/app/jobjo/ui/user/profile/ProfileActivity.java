package ir.hosseinrasti.app.jobjo.ui.user.profile;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobListRequestedActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.profile.ModifyProfileActivity;
import ir.hosseinrasti.app.jobjo.ui.user.intruduce.UsersIntroducedActivity;
import ir.hosseinrasti.app.jobjo.utils.CircleImageView;
import ir.hosseinrasti.app.jobjo.utils.Config;
import ir.hosseinrasti.app.jobjo.utils.Font;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Hossein on 5/27/2018.
 */

public class ProfileActivity extends AppCompatActivity implements
        ProfileContract.View, EasyPermissions.PermissionCallbacks {

    private static final int PICK_IMAGE = 100;
    private static final int RC_READ_FILE = 999;

    @BindView(R.id.container)
    public CoordinatorLayout container;
    @BindView(R.id.progressBarImageUpload)
    public ProgressBar progressBarImageUpload;
    @BindView(R.id.progressBarServer)
    public ProgressBar progressBarServer;
    @BindView(R.id.btnProfileModify)
    public Button btnProfileModify;
    @BindView(R.id.imgProfile)
    public CircleImageView imgProfile;
    @BindView(R.id.imgUploadImageProfile)
    public ImageView imgUploadImageProfile;
    @BindView(R.id.txtTitleInfoPerson)
    public TextView txtTitleInfoPerson;
    @BindView(R.id.txtTitleInfoPersonFav)
    public TextView txtTitleInfoPersonFav;
    @BindView(R.id.txtTitleInfoPersonDesc)
    public TextView txtTitleInfoPersonDesc;
    @BindView(R.id.txtProfileFullname)
    public TextView txtProfileFullname;
    @BindView(R.id.txtProfileMyExpertise)
    public TextView txtProfileMyExpertise;
    @BindView(R.id.txtProfileAccess)
    public TextView txtProfileAccess;
    @BindView(R.id.txtProfileGender)
    public TextView txtProfileGender;
    @BindView(R.id.txtTitleProfileGender)
    public TextView txtTitleProfileGender;
    @BindView(R.id.txtProfileAge)
    public TextView txtProfileAge;
    @BindView(R.id.txtTitleProfileAge)
    public TextView txtTitleProfileAge;
    @BindView(R.id.txtTitleProfileMarried)
    public TextView txtTitleProfileMarried;
    @BindView(R.id.txtProfileMarried)
    public TextView txtProfileMarried;
    @BindView(R.id.txtTitleProfileEducation)
    public TextView txtTitleProfileEducation;
    @BindView(R.id.txtProfileEducation)
    public TextView txtProfileEducation;
    @BindView(R.id.txtTitleProfileSchool)
    public TextView txtTitleProfileSchool;
    @BindView(R.id.txtProfileSchool)
    public TextView txtProfileSchool;
    @BindView(R.id.txtTitleProfileTelephone)
    public TextView txtTitleProfileTelephone;
    @BindView(R.id.txtProfileTelephone)
    public TextView txtProfileTelephone;
    @BindView(R.id.txtTitleProfileCity)
    public TextView txtTitleProfileCity;
    @BindView(R.id.txtProfileCity)
    public TextView txtProfileCity;
    @BindView(R.id.txtTitleProfileAddress)
    public TextView txtTitleProfileAddress;
    @BindView(R.id.txtProfileAddress)
    public TextView txtProfileAddress;
    @BindView(R.id.txtProfileWorkExperience)
    public TextView txtProfileWorkExperience;
    @BindView(R.id.txtTitleWorkExperience)
    public TextView txtTitleWorkExperience;
    @BindView(R.id.txtTitleSkill)
    public TextView txtTitleSkill;
    @BindView(R.id.txtProfileSkill)
    public TextView txtProfileSkill;
    @BindView(R.id.txtProfileFavorite)
    public TextView txtProfileFavorite;
    @BindView(R.id.txtProfileDescription)
    public TextView txtProfileDescription;
    @BindView(R.id.txtButtonIntroduced)
    public TextView txtButtonIntroduced;
    @BindView(R.id.txtButtonJobRequested)
    public TextView txtButtonJobRequested;
    @BindView(R.id.includeInfoPerson)
    public ViewGroup includeInfoPerson;
    @BindView(R.id.imgLogout)
    public ImageView imgLogout;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private Unbinder unbinder;

    @Inject
    ProfileContract.Presenter presenter;
    @Inject
    Font font;

    private boolean mustUpdatePic;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );
        presenter.takeView( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( getApplicationContext().getString( R.string.title_profile ) );

        setFont();

        try {
            presenter.fetchUserModel();
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }
        btnProfileModify.setOnClickListener( ( View v ) -> {
            if ( presenter.getUserModel() == null ) {
                showSnack( "اطلاعاتی برای ویرایش وجود ندارد" );
                return;
            }
            Intent intent = new Intent( ProfileActivity.this, ModifyProfileActivity.class );
            intent.putExtra( "userModel", presenter.getUserModel() );
            startActivityForResult( intent, Config.RESULT_UPDATE );
        } );

        imgProfile.setOnClickListener( v -> {
            selectImage();
        } );
        imgUploadImageProfile.setOnClickListener( v -> {
            selectImage();
        } );
        imgLogout.setOnClickListener( v -> {
            presenter.logout( this );
            finish();
        } );

        txtButtonJobRequested.setOnClickListener( v -> {
            startActivity( new Intent( ProfileActivity.this, JobListRequestedActivity.class ) );
        } );
        txtButtonIntroduced.setOnClickListener( v -> {
            startActivity( new Intent( ProfileActivity.this, UsersIntroducedActivity.class ) );
        } );

        methodRequiresReadPermission();
    }

    private void selectImage() {
        Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        intent.setType( "image/*" );
        startActivityForResult( Intent.createChooser( intent, getString( R.string.select_image ) ), PICK_IMAGE );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if ( requestCode == PICK_IMAGE && resultCode == RESULT_OK ) {
            try {
                presenter.uploadImage( data.getData() );
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
        if ( requestCode == Config.RESULT_UPDATE && resultCode == RESULT_OK ) {
            try {
                boolean isUpdate = data.getExtras().getBoolean( "update" );
                if ( isUpdate ) {
                    presenter.fetchUserModel();
                }
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if ( mustUpdatePic ) {
            Intent intent = new Intent();
            intent.putExtra( "update", true );
            setResult( RESULT_OK );
        }
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
        showThumbnail( userModel.getPicUser() );
        txtProfileFullname.setText( userModel.getFullname() );
        txtProfileMyExpertise.setText( userModel.getMyExpertise() );
        txtProfileAccess.setText( userModel.getAccessibility() );
        txtProfileMarried.setText( userModel.getMarried() );
        txtProfileAddress.setText( userModel.getAddress() );
        txtProfileAge.setText( userModel.getAge() );
        txtProfileCity.setText( userModel.getCity() );
        txtProfileEducation.setText( userModel.getEducation() );
        txtProfileGender.setText( userModel.getGender() );
        txtProfileSchool.setText( userModel.getSchool() );
        txtProfileTelephone.setText( userModel.getTelephone() );
        txtProfileWorkExperience.setText( userModel.getWorkExperience() );
        txtProfileFavorite.setText( userModel.getFavorite() );
        txtProfileDescription.setText( userModel.getDescription() );
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
    public void showThumbnail( Uri selectedImage ) {
        if ( selectedImage != null ) {
            Picasso.with( this )
                    .load( selectedImage )
                    .into( imgProfile );
        }
    }

    @Override
    public void showThumbnail( String url ) {
        if ( !url.isEmpty() ) {
            Picasso.with( this )
                    .load( url )
                    .into( imgProfile );
        }
    }

    @Override
    public void uploadCompleted() {
        progressBarImageUpload.setVisibility( View.GONE );
        imgUploadImageProfile.setVisibility( View.VISIBLE );
        presenter.savePicUser();
        hideProgress();
        mustUpdatePic = true;
    }

    @Override
    public void setUploadProgress( int progress ) {
        progressBarImageUpload.setProgress( progress );
    }

    @Override
    public void showProgressUpload() {
        imgUploadImageProfile.setVisibility( View.GONE );
        progressBarImageUpload.setVisibility( View.VISIBLE );
    }

    @Override
    public void showProgress() {
        progressBarServer.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        progressBarServer.setVisibility( View.GONE );
    }


    @AfterPermissionGranted(RC_READ_FILE)
    private void methodRequiresReadPermission() {
        String[] perms = { Manifest.permission.READ_EXTERNAL_STORAGE };
        if ( !EasyPermissions.hasPermissions( this, perms ) ) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions( this, getString( R.string.rationale_read_storage ),
                    RC_READ_FILE, perms );
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode,
                                            @NonNull String[] permissions,
                                            @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        EasyPermissions.onRequestPermissionsResult( requestCode, permissions, grantResults, this );
    }

    @Override
    public void onPermissionsDenied( int requestCode, @NonNull List<String> perms ) {
        finish();
    }

    @Override
    public void onPermissionsGranted( int i, @NonNull List<String> list ) {
    }

    private void setFont() {
        font.titr( txtProfileFullname );
        font.iran( txtProfileAccess );
        font.iran( txtProfileMyExpertise );
        font.koodak( txtTitleInfoPerson );
        font.koodak( txtTitleInfoPersonDesc );
        font.koodak( txtTitleInfoPersonFav );
        font.koodak( txtTitleSkill );
        font.koodak( txtTitleWorkExperience );
        font.iran( txtProfileAddress );
        font.iran( txtProfileAge );
        font.iran( txtProfileCity );
        font.iran( txtProfileDescription );
        font.iran( txtProfileEducation );
        font.iran( txtProfileFavorite );
        font.iran( txtProfileGender );
        font.iran( txtProfileMarried );
        font.iran( txtProfileSchool );
        font.iran( txtProfileTelephone );
        font.iran( txtProfileSkill );
        font.iran( txtProfileWorkExperience );
        font.yekan( txtTitleProfileAddress );
        font.yekan( txtTitleProfileAge );
        font.yekan( txtTitleProfileCity );
        font.yekan( txtTitleProfileEducation );
        font.yekan( txtTitleProfileGender );
        font.yekan( txtTitleProfileMarried );
        font.yekan( txtTitleProfileSchool );
        font.yekan( txtTitleProfileTelephone );
        font.titr( txtButtonIntroduced );
        font.titr( txtButtonJobRequested );
    }
}
