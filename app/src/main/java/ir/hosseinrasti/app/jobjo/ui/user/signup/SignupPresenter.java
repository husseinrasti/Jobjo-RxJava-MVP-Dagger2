package ir.hosseinrasti.app.jobjo.ui.user.signup;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.repository.SignupRepository;

/**
 * Created by Hossein on 5/24/2018.
 */

public class SignupPresenter implements SignupContract.Presenter {

    private DataSource.Signup repository;
    private SignupContract.View view;
    private CompositeDisposable subscription = new CompositeDisposable();

    private boolean isUserAvailable = false;

    public SignupPresenter( DataSource.Signup repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( SignupContract.View view ) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void rxUnsubscribe() {
        subscription.clear();
        subscription.dispose();
    }

    @Override
    public void checkUsername() {
        subscription.add( RxTextView.textChangeEvents( view.getEdtUserName() )
                .skipInitialValue()
                .debounce( 500, TimeUnit.MILLISECONDS )
                .distinctUntilChanged()
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( search() ) );
    }

    private DisposableObserver<TextViewTextChangeEvent> search() {
        return new DisposableObserver<TextViewTextChangeEvent>() {

            @Override
            public void onNext( TextViewTextChangeEvent textViewTextChangeEvent ) {
                repository.isUsernameAvailable( textViewTextChangeEvent.text().toString() )
                        .subscribeOn( Schedulers.newThread() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<UserModel>() {

                            @Override
                            public void onSuccess( UserModel userModel ) {
                                if ( userModel.isError() ) {
                                    view.showErrorInputUsername( userModel.getErrorMessage() );
                                    isUserAvailable = true;
                                } else {
                                    view.showErrorInputUsername( userModel.getErrorMessage() );
                                    isUserAvailable = false;
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                e.printStackTrace();
                                view.showErrorRegister( "خطا! اتصال اینترنت را بررسی کنید." );
                            }
                        } );
            }

            @Override
            public void onError( Throwable e ) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void clickedRegister() {
        if ( view != null ) {
            if ( isUserAvailable ) {
                return;
            }
            String username = view.getUsername();
            String email = view.getEmail();
            String fullname = view.getFullname();
            String password = view.getPassword();
            String passwordConfirm = view.getPasswordConfirm();
            if ( email.isEmpty() ) {
                view.showErrorRegister( "لطفا ایمیل را وارد کنید." );
                return;
            }
            if ( fullname.isEmpty() ) {
                view.showErrorRegister( "لطفا نام کامل خودتان را وارد کنید." );
                return;
            }
            if ( password.isEmpty() ) {
                view.showErrorInputPassword( "لطفا رمز را وارد کنید." );
                return;
            }
            if ( password.length() <= 5 ) {
                view.showErrorInputPassword( "لطفا برای رمز حداقل 6 حرف استفاده کنید." );
                return;
            }
            if ( !password.equals( passwordConfirm ) ) {
                view.showErrorInputPassword( "رمز ها با هم مطابقت ندارند!" );
                return;
            }
            if ( username.isEmpty() ) {
                view.showErrorInputUsername( "لطفا نام کاربری را وارد کنید." );
                return;
            }
            UserModel userModel = new UserModel();
            userModel.setUsername( username );
            userModel.setEmail( email );
            userModel.setFullname( fullname );
            userModel.setPassword( password );
            view.showProgress();
            subscription.add( repository.registerUser( userModel )
                    .subscribeOn( Schedulers.io() )
                    .observeOn( AndroidSchedulers.mainThread() )
                    .subscribeWith( new DisposableSingleObserver<UserModel>() {

                        @Override
                        public void onSuccess( UserModel userModel ) {
                            if ( !userModel.isError() ) {
                                view.showSuccessRegister( userModel.getErrorMessage() );
                            } else {
                                view.showErrorRegister( userModel.getErrorMessage() );
                            }
                            view.hideProgress();
                        }

                        @Override
                        public void onError( Throwable e ) {
                            view.hideProgress();
                            view.showErrorRegister( "خطا! اتصال اینترنت را بررسی کنید." );
                        }
                    } ) );
        }
    }
}
