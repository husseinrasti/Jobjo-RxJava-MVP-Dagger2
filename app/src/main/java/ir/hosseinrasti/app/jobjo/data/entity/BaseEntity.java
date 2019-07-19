package ir.hosseinrasti.app.jobjo.data.entity;


import android.os.Parcelable;

/**
 * Created by Hossein on 7/18/2018.
 */

public class BaseEntity {


    Object object;

    BaseEntity() {
        object = this;
    }

    public Object clone() {
        return object;
    }
}
