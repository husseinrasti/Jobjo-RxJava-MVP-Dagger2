package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category;

import java.util.List;

import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.utils.BasePresenter;
import ir.hosseinrasti.app.jobjo.utils.BaseView;

/**
 * Created by Hossein on 7/15/2018.
 */

public interface CategoryContract {

    interface View extends BaseView<Presenter> {

        void notifyAdapter( List<WorkGroupModel> workGroupList );

        void showSnack( String message );

        void showSnackDelete( WorkGroupModel model );

        void setTextEmpty( String message );

        void hideTextEmpty();

        void showTextEmpty();

        void showProgress();

        void hideProgress();

        String getTitleCategory();

        void setList( List<WorkGroupModel> workGroupModels );

        List<WorkGroupModel> getList();

    }



    interface Presenter extends BasePresenter<View> {

        void fetchCategory();

        void removeCategory( WorkGroupModel model );

        void insertCategory();

        void rxUnsubscribe();

        boolean isGrantManager();

        void refresh();
    }
}
