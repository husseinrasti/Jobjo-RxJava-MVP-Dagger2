package ir.hosseinrasti.app.jobjo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hossein on 7/13/2018.
 */

public class CommentModel extends BaseEntity implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("idUser")
    @Expose
    private long idUser;

    @SerializedName("idJob")
    @Expose
    private long idJob;

    @SerializedName("nameUser")
    @Expose
    private String nameUser;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("urlPicUser")
    @Expose
    private String urlPicUser;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("error_msg")
    @Expose
    private String errorMessage;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser( long idUser ) {
        this.idUser = idUser;
    }

    public long getIdJob() {
        return idJob;
    }

    public void setIdJob( long idJob ) {
        this.idJob = idJob;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser( String nameUser ) {
        this.nameUser = nameUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public String getUrlPicUser() {
        return urlPicUser;
    }

    public void setUrlPicUser( String urlPicUser ) {
        this.urlPicUser = urlPicUser;
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
}
