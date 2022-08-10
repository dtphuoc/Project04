package com.example.onlytest;

//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlytest.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText fullname;
    EditText email;
    EditText phone;
    EditText password;
    EditText conPassword;

    Button loginNowBtn;
    TextView registerBtn;
    TextView forgetBtn;


    FirebaseAuth auth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shopdemo-8c277-default-rtdb.firebaseio.com/");

    DatabaseReference table_users = FirebaseDatabase.getInstance().getReference("Users");

    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        conPassword = findViewById(R.id.conPassword);

        registerBtn = findViewById(R.id.sign_up_button);
        loginNowBtn = findViewById(R.id.sign_in_button);
        forgetBtn = findViewById(R.id.btn_reset_password);


        auth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(view -> {
            createUser();
        });

        loginNowBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });

        forgetBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
        });
    }

    private void createUser() {
        String fullname = this.fullname.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String pass = password.getText().toString();
        String conPass = this.conPassword.getText().toString();
        String role = "buyer";
        String site_id = "0";
        if (fullname.isEmpty()) {
            this.fullname.setError("Full Name is required !");
            this.fullname.requestFocus();

        } else if (email.isEmpty()) {
            this.email.setError("Email is required !");
            this.email.requestFocus();

        } else if (phone.isEmpty()) {
            this.phone.setError("Phone is required !");
            this.phone.requestFocus();

        } else if (phone.length() < 9 ) {
            this.phone.setError("Phone length 10 !");
            this.phone.requestFocus();

        } else if ( phone.length() > 11) {
            this.phone.setError("Phone length 10 !");
            this.phone.requestFocus();

        } else if (pass.isEmpty()) {
            this.password.setError("Pass is required !");
            this.password.requestFocus();

        } else if (pass.length() < 6) {
            this.password.setError("Pass length min 6 !");
            this.password.requestFocus();

        } else if (conPass.isEmpty()) {
            this.conPassword.setError("Confirm is required !");
            this.conPassword.requestFocus();

        } else if (!conPass.equals(pass)) {
            this.conPassword.setError("Password Are Not Matching !");
            this.conPassword.requestFocus();

        } else {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "User Registered Successfully ", Toast.LENGTH_SHORT).show();
                        //insert to db
                        User userObject = new User(email, phone, fullname, role ,site_id);
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        databaseReference.child("Users").child(firebaseUser.getUid()).setValue(userObject);
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registered Error :" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
