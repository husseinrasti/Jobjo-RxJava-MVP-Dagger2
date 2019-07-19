package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home;

import java.util.List;

import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 7/13/2018.
 */

public interface HomeContract {

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

        void fetchJobs();

        void onItemClick( JobModel jobModel, Action action );

        void removeJob( JobModel jobModel );

        void rxUnsubscribe();

        boolean isGrantManager();

        long getCurrentId();

        void refresh();
    }

}
