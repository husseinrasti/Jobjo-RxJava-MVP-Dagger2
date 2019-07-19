package ir.hosseinrasti.app.jobjo.ui.activites.modify.job;

import android.net.Uri;

import java.util.List;

import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 7/18/2018.
 */

public interface ModifyJobContract {

    interface View extends BaseView<Presenter> {

        void notifyAdapter( List<WorkGroupModel> workGroupModels );

        void showThumbnail( Uri selectedImage );

        void showThumbnail( String url );

        void uploadCompleted();

        void setUploadProgress( int progress );

        void showProgressUpload();

        void showSnack( String message );


        void fillJobModel( JobModel jobModel );

        JobModel getJobModel();

        void setFont();

        void taskCompleted();
    }



    interface Presenter extends BasePresenter<View> {

        void fetchAllCategory();

        void rxUnsubscribe();

        void uploadImage( Uri selectedImage );

        void savePicJob();

        void updateJobModel();

        void insertJobModel();
    }
}
