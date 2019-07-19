package ir.hosseinrasti.app.jobjo.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.MessageModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class MessageRepository implements DataSource.Message {

    private ApiService apiService;
    private SessionPref pref;

    public MessageRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    @Override
    public long getIdCurrentUser() {
        return pref.getId();
    }

    @Override
    public Single<List<MessageModel>> fetchMessageByIdUsers( long idSender, long idReceiver ) {
        return apiService.fetchMessage( idSender, idReceiver );
    }

    @Override
    public Single<List<UserModel>> fetchSenderMessage( long idReceiver ) {
        return apiService.fetchSenderMessage( idReceiver );
    }
}
