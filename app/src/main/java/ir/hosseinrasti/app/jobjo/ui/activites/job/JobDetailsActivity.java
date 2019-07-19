package ir.hosseinrasti.app.jobjo.ui.activites.job;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/27/2018.
 */

public class JobDetailsActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.imgSheetJobPic)
    public ImageView imgSheetJobPic;
    @BindView(R.id.txtSheetJobNameCreator)
    public TextView txtSheetJobNameCreator;
    @BindView(R.id.txtSheetJobName)
    public TextView txtSheetJobName;
    @BindView(R.id.txtSheetJobAddress)
    public TextView txtSheetJobAddress;
    @BindView(R.id.txtSheetJobWorkGroup)
    public TextView txtSheetJobWorkGroup;
    @BindView(R.id.txtSheetTypeOfJob)
    public TextView txtSheetTypeOfJob;
    @BindView(R.id.txtSheetHoursOfWork)
    public TextView txtSheetHoursOfWork;
    @BindView(R.id.txtSheetBenefit)
    public TextView txtSheetBenefit;
    @BindView(R.id.txtSheetTravelOfWork)
    public TextView txtSheetTravelOfWork;
    @BindView(R.id.txtSheetAge)
    public TextView txtSheetAge;
    @BindView(R.id.txtSheetGender)
    public TextView txtSheetGender;
    @BindView(R.id.txtSheetMilitaryService)
    public TextView txtSheetMilitaryService;
    @BindView(R.id.txtSheetWorkExperience)
    public TextView txtSheetWorkExperience;
    @BindView(R.id.txtSheetEducation)
    public TextView txtSheetEducation;
    @BindView(R.id.txtSheetLanguage)
    public TextView txtSheetLanguage;
    @BindView(R.id.txtSheetSoftware)
    public TextView txtSheetSoftware;
    @BindView(R.id.txtSheetProfessionalKnowledge)
    public TextView txtSheetProfessionalKnowledge;
    @BindView(R.id.txtSheetJobDescription)
    public TextView txtSheetJobDescription;
    @BindView(R.id.txtTitleIntroduceJob)
    public TextView txtTitleIntroduceJob;
    @BindView(R.id.txtTitleDescJob)
    public TextView txtTitleDescJob;
    @BindView(R.id.txtTitleSkill)
    public TextView txtTitleSkill;
    @BindView(R.id.txtTitleSoftware)
    public TextView txtTitleSoftware;
    @BindView(R.id.txtTitleLanguage)
    public TextView txtTitleLanguage;
    @BindView(R.id.txtTitleSkillJob)
    public TextView txtTitleSkillJob;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.userJobApply)
    public LinearLayout userJobApply;
    @BindView(R.id.userJobComment)
    public LinearLayout userJobComment;
    @BindView(R.id.userJobShare)
    public LinearLayout userJobShare;
    @BindView(R.id.container)
    public LinearLayoutCompat container;
    @BindView(R.id.txtUserJobApply)
    public TextView txtUserJobApply;
    @BindView(R.id.txtUserJobComment)
    public TextView txtUserJobComment;
    @BindView(R.id.txtUserJobShare)
    public TextView txtUserJobShare;

    private JobModel jobModel;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    Font font;
    @Inject
    DataSource.Home repository;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_job_details );
        unbinder = ButterKnife.bind( this );
        App.getComponent().inject( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );

        try {
            Bundle bundle = getIntent().getExtras();
            if ( bundle != null ) {
                jobModel = ( JobModel ) bundle.getSerializable( "job" );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        setFont();
        if ( jobModel != null ) {
            fillView( jobModel );
            if ( jobModel.getIdUserCreator() == repository.getIdCurrentUser() ) {
                userJobApply.setVisibility( View.GONE );
            }
        }

        userJobApply.setOnClickListener( v -> {
            applyJob();
        } );
        userJobComment.setOnClickListener( v -> {
            Intent intent = new Intent( this, CommentActivity.class );
            intent.putExtra( "job",  jobModel );
            startActivity( intent );
        } );
        userJobShare.setOnClickListener( v -> {

        } );
    }

    private void applyJob() {
        disposable.add( repository.insertJobApply( jobModel.getId(), jobModel.getIdUserCreator() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel JobModels ) {
                        showSnack( JobModels.getErrorMessage() );
                    }

                    @Override
                    public void onError( Throwable e ) {
                        showSnack( "برقراری ارتباط ممکن نیست!" );
                    }
                } )
        );
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( container, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    private void fillView( JobModel jobModel ) {
        Picasso.with( this ).load( jobModel.getPicJob() ).into( imgSheetJobPic );
        txtSheetJobNameCreator.setText( jobModel.getNameJobCreator() );
        txtSheetJobName.setText( jobModel.getNameJob() );
        txtSheetJobAddress.setText( jobModel.getAddress() );
        txtSheetJobWorkGroup.setText( txtSheetJobWorkGroup.getText().toString() + " " + jobModel.getNameWorkGroup() );
        txtSheetTypeOfJob.setText( txtSheetTypeOfJob.getText().toString() + " " + jobModel.getTypeOfJob() );
        txtSheetHoursOfWork.setText( txtSheetHoursOfWork.getText().toString() + " " + jobModel.getHoursOfWork() );
        txtSheetBenefit.setText( txtSheetBenefit.getText().toString() + " " + jobModel.getBenefit() );
        txtSheetTravelOfWork.setText( txtSheetTravelOfWork.getText().toString() + " " + jobModel.getTravelOfWork() );
        txtSheetAge.setText( txtSheetAge.getText().toString() + " " + jobModel.getAge() );
        txtSheetGender.setText( txtSheetGender.getText().toString() + " " + jobModel.getGender() );
        txtSheetMilitaryService.setText( txtSheetMilitaryService.getText().toString() + " " + jobModel.getMilitaryService() );
        txtSheetWorkExperience.setText( txtSheetWorkExperience.getText().toString() + " " + jobModel.getWorkExperience() );
        txtSheetEducation.setText( txtSheetEducation.getText().toString() + " " + jobModel.getEducation() );
        txtSheetLanguage.setText( jobModel.getLang() );
        txtSheetSoftware.setText( jobModel.getSoftware() );
        txtSheetProfessionalKnowledge.setText( jobModel.getProfessionalKnowledge() );
        txtSheetJobDescription.setText( jobModel.getJobDescription() );
    }

    private void setFont() {
        font.iran( txtUserJobApply );
        font.iran( txtUserJobComment );
        font.iran( txtUserJobShare );
        font.iran( txtSheetJobNameCreator );
        font.iran( txtSheetJobWorkGroup );
        font.iran( txtSheetJobDescription );
        font.iran( txtSheetJobAddress );
        font.iran( txtSheetJobName );
        font.iran( txtSheetAge );
        font.iran( txtSheetBenefit );
        font.iran( txtSheetEducation );
        font.iran( txtSheetGender );
        font.iran( txtSheetHoursOfWork );
        font.iran( txtSheetLanguage );
        font.iran( txtSheetMilitaryService );
        font.iran( txtSheetProfessionalKnowledge );
        font.iran( txtSheetSoftware );
        font.iran( txtSheetTravelOfWork );
        font.iran( txtSheetTypeOfJob );
        font.iran( txtSheetWorkExperience );
        font.koodak( txtTitleDescJob );
        font.koodak( txtTitleIntroduceJob );
        font.koodak( txtTitleSkillJob );
        font.yekan( txtTitleLanguage );
        font.yekan( txtTitleSkill );
        font.yekan( txtTitleSoftware );
    }
}
















