package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.repository.PostRepository;

/**
 * Created by Hossein on 7/15/2018.
 */

public class PostPresenter implements PostContract.Presenter {

    private CompositeDisposable disposable = new CompositeDisposable();

    private DataSource.Post repository;
    private PostContract.View view;

    public PostPresenter( DataSource.Post repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( PostContract.View view ) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void fetchPosts() {
        if ( isRefresh ) {
            view.showProgress();
        }
        disposable.add(
                repository.fetchPosts()
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<List<JobModel>>() {

                            @Override
                            public void onSuccess( List<JobModel> jobModels ) {
                                view.hideProgress();
                                if ( jobModels.get( 0 ).isError() ) {
                                    view.showSnack( jobModels.get( 0 ).getErrorMessage() );
                                    view.showTextEmpty();
                                    view.setTextEmpty( jobModels.get( 0 ).getErrorMessage() );
                                } else {
                                    view.hideTextEmpty();
                                    view.notifyAdapter( jobModels );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                view.hideProgress();
                                view.showTextEmpty();
                                view.setTextEmpty( "برقراری ارتباط ممکن نیست اینترنت را چک کنید." );
                                view.showSnack( "مشکل در برقراری ارتباط" );
                            }
                        } )
        );
    }

    @Override
    public void removePost( long id ) {
        disposable.add( repository.removePostById( id )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<JobModel>() {

                    @Override
                    public void onSuccess( JobModel jobModel ) {
                        fetchPosts();
                        view.showSnack( jobModel.getErrorMessage() );
                    }

                    @Override
                    public void onError( Throwable e ) {
                        view.showSnack( "مشکل دربرقراری ارتباط!" );
                    }
                } )
        );
    }

    @Override
    public void rxUnsubscribe() {
        disposable.clear();
        disposable.dispose();
    }

    private boolean isRefresh;

    @Override
    public void refresh() {
        isRefresh = true;
    }
}
