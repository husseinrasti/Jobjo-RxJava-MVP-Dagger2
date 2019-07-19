package ir.hosseinrasti.app.jobjo.ui.user.login;

import android.app.Activity;
import android.content.Intent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.repository.LoginRepository;
import ir.hosseinrasti.app.jobjo.ui.user.signup.SignupActivity;

/**
 * Created by Hossein on 5/24/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private DataSource.Login repository;
    private LoginContract.View view;
    private Disposable subscription = null;

    public LoginPresenter( DataSource.Login repository ) {
        this.repository = repository;
    }

    @Override
    public void setView( LoginContract.View view ) {
        this.view = view;
    }

    @Override
    public void clickedLogin() {
        if ( view != null ) {
            final String username = view.getUsername();
            if ( username.isEmpty() ) {
                view.showErrorUsername( "فیلد نام کاربری خالی است!" );
                return;
            }
            final String password = view.getPassword();
            if ( password.isEmpty() ) {
                view.showErrorPassword( "فیلد رمز خالی است!" );
                return;
            }
            view.showProgress();
            UserModel userModel = new UserModel();
            userModel.setUsername( username );
            userModel.setPassword( password );
            subscription = repository.getUserModelFromNetwork( userModel )
                    .subscribeOn( Schedulers.io() )
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribeWith( new DisposableSingleObserver<UserModel>() {

                        @Override
                        public void onSuccess( UserModel userModel ) {
                            if ( !userModel.isError() ) {
                                view.hideProgress();
                                repository.saveSession( userModel );
                                view.showSuccessLogin( userModel.getErrorMessage() );
                                view.hideProgress();
                            } else {
                                view.hideProgress();
                                view.showErrorLogin( userModel.getErrorMessage() );
                            }
                        }

                        @Override
                        public void onError( Throwable e ) {
                            view.showErrorLogin( "خطا! اتصال اینترنت را بررسی کنید." );
                        }
                    } );
        }
    }

    @Override
    public void clickedSignup( Activity activity ) {
        Intent intent = new Intent( activity, SignupActivity.class );
        activity.startActivity( intent );
    }

    @Override
    public void rxUnsubscribe() {
        if ( subscription != null ) {
            if ( !subscription.isDisposed() ) {
                subscription.dispose();
            }
        }
    }
}
