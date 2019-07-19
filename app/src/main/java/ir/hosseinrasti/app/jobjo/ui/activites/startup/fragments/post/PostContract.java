package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post;

import java.util.List;

import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 7/15/2018.
 */

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void notifyAdapter( List<JobModel> JobModels );

        void showSnack( String message );

        void showSnackDelete( final JobModel jobModel );

        void setTextEmpty( String message );

        void hideTextEmpty();

        void showTextEmpty();

        void showProgress();

        void hideProgress();
        void setList( List<JobModel> jobModels );

        List<JobModel> getList();
    }



    interface Presenter extends BasePresenter<View> {

        void fetchPosts();

        void removePost( long id );

        void rxUnsubscribe();

        void refresh();
    }
}
