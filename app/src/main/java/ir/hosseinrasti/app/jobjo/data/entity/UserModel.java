package ir.hosseinrasti.app.jobjo.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hossein on 5/20/2018.
 */

public class UserModel extends BaseEntity implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("myExpertise")
    @Expose
    private String myExpertise;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("accessibility")
    @Expose
    private String accessibility;

    @SerializedName("state")
    @Expose
    private boolean state;

    @SerializedName("userResumeModel")
    @Expose
    private List<UserResumeModel> userResumeModel;

    @SerializedName("workExperience")
    @Expose
    private String workExperience;

    @SerializedName("idUserResume")
    @Expose
    private long idUserResume;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("telephone")
    @Expose
    private String telephone;

    @SerializedName("professionalKnowledge")
    @Expose
    private String professionalKnowledge;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("favorite")
    @Expose
    private String favorite;

    @SerializedName("education")
    @Expose
    private String education;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("school")
    @Expose
    private String school;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("married")
    @Expose
    private String married;

    @SerializedName("picUser")
    @Expose
    private String picUser;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("error_msg")
    @Expose
    private String errorMessage;

    public String getMyExpertise() {
        return myExpertise;
    }

    public void setMyExpertise( String myExpertise ) {
        this.myExpertise = myExpertise;
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

    public List<UserResumeModel> getUserResumeModel() {
        return userResumeModel;
    }

    public void setUserResumeModel( List<UserResumeModel> userResumeModel ) {
        this.userResumeModel = userResumeModel;
    }

    public String getPicUser() {
        return picUser;
    }

    public void setPicUser( String picUser ) {
        this.picUser = picUser;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience( String workExperience ) {
        this.workExperience = workExperience;
    }

    public boolean getState() {
        return state;
    }

    public void setState( boolean state ) {
        this.state = state;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility( String accessibility ) {
        this.accessibility = accessibility;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public void setFullname( String fullname ) {
        this.fullname = fullname;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public long getId() {

        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried( String married ) {
        this.married = married;
    }

    public long getIdUserResume() {
        return idUserResume;
    }

    public void setIdUserResume( long idUserResume ) {
        this.idUserResume = idUserResume;
    }

    public String getAge() {
        return age;
    }

    public void setAge( String age ) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone( String telephone ) {
        this.telephone = telephone;
    }

    public String getProfessionalKnowledge() {
        return professionalKnowledge;
    }

    public void setProfessionalKnowledge( String professionalKnowledge ) {
        this.professionalKnowledge = professionalKnowledge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite( String favorite ) {
        this.favorite = favorite;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation( String education ) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool( String school ) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity( String city ) {
        this.city = city;
    }

}
