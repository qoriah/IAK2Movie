package id.paniclabs.movies.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class Prefs {
    private SharedPreferences mSharedPreferences;


    public Prefs(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public void putStringPreferences(String key, String val) {
        mSharedPreferences.edit()
                .putString(key,val)
                .apply();

    }
    public void putIntPreferences(String key, int val) {
        mSharedPreferences.edit()
                .putInt(key,val)
                .apply();

    }
    public void putBooleanPreferences(String key, boolean val) {
        mSharedPreferences.edit()
                .putBoolean(key,val)
                .apply();

    }

    public String getStringPreferences(String key,String defaultString) {
        return mSharedPreferences.getString(key, defaultString);
    }
    public int getIntPreferences(String  key,int defaultInt) {

        return mSharedPreferences.getInt(key, defaultInt);
    }
    public boolean getBooleanPreferences(String key,boolean defaultBoolean) {
        return mSharedPreferences.getBoolean(key, defaultBoolean);
    }
}
