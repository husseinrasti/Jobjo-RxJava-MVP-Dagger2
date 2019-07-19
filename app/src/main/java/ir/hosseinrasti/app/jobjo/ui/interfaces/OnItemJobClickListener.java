package ir.hosseinrasti.app.jobjo.ui.interfaces;

import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public interface OnItemJobClickListener {

    void onItemClick( JobModel jobModel, Action action );

}
