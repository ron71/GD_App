package in.ac.kiit.justtalk;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    void initialiseDatabase(){
        String sql = "CREATE TABLE IF NOT EXISTS USER(" +
                "userId varchar(20) PRIMARY KEY," +
                "userName varchar(30) NOT NULL," +
                "userEmail varchar(30) NOT NULL," +
                "branch varchar(30) NOT NULL," +
                "type varchar(10) NOT NULL," +
                "year integer(1)," +
                "members varchar(100)," +
                "accessCode integer(6));";

        SQLiteDatabase db = openOrCreateDatabase("gddb", MODE_PRIVATE,null);
        db.execSQL(sql);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        hideNavigationBar();
        transition();
        initialiseDatabase();
    }

    public void transition(){
        Thread t =  new Thread(){
            public void run(){
                try{
                    sleep(2000);

                }
                catch(Exception e){
                    System.out.println(e);
                }

                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        };
        t.start();


    }

    public void hideNavigationBar() {
        this.getWindow().getDecorView().
                setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }
}
