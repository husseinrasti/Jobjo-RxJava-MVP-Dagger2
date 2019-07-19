package ir.hosseinrasti.app.jobjo.ui.activites.modify.job;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.ui.adapter.CategoryAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemCategoryClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Hossein on 5/27/2018.
 */

public class ModifyJobActivity extends AppCompatActivity implements
        OnItemCategoryClickListener,
        EasyPermissions.PermissionCallbacks,
        ModifyJobContract.View {

    private static final int PICK_IMAGE = 100;
    private static final int RC_READ_FILE = 999;

    private Unbinder unbinder;

    @BindView(R.id.txtTitleIntroduceJob)
    public TextView txtTitleIntroduceJob;
    @BindView(R.id.txtTitleJobWordGroup)
    public TextView txtTitleJobWordGroup;
    @BindView(R.id.txtTitleInfoSkillJob)
    public TextView txtTitleInfoSkillJob;
    @BindView(R.id.txtTitleSkillJob)
    public TextView txtTitleSkillJob;
    @BindView(R.id.txtTitleSoftware)
    public TextView txtTitleSoftware;
    @BindView(R.id.txtTitleLanguage)
    public TextView txtTitleLanguage;
    @BindView(R.id.txtTitleDescJob)
    public TextView txtTitleDescJob;
    @BindView(R.id.imgModifyJobPic)
    public ImageView imgModifyJobPic;
    @BindView(R.id.imgUploadImageJob)
    public ImageView imgUploadImageJob;
    @BindView(R.id.edtModifyJobName)
    public EditText edtModifyJobName;
    @BindView(R.id.edtModifyJobAddress)
    public EditText edtModifyJobAddress;
    @BindView(R.id.txtModifyJobWorkGroup)
    public TextView txtModifyJobWorkGroup;
    @BindView(R.id.edtModifyJobTypeOfJob)
    public EditText edtModifyJobTypeOfJob;
    @BindView(R.id.edtModifyJobHoursOfWork)
    public EditText edtModifyJobHoursOfWork;
    @BindView(R.id.edtModifyJobBenefit)
    public EditText edtModifyJobBenefit;
    @BindView(R.id.edtModifyJobTravelOfWork)
    public EditText edtModifyJobTravelOfWork;
    @BindView(R.id.edtModifyJobAge)
    public EditText edtModifyJobAge;
    @BindView(R.id.edtModifyJobGender)
    public EditText edtModifyJobGender;
    @BindView(R.id.edtModifyJobMilitaryService)
    public EditText edtModifyJobMilitaryService;
    @BindView(R.id.edtModifyJobWorkExperience)
    public EditText edtModifyJobWorkExperience;
    @BindView(R.id.edtModifyJobEducation)
    public EditText edtModifyJobEducation;
    @BindView(R.id.edtModifyJobLanguage)
    public EditText edtModifyJobLanguage;
    @BindView(R.id.edtModifyJobSoftware)
    public EditText edtModifyJobSoftware;
    @BindView(R.id.edtModifyJobProfessionalKnowledge)
    public EditText edtModifyJobProfessionalKnowledge;
    @BindView(R.id.edtModifyJobDescription)
    public EditText edtModifyJobDescription;
    @BindView(R.id.coordinatorModify)
    public CoordinatorLayout coordinatorModify;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.btnModifySave)
    public ImageView btnModifySave;
    @BindView(R.id.txtTitleToolbar)
    public TextView txtTitleToolbar;
    @BindView(R.id.recyclerCategory)
    public RecyclerView recyclerCategory;
    @BindView(R.id.progressBarImageUpload)
    public ProgressBar progressBarImageUpload;

    private List<WorkGroupModel> workGroupList = new ArrayList<>();

    private JobModel jobModel;
    private boolean mustUpdate;

    @Inject
    Font font;
    @Inject
    ModifyJobContract.Presenter presenter;
    @Inject
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_modify_job );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );
        presenter.takeView( this );

        categoryAdapter.setContext( this );
        categoryAdapter.setWorkGroupModels( workGroupList );
        categoryAdapter.setOnItemClickListener( this );
        categoryAdapter.setModify( true );
        categoryAdapter.setFont( font );

        recyclerCategory.setLayoutManager( new GridLayoutManager( this, 2 ) );
        recyclerCategory.setItemAnimator( new DefaultItemAnimator() );
        recyclerCategory.setAdapter( categoryAdapter );

        presenter.fetchAllCategory();

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );

        try {
            Bundle bundle = getIntent().getExtras();
            jobModel = ( JobModel ) bundle.getSerializable( "jobModel" );
            mustUpdate = bundle.getBoolean( "mustUpdate" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        if ( mustUpdate ) {
            txtTitleToolbar.setText( "ویرایش آگهی" );
            fillJobModel( jobModel );
        } else {
            txtTitleToolbar.setText( "ایجاد آگهی جدید" );
            jobModel = new JobModel();
        }

        btnModifySave.setOnClickListener( v -> {
            if ( mustUpdate ) {
                presenter.updateJobModel();
            } else {
                presenter.insertJobModel();
            }

        } );

        imgModifyJobPic.setOnClickListener( v -> selectImage() );
        imgUploadImageJob.setOnClickListener( v -> selectImage() );

        setFont();

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
        if ( requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK ) {
            try {
                presenter.uploadImage( data.getData() );
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public JobModel getJobModel() {
        jobModel.setNameJob( edtModifyJobName.getText().toString() );
        jobModel.setAddress( edtModifyJobAddress.getText().toString() );
        jobModel.setNameWorkGroup( txtModifyJobWorkGroup.getText().toString() );
        jobModel.setTypeOfJob( edtModifyJobTypeOfJob.getText().toString() );
        jobModel.setHoursOfWork( edtModifyJobHoursOfWork.getText().toString() );
        jobModel.setBenefit( edtModifyJobBenefit.getText().toString() );
        jobModel.setTravelOfWork( edtModifyJobTravelOfWork.getText().toString() );
        jobModel.setAge( edtModifyJobAge.getText().toString() );
        jobModel.setGender( edtModifyJobGender.getText().toString() );
        jobModel.setMilitaryService( edtModifyJobMilitaryService.getText().toString() );
        jobModel.setWorkExperience( edtModifyJobWorkExperience.getText().toString() );
        jobModel.setEducation( edtModifyJobEducation.getText().toString() );
        jobModel.setLang( edtModifyJobLanguage.getText().toString() );
        jobModel.setSoftware( edtModifyJobSoftware.getText().toString() );
        jobModel.setProfessionalKnowledge( edtModifyJobProfessionalKnowledge.getText().toString() );
        jobModel.setJobDescription( edtModifyJobDescription.getText().toString() );
        return jobModel;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        presenter.rxUnsubscribe();
        super.onDestroy();
    }

    @Override
    public void notifyAdapter( List<WorkGroupModel> workGroupModels ) {
        workGroupList.clear();
        workGroupList.addAll( workGroupModels );
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showThumbnail( Uri selectedImage ) {
        if ( selectedImage != null ) {
            Picasso.with( this )
                    .load( selectedImage )
                    .into( imgModifyJobPic );
        }
    }

    @Override
    public void showThumbnail( String url ) {
        if ( !url.isEmpty() ) {
            Picasso.with( this )
                    .load( url )
                    .into( imgModifyJobPic );
        }
    }

    @Override
    public void uploadCompleted() {
        progressBarImageUpload.setVisibility( View.GONE );
        presenter.savePicJob();
    }

    @Override
    public void setUploadProgress( int progress ) {
        progressBarImageUpload.setProgress( progress );
    }

    @Override
    public void showProgressUpload() {
        progressBarImageUpload.setVisibility( View.VISIBLE );
    }

    @Override
    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinatorModify, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void fillJobModel( JobModel jobModel ) {
        showThumbnail( jobModel.getPicJob() );
        edtModifyJobName.setText( jobModel.getNameJob() );
        edtModifyJobAddress.setText( jobModel.getAddress() );
        txtModifyJobWorkGroup.setText( jobModel.getNameWorkGroup() );
        edtModifyJobTypeOfJob.setText( jobModel.getTypeOfJob() );
        edtModifyJobHoursOfWork.setText( jobModel.getHoursOfWork() );
        edtModifyJobBenefit.setText( jobModel.getBenefit() );
        edtModifyJobTravelOfWork.setText( jobModel.getTravelOfWork() );
        edtModifyJobAge.setText( jobModel.getAge() );
        edtModifyJobGender.setText( jobModel.getGender() );
        edtModifyJobMilitaryService.setText( jobModel.getMilitaryService() );
        edtModifyJobWorkExperience.setText( jobModel.getWorkExperience() );
        edtModifyJobEducation.setText( jobModel.getEducation() );
        edtModifyJobLanguage.setText( jobModel.getLang() );
        edtModifyJobSoftware.setText( jobModel.getSoftware() );
        edtModifyJobProfessionalKnowledge.setText( jobModel.getProfessionalKnowledge() );
        edtModifyJobDescription.setText( jobModel.getJobDescription() );
    }

    @Override
    public void setFont() {
        font.iran( edtModifyJobAddress );
        font.iran( edtModifyJobAge );
        font.iran( edtModifyJobBenefit );
        font.iran( edtModifyJobDescription );
        font.iran( edtModifyJobEducation );
        font.iran( edtModifyJobGender );
        font.iran( edtModifyJobHoursOfWork );
        font.iran( edtModifyJobLanguage );
        font.iran( edtModifyJobMilitaryService );
        font.iran( edtModifyJobName );
        font.iran( edtModifyJobProfessionalKnowledge );
        font.iran( edtModifyJobSoftware );
        font.iran( edtModifyJobTravelOfWork );
        font.iran( edtModifyJobTypeOfJob );
        font.iran( edtModifyJobWorkExperience );
        font.iran( txtTitleToolbar );
        font.iran( txtModifyJobWorkGroup );
        font.koodak( txtTitleIntroduceJob );
        font.koodak( txtTitleInfoSkillJob );
        font.koodak( txtTitleDescJob );
        font.yekan( txtTitleJobWordGroup );
        font.yekan( txtTitleLanguage );
        font.yekan( txtTitleSkillJob );
        font.yekan( txtTitleSoftware );
    }

    @Override
    public void taskCompleted() {
        Toast.makeText( this, "با موفقیت انجام شد", Toast.LENGTH_SHORT ).show();
        Intent intent = new Intent();
        intent.putExtra( "update", true );
        setResult( RESULT_OK, intent );
        finish();
    }

    @Override
    public void onItemClick( WorkGroupModel workGroupModel, Action action, int color ) {
        jobModel.setIdWorkGroup( workGroupModel.getId() );
        jobModel.setNameWorkGroup( workGroupModel.getNameWorkGroup() );
        txtModifyJobWorkGroup.setText( workGroupModel.getNameWorkGroup() );
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
}
