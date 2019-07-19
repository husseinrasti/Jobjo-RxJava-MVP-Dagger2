package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class JobRepository implements DataSource.Job {

    private ApiService apiService;

    public JobRepository( ApiService apiService ) {
        this.apiService = apiService;
    }

    @Override
    public Single<List<JobModel>> fetchAllMyJobs( long id ) {
        return apiService.fetchAllMyJobs( id );
    }

    @Override
    public Single<List<UserModel>> fetchUserModelByIdJobApply( long idJob ) {
        return apiService.fetchUserModelByIdJobApply( idJob );
    }

    @Override
    public Single<List<UserModel>> fetchAllUsers() {
        return apiService.fetchAllUserModel();
    }

    @Override
    public Single<UserModel> insertUserIntroduced( long idUserBroker, long idUserEmployer, long idUserWorker ) {
        return apiService.insertUserIntroduced( idUserBroker,  idUserEmployer ,idUserWorker );
    }

}
