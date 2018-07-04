package br.ufg.inf.eadep.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private Context context;
    private SharedPreferences sharedPref;
    private String FILE_NAME = "eadep.preferences";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String KEY_EMAIL = "email";

    public Preferences(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(FILE_NAME, MODE);

        editor = sharedPref.edit();
    }

    public void insertEmail(String userIdentifier){
        editor.putString(KEY_EMAIL, userIdentifier);
        editor.commit();
    }

    public String getEmail() {
        return sharedPref.getString(KEY_EMAIL, null);
    }
}
