package com.example.sai.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai.myfarmerapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginBoth extends AppCompatActivity {

    private Button loginbtn,  googleSignInBtn;
    private static String TAG = "TAG";
    private GoogleSignInClient mgoogleSignInClient;
    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progress;
    private TextView signuptxt;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        updateUI(user);

    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log_google_custom);

            loginbtn = findViewById(R.id.custom_login);
            googleSignInBtn = findViewById(R.id.googlelogin);
            signuptxt = findViewById(R.id.custom_sign_up);

            signuptxt.setOnClickListener(v -> {
                startActivity(new Intent(LoginBoth.this,Register1.class));
            });

            loginbtn.setOnClickListener(v -> startActivity(new Intent(LoginBoth.this, Login.class)));

            googleSignInBtn = findViewById(R.id.googlelogin);
            firebaseAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mgoogleSignInClient = GoogleSignIn.getClient(this, gso);

            googleSignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress = ProgressDialog.show(LoginBoth.this, "Please Wait..",
                            "Authenticating...", true);
                    progress.show();
                    startActivityForResult(mgoogleSignInClient.getSignInIntent(), 101);
                }
            });


        }

        private void updateUI(FirebaseUser user) {
            if (user != null) {
                Intent intent = new Intent(LoginBoth.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);

                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();

                    // ...
                }
            }
        }


        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                progress.hide();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginBoth.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginBoth.this, "login failed :(", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                                progress.hide();
                            }

                            // ...
                        }
                    });


        }
    }
