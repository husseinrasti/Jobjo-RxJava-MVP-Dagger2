package ir.hosseinrasti.app.jobjo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hossein on 5/27/2018.
 */

public class JobModel extends BaseEntity implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("idUserCreator")
    @Expose
    private long idUserCreator;

    @SerializedName("idWorkGroup")
    @Expose
    private long idWorkGroup;

    @SerializedName("nameWorkGroup")
    @Expose
    private String nameWorkGroup;

    @SerializedName("nameJob")
    @Expose
    private String nameJob;

    @SerializedName("nameJobCreator")
    @Expose
    private String nameJobCreator;

    @SerializedName("picJob")
    @Expose
    private String picJob;

    @SerializedName("picUser")
    @Expose
    private String picUser;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("typeOfJob")
    @Expose
    private String typeOfJob;

    @SerializedName("hoursOfWork")
    @Expose
    private String hoursOfWork;

    @SerializedName("benefit")
    @Expose
    private String benefit;

    @SerializedName("travelOfWork")
    @Expose
    private String travelOfWork;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("militaryService")
    @Expose
    private String militaryService;

    @SerializedName("workExperience")
    @Expose
    private String workExperience;

    @SerializedName("education")
    @Expose
    private String education;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("software")
    @Expose
    private String software;

    @SerializedName("professionalKnowledge")
    @Expose
    private String professionalKnowledge;

    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("error_msg")
    @Expose
    private String errorMessage;

    @SerializedName("countComment")
    @Expose
    private String countComment;

    @SerializedName("countSeeker")
    @Expose
    private String countSeeker;

    public String getCountComment() {
        return countComment;
    }

    public void setCountComment( String countComment ) {
        this.countComment = countComment;
    }

    public String getCountSeeker() {
        return countSeeker;
    }

    public void setCountSeeker( String countSeeker ) {
        this.countSeeker = countSeeker;
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

    public String getPicJob() {
        return picJob;
    }

    public void setPicJob( String picJob ) {
        this.picJob = picJob;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public long getIdUserCreator() {
        return idUserCreator;
    }

    public void setIdUserCreator( long idUserCreator ) {
        this.idUserCreator = idUserCreator;
    }

    public long getIdWorkGroup() {
        return idWorkGroup;
    }

    public void setIdWorkGroup( long idWorkGroup ) {
        this.idWorkGroup = idWorkGroup;
    }

    public String getNameWorkGroup() {
        return nameWorkGroup;
    }

    public void setNameWorkGroup( String nameWorkGroup ) {
        this.nameWorkGroup = nameWorkGroup;
    }

    public String getNameJob() {
        return nameJob;
    }

    public void setNameJob( String nameJob ) {
        this.nameJob = nameJob;
    }

    public String getNameJobCreator() {
        return nameJobCreator;
    }

    public void setNameJobCreator( String nameJobCreator ) {
        this.nameJobCreator = nameJobCreator;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getTypeOfJob() {
        return typeOfJob;
    }

    public void setTypeOfJob( String typeOfJob ) {
        this.typeOfJob = typeOfJob;
    }

    public String getHoursOfWork() {
        return hoursOfWork;
    }

    public void setHoursOfWork( String hoursOfWork ) {
        this.hoursOfWork = hoursOfWork;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit( String benefit ) {
        this.benefit = benefit;
    }

    public String getTravelOfWork() {
        return travelOfWork;
    }

    public void setTravelOfWork( String travelOfWork ) {
        this.travelOfWork = travelOfWork;
    }

    public String getAge() {
        return age;
    }

    public String getMilitaryService() {
        return militaryService;
    }

    public void setMilitaryService( String militaryService ) {
        this.militaryService = militaryService;
    }

    public void setAge( String age ) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience( String workExperience ) {
        this.workExperience = workExperience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation( String education ) {
        this.education = education;
    }

    public String getLang() {
        return lang;
    }

    public void setLang( String lang ) {
        this.lang = lang;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware( String software ) {
        this.software = software;
    }

    public String getProfessionalKnowledge() {
        return professionalKnowledge;
    }

    public void setProfessionalKnowledge( String professionalKnowledge ) {
        this.professionalKnowledge = professionalKnowledge;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription( String jobDescription ) {
        this.jobDescription = jobDescription;
    }

    public String getPicUser() {
        return picUser;
    }

    public void setPicUser( String picUser ) {
        this.picUser = picUser;
    }

}
