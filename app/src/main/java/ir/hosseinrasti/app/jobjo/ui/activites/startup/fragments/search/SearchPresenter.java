package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search;

import android.view.View;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.repository.SearchRepository;
import ir.hosseinrasti.app.jobjo.utils.Util;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 7/14/2018.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private CompositeDisposable disposable = new CompositeDisposable();
    private DataSource.Search repository;
    private SearchContract.View view;

    public SearchPresenter( DataSource.Search repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( SearchContract.View view ) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void search() {
        disposable.add( RxTextView.textChangeEvents( view.getEditText() )
                .skipInitialValue()
                .debounce( 500, TimeUnit.MILLISECONDS )
                .distinctUntilChanged()
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( doSearch() ) );
    }

    private DisposableObserver<TextViewTextChangeEvent> doSearch() {
        return new DisposableObserver<TextViewTextChangeEvent>() {

            @Override
            public void onNext( TextViewTextChangeEvent textViewTextChangeEvent ) {
                String word = textViewTextChangeEvent.text().toString();
                if ( word.isEmpty() ) {
                    view.showTextEmpty();
                    view.setTextEmpty( "لطفا کلمه مورد نظر را برای جستجو وارد کنید!" );
                    return;
                }
                view.hideTextEmpty();
                view.showProgress();
                repository.search( word )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<JobModel>>() {

                            @Override
                            public void onSuccess( List<JobModel> jobModels ) {
                                view.hideProgress();
                                if ( jobModels.get( 0 ).isError() ) {
                                    view.showTextEmpty();
                                    view.setTextEmpty( jobModels.get( 0 ).getErrorMessage() );
                                } else {
                                    view.notifyAdapter( jobModels );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.hideProgress();
                                view.showTextEmpty();
                                view.setTextEmpty( "لطفا کلمه مورد نظر را برای جستجو وارد کنید!" );
                                view.showSnack( "مشکلی در دریافت اطلاعات پیش آمده!" );
                            }
                        } );
            }

            @Override
            public void onError( Throwable e ) {
                view.hideProgress();
                view.showTextEmpty();
                view.setTextEmpty( "لطفا کلمه مورد نظر را برای جستجو وارد کنید!" );
                view.showSnack( "مشکلی در دریافت اطلاعات پیش آمده!" );
            }

            @Override
            public void onComplete() {
                view.hideTextEmpty();
                view.hideProgress();
            }
        };
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

    private void jobApply( JobModel jobModel ) throws NullPointerException {
        disposable.add(
                repository.insertJobApply( jobModel.getId(), jobModel.getIdUserCreator(), repository.getCurrentId() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<JobModel>() {

                            @Override
                            public void onSuccess( JobModel JobModels ) {
                                view.showSnack( JobModels.getErrorMessage() );
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.showSnack( "مشکلی در ارسال اطلاعات پیش آمده!" );
                            }
                        } )
        );
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
                        view.showSnack( "مشکلی در ارسال اطلاعات پیش آمده!" );
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
    public boolean isGrantManager() {
        return repository.isGrantManager();
    }

    @Override
    public long getCurrentId() {
        return repository.getCurrentId();
    }
}
