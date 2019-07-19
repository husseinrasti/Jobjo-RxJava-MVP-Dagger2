package ir.hosseinrasti.app.jobjo.ui.interfaces;

import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public interface OnItemCommentClickListener {

    void onItemClick( CommentModel commentModel, Action action );
}
