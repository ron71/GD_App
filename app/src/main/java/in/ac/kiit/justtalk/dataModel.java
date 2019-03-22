package in.ac.kiit.justtalk;


import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class dataModel {

     int rolltv,tv1,tv2,tv3,tv4,tv5;

    public dataModel(int rolltv, int tv1, int tv2, int tv3, int tv4, int tv5) {
        this.rolltv = rolltv;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.tv5 = tv5;
    }

    public int getRolltv() {
        return rolltv;
    }

    public void setRolltv(int rolltv) {
        this.rolltv = rolltv;
    }

    public int getTv1() {
        return tv1;
    }

    public void setTv1(int tv1) {
        this.tv1 = tv1;
    }

    public int getTv2() {
        return tv2;
    }

    public void setTv2(int tv2) {
        this.tv2 = tv2;
    }

    public int getTv3() {
        return tv3;
    }

    public void setTv3(int tv3) {
        this.tv3 = tv3;
    }

    public int getTv4() {
        return tv4;
    }

    public void setTv4(int tv4) {
        this.tv4 = tv4;
    }

    public int getTv5() {
        return tv5;
    }

    public void setTv5(int tv5) {
        this.tv5 = tv5;
    }

    private ArrayList<dataModel> dataModels(){
        ArrayList<dataModel> list= new ArrayList<>();

        //Bundle b=getIntent().getExtras();
        //Add content received  to arraylist.
        return list;
    }

}
