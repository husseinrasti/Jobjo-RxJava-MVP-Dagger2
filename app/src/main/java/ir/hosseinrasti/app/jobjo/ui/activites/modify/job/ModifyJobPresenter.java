package ir.hosseinrasti.app.jobjo.ui.activites.modify.job;

import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.data.repository.CategoryRepository;
import ir.hosseinrasti.app.jobjo.data.repository.ModifyJobRepository;
import ir.hosseinrasti.app.jobjo.data.repository.UploadRepository;
import ir.hosseinrasti.app.jobjo.utils.Config;
import ir.hosseinrasti.app.jobjo.utils.FileResolver;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 7/18/2018.
 */

public class ModifyJobPresenter implements ModifyJobContract.Presenter {

    private CompositeDisposable disposable;
    private ModifyJobContract.View view;
    private DataSource.ModifyJob repository;
    private DataSource.Upload uploadRepository;
    private DataSource.Category categoryRepository;
    private FileResolver fileResolver;
    private String urlPic;
    private boolean mustUpdatePic;

    public ModifyJobPresenter( DataSource.ModifyJob repository,
                               DataSource.Upload uploadRepository,
                               DataSource.Category categoryRepository,
                               FileResolver fileResolver ) {
        this.repository = repository;
        this.uploadRepository = uploadRepository;
        this.categoryRepository = categoryRepository;
        this.fileResolver = fileResolver;
    }

    @Override
    public void takeView( ModifyJobContract.View view ) {
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void fetchAllCategory() {
        disposable.add(
                categoryRepository.fetchCategory()
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<WorkGroupModel>>() {

                            @Override
                            public void onSuccess( List<WorkGroupModel> workGroupModels ) {
                                view.notifyAdapter( workGroupModels );
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.showSnack( "مشکلی در دریافت دسته بندی ها رخ داده!" );
                            }
                        } )
        );
    }

    @Override
    public void rxUnsubscribe() {
        disposable.clear();
        disposable.dispose();
    }

    @Override
    public void uploadImage( Uri selectedImage ) {
        uploadRepository.isProfile( false );
        String filePath = fileResolver.getFilePath( selectedImage );
        if ( TextUtils.isEmpty( filePath ) ) {
            view.showSnack( "فایل انتخابی مشکل دارد." );
            return;
        }
        view.showThumbnail( selectedImage );
        view.showProgressUpload();
        disposable.add( uploadRepository.uploadImage( filePath )
                .subscribeOn( Schedulers.computation() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        progress -> {
                            view.showProgressUpload();
                            view.setUploadProgress( ( int ) ( 100 * progress ) );
                        },
                        error -> view.showSnack( "آپلودناموفق! دوباره تلاش کنید." ),
                        view::uploadCompleted
                )
        );
    }

    @Override
    public void savePicJob() {
        boolean status = Boolean.parseBoolean( uploadRepository.getBody() );
        if ( status ) {
            view.showSnack( "آپلودناموفق! دوباره تلاش کنید." );
            mustUpdatePic = false;
        } else {
            view.showSnack( "آپلود عکس با موفقیت انجام شد." );
            mustUpdatePic = true;
            urlPic = uploadRepository.getBody();
        }
    }

    @Override
    public void updateJobModel() {
        JobModel jobModel = view.getJobModel();
        if ( jobModel.getNameJob().isEmpty() ) {
            view.showSnack( "لطفا اطلاعات را وارد کنید" );
            return;
        }
        if ( mustUpdatePic ) {
            jobModel.setPicJob( urlPic );
        }
        disposable.add( repository.updateJobModel( jobModel )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel jobModel ) {
                        view.showSnack( jobModel.getErrorMessage() );
                        view.taskCompleted();
                    }

                    @Override
                    public void onError( Throwable e ) {
                        e.printStackTrace();
                    }
                } ) );
    }

    @Override
    public void insertJobModel() {
        JobModel jobModel = view.getJobModel();
        if ( jobModel == null ) {
            view.showSnack( "لطفا اطلاعات را وارد کنید" );
            return;
        }
        if ( mustUpdatePic ) {
            jobModel.setPicJob( urlPic );
        } else {
            jobModel.setPicJob( Config.DEFAULT_IMAGE_URL );
        }
        disposable.add( repository.insertJobModel( jobModel )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel jobModel ) {
                        view.showSnack( jobModel.getErrorMessage() );
                        view.taskCompleted();
                    }

                    @Override
                    public void onError( Throwable e ) {
                        e.printStackTrace();
                    }
                } ) );
    }
}
