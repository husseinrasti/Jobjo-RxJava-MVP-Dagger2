package ir.hosseinrasti.app.jobjo.ui.user.login;

import android.app.Activity;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;

/**
 * Created by Hossein on 5/20/2018.
 */

public interface LoginContract {

    interface View {

        String getUsername();

        String getPassword();

        void showErrorUsername( String message );

        void showErrorPassword( String message );

        void showSuccessLogin( String message );

        void showErrorLogin( String message );

        void showProgress();

        void hideProgress();
    }



    interface Presenter {

        void setView( LoginContract.View view );

        void clickedLogin();

        void clickedSignup( Activity activity );

        void rxUnsubscribe();
    }



    interface Model {

        void saveSession( UserModel userModel );

        Single<UserModel> getUserFromNetwork( UserModel userModel);

    }
}
