package ir.hosseinrasti.app.jobjo.data.repository;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/24/2018.
 */

public class LoginRepository implements DataSource.Login {

    private ApiService apiUser;
    private SessionPref sessionPref;

    public LoginRepository( ApiService apiUser, SessionPref sessionPref ) {
        this.apiUser = apiUser;
        this.sessionPref = sessionPref;
    }

    @Override
    public void saveSession( UserModel userModel ) {
        sessionPref.createLoginSession( userModel );
    }

    @Override
    public Single<UserModel> getUserModelFromNetwork( UserModel userModel ) {
        return apiUser.login( userModel.getUsername(), userModel.getPassword() );
    }

}
