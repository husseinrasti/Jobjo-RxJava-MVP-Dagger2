package ir.hosseinrasti.app.jobjo.ui.user.profileOthers;

import android.app.Activity;
import android.net.Uri;

import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 5/20/2018.
 */

public interface ProfileOthersContract {


    interface View extends BaseView<Presenter> {

        void setUserProfile( UserModel userModel );

        void showThumbnail( String url );

        void showSnack( String message );

        void showProgress();

        void hideProgress();
    }



    interface Presenter extends BasePresenter<View> {

        void fetchUserModel(long idUser );

        void rxUnsubscribe();

    }

}
