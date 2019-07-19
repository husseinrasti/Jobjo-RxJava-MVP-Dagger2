package ir.hosseinrasti.app.jobjo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.HttpException;

/**
 * Created by Hossein on 5/24/2018.
 */

public class Util {

    public static String getCurrentDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm" );
        return formatter.format( today );
    }


    public static void whiteNotificationBar( Activity activity, View view, int color ) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility( flags );
            activity.getWindow().setStatusBarColor( color );
        }
    }

    public static void showError( CoordinatorLayout coordinatorLayout, Throwable e ) {
        String message = "";
        try {
            if ( e instanceof IOException ) {
                message = "مشکلی در دریافت اطلاعات پیش آمده!";
            } else if ( e instanceof HttpException ) {
                HttpException error = ( HttpException ) e;
                String errorBody = error.response().errorBody().string();
                JSONObject jObj = new JSONObject( errorBody );

                message = jObj.getString( "error" );
            }
        } catch ( IOException e1 ) {
            e1.printStackTrace();
        } catch ( JSONException e1 ) {
            e1.printStackTrace();
        } catch ( Exception e1 ) {
            e1.printStackTrace();
        }

        if ( TextUtils.isEmpty( message ) ) {
            message = "خطای نامشخص!";
        }

        Snackbar snackbar = Snackbar.make( coordinatorLayout, message, Snackbar.LENGTH_LONG );

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    public static void log( String message ) {
        Log.i( Config.TAG_APP, message );
    }


    public static void showDialogAbout( Context context ) {
        Toast.makeText( context, "این برنامه توسط حسین راستی برای مسابقات فناورد ساخته شده.", Toast.LENGTH_LONG ).show();
    }
}
