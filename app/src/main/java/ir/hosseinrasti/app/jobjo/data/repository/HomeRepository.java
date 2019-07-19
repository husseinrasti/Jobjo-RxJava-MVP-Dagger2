package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.utils.Config;

/**
 * Created by Hossein on 5/31/2018.
 */

public class HomeRepository implements DataSource.Home {

    private ApiService apiService;
    private SessionPref pref;

    public HomeRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    @Override
    public long getIdCurrentUser() {
        return pref.getId();
    }

    @Override
    public boolean isGrantManager() {
        if ( pref.getAccess().equals( Config.KEY_ACCESS_MANAGER ) ) {
            return true;
        }
        return false;
    }

    @Override
    public Single<JobModel> removeJobById( long id ) {
        return apiService.removeJobById( id );
    }

    @Override
    public Single<List<JobModel>> fetchAllJob() {
        return apiService.fetchAllJob();
    }

    @Override
    public Single<JobModel> insertJobApply( long idJob, long idUserEmployer ) {
        return apiService.insertJobApply( idJob, idUserEmployer, pref.getId() );
    }

    @Override
    public Single<List<JobModel>> fetchJobByCategory( long idCategory ) {
        return apiService.fetchJobByCategory( idCategory );
    }
}
