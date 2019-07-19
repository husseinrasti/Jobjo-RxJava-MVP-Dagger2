package ir.hosseinrasti.app.jobjo.data.repository;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class ProfileRepository implements DataSource.Profile {

    private ApiService apiService;
    private SessionPref pref;
    private UserModel userModel;

    public ProfileRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }


    @Override
    public Single<UserModel> fetchUserModel( String username ) {
        return apiService.fetchUserModel( username );
    }

    @Override
    public Single<UserModel> fetchUserById( long idUser ) {
        return apiService.fetchUserById( idUser );
    }

    @Override
    public UserModel getUserSession() {
        return pref.getUserDetails();
    }

    @Override
    public void saveUserModel( UserModel userModel ) {
        pref.createLoginSession( userModel );
    }

    @Override
    public UserModel getUserModel() {
        return this.userModel;
    }

    @Override
    public void setUserModel( UserModel userModel ) {
        this.userModel = userModel;
    }

    @Override
    public void logout() {
        pref.logoutUser();
    }

    @Override
    public void savePicUser( String url ) {
        pref.setPic( url );
    }
}
