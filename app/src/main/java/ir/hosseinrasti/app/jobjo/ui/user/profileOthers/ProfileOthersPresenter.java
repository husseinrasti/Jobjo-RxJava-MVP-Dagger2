package ir.hosseinrasti.app.jobjo.ui.user.profileOthers;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.repository.ProfileRepository;

/**
 * Created by Hossein on 7/14/2018.
 */

public class ProfileOthersPresenter implements ProfileOthersContract.Presenter {

    private CompositeDisposable disposable;

    private DataSource.Profile repository;
    private ProfileOthersContract.View view;

    public ProfileOthersPresenter( DataSource.Profile repository ) {
        this.repository = repository;
    }

    @Override
    public void takeView( ProfileOthersContract.View view ) {
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void fetchUserModel( long idUser ) {
        view.showProgress();
        disposable.add(
                repository.fetchUserById( idUser )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableSingleObserver<UserModel>() {

                            @Override
                            public void onSuccess( UserModel userModel ) {
                                view.setUserProfile( userModel );
                                view.showThumbnail( userModel.getPicUser() );
                                view.hideProgress();
                            }

                            @Override
                            public void onError( Throwable e ) {
                                e.printStackTrace();
                                view.hideProgress();
                                view.showSnack( "مشکلی در ارسال اطلاعات رخ داده!" );
                            }

                        } ) );
    }

    @Override
    public void rxUnsubscribe() {
        disposable.clear();
        disposable.dispose();
    }
}
