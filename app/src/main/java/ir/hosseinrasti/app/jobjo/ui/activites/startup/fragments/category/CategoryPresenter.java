package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.data.repository.CategoryRepository;

/**
 * Created by Hossein on 7/15/2018.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private CompositeDisposable disposable = new CompositeDisposable();

    private DataSource.Category repository;
    private CategoryContract.View view;

    public CategoryPresenter( DataSource.Category repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( CategoryContract.View view ) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void fetchCategory() {
        if ( isRefresh ) {
            view.showProgress();
        }
        view.hideTextEmpty();
        disposable.add(
                repository.fetchCategory()
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<WorkGroupModel>>() {

                            @Override
                            public void onSuccess( List<WorkGroupModel> workGroupModels ) {
                                view.hideTextEmpty();
                                view.notifyAdapter( workGroupModels );
                                view.hideProgress();
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.hideProgress();
                                view.showTextEmpty();
                                view.setTextEmpty( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
                            }
                        } )
        );
    }

    @Override
    public void removeCategory( WorkGroupModel model ) {
        disposable.add(
                repository.removeCategory( model.getId() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<WorkGroupModel>() {

                            @Override
                            public void onSuccess( WorkGroupModel workGroupModels ) {
                                fetchCategory();
                                view.showSnack( workGroupModels.getErrorMessage() );
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.showSnack( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
                            }
                        } )
        );
    }

    @Override
    public void insertCategory() {
        disposable.add(
                repository.insertCategory( view.getTitleCategory() )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<WorkGroupModel>() {

                            @Override
                            public void onSuccess( WorkGroupModel workGroupModels ) {
                                fetchCategory();
                                view.showSnack( workGroupModels.getErrorMessage() );
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.showSnack( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
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

    private boolean isRefresh;

    @Override
    public void refresh() {
        isRefresh = true;
    }
}
