package ir.hosseinrasti.app.jobjo.ui.activites.comment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.repository.CommentRepository;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 7/13/2018.
 */

public class CommentPresenter implements CommentContract.Presenter {

    private CompositeDisposable disposable;
    private CommentContract.View view;
    private DataSource.Comment repository;
    private JobModel jobModel;

    public CommentPresenter( DataSource.Comment repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( CommentContract.View view ) {
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void setJobModel( JobModel jobModel ) {
        this.jobModel = jobModel;
    }

    @Override
    public void fetchComments() {
        if ( jobModel == null ) {
            view.showTextEmpty();
            view.setTextEmpty( "اطلاعات آگهی موجود نیست!" );
            return;
        }
        view.hideTextEmpty();
        view.showProgress();
        disposable.add( repository.fetchComments( jobModel.getId() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<List<CommentModel>>() {

                    @Override
                    public void onSuccess( List<CommentModel> commentModels ) {
                        view.hideProgress();
                        if ( commentModels.get( 0 ).isError() ) {
                            view.showTextEmpty();
                            view.setTextEmpty( commentModels.get( 0 ).getErrorMessage() );
                        } else {
                            view.hideTextEmpty();
                            view.notifyAdapter( commentModels );
                        }
                    }

                    @Override
                    public void onError( Throwable e ) {
                        view.hideProgress();
                        view.showTextEmpty();
                        view.setTextEmpty( "امکان برقراری ارتباط نیست،اینترنت را چک کنید!" );
                        view.showSnack( "امکان برقراری ارتباط نیست" );
                    }
                } )
        );
    }

    @Override
    public void sendComment() {
        if ( jobModel == null ) {
            view.showTextEmpty();
            view.setTextEmpty( "اطلاعات آگهی موجود نیست!" );
            return;
        }
        view.hideTextEmpty();
        view.showProgress();
        String createAt = Util.getCurrentDate();
        String comment = view.getComment();
        disposable.add( repository.sendComment( comment, createAt, jobModel.getId() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeWith( new DisposableSingleObserver<CommentModel>() {

                    @Override
                    public void onSuccess( CommentModel commentModel ) {
                        view.hideProgress();
                        if ( commentModel.isError() ) {
                            view.showTextEmpty();
                            view.setTextEmpty( commentModel.getErrorMessage() );
                        } else {
                            view.hideTextEmpty();
                            commentModel.setComment( comment );
                            commentModel.setIdJob( jobModel.getId() );
                            commentModel.setIdUser( repository.getIdUser() );
                            commentModel.setNameUser( repository.getFullNameUser() );
                            commentModel.setUrlPicUser( repository.getPicUser() );
                            commentModel.setCreatedAt( createAt );
                            view.notifyAdapter( commentModel );
                        }
                    }

                    @Override
                    public void onError( Throwable e ) {
                        view.hideProgress();
                        view.showSnack( "امکان برقراری ارتباط نیست،اینترنت را چک کنید!" );
                    }
                } ) );
    }

    @Override
    public void rxUnsubscribe() {
        disposable.clear();
        disposable.dispose();
    }
}
