package com.example.registrationloginexercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 TextView username = (TextView) findViewById(R.id.username);
                 TextView password = (TextView) findViewById(R.id.passwordField);

                String usernameText = (String) username.getText();
                String passwordText = (String) password.getText();

                signIn(usernameText, passwordText);
            }
        });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            System.out.println("Successful sign in");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            System.out.println("could not sign in ");
                            Toast.makeText(getApplicationContext(), "Authentication faliure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void OpenRegistrationPage(View v){
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName("com.example.registrationloginexercise", true, "12")
                .setHandleCodeInApp(true)
                .setUrl("https://registrationloginexercise.page.link/randomDomain/")
                .build();

        Intent intent = new Intent(this, Registration.class);
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().enableEmailLinkSignIn()
                                        .setActionCodeSettings(actionCodeSettings).build() ))
                        .setTheme(R.style.Theme_AppCompat)
                        .build(),
                RC_SIGN_IN
        );

        EditText email = (EditText) findViewById(R.id.email);
        Editable emailText = email.getText();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail(String.valueOf(emailText), actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Emain Sent");
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            System.out.println("Authentication Failure");
                            Toast.makeText(getApplicationContext(), "Authentication Faliure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "Could not Sign in Try Again", Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }
}