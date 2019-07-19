package ir.hosseinrasti.app.jobjo.ui.activites.comment;

import java.util.List;

import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 7/13/2018.
 */

public interface CommentContract {

    interface View extends BaseView<Presenter> {

        void notifyAdapter( List<CommentModel> commentModels );

        void notifyAdapter( CommentModel commentModels );

        void setTextEmpty( String message );

        void hideTextEmpty();

        void showTextEmpty();

        void showProgress();

        void hideProgress();

        String getComment();

        void showSnack( String message );
    }



    interface Presenter extends BasePresenter<View> {

        void setJobModel( JobModel jobModel );

        void fetchComments();

        void sendComment();

        void rxUnsubscribe();

    }

}
