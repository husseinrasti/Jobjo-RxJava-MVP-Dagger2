package ir.hosseinrasti.app.jobjo.ui.user.profile;

import android.app.Activity;
import android.net.Uri;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 5/20/2018.
 */

public interface ProfileContract {


    interface View extends BaseView<Presenter> {

        void setUserProfile( UserModel userModel );

        void showThumbnail( Uri selectedImage );

        void showThumbnail( String url );

        void uploadCompleted();

        void setUploadProgress( int progress );

        void showSnack( String message );

        void showProgressUpload();

        void showProgress();

        void hideProgress();
    }



    interface Presenter extends BasePresenter<View> {

        UserModel getUserModel();

        void savePicUser();

        void fetchUserModel();

        void rxUnsubscribe();

        void uploadImage( Uri selectedImage );

        void uploadImageWithoutShowProgress( Uri selectedImage );

        void logout( Activity activity );

    }

}
