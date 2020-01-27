package com.google.sites.medcare.SignInSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.sites.medcare.R;

public class SignUp extends AppCompatActivity {

    TextView email, password;
    FirebaseAuth firebaseAuth;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        signUp = findViewById(R.id.buttonSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this, "Please enter your E-mail and Password", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this, "Please enter your E-mail", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                Intent openHome = new Intent(SignUp.this, SignIn.class);
                                SignUp.this.startActivity(openHome);
                            }
                            else {
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });
    }
}
