package ir.hosseinrasti.app.jobjo.di.module;

import dagger.Module;
import dagger.Provides;
import ir.hosseinrasti.app.jobjo.ui.adapter.CategoryAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.ChatAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.CommentAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.JobSeekerAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.PostAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.UserSenderAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.UsersAdapter;
import ir.hosseinrasti.app.jobjo.ui.adapter.UsersEmployerAdapter;

/**
 * Created by Hossein on 7/18/2018.
 */

@Module
public class AdapterModule {

    @Provides
    CategoryAdapter provideCategoryAdapter() {
        return new CategoryAdapter();
    }

    @Provides
    CommentAdapter provideCommentAdapter() {
        return new CommentAdapter();
    }

    @Provides
    ChatAdapter provideChatAdapter() {
        return new ChatAdapter();
    }

    @Provides
    JobAdapter provideJobAdapter() {
        return new JobAdapter();
    }

    @Provides
    JobSeekerAdapter provideJobSeekerAdapter() {
        return new JobSeekerAdapter();
    }

    @Provides
    PostAdapter providePostAdapter() {
        return new PostAdapter();
    }

    @Provides
    UsersAdapter provideUsersAdapter() {
        return new UsersAdapter();
    }

    @Provides
    UsersEmployerAdapter provideUsersEmployerAdapter() {
        return new UsersEmployerAdapter();
    }

    @Provides
    UserSenderAdapter provideUserSenderAdapter() {
        return new UserSenderAdapter();
    }
}
