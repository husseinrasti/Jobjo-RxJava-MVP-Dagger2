package ir.hosseinrasti.app.jobjo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hossein on 5/20/2018.
 */

public class WorkGroupModel extends BaseEntity implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("nameWorkGroup")
    @Expose
    private String nameWorkGroup;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("error_msg")
    @Expose
    private String errorMessage;

    @SerializedName("pic")
    @Expose
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic( String pic ) {
        this.pic = pic;
    }

    public boolean isError() {
        return error;
    }

    public void setError( boolean error ) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getNameWorkGroup() {
        return nameWorkGroup;
    }

    public void setNameWorkGroup( String nameWorkGroup ) {
        this.nameWorkGroup = nameWorkGroup;
    }
}
