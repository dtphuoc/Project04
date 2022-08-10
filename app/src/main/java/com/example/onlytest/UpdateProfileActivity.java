package com.example.onlytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlytest.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {

    private TextInputEditText fullName_edit_text;
    private TextInputEditText email_edit_text;
    private TextInputEditText phone_edit_text;

    String FULLNAME, EMAIL, PHONE;

    private String name, email, phone;

    private DatabaseReference databaseReferenceUsers;

    private FirebaseDatabase database;

    FirebaseUser user;

    FirebaseAuth auth;

    Button btn_logout, btn_update;


    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_EMAIL = "email";
    static String KEY_NAME = "name";
    static String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btn_logout = findViewById(R.id.LogOutBtn);

        btn_update = findViewById(R.id.UpdateBtn);

        Intent intent = getIntent();

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("Users");

        user = FirebaseAuth.getInstance().getCurrentUser();

        auth = FirebaseAuth.getInstance();


        fullName_edit_text = (TextInputEditText) findViewById(R.id.name_text);
        email_edit_text = (TextInputEditText) findViewById(R.id.email_text);
        phone_edit_text = (TextInputEditText) findViewById(R.id.phone_text);

        FULLNAME = intent.getStringExtra("fullname");
        EMAIL = intent.getStringExtra("email");
        PHONE = intent.getStringExtra("phone");

        fullName_edit_text.setText(FULLNAME);
        email_edit_text.setText(EMAIL);
        phone_edit_text.setText(PHONE);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(UpdateProfileActivity.this, LoginActivity.class));
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = fullName_edit_text.getText().toString();
                String phone = phone_edit_text.getText().toString();
                String email = email_edit_text.getText().toString();
                FirebaseUser firebaseUser = auth.getCurrentUser();
                User user = new User(email, fullName, phone);
                databaseReferenceUsers.child(firebaseUser.getUid()).setValue(user);

            }
        });

        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().equals(EMAIL)) {
                        fullName_edit_text.setText(dataSnapshot.child("fullname").getValue(String.class));
                        email_edit_text.setText(dataSnapshot.child("email").getValue(String.class));
                        phone_edit_text.setText(dataSnapshot.child("phone").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void update() {
        if (isFullNameChanged() || isEmailChanged() || isPhoneChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Data same can not update", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isFullNameChanged() {
        if (!FULLNAME.equals(fullName_edit_text.getText().toString())) {
            databaseReferenceUsers.child(FULLNAME).child("fullname").setValue(fullName_edit_text.getText().toString());
            FULLNAME = fullName_edit_text.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailChanged() {
        if (!EMAIL.equals(email_edit_text.getText().toString())) {
            databaseReferenceUsers.child(EMAIL).child("email").setValue(email_edit_text.getText().toString());
            EMAIL = email_edit_text.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isPhoneChanged() {
        if (!PHONE.equals(phone_edit_text.getText().toString())) {
            databaseReferenceUsers.child(PHONE).child("phone").setValue(phone_edit_text.getText().toString());
            PHONE = phone_edit_text.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    private void loadInformation(){
        fullName_edit_text.setText(name);
        email_edit_text.setText(email);
        phone_edit_text.setText(phone);
    }
}