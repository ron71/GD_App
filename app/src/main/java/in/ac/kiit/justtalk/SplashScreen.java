package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        hideNavigationBar();
        transition();
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
