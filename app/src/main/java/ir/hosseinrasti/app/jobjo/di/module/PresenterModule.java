package ir.hosseinrasti.app.jobjo.di.module;

import dagger.Module;
import dagger.Provides;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.repository.CategoryRepository;
import ir.hosseinrasti.app.jobjo.data.repository.CommentRepository;
import ir.hosseinrasti.app.jobjo.data.repository.HomeRepository;
import ir.hosseinrasti.app.jobjo.data.repository.LoginRepository;
import ir.hosseinrasti.app.jobjo.data.repository.ModifyJobRepository;
import ir.hosseinrasti.app.jobjo.data.repository.PostRepository;
import ir.hosseinrasti.app.jobjo.data.repository.ProfileRepository;
import ir.hosseinrasti.app.jobjo.data.repository.SearchRepository;
import ir.hosseinrasti.app.jobjo.data.repository.SignupRepository;
import ir.hosseinrasti.app.jobjo.data.repository.UploadRepository;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentContract;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentPresenter;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.job.ModifyJobContract;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.job.ModifyJobPresenter;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category.CategoryContract;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category.CategoryPresenter;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home.HomeContract;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home.HomePresenter;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post.PostContract;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post.PostPresenter;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search.SearchContract;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search.SearchPresenter;
import ir.hosseinrasti.app.jobjo.ui.user.login.LoginContract;
import ir.hosseinrasti.app.jobjo.ui.user.login.LoginPresenter;
import ir.hosseinrasti.app.jobjo.ui.user.profile.ProfileContract;
import ir.hosseinrasti.app.jobjo.ui.user.profile.ProfilePresenter;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersContract;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersPresenter;
import ir.hosseinrasti.app.jobjo.ui.user.signup.SignupContract;
import ir.hosseinrasti.app.jobjo.ui.user.signup.SignupPresenter;
import ir.hosseinrasti.app.jobjo.utils.FileResolver;

/**
 * Created by Hossein on 7/19/2018.
 */

@Module
public class PresenterModule {

    @Provides
    SignupContract.Presenter provideSignupPresenter( DataSource.Signup repository ) {
        return new SignupPresenter( repository );
    }

    @Provides
    LoginContract.Presenter provideLoginPresenter( DataSource.Login repository ) {
        return new LoginPresenter( repository );
    }


    @Provides
    ProfileContract.Presenter provideProfilePresenter( DataSource.Profile repository,
                                                       DataSource.Upload uploadRepository,
                                                       FileResolver fileResolver ) {
        return new ProfilePresenter( repository, uploadRepository, fileResolver );
    }

    @Provides
    ProfileOthersContract.Presenter provideProfileOthersPresenter( DataSource.Profile repository ) {
        return new ProfileOthersPresenter( repository );
    }

    @Provides
    CategoryContract.Presenter provideCategoryPresenter( DataSource.Category repository ) {
        return new CategoryPresenter( repository );
    }

    @Provides
    SearchContract.Presenter provideSearchPresenter( DataSource.Search repository ) {
        return new SearchPresenter( repository );
    }

    @Provides
    PostContract.Presenter providePostPresenter( DataSource.Post repository ) {
        return new PostPresenter( repository );
    }

    @Provides
    CommentContract.Presenter provideCommentPresenter( DataSource.Comment repository ) {
        return new CommentPresenter( repository );
    }

    @Provides
    ModifyJobContract.Presenter provideModifyJobPresenter( DataSource.ModifyJob repository,
                                                           DataSource.Upload uploadRepository,
                                                           DataSource.Category categoryRepository,
                                                           FileResolver fileResolver ) {
        return new ModifyJobPresenter( repository, uploadRepository, categoryRepository, fileResolver );
    }

    @Provides
    HomeContract.Presenter provideHomePresenter( DataSource.Home repository ) {
        return new HomePresenter( repository );
    }
}
