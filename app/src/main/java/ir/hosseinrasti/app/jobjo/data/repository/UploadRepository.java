package ir.hosseinrasti.app.jobjo.data.repository;

import java.io.File;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.data.network.CountingRequestBody;
import ir.hosseinrasti.app.jobjo.utils.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Hossein on 7/15/2018.
 */

public class UploadRepository implements DataSource.Upload {

    private ApiService apiService;
    private SessionPref pref;

    private String body;
    private boolean isProfile;
    private long id;

    @Override
    public void isProfile( boolean profile ) {
        this.isProfile = profile;
    }

    @Override
    public void setId( long id ) {
        this.id = id;
    }

    @Override
    public String getBody() {
        return body;
    }

    public UploadRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    private void checkProfile() {
        if ( isProfile ) {
            id = pref.getId();
        }
    }

    @Override
    public Flowable<Double> uploadImage( String filePath ) {
        checkProfile();
        return Flowable.create( emitter -> {
            try {
                ResponseBody response = null;
                if ( isProfile ) {
                    response = apiService.postImage( createMultipartBody( filePath, emitter ),
                            createRequestBody( String.valueOf( id ) ),
                            createRequestBody( String.valueOf( isProfile ) ) ).blockingGet();
                } else {
                    response = apiService.postImage( createMultipartBody( filePath, emitter ) ).blockingGet();
                }
                body = response.string();
                emitter.onComplete();
            } catch ( Exception e ) {
                emitter.tryOnError( e );
            }
        }, BackpressureStrategy.LATEST );
    }

    private MultipartBody.Part createMultipartBody( String filePath, FlowableEmitter<Double> emitter ) {
        File file = new File( filePath );
        return MultipartBody.Part.createFormData( "upload", file.getName(),
                createCountingRequestBody( file, emitter ) );
    }

    private RequestBody createCountingRequestBody( File file, FlowableEmitter<Double> emitter ) {
        RequestBody requestBody = createRequestBody( file );
        return new CountingRequestBody( requestBody, ( bytesWritten, contentLength ) -> {
            double progress = ( 1.0 * bytesWritten ) / contentLength;
            emitter.onNext( progress );
        } );
    }

    @Override
    public Single<ResponseBody> uploadImageWithoutProgress( String filePath ) {
        checkProfile();
        return apiService.postImage( createMultipartBody( filePath ),
                createRequestBody( String.valueOf( id ) ), createRequestBody( String.valueOf( isProfile ) ) );
    }

    private MultipartBody.Part createMultipartBody( String filePath ) {
        File file = new File( filePath );
        RequestBody requestBody = createRequestBody( file );
        return MultipartBody.Part.createFormData( "upload", file.getName(), requestBody );
    }

    private RequestBody createRequestBody( File file ) {
        return RequestBody.create( MediaType.parse( "image/*" ), file );
    }

    private RequestBody createRequestBody( String str ) {
        return RequestBody.create( MediaType.parse( "text/plain" ), str );
    }
}
