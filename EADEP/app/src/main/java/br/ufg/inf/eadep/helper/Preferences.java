package br.ufg.inf.eadep.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private Context context;
    private static SharedPreferences sharedPref;
    private String FILE_NAME = "eadep.preferences";
    private int MODE = 0;
    private static SharedPreferences.Editor editor;

    private final String EMAIL_KEY = "email";
    private final String SUBJECT_KEY = "disciplina";
    private final String ACTIVITY_KEY = "tarefas";

    public Preferences(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        editor = sharedPref.edit();
    }

    public void saveUserEmail(String email){
        editor.putString(EMAIL_KEY, email);
        editor.commit();
    }

    public void saveActivityTitle(String title){
        editor.putString(ACTIVITY_KEY, title);
        editor.commit();
    }

    public void saveSubject(String subject){
        editor.putString(SUBJECT_KEY, subject);
        editor.commit();
    }

    public String getEmail(){
        return sharedPref.getString(EMAIL_KEY, "");
    }

    public String getSubject(){
        return sharedPref.getString(SUBJECT_KEY, "");
    }

    public String getActivityTitle(){
        return sharedPref.getString(ACTIVITY_KEY, "");
    }
}
