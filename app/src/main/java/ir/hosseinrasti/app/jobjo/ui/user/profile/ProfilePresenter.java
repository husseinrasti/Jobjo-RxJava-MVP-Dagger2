package ir.hosseinrasti.app.jobjo.ui.user.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.repository.ProfileRepository;
import ir.hosseinrasti.app.jobjo.data.repository.UploadRepository;
import ir.hosseinrasti.app.jobjo.utils.FileResolver;

/**
 * Created by Hossein on 7/14/2018.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private CompositeDisposable disposable;

    private DataSource.Profile repository;
    private DataSource.Upload uploadRepository;
    private FileResolver fileResolver;
    private ProfileContract.View view;

    public ProfilePresenter( DataSource.Profile repository, DataSource.Upload uploadRepository, FileResolver fileResolver ) {
        this.repository = repository;
        this.uploadRepository = uploadRepository;
        this.fileResolver = fileResolver;
    }

    @Override
    public void takeView( ProfileContract.View view ) {
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public UserModel getUserModel() {
        return repository.getUserModel();
    }

    @Override
    public void savePicUser() {
        boolean status = Boolean.parseBoolean( uploadRepository.getBody() );
        if ( status ) {
            view.showSnack( "آپلودناموفق! دوباره تلاش کنید." );
        } else {
            repository.savePicUser( uploadRepository.getBody() );
        }
    }

    @Override
    public void fetchUserModel() {
        view.showProgress();
        disposable.add(
                repository.fetchUserModel( repository.getUserSession().getUsername() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<UserModel>() {

                            @Override
                            public void onSuccess( UserModel userModel ) {
                                view.hideProgress();
                                view.setUserProfile( userModel );
                                repository.setUserModel( userModel );
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.hideProgress();
                            }

                        } ) );
    }

    @Override
    public void rxUnsubscribe() {
        disposable.clear();
        disposable.dispose();
    }

    @Override
    public void uploadImage( Uri selectedImage ) {
        uploadRepository.isProfile( true );
        String filePath = fileResolver.getFilePath( selectedImage );
        if ( TextUtils.isEmpty( filePath ) ) {
            view.showSnack( "فایل انتخابی مشکل دارد." );
            return;
        }
        view.showThumbnail( selectedImage );
        view.showProgress();
        disposable.add( uploadRepository.uploadImage( filePath )
                        .subscribeOn( Schedulers.computation() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe(
                                progress -> {
                                    view.hideProgress();
                                    view.showProgressUpload();
                                    view.setUploadProgress( ( int ) ( 100 * progress ) );
                                },
                                error -> view.showSnack( "آپلودناموفق! دوباره تلاش کنید." ),
//                                error -> Util.log( "Error Upload: " + error.getMessage() ),
                                view::uploadCompleted
                        )
        );
    }

    @Override
    public void uploadImageWithoutShowProgress( Uri selectedImage ) {
        uploadRepository.isProfile( true );
        String filePath = fileResolver.getFilePath( selectedImage );
        if ( TextUtils.isEmpty( filePath ) ) {
            view.showSnack( "مسیر فایل اشتباه است!" );
            return;
        }
        view.showThumbnail( selectedImage );
        disposable.add( uploadRepository.uploadImageWithoutProgress( filePath )
                .subscribeOn( Schedulers.computation() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        result -> view.uploadCompleted(),
                        error -> view.showSnack( error.getMessage() )
                )
        );
    }

    @Override
    public void logout( Activity activity ) {
        repository.logout();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction( "ir.hosseinrasti.app.jobjo.ACTION_LOGOUT" );
        activity.sendBroadcast( broadcastIntent );
    }
}
