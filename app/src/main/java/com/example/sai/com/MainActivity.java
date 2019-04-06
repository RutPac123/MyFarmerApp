package com.example.sai.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sai.com.chatbot.MainActivityChat;
import com.example.sai.com.schemes.Schemes;
import com.example.sai.myfarmerapp.R;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wenchao.cardstack.CardStack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CardStack.CardEventListener {

    private FirebaseAuth firebaseAuth;
    private CoordinatorLayout coordinatorLayout;
    private Boolean exit = false;
    private DrawerLayout drawer;
    private String nametxt;
    private TextView usrname;
    private DatabaseReference databaseReference;
    private TextView usrmail;
    private GoogleApiClient googleApiClient;
    private Firebase firebase;
    private ImageView image;
    private CardStack cardStack;
    private CardAdapter cardAdapter;
    private ImageView mImg;
    private TextView tempTXT;
    private TextView unitTXT;
    private Animation anime;
    private CardView cardTools;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NetworkHelper helper = new NetworkHelper();
        helper.checkNet(coordinatorLayout,this);
        firebaseAuth= FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // weather data retrieval
        mImg = findViewById(R.id.img);
        tempTXT = findViewById(R.id.temp);
        unitTXT = findViewById(R.id.unit);
        anime = AnimationUtils.loadAnimation(this,R.anim.fade);
        Weather weather = new Weather();
        weather.giveWeather(MainActivity.this,mImg,tempTXT,unitTXT,anime);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivityChat.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerview = navigationView.getHeaderView(0);
        usrname = headerview.findViewById(R.id.uname);
        usrmail = headerview.findViewById(R.id.umail);
        image = headerview.findViewById(R.id.imageView1);

        initimages();
        cardStack = findViewById(R.id.stackview);
        cardStack.setContentResource(R.layout.card_layout);
        cardStack.setAdapter(cardAdapter);
        cardStack.setListener(this);

        cardTools = findViewById(R.id.card4);
        cardTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Tools.class));
            }
        });

        Glide.with(getApplicationContext()).load(firebaseAuth.getCurrentUser().getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(image);
        usrname.setText(firebaseAuth.getCurrentUser().getDisplayName());
        usrmail.setText(firebaseAuth.getCurrentUser().getEmail());


        SharedPreferences preferences = getSharedPreferences("NAME",MODE_PRIVATE);
        nametxt = preferences.getString("uName",null);


        databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("User name");
        databaseReference.setValue(nametxt);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());



    }

    private void initimages() {
        cardAdapter = new CardAdapter(getApplicationContext(),0);
        cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);
        cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);
        cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);
        cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);cardAdapter.add(R.mipmap.farmeraccident);
        cardAdapter.add(R.mipmap.fasal);
        cardAdapter.add(R.mipmap.kisansanman);
        cardAdapter.add(R.mipmap.pashu);
        cardAdapter.add(R.mipmap.yojna);





    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit) {
            finish(); // finish activity
        } else if (!drawer.isDrawerOpen(GravityCompat.START)){ // if drawer isn't opened
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           sendFeedback();
           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_blogs) {
            startActivity(new Intent(MainActivity.this,ExecuteRSS.class));
        } else if (id == R.id.nav_change_pno) {
            startActivity(new Intent(MainActivity.this,OTP.class));
        } else if (id == R.id.nav_group) {
            startActivity(new Intent(MainActivity.this,MainGroupChat.class));
        } else if (id == R.id.nav_share) {
            shareIt();
        } else if (id == R.id.nav_signout) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Alert!")
                    .setMessage("Are you sure you want to sign out?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, LoginBoth.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "You are signed out!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.alert)
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void shareIt(){
        final String link = "https://play.google.com/store";
        Intent shareintnet = new Intent(Intent.ACTION_SEND);
        shareintnet.putExtra(Intent.EXTRA_TEXT,"Hey download this useful farming related application  -> \n"+ link);
        shareintnet.setType("text/plain");
        startActivity(shareintnet);
    }

    private void sendFeedback(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={"developer@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
        intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    @Override
    public boolean swipeEnd(int i, float v) {
        return v > 300;
    }

    @Override
    public boolean swipeStart(int i, float v) {
        return true;
    }

    @Override
    public boolean swipeContinue(int i, float v, float v1) {
        return true;
    }

    @Override
    public void discarded(int i, int i1) {

    }

    @Override
    public void topCardTapped() {
        startActivity(new Intent(MainActivity.this, Schemes.class));
    }
}
