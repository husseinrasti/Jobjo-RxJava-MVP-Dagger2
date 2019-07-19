package ir.hosseinrasti.app.jobjo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.utils.Config;
import ir.hosseinrasti.app.jobjo.utils.Util;

/**
 * Created by Hossein on 5/24/2018.
 */

public class SessionPref {

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ACCESS = "accessibility";
    private static final String KEY_IS_LOGIN = "IsLoggedIn";
    private static final String KEY_PIC = "picUser";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    public SessionPref( Context context ) {
        this.context = context;
        pref = context.getSharedPreferences( Config.NAME_SESSION_PREF, Context.MODE_PRIVATE );
    }

    public void createLoginSession( UserModel user ) {
        editor = pref.edit();
        editor.putBoolean( KEY_IS_LOGIN, true );
        editor.putString( KEY_USERNAME, user.getUsername() );
        editor.putString( KEY_FULLNAME, user.getFullname() );
        editor.putString( KEY_EMAIL, user.getEmail() );
        editor.putString( KEY_ACCESS, user.getAccessibility() );
        editor.putString( KEY_PIC, user.getPicUser() );
        editor.putLong( KEY_ID, user.getId() );
        editor.apply();
    }

    public UserModel getUserDetails() {
        UserModel user = new UserModel();
        user.setUsername( pref.getString( KEY_USERNAME, null ) );
        user.setFullname( pref.getString( KEY_FULLNAME, null ) );
        user.setAccessibility( pref.getString( KEY_ACCESS, null ) );
        user.setEmail( pref.getString( KEY_EMAIL, null ) );
        user.setPicUser( pref.getString( KEY_PIC, null ) );
        user.setId( pref.getLong( KEY_ID, 0 ) );
        return user;
    }

    public long getId() {
        return pref.getLong( KEY_ID, 0 );
    }

    public String getPic() {
        return pref.getString( KEY_PIC, null );
    }

    public void setPic( String url ) {
        editor = pref.edit();
        editor.putString( KEY_PIC, url );
        editor.apply();
    }

    public String getAccess() {
        return pref.getString( KEY_ACCESS, null );
    }

    public String getFullname() {
        return pref.getString( KEY_FULLNAME, null );
    }

    public void logoutUser() {
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean( KEY_IS_LOGIN, false );
    }

    public void login( boolean value ) {
        editor = pref.edit();
        editor.putBoolean( KEY_IS_LOGIN, value );
        editor.apply();
    }
}
