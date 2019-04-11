package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoInternetAcitivty extends AppCompatActivity {
    int status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_acitivty);
        hideNavigationBar();

        Intent i = getIntent();
        status = i.getExtras().getInt("status");
        if(status==0){
            ((Button)findViewById(R.id.exitBtn)).setText("EXIT");
        }

    }

    public void exitApp(View view) {
        if(status==1){
            startActivity(new Intent(NoInternetAcitivty.this, ProfileActivity.class));
        }else if(status==2){
            startActivity(new Intent(NoInternetAcitivty.this, HomeActivity.class));
        }
        finishAffinity();
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