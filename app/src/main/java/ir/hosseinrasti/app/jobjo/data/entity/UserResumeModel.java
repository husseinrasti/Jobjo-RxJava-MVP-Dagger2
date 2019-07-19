package ir.hosseinrasti.app.jobjo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hossein on 5/20/2018.
 */

public class UserResumeModel extends BaseEntity implements Serializable{

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("idUsername")
    @Expose
    private long idUsername;

    @SerializedName("workExperience")
    @Expose
    private String workExperience;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("jobSide")
    @Expose
    private String jobSide;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public long getIdUsername() {
        return idUsername;
    }

    public void setIdUsername( long idUsername ) {
        this.idUsername = idUsername;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience( String workExperience ) {
        this.workExperience = workExperience;
    }

    public int getYear() {
        return year;
    }

    public void setYear( int year ) {
        this.year = year;
    }

    public String getJobSide() {
        return jobSide;
    }

    public void setJobSide( String jobSide ) {
        this.jobSide = jobSide;
    }
}
