package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class PostRepository implements DataSource.Post {

    private ApiService apiService;
    private SessionPref pref;

    public PostRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    @Override
    public Single<List<JobModel>> fetchPosts() {
        return apiService.fetchAllMyJobs( pref.getId() );
    }

    @Override
    public Single<JobModel> removePostById( long idJob ) {
        return apiService.removeJobById( idJob );
    }
}
