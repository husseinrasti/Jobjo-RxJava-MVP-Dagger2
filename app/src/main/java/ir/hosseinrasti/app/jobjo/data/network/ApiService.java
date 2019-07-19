package ir.hosseinrasti.app.jobjo.data.network;


import java.util.List;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.entity.MessageModel;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.utils.URLs;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Hossein on 5/20/2018.
 */

public interface ApiService {

    @Multipart
    @POST(URLs.uploadImage)
    Single<ResponseBody> postImage( @Part MultipartBody.Part image );

    @Multipart
    @POST(URLs.uploadImage)
    Single<ResponseBody> postImage( @Part MultipartBody.Part image,
                                    @Part("id") RequestBody id,
                                    @Part("isProfile") RequestBody isProfile );

    @FormUrlEncoded
    @POST(URLs.login)
    Single<UserModel> login( @Field("username") String username, @Field("password") String password );

    @FormUrlEncoded
    @POST(URLs.register)
    Single<UserModel> register( @Field("username") String username,
                                @Field("password") String password,
                                @Field("email") String email,
                                @Field("fullname") String fullname );

    @FormUrlEncoded
    @POST(URLs.checkValidateUsername)
    Single<UserModel> checkValidateUsername( @Field("username") String username );

    @GET(URLs.fetchAllJob)
    Single<List<JobModel>> fetchAllJob();

    @FormUrlEncoded
    @POST(URLs.fetchAllMyJobs)
    Single<List<JobModel>> fetchAllMyJobs( @Field("idUser") long idUser );

    @FormUrlEncoded
    @POST(URLs.fetchUserModelByIdJobApply)
    Single<List<UserModel>> fetchUserModelByIdJobApply( @Field("idJob") long idJob );

    @FormUrlEncoded
    @POST(URLs.fetchJobApplyById)
    Single<List<JobModel>> fetchJobApplyById( @Field("id") long id );

    @FormUrlEncoded
    @POST(URLs.insertJobApply)
    Single<JobModel> insertJobApply( @Field("idJob") long idJob,
                                     @Field("idUserEmployer") long idUserEmployer,
                                     @Field("idUserWorker") long idUserWorker );

    @FormUrlEncoded
    @POST(URLs.removeJobApply)
    Single<JobModel> removeJobApply( @Field("idJob") long idJob,
                                     @Field("idUserEmployer") long idUserEmployer,
                                     @Field("idUserWorker") long idUserWorker );

    @FormUrlEncoded
    @POST(URLs.insertUserIntroduced)
    Single<UserModel> insertUserIntroduced( @Field("idUserBroker") long idUserBroker,
                                            @Field("idUserEmployer") long idUserEmployer,
                                            @Field("idUserWorker") long idUserWorker );

    @FormUrlEncoded
    @POST(URLs.fetchUserIntroduced)
    Single<List<UserModel>> fetchUserIntroduced( @Field("idUserEmployer") long idUserEmployer );

    @FormUrlEncoded
    @POST(URLs.fetchUserModel)
    Single<UserModel> fetchUserModel( @Field("username") String username );

    @GET(URLs.fetchAllUserModel)
    Single<List<UserModel>> fetchAllUserModel();

    @FormUrlEncoded
    @POST(URLs.updateUserModel)
    Single<UserModel> updateUserDetails( @Field("id") long userId,
                                         @Field("myExpertise") String myExpertise,
                                         @Field("workExperience") String workExperience,
                                         @Field("age") String age,
                                         @Field("accessibility") String accessibility,
                                         @Field("telephone") String telephone,
                                         @Field("professionalKnowledge") String professionalKnowledge,
                                         @Field("description") String description,
                                         @Field("favorite") String favorite,
                                         @Field("education") String education,
                                         @Field("gender") String gender,
                                         @Field("city") String city,
                                         @Field("school") String school,
                                         @Field("address") String address,
                                         @Field("married") String married );

    @FormUrlEncoded
    @POST(URLs.removeJobById)
    Single<JobModel> removeJobById( @Field("id") long id );

