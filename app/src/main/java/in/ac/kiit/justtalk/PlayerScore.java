package in.ac.kiit.justtalk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class PlayerScore extends AppCompatActivity implements View.OnClickListener {

    ImageView profileimage= (ImageView)findViewById(R.id.profile_image);

    int Trait1 = getIntent().getExtras().getInt("Fluency",0);
    int Trait2 = getIntent().getExtras().getInt("Content",0);
    int Trait3 = getIntent().getExtras().getInt("TeamWork",0);
    int Trait4 = getIntent().getExtras().getInt("BodyLanguage",0);
    int Trait5 = getIntent().getExtras().getInt("Language",0);

    TextView name=(TextView)findViewById(R.id.PlayerName);
    TextView year=(TextView)findViewById(R.id.PlayerYear);
    TextView stream=(TextView)findViewById(R.id.PlayerStream);

    Button Fluency1=(Button)findViewById(R.id.Fluency1);
    Button Fluency2=(Button)findViewById(R.id.Fluency2);
    Button Fluency3=(Button)findViewById(R.id.Fluency3);
    Button Fluency4=(Button)findViewById(R.id.Fluency4);
    Button Fluency5=(Button)findViewById(R.id.Fluency5);
    Button Content1=(Button)findViewById(R.id.Content1);
    Button Content2=(Button)findViewById(R.id.Content2);
    Button Content3=(Button)findViewById(R.id.Content3);
    Button Content4=(Button)findViewById(R.id.Content4);
    Button Content5=(Button)findViewById(R.id.Content5);
    Button TW1=(Button)findViewById(R.id.TW1);
    Button TW2=(Button)findViewById(R.id.TW2);
    Button TW3=(Button)findViewById(R.id.TW3);
    Button TW4=(Button)findViewById(R.id.TW4);
    Button TW5=(Button)findViewById(R.id.TW5);
    Button BL1=(Button)findViewById(R.id.BL1);
    Button BL2=(Button)findViewById(R.id.BL2);
    Button BL3=(Button)findViewById(R.id.BL3);
    Button BL4=(Button)findViewById(R.id.BL4);
    Button BL5=(Button)findViewById(R.id.BL5);
    Button Language1=(Button)findViewById(R.id.Language1);
    Button Language2=(Button)findViewById(R.id.Language2);
    Button Language3=(Button)findViewById(R.id.Language3);
    Button Language4=(Button)findViewById(R.id.Language4);
    Button Language5=(Button)findViewById(R.id.Language5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_score2);


        Fluency1.setOnClickListener(this);
        Fluency2.setOnClickListener(this);
        Fluency3.setOnClickListener(this);
        Fluency4.setOnClickListener(this);
        Fluency5.setOnClickListener(this);

        Content1.setOnClickListener(this);
        Content2.setOnClickListener(this);
        Content3.setOnClickListener(this);
        Content4.setOnClickListener(this);
        Content5.setOnClickListener(this);

        TW1.setOnClickListener(this);
        TW2.setOnClickListener(this);
        TW3.setOnClickListener(this);
        TW4.setOnClickListener(this);
        TW5.setOnClickListener(this);

        BL1.setOnClickListener(this);
        BL2.setOnClickListener(this);
        BL3.setOnClickListener(this);
        BL4.setOnClickListener(this);
        BL5.setOnClickListener(this);

        Language1.setOnClickListener(this);
        Language2.setOnClickListener(this);
        Language3.setOnClickListener(this);
        Language4.setOnClickListener(this);
        Language5.setOnClickListener(this);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(PlayerScore.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            name.setText(personName);
            String personEmail = acct.getEmail();
            String temp= personEmail.substring(0,2);
            if(temp.equals("17"))
            {year.setText("2 nd Year");}
            if(temp.equals("18"))
            {year.setText("1 nd Year");}
            if(temp.equals("16"))
            {year.setText("3 nd Year");}
            if(temp.equals("15"))
            {year.setText("4 nd Year");}
            else
                stream.setText("No data");

            Uri personPhoto = acct.getPhotoUrl();
            LoadImageFromURL(personPhoto);
        }

    }

    private void LoadImageFromURL(Uri personPhoto) {
        profileimage.setImageURI(personPhoto);
    }

    @Override
    public void onClick(View v) {
        //Intent intent=new Intent(this,PreviousActivity.class);
        switch (v.getId())
        {
            case R.id.Fluency1 :
                Toast.makeText(this,"FLuency at 1",Toast.LENGTH_SHORT).show();
                Trait1=1;
                break;
            case R.id.Fluency2 :
                Toast.makeText(this,"FLuency at 2",Toast.LENGTH_SHORT).show();
                Trait1=2;break;
            case R.id.Fluency3 :
                Toast.makeText(this,"FLuency at 3",Toast.LENGTH_SHORT).show();
                Trait1=3;break;
            case R.id.Fluency4 :
                Toast.makeText(this,"FLuency at 4",Toast.LENGTH_SHORT).show();
                Trait1=4;break;
            case R.id.Fluency5 :
                Toast.makeText(this,"FLuency at 5",Toast.LENGTH_SHORT).show();
                Trait1=5;break;
            case R.id.Content1 :
                Toast.makeText(this,"Content at 1",Toast.LENGTH_SHORT).show();
                Trait2=1;
                break;
            case R.id.Content2 :
                Toast.makeText(this,"Content at 2",Toast.LENGTH_SHORT).show();
                Trait2=2;
                break;
            case R.id.Content3 :
                Toast.makeText(this,"Content at 3",Toast.LENGTH_SHORT).show();
                Trait2=3;
                break;
            case R.id.Content4 :
                Toast.makeText(this,"Content at 4",Toast.LENGTH_SHORT).show();
                Trait2=4;
                break;
            case R.id.Content5 :
                Toast.makeText(this,"Content at 5",Toast.LENGTH_SHORT).show();
                Trait2=5;
                break;

            case R.id.TW1 :
                Toast.makeText(this,"Team Work at 1",Toast.LENGTH_SHORT).show();
                Trait3=1;
                break;

            case R.id.TW2 :
                Toast.makeText(this,"Team Work at 2",Toast.LENGTH_SHORT).show();
                Trait3=2;
                break;
            case R.id.TW3 :
                Toast.makeText(this,"Team Work at 3",Toast.LENGTH_SHORT).show();
                Trait3=3;
                break;
            case R.id.TW4 :
                Toast.makeText(this,"Team Work at 4",Toast.LENGTH_SHORT).show();
                Trait3=4;
                break;
            case R.id.TW5 :
                Toast.makeText(this,"Team Work at 5",Toast.LENGTH_SHORT).show();
                Trait3=5;
                break;
            case R.id.BL1 :
                Toast.makeText(this,"Body Language at 1",Toast.LENGTH_SHORT).show();
                Trait4=1;
                break;

            case R.id.BL2 :
                Toast.makeText(this,"Body Language at 2",Toast.LENGTH_SHORT).show();
                Trait4=2;
                break;
            case R.id.BL3 :
                Toast.makeText(this,"Body Language at 3",Toast.LENGTH_SHORT).show();
                Trait4=3;
                break;
            case R.id.BL4 :
                Toast.makeText(this,"Body Language at 4",Toast.LENGTH_SHORT).show();
                Trait4=4;
                break;
            case R.id.BL5 :
                Toast.makeText(this,"Body Language at 5",Toast.LENGTH_SHORT).show();
                Trait4=5;
                break;

            case R.id.Language1 :
                Toast.makeText(this,"Language at 1",Toast.LENGTH_SHORT).show();
                Trait5=1;
                break;
            case R.id.Language2 :
                Toast.makeText(this,"Language at 2",Toast.LENGTH_SHORT).show();
                Trait5=2;
                break;
            case R.id.Language3 :
                Toast.makeText(this,"Language at 3",Toast.LENGTH_SHORT).show();
                Trait5=3;
                break;
            case R.id.Language4 :
                Toast.makeText(this,"Language at 4",Toast.LENGTH_SHORT).show();
                Trait5=4;
                break;
            case R.id.Language5 :
                Toast.makeText(this,"Language at 5",Toast.LENGTH_SHORT).show();
                Trait5=5;
                break;
        }
        //intent.putExtra("Fluency", Trait1);
        //intent.putExtra("Content", Trait2);
        //intent.putExtra("Team Work", Trait3);
        //intent.putExtra("Body Language", Trait4);
        //intent.putExtra("Language", Trait5);
    }

}
