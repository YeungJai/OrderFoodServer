package com.example.orderfoodserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfoodserver.Common.Common;
import com.example.orderfoodserver.Model.Users;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText edtName, edtPassword;
    ImageButton btn_sign;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btn_sign = findViewById(R.id.btn_sign);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(edtName.getText().toString(),edtPassword.getText().toString());
            }
        });



    }

    private void signInUser(String name, String password) {
        ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();


        final String localName = name;
        final String loacalPassword = password;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localName).exists())
                {
                    mDialog.dismiss();
                    Users user = dataSnapshot.child(localName).getValue(Users.class);
                    user.setName(localName);
                    if (Boolean.parseBoolean(user.getIsStaff()))
                    {
                        if (user.getPassword().equals(loacalPassword))
                        {
                            Intent login = new Intent(SignIn.this, Home.class);
                            Common.currentUser=user;
                            startActivity(login);
                            finish();
                        }
                        else
                            Toast.makeText(SignIn.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Please login with Staff account", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }
}
