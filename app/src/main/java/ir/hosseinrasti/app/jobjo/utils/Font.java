package ir.hosseinrasti.app.jobjo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Hossein on 7/10/2018.
 */

public class Font {

    private Typeface typeface;
    private AssetManager assetManager;

    public Font( Context context ) {
        assetManager = context.getApplicationContext().getAssets();
    }

    public void titr( TextView view ) {
        typeface = Typeface.createFromAsset( assetManager, "fonts/titr.ttf" );
        view.setTypeface( typeface );
    }

    public void iran( TextView view ) {
        typeface = Typeface.createFromAsset( assetManager, "fonts/iran_sans.ttf" );
        view.setTypeface( typeface );
    }

    public void nazanin( TextView view ) {
        typeface = Typeface.createFromAsset( assetManager, "fonts/nazanin.ttf" );
        view.setTypeface( typeface );
    }

    public void ubuntu( TextView view ) {
        typeface = Typeface.createFromAsset( assetManager, "fonts/ubuntu.ttf" );
        view.setTypeface( typeface );
    }

    public void yekan( TextView view ) {
        typeface = Typeface.createFromAsset( assetManager, "fonts/yekan.ttf" );
        view.setTypeface( typeface );
    }

    public void koodak( TextView view ) {
        typeface = Typeface.createFromAsset( assetManager, "fonts/koodak.ttf" );
        view.setTypeface( typeface );
    }


}
