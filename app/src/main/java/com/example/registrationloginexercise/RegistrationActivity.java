package com.example.registrationloginexercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        Intent intent = getIntent();

        EditText email = (EditText)findViewById(R.id.registerEmail);
        EditText password = (EditText)findViewById(R.id.registerPassword);
        Button registration = (Button)findViewById(R.id.register);

//        registration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String mEmail = email.getText().toString();
//                String mPassword = password.getText().toString();
//
//                createAccount(mEmail, mPassword);
//            }
//        });
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            System. out.println("createUserWithEmail:Success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Created new user successfully", Toast.LENGTH_LONG).show();
//                            updateUI(user);
                        } else {
                            System.out.println("signInWithEmail:failure");
                            Toast.makeText(getApplicationContext(), "Could not create new user", Toast.LENGTH_LONG).show();
//                            updateUI(null);

                        }
                    }
                });
    }
}
