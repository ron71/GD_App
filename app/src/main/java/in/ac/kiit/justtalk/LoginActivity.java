package in.ac.kiit.justtalk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import in.ac.kiit.justtalk.models.AppUser;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    SignInButton signInButton;
    FirebaseAuth mAuth;

    private final int RC_SIGN_IN = 9001;
    GoogleSignInClient mGoogleSignInClient;
    String TAG = "TAG";
    FirebaseAuth.AuthStateListener mAuthStateListener;
    GoogleApiClient mGoogleApiClient;
    Intent signInIntent;



    private void signIn() {
        signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        //This listener is responsible to track down the authentication state being changed
        // from sign in to sign Out or vice versa.
        // means we will add a listener that will take to another activity if signed in or if not keep it ther.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ConnectivityHelper.isConnectedToNetwork(this)) {
            Intent i = new Intent(this, NoInternetAcitivty.class);
            i.putExtra("status", 0);
            startActivity(i);
            finishAffinity();

        }

        setContentView(R.layout.activity_login);
        signInButton = findViewById(R.id.signInBtn);
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){

                    if(firebaseAuth.getCurrentUser().getEmail().contains("@kiit.ac.in")) {

                        //Intent navigation
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                    else{
                        mAuth.signOut();
                    }
                }
                else{

                   // Toast.makeText(LoginActivity.this, "No Signed", Toast.LENGTH_LONG).show();
                }
            }
        };

        signInButton = findViewById(R.id.signInBtn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id_))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }else{
            mGoogleSignInClient.signOut();
            mGoogleSignInClient.revokeAccess();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("data",result.getStatus().toString());
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                String email = account.getEmail();
                Log.e("Email", email);
                if(!email.contains("@kiit.ac.in")){
                    mGoogleSignInClient.signOut();
                    mGoogleSignInClient.revokeAccess();
                    Snackbar.make(signInButton, "PLease sign in using KIIT EMAIL ID.", Snackbar.LENGTH_LONG).show();
                    signIn();

                }else {
                    authWithGoogle(account);
                }
            }
            else{
                Log.e("Erorr", "onActivityResult");
                startActivity(new Intent(LoginActivity.this, PromptingErrorActivity.class));
                finish();
            }
        }
    }



    private void authWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if (isNotAvailableInLocalDB()){
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Auth Error",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    boolean isNotAvailableInLocalDB(){

        String sql = "SELECT * FROM USER;";
        SQLiteDatabase db = openOrCreateDatabase("gddb", MODE_PRIVATE, null);
        Cursor c = db.rawQuery(sql,null);
        if(!c.moveToNext()){
            // no entries
            return true;
        }
        return false;

    }


}
