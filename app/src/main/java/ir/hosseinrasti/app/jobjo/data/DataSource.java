package ir.hosseinrasti.app.jobjo.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.MessageModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import okhttp3.ResponseBody;

/**
 * Created by Hossein on 5/24/2018.
 */

public interface DataSource {

    public interface Upload {

        void isProfile( boolean profile );

        void setId( long id );

        String getBody();

        Flowable<Double> uploadImage( String filePath );

        Single<ResponseBody> uploadImageWithoutProgress( String filePath );
    }



    interface ModifyJob {

        Single<JobModel> insertJobModel( JobModel jobModels );

        Single<JobModel> updateJobModel( JobModel jobModels );
    }



    public interface Login {

        void saveSession( UserModel userModel );

        Single<UserModel> getUserModelFromNetwork( UserModel userModel );
    }



    public interface Signup {

        Single<UserModel> registerUser( UserModel userModel );

        Single<UserModel> isUsernameAvailable( String username );
    }



    public interface Comment {

        Single<List<CommentModel>> fetchComments( long idJob );

        Single<CommentModel> sendComment( String comment, String createAt, long idJob );

        String getPicUser();

        String getFullNameUser();

        long getIdUser();
    }



    interface Home {

        long getIdCurrentUser();

        boolean isGrantManager();

        Single<JobModel> removeJobById( long id );

        Single<List<JobModel>> fetchAllJob();

        Single<JobModel> insertJobApply( long idJob, long idUserEmployer );

        Single<List<JobModel>> fetchJobByCategory( long idCategory );
    }



    interface Search {

        Single<List<JobModel>> search( String search );

        Single<JobModel> insertJobApply( long idJob, long idUserEmployer, long idUserWorker );

        Single<JobModel> removeJobById( long id );

        boolean isGrantManager();

        long getCurrentId();
    }



    interface Users {

        Single<List<UserModel>> fetchAllUsers();

        Single<List<UserModel>> searchUser( String user );

        Single<UserModel> fetchUserModel( String username );
    }



    interface UsersIntroduced {

        Single<List<UserModel>> fetchAllUsersIntroduced( long idUserEmployer );

        Single<UserModel> fetchUserModel( String username );
    }



    interface Category {

        Single<List<WorkGroupModel>> fetchCategory();

        Single<WorkGroupModel> insertCategory( String nameCategory );

        Single<WorkGroupModel> removeCategory( long idCategory );

        boolean isGrantManager();
    }



    interface Profile {

        Single<UserModel> fetchUserModel( String username );

        Single<UserModel> fetchUserById( long idUser );

        UserModel getUserSession();

        void saveUserModel( UserModel userModel );

        UserModel getUserModel();

        void setUserModel( UserModel userModel );

        void logout();

        void savePicUser( String url );
    }



    interface Job {

        Single<List<JobModel>> fetchAllMyJobs( long id );

        Single<List<UserModel>> fetchUserModelByIdJobApply( long idJob );

        Single<List<UserModel>> fetchAllUsers();

        Single<UserModel> insertUserIntroduced( long idUserBroker,
                                                long idUserEmployer,
                                                long idUserWorker );
    }



    interface Post {

        Single<List<JobModel>> fetchPosts();


        Single<JobModel> removePostById( long idJob );
    }



    interface Message {

        long getIdCurrentUser();

        Single<List<MessageModel>> fetchMessageByIdUsers( long idSender, long idReceiver );

        Single<List<UserModel>> fetchSenderMessage( long idReceiver );

    }

}
