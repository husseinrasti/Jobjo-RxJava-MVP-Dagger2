package ir.hosseinrasti.app.jobjo.di.module;

import dagger.Module;
import dagger.Provides;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category.CategoryFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.home.HomeFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview.InterviewFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.post.PostFragment;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.search.SearchFragment;
import ir.hosseinrasti.app.jobjo.ui.user.BottomSheetUsersFragment;

/**
 * Created by Hossein on 7/19/2018.
 */

@Module
public class FragmentModule {

    @Provides
    HomeFragment provideHomeFragment() {
        return new HomeFragment();
    }

    @Provides
    SearchFragment provideSearchFragment() {
        return new SearchFragment();
    }


    @Provides
    CategoryFragment provideCategoryFragment() {
        return new CategoryFragment();
    }


    @Provides
    InterviewFragment provideMessageFragment() {
        return new InterviewFragment();
    }

    @Provides
    PostFragment providePostFragment() {
        return new PostFragment();
    }


    @Provides
    BottomSheetUsersFragment provideBottomSheetUsersFragment() {
        return new BottomSheetUsersFragment();
    }

}
