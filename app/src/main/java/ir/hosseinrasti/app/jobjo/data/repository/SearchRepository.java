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

public class SearchRepository implements DataSource.Search {

    private ApiService apiService;
    private SessionPref pref;

    public SearchRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    public SearchRepository( ApiService apiService ) {
        this.apiService = apiService;
    }

    @Override
    public Single<List<JobModel>> search( String search ) {
        return apiService.search( search );
    }

    @Override
    public Single<JobModel> insertJobApply( long idJob, long idUserEmployer, long idUserWorker ) {
        return apiService.insertJobApply( idJob, idUserEmployer, idUserWorker );
    }

    @Override
    public Single<JobModel> removeJobById( long id ) {
        return apiService.removeJobById( id );
    }

    @Override
    public boolean isGrantManager() {
        if ( pref.getAccess().equals( Config.KEY_ACCESS_MANAGER ) ) {
            return true;
        }
        return false;
    }

    @Override
    public long getCurrentId() {
        return pref.getId();
    }
}
