package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class UsersRepository implements DataSource.Users {

    private ApiService apiService;

    public UsersRepository( ApiService apiService ) {
        this.apiService = apiService;
    }

    @Override
    public Single<List<UserModel>> fetchAllUsers() {
        return apiService.fetchAllUserModel();
    }

    @Override
    public Single<List<UserModel>> searchUser( String user ) {
        return apiService.searchUser( user );
    }

    @Override
    public Single<UserModel> fetchUserModel( String username ) {
        return apiService.fetchUserModel( username );
    }
}
