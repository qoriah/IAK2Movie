package id.paniclabs.movies.Utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class Utils {

    public static Typeface getNotoTypeFace(Context context) {
        AssetManager assetManager = context.getAssets();
        return Typeface.createFromAsset(assetManager, "NotoSerif-Regular.ttf");
    }
    public static Typeface getBreeTypeFace(Context context) {
        AssetManager assetManager = context.getAssets();
        return Typeface.createFromAsset(assetManager, "BreeSerif-Regular.ttf");
    }
    public static Typeface getSatifyTypeFace(Context context) {
        AssetManager assetManager = context.getAssets();
        return Typeface.createFromAsset(assetManager, "Satisfy-Regular.ttf");
    }
    public static Typeface getRobotoTypeface(Context context) {
        AssetManager assetManager = context.getAssets();
        return Typeface.createFromAsset(assetManager, "RobotoSlab-Regular.ttf");
    }
}
