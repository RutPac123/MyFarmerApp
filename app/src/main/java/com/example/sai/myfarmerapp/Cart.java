package com.example.sai.myfarmerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.sai.CartAdapter;
import com.example.sai.RequestUser;
import com.example.sai.com.BounceInterpolar;
import com.example.sai.com.CardAdapter;
import com.example.sai.com.Database.Database;
import com.example.sai.com.LoginBoth;
import com.example.sai.com.MainActivity;
import com.example.sai.com.Model.Order;
import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    private TextView txtTotalPrice;
    private Button placeBTN;
    private List<Order> cart= new ArrayList<>();
    private CartAdapter adapter;
    private Animation fadeIn,bounce;
    private Button clearCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.price);
        placeBTN = findViewById(R.id.PlaceOrderbtn);
        bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade);

        recyclerView.startAnimation(fadeIn);
        BounceInterpolar interpolator = new BounceInterpolar(0.2, 20);
        bounce.setInterpolator(interpolator);
        placeBTN.startAnimation(bounce);
        placeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtTotalPrice.getText().toString().equals("")){
                    Toast.makeText(Cart.this, "Zero orders into cart!", Toast.LENGTH_SHORT).show();
                }else{
                    showAlertDialog();
                }

            }
        });

        loadListItems();

        clearCart = findViewById(R.id.btnclear);
        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Cart.this)
                        .setTitle("Alert!")
                        .setMessage("Are you sure you want to clear the cart?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Database(getBaseContext()).cleanCart();
                                Toast.makeText(Cart.this, "Cart is cleared!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.clearcart)
                        .show();


            }
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
        alert.setTitle("Just there...");
        alert.setMessage("Enter your address: ");

        final EditText edtAdd = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAdd.setLayoutParams(lp);
        alert.setView(edtAdd);
        alert.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);
        alert.setPositiveButton("Place it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtAdd.getText().toString().isEmpty()){
                    Toast.makeText(Cart.this, "Empty address!", Toast.LENGTH_SHORT).show();

                }else{
                    RequestUser request = new RequestUser(
                            edtAdd.getText().toString(),
                            txtTotalPrice.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            cart
                    );
                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request);
                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(Cart.this, "Thank you for placing the order :)", Toast.LENGTH_SHORT).show();

                    finish();
                }

            }
        });
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void loadListItems() {
        cart = new Database(this).getCart();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //calculation of total

        int total =0;
        for (Order order:cart){
            total+=(Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en","in");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            txtTotalPrice.setText(fmt.format(total));
        }

    }

}
