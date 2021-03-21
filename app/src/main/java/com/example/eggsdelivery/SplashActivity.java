package com.example.eggsdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    public static final String LANG = "LANGUAGE";
    public static final String LANG_VALUE = "VALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            currentUser.reload();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(LANG, MODE_PRIVATE);

        String lang = sharedPreferences.getString(LANG_VALUE, "ar");
        setLocale(lang);

        if(lang.equals("ar")){
            ((TextView)findViewById(R.id.txt_splash_stmt)).setText("بيض طازج لحد باب بيتك :)");
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

                finish();
            }
        }, 2000);
    }

    public void setLocale(String lang) {
        String languageToLoad  = lang;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}