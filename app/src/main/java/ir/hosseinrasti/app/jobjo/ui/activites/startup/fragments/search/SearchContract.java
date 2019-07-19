package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search;

import android.widget.EditText;

import java.util.List;

import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 7/14/2018.
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        EditText getEditText();

        void notifyAdapter( List<JobModel> JobModels );

        void showSnack( String message );

        void showSnackDelete( final JobModel jobModel );

        void setTextEmpty( String message );

        void hideTextEmpty();

        void showTextEmpty();

        void showProgress();

        void hideProgress();

    }



    interface Presenter extends BasePresenter<View> {

        void search();

        void onItemClick( JobModel jobModel, Action action );

        void removeJob( JobModel jobModel );

        void rxUnsubscribe();

        boolean isGrantManager();

        long getCurrentId();
    }
}
