package com.example.registrationloginexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.login_activity);

        EditText email = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.passwordField);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = String.valueOf(email.getText());
                String mPassword = password.getText().toString();

                signIn(mEmail, mPassword);
            }
        });

        TextView openReg = (TextView)findViewById(R.id.register_link);

        openReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegistrationPage();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }



    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            System.out.println("Successful sign in");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Authentication Success", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("could not sign in ");
                            Toast.makeText(getApplicationContext(), "Authentication faliure", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void OpenRegistrationPage(){
//        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
//                .setAndroidPackageName("com.example.registrationloginexercise", true, "12")
//                .setHandleCodeInApp(true)
//                .setUrl("https://registrationloginexercise.page.link/randomDomain/")
//                .build();
//
//        Intent intent = new Intent(this, RegistrationActivity.class);
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(Arrays.asList(
//                                new AuthUI.IdpConfig.EmailBuilder().enableEmailLinkSignIn()
//                                        .setActionCodeSettings(actionCodeSettings).build() ))
//                        .setTheme(R.style.Theme_AppCompat)
//                        .build(),
//                RC_SIGN_IN
//        );
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


}