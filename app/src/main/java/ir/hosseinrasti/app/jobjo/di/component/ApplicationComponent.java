package ir.hosseinrasti.app.jobjo.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ir.hosseinrasti.app.jobjo.di.module.ActivityModule;
import ir.hosseinrasti.app.jobjo.di.module.AdapterModule;
import ir.hosseinrasti.app.jobjo.di.module.ApiModule;
import ir.hosseinrasti.app.jobjo.di.module.FragmentModule;
import ir.hosseinrasti.app.jobjo.di.module.PresenterModule;
import ir.hosseinrasti.app.jobjo.di.module.DataSourceModule;
import ir.hosseinrasti.app.jobjo.di.module.ApplicationModule;
import ir.hosseinrasti.app.jobjo.ui.activites.BaseActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.Splash;
import ir.hosseinrasti.app.jobjo.ui.activites.comment.CommentActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobListByCategoryActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobListRequestedActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobSeekersActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobDetailsActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post.PostFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.StartupActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category.CategoryFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home.HomeFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview.ChatActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.job.ModifyJobActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview.InterviewFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search.SearchFragment;
import ir.hosseinrasti.app.jobjo.ui.user.BottomSheetUsersFragment;
import ir.hosseinrasti.app.jobjo.ui.user.intruduce.UsersIntroducedActivity;
import ir.hosseinrasti.app.jobjo.ui.user.login.LoginActivity;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.profile.ModifyProfileActivity;
import ir.hosseinrasti.app.jobjo.ui.user.profile.ProfileActivity;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersActivity;
import ir.hosseinrasti.app.jobjo.ui.user.signup.SignupActivity;
import ir.hosseinrasti.app.jobjo.ui.user.users.UsersActivity;

/**
 * Created by Hossein on 5/17/2018.
 */

@Singleton
@Component(modules = { ApplicationModule.class ,
        ActivityModule.class ,
        AdapterModule.class ,
        PresenterModule.class ,
        FragmentModule.class ,
        ApiModule.class ,
        DataSourceModule.class })
public interface ApplicationComponent {

    // Injection Activity
    void inject( Splash splash );

    void inject( BaseActivity baseActivity );

    void inject( StartupActivity StartupActivity );

    void inject( CommentActivity commentActivity );

    void inject( JobListByCategoryActivity jobListByCategoryActivity );

    void inject( ProfileActivity profileActivity );

    void inject( ProfileOthersActivity profileOthersActivity );

    void inject( LoginActivity loginActivity );

    void inject( SignupActivity signupActivity );

    void inject( JobSeekersActivity jobSeekersActivity );

    void inject( ModifyProfileActivity modifyProfileActivity );

    void inject( ModifyJobActivity modifyJobActivity );

    void inject( JobListRequestedActivity requestedActivity );

    void inject( UsersIntroducedActivity introducedActivity );

    void inject( ChatActivity chatActivity );

    void inject( JobDetailsActivity jobDetailsActivity );

    void inject( UsersActivity usersActivity );

    // Injection Fragments
    void inject( HomeFragment homeFragment );

    void inject( PostFragment postFragment );

    void inject( SearchFragment searchFragment );

    void inject( CategoryFragment categoryFragment );

    void inject( InterviewFragment interviewFragment );

    void inject( BottomSheetUsersFragment sheetUsersFragment );


}
