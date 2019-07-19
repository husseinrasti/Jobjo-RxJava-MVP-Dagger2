package ir.hosseinrasti.app.jobjo.ui.user.signup;

import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 5/24/2018.
 */

public interface SignupContract {

    interface View extends BaseView<Presenter> {

        String getEmail();

        String getFullname();

        String getUsername();

        String getPassword();

        String getPasswordConfirm();

        void showErrorInputUsername( String message );

        void showErrorInputPassword( String message );

        void showSuccessRegister( String message );

        void showErrorRegister( String message );

        void showProgress();

        void hideProgress();

        EditText getEdtUserName();
    }



    interface Presenter extends BasePresenter<View> {

        void clickedRegister();

        void checkUsername();

        void rxUnsubscribe();
    }
}
