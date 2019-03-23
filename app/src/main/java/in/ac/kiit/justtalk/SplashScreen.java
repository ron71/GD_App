package in.ac.kiit.justtalk;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        initialiseDatabase();
        transition();


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        if (networkInfo != null && networkInfo.isConnected()) {
//            transition();
//        } else {
//            alert();
//        }
//    }

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

    public void alert(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(SplashScreen.this);
        a_builder.setMessage(R.string.Alert_msg).setCancelable(false)
                .setNegativeButton(R.string.neg_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent openSettingsApp = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                        startActivity(openSettingsApp);
                    }
                });
        AlertDialog alertDialog = a_builder.create();
        alertDialog.setTitle(R.string.Alert_title);
        alertDialog.show();
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

