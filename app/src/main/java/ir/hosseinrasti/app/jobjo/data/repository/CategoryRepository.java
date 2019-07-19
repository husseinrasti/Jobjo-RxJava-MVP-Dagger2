package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.utils.Config;

/**
 * Created by Hossein on 5/31/2018.
 */

public class CategoryRepository implements DataSource.Category {

    private ApiService apiService;
    private SessionPref pref;

    public CategoryRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    @Override
    public Single<List<WorkGroupModel>> fetchCategory() {
        return apiService.fetchAllCategory();
    }

    @Override
    public Single<WorkGroupModel> insertCategory( String nameCategory ) {
        return apiService.insertCategory( nameCategory );
    }

    @Override
    public Single<WorkGroupModel> removeCategory( long idCategory ) {
        return apiService.removeCategory( idCategory );
    }

    @Override
    public boolean isGrantManager() {
        if ( pref.getAccess().equals( Config.KEY_ACCESS_MANAGER ) ) {
            return true;
        }
        return false;
    }
}
