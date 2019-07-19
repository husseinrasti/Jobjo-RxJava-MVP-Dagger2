package ir.hosseinrasti.app.jobjo.ui.activites.modify.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 5/29/2018.
 */

public class ModifyProfileActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.txtTitleWorkExperience)
    public TextView txtTitleWorkExperience;
    @BindView(R.id.txtTitleSkill)
    public TextView txtTitleSkill;
    @BindView(R.id.txtTitleInfoPersonDesc)
    public TextView txtTitleInfoPersonDesc;
    @BindView(R.id.txtTitleInfoPersonFav)
    public TextView txtTitleInfoPersonFav;
    @BindView(R.id.txtTitleInfoPerson)
    public TextView txtTitleInfoPerson;
    @BindView(R.id.btnModifySave)
    public ImageView btnModifySave;
    @BindView(R.id.edtModifyWorkExperience)
    public TextView edtModifyWorkExperience;
    @BindView(R.id.edtModifyGender)
    public TextView edtModifyGender;
    @BindView(R.id.edtModifyAge)
    public TextView edtModifyAge;
    @BindView(R.id.edtModifyAccessibility)
    public TextView edtModifyAccessibility;
    @BindView(R.id.edtModifyMarried)
    public TextView edtModifyMarried;
    @BindView(R.id.edtModifyEducation)
    public TextView edtModifyEducation;
    @BindView(R.id.edtModifySchool)
    public TextView edtModifySchool;
    @BindView(R.id.edtModifyTelephone)
    public TextView edtModifyTelephone;
    @BindView(R.id.edtModifyCity)
    public TextView edtModifyCity;
    @BindView(R.id.edtModifyAddress)
    public TextView edtModifyAddress;
    @BindView(R.id.edtModifyProfessionalKnowledge)
    public TextView edtModifyProfessionalKnowledge;
    @BindView(R.id.edtModifyFavorite)
    public TextView edtModifyFavorite;
    @BindView(R.id.edtModifyDescription)
    public TextView edtModifyDescription;
    @BindView(R.id.edtModifyMyExpertise)
    public TextView edtModifyMyExpertise;
    @BindView(R.id.edtModifyFullname)
    public TextView edtModifyFullname;
    @BindView(R.id.edtModifyEmail)
    public TextView edtModifyEmail;

    @BindView(R.id.coordinatorModify)
    public CoordinatorLayout coordinatorModify;
    @BindView(R.id.txtTitleToolbar)
    public TextView txtTitleToolbar;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private UserModel userModel;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ApiService apiService;
    @Inject
    public SessionPref pref;
    @Inject
    Font font;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_modify_profile );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        setFont();

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        txtTitleToolbar.setText( "ویرایش اطلاعات" );

        try {
            Bundle bundle = getIntent().getExtras();
            userModel = ( UserModel ) bundle.getSerializable( "userModel" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        fillUserModel();

        btnModifySave.setOnClickListener( v -> modifyUserModel() );
    }

    private void modifyUserModel() {
        UserModel user = getUserModel();
        disposable.add( apiService.updateUserDetails( pref.getId()
                , user.getMyExpertise()
                , user.getWorkExperience()
                , user.getAge()
                , user.getAccessibility()
                , user.getTelephone()
                , user.getProfessionalKnowledge()
                , user.getDescription()
                , user.getFavorite()
                , user.getEducation()
                , user.getGender()
                , user.getCity()
                , user.getSchool()
                , user.getAddress(),
                user.getMarried() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<UserModel>() {

                    @Override
                    public void onSuccess( UserModel userModel ) {
                        Toast.makeText( ModifyProfileActivity.this
                                , userModel.getErrorMessage(), Toast.LENGTH_SHORT ).show();
                        Intent intent = new Intent();
                        intent.putExtra( "update", true );
                        setResult( RESULT_OK, intent );
                        finish();
                    }

                    @Override
                    public void onError( Throwable e ) {
                        e.printStackTrace();
                        Util.showError( coordinatorModify, e );
                    }
                } ) );
    }

    private void fillUserModel() {
        edtModifyMarried.setText( userModel.getMarried() );
        edtModifyAddress.setText( userModel.getAddress() );
        edtModifyAge.setText( userModel.getAge() );
        edtModifyAccessibility.setText( userModel.getAccessibility() );
        edtModifyWorkExperience.setText( userModel.getWorkExperience() );
        edtModifyCity.setText( userModel.getCity() );
        edtModifyEducation.setText( userModel.getEducation() );
        edtModifyGender.setText( userModel.getGender() );
        edtModifySchool.setText( userModel.getSchool() );
        edtModifyMyExpertise.setText( userModel.getMyExpertise() );
        edtModifyFullname.setText( userModel.getFullname() );
        edtModifyEmail.setText( userModel.getEmail() );
        edtModifyTelephone.setText( userModel.getTelephone() );
        edtModifyFavorite.setText( userModel.getFavorite() );
        edtModifyDescription.setText( userModel.getDescription() );
        edtModifyProfessionalKnowledge.setText( userModel.getProfessionalKnowledge() );
    }

    private UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setFullname( edtModifyFullname.getText().toString() );
        userModel.setEmail( edtModifyEmail.getText().toString() );
        userModel.setAddress( edtModifyAddress.getText().toString() );
        userModel.setMarried( edtModifyMarried.getText().toString() );
        userModel.setAge( edtModifyAge.getText().toString() );
        userModel.setAccessibility( edtModifyAccessibility.getText().toString() );
        userModel.setCity( edtModifyCity.getText().toString() );
        userModel.setEducation( edtModifyEducation.getText().toString() );
        userModel.setMyExpertise( edtModifyMyExpertise.getText().toString() );
        userModel.setWorkExperience( edtModifyWorkExperience.getText().toString() );
        userModel.setGender( edtModifyGender.getText().toString() );
        userModel.setSchool( edtModifySchool.getText().toString() );
        userModel.setTelephone( edtModifyTelephone.getText().toString() );
        userModel.setFavorite( edtModifyFavorite.getText().toString() );
        userModel.setDescription( edtModifyDescription.getText().toString() );
        userModel.setProfessionalKnowledge( edtModifyProfessionalKnowledge.getText().toString() );
        return userModel;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        disposable.clear();
        disposable.dispose();
    }

    private void setFont() {
        font.koodak( txtTitleInfoPerson );
        font.koodak( txtTitleInfoPersonDesc );
        font.koodak( txtTitleInfoPersonFav );
        font.koodak( txtTitleSkill );
        font.koodak( txtTitleWorkExperience );
        font.iran( txtTitleToolbar );
        font.iran( edtModifyAddress );
        font.iran( edtModifyAge );
        font.iran( edtModifyCity );
        font.iran( edtModifyDescription );
        font.iran( edtModifyEducation );
        font.iran( edtModifyFavorite );
        font.iran( edtModifyGender );
        font.iran( edtModifyMarried );
        font.iran( edtModifySchool );
        font.iran( edtModifyTelephone );
        font.iran( edtModifyWorkExperience );
        font.iran( edtModifyAccessibility );
        font.iran( edtModifyEmail );
        font.iran( edtModifyFullname );
        font.iran( edtModifyMyExpertise );
        font.iran( edtModifyProfessionalKnowledge );
    }
}