    @FormUrlEncoded
    @POST(URLs.updateJobById)
    Single<JobModel> updateJobById( @Field("id") long id,
                                    @Field("nameJob") String nameJob,
                                    @Field("idWorkGroup") long idWorkGroup,
                                    @Field("idUserCreator") long idUserCreator,
                                    @Field("nameWorkGroup") String nameWorkGroup,
                                    @Field("nameJobCreator") String nameJobCreator,
                                    @Field("address") String address,
                                    @Field("typeOfJob") String typeOfJob,
                                    @Field("hoursOfWork") String hoursOfWork,
                                    @Field("benefit") String benefit,
                                    @Field("travelOfWork") String travelOfWork,
                                    @Field("gender") String gender,
                                    @Field("education") String education,
                                    @Field("lang") String language,
                                    @Field("age") String age,
                                    @Field("jobDescription") String jobDescription,
                                    @Field("professionalKnowledge") String professionalKnowledge,
                                    @Field("software") String software,
                                    @Field("workExperience") String workExperience,
                                    @Field("militaryService") String militaryService,
                                    @Field("picJob") String picJob );

    @FormUrlEncoded
    @POST(URLs.insertJobModel)
    Single<JobModel> insertJobModel( @Field("idWorkGroup") long idWorkGroup,
                                     @Field("idUserCreator") long idUserCreator,
                                     @Field("nameJob") String nameJob,
                                     @Field("picUser") String picUser,
                                     @Field("picJob") String picJob,
                                     @Field("nameWorkGroup") String nameWorkGroup,
                                     @Field("nameJobCreator") String nameJobCreator,
                                     @Field("address") String address,
                                     @Field("typeOfJob") String typeOfJob,
                                     @Field("hoursOfWork") String hoursOfWork,
                                     @Field("benefit") String benefit,
                                     @Field("travelOfWork") String travelOfWork,
                                     @Field("gender") String gender,
                                     @Field("education") String education,
                                     @Field("lang") String language,
                                     @Field("age") String age,
                                     @Field("jobDescription") String jobDescription,
                                     @Field("professionalKnowledge") String professionalKnowledge,
                                     @Field("software") String software,
                                     @Field("workExperience") String workExperience,
                                     @Field("militaryService") String militaryService );

    @GET(URLs.fetchCategory)
    Single<List<WorkGroupModel>> fetchAllCategory();

    @FormUrlEncoded
    @POST(URLs.removeCategory)
    Single<WorkGroupModel> removeCategory( @Field("id") long id );

    @FormUrlEncoded
    @POST(URLs.insertCategory)
    Single<WorkGroupModel> insertCategory( @Field("workGroup") String workGroup );

    @FormUrlEncoded
    @POST(URLs.fetchJobByCategory)
    Single<List<JobModel>> fetchJobByCategory( @Field("idWorkGroup") long idWorkGroup );

    @FormUrlEncoded
    @POST(URLs.search)
    Single<List<JobModel>> search( @Field("search") String search );

    @FormUrlEncoded
    @POST(URLs.searchUser)
    Single<List<UserModel>> searchUser( @Field("search") String search );

    @FormUrlEncoded
    @POST(URLs.insertMessage)
    Single<MessageModel> insertMessage( @Field("idUserSender") long idUserSender,
                                        @Field("idUserReceiver") long idUserReceiver,
                                        @Field("createdAt") String createdAt,
                                        @Field("message") String message );

    @FormUrlEncoded
    @POST(URLs.fetchMessage)
    Single<List<MessageModel>> fetchMessage( @Field("idUserSender") long idUserSender,
                                             @Field("idUserReceiver") long idUserReceiver );

    @FormUrlEncoded
    @POST(URLs.fetchSenderMessage)
    Single<List<UserModel>> fetchSenderMessage( @Field("idUserReceiver") long idUserReceiver );

    @FormUrlEncoded
    @POST(URLs.fetchUserById)
    Single<UserModel> fetchUserById( @Field("id") long idUser );

    @FormUrlEncoded
    @POST(URLs.fetchComments)
    Single<List<CommentModel>> fetchComments( @Field("idJob") long idJob );

    @FormUrlEncoded
    @POST(URLs.postComment)
    Single<CommentModel> postComment( @Field("idUser") long idUser,
                                      @Field("nameUser") String nameUser,
                                      @Field("comment") String comment,
                                      @Field("createdAt") String createdAt,
                                      @Field("urlPicUser") String urlPicUser,
                                      @Field("idJob") long idJob );
}
