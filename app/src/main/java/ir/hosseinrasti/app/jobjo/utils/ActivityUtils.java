package ir.hosseinrasti.app.jobjo.utils;

import android.support.annotation.NonNull;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentTransaction;

/**
 * Created by Hossein on 7/12/2018.
 */

public class ActivityUtils {

    public static void addFragmentToActivity( @NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId, String tag ) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace( frameId, fragment, tag );
        transaction.commit();
    }
}
