package ir.hosseinrasti.app.jobjo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hossein on 5/31/2018.
 */

public class MessageModel extends BaseEntity implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("idUserSender")
    @Expose
    private long idUserSender;

    @SerializedName("idUserReceiver")
    @Expose
    private long idUserReceiver;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("userModelSender")
    @Expose
    private List<UserModel> userModelSender;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("error_msg")
    @Expose
    private String errorMessage;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
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

    public long getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender( long idUserSender ) {
        this.idUserSender = idUserSender;
    }

    public long getIdUserReceiver() {
        return idUserReceiver;
    }

    public void setIdUserReceiver( long idUserReceiver ) {
        this.idUserReceiver = idUserReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public List<UserModel> getUserModelSender() {
        return userModelSender;
    }

    public void setUserModelSender( List<UserModel> userModelSender ) {
        this.userModelSender = userModelSender;
    }
}
