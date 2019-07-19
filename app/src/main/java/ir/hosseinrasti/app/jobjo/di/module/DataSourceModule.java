package ir.hosseinrasti.app.jobjo.di.module;

import dagger.Module;
import dagger.Provides;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.data.repository.CategoryRepository;
import ir.hosseinrasti.app.jobjo.data.repository.CommentRepository;
import ir.hosseinrasti.app.jobjo.data.repository.HomeRepository;
import ir.hosseinrasti.app.jobjo.data.repository.JobRepository;
import ir.hosseinrasti.app.jobjo.data.repository.LoginRepository;
import ir.hosseinrasti.app.jobjo.data.repository.MessageRepository;
import ir.hosseinrasti.app.jobjo.data.repository.ModifyJobRepository;
import ir.hosseinrasti.app.jobjo.data.repository.PostRepository;
import ir.hosseinrasti.app.jobjo.data.repository.ProfileRepository;
import ir.hosseinrasti.app.jobjo.data.repository.SearchRepository;
import ir.hosseinrasti.app.jobjo.data.repository.SignupRepository;
import ir.hosseinrasti.app.jobjo.data.repository.UploadRepository;
import ir.hosseinrasti.app.jobjo.data.repository.UsersIntroducedRepository;
import ir.hosseinrasti.app.jobjo.data.repository.UsersRepository;

/**
 * Created by Hossein on 5/31/2018.
 */

@Module
public class DataSourceModule {

    @Provides
    DataSource.Login provideLoginRepository( ApiService apiService, SessionPref pref ) {
        return new LoginRepository( apiService, pref );
    }

    @Provides
    DataSource.ModifyJob provideModifyJobRepository( ApiService apiService, SessionPref pref ) {
        return new ModifyJobRepository( apiService, pref );
    }

    @Provides
    DataSource.Signup provideSignupRepository( ApiService apiService ) {
        return new SignupRepository( apiService );
    }

    @Provides
    DataSource.Home provideHomeRepository( ApiService apiService, SessionPref pref ) {
        return new HomeRepository( apiService, pref );
    }

    @Provides
    DataSource.Upload provideUploadRepository( ApiService apiService, SessionPref pref ) {
        return new UploadRepository( apiService, pref );
    }

    @Provides
    DataSource.Post providePostRepository( ApiService apiService, SessionPref pref ) {
        return new PostRepository( apiService, pref );
    }

    @Provides
    DataSource.Users provideUsersRepository( ApiService apiService ) {
        return new UsersRepository( apiService );
    }

    @Provides
    DataSource.UsersIntroduced provideUsersIntroducedRepository( ApiService apiService ) {
        return new UsersIntroducedRepository( apiService );
    }

    @Provides
    DataSource.Category provideCategoryRepository( ApiService apiService, SessionPref pref ) {
        return new CategoryRepository( apiService, pref );
    }

    @Provides
    DataSource.Search provideSearchRepository( ApiService apiService, SessionPref pref ) {
        return new SearchRepository( apiService, pref );
    }

    @Provides
    DataSource.Job provideJobRepository( ApiService apiService ) {
        return new JobRepository( apiService );
    }

    @Provides
    DataSource.Profile provideProfileRepository( ApiService apiService, SessionPref pref ) {
        return new ProfileRepository( apiService, pref );
    }

    @Provides
    DataSource.Message provideMessageRepository( ApiService apiService, SessionPref pref ) {
        return new MessageRepository( apiService, pref );
    }

    @Provides
    DataSource.Comment provideCommentRepository( ApiService apiService, SessionPref pref ) {
        return new CommentRepository( apiService, pref );
    }
}