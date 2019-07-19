package ir.hosseinrasti.app.jobjo.data.repository;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/24/2018.
 */

public class SignupRepository implements DataSource.Signup {

    private ApiService apiUser;

    public SignupRepository( ApiService apiUser ) {
        this.apiUser = apiUser;
    }

    @Override
    public Single<UserModel> registerUser( UserModel userModel ) {
        return apiUser.register( userModel.getUsername()
        ,userModel.getPassword()
        ,userModel.getEmail()
        ,userModel.getFullname());
    }

    @Override
    public Single<UserModel> isUsernameAvailable( String username ) {
        return apiUser.checkValidateUsername( username );
    }
}
