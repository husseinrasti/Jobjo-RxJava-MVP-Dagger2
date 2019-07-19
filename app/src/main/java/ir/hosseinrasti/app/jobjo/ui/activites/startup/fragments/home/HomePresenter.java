package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 7/13/2018.
 */

public class HomePresenter implements HomeContract.Presenter {

    private CompositeDisposable disposable = new CompositeDisposable();

    private HomeContract.View view;
    private DataSource.Home repository;

    public HomePresenter( DataSource.Home repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( HomeContract.View view ) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void fetchJobs() {
        if ( !isRefresh ) {
            view.showProgress();
        }
        view.hideTextEmpty();
        disposable.add( repository.fetchAllJob()
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<List<JobModel>>() {

                    @Override
                    public void onSuccess( List<JobModel> JobModels ) {
                        view.hideProgress();
                        if ( JobModels.get( 0 ).isError() ) {
                            view.showTextEmpty();
                            view.setTextEmpty( JobModels.get( 0 ).getErrorMessage() );
                            view.showSnack( JobModels.get( 0 ).getErrorMessage() );
                        } else {
                            view.hideTextEmpty();
                            view.notifyAdapter( JobModels );
                        }
                    }

                    @Override
                    public void onError( Throwable e ) {
                        view.hideProgress();
                        view.showTextEmpty();
                        view.setTextEmpty( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
                        view.showSnack( "مشکلی در دریافت اطلاعات پیش آمده!" );
                    }
                } )
        );
    }

    @Override
    public void onItemClick( JobModel jobModel, Action action ) {
        switch ( action ) {
            case APPLY:
                try {
                    jobApply( jobModel );
                } catch ( NullPointerException e ) {
                    e.printStackTrace();
                }
                break;
            case DELETE:
                view.showSnackDelete( jobModel );
                break;
        }
    }

    @Override
    public void removeJob( JobModel jobModel ) {
        disposable.add( repository.removeJobById( jobModel.getId() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel jobModel ) {
                        view.showSnack( jobModel.getErrorMessage() );
                    }

                    @Override
                    public void onError( Throwable e ) {
                        view.showSnack( "برقراری ارتباط ممکن نیست!" );
                    }
                } )
        );
    }

    private void jobApply( JobModel jobModel ) {
        disposable.add( repository.insertJobApply( jobModel.getId(), jobModel.getIdUserCreator() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel JobModels ) {
                        view.showSnack( JobModels.getErrorMessage() );
                    }

                    @Override
                    public void onError( Throwable e ) {
                        view.showSnack( "برقراری ارتباط ممکن نیست!" );
                    }
                } )
        );
    }

    @Override
    public void rxUnsubscribe() {
        if ( disposable != null ) {
            if ( !disposable.isDisposed() ) {
                disposable.clear();
                disposable.dispose();
            }
        }
    }

    @Override
    public boolean isGrantManager() {
        return repository.isGrantManager();
    }

    @Override
    public long getCurrentId() {
        return repository.getIdCurrentUser();
    }

    private boolean isRefresh;

    @Override
    public void refresh() {
        isRefresh = true;
    }
}
