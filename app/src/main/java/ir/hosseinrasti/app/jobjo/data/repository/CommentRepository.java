package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 7/13/2018.
 */

public class CommentRepository implements DataSource.Comment {

    private ApiService apiService;
    private SessionPref pref;

    public CommentRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    @Override
    public Single<List<CommentModel>> fetchComments( long idJob ) {
        return apiService.fetchComments( idJob );
    }

    @Override
    public Single<CommentModel> sendComment( String comment, String createAt, long idJob ) {
        return apiService.postComment( getIdUser(),
                getFullNameUser(),
                comment,
                createAt,
                getPicUser(),
                idJob );
    }

    @Override
    public String getPicUser() {
        return pref.getPic();
    }

    @Override
    public String getFullNameUser() {
        return pref.getFullname();
    }

    @Override
    public long getIdUser() {
        return pref.getId();
    }
}
