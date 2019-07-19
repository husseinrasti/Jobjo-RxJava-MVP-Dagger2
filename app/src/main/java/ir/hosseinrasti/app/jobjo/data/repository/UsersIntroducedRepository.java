package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class UsersIntroducedRepository implements DataSource.UsersIntroduced {

    private ApiService apiService;

    public UsersIntroducedRepository( ApiService apiService ) {
        this.apiService = apiService;
    }


    @Override
    public Single<List<UserModel>> fetchAllUsersIntroduced( long idUserEmployer ) {
        return apiService.fetchUserIntroduced( idUserEmployer );
    }

    @Override
    public Single<UserModel> fetchUserModel( String username ) {
        return apiService.fetchUserModel( username );
    }
}
