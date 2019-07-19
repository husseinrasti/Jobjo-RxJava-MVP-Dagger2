package ir.hosseinrasti.app.jobjo.ui.interfaces;

import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public interface OnItemCategoryClickListener {

    void onItemClick( WorkGroupModel workGroupModel, Action action, int color );
}
