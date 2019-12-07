package com.example.pranav.pec;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btnSignIn, btnSignUp;
    private EditText edtEmail, edtPassword;
    private ImageView image;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }
        image = findViewById(R.id.image);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait......");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String iemail = edtEmail.getText().toString();
                String ipassword = edtPassword.getText().toString();

                if (iemail.isEmpty()) {
                    progressDialog.dismiss();
                    edtEmail.setError("Enter Email");
                    edtEmail.requestFocus();
                    return;

                }
                if (ipassword.isEmpty()) {
                    progressDialog.dismiss();
                    edtEmail.setError("Enter Password");
                    edtEmail.requestFocus();
                    return;

                }
                firebaseAuth.signInWithEmailAndPassword(iemail, ipassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Toast.makeText(LoginActivity.this, "okkk", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email not in database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        ObjectAnimator.ofFloat(image, "translationY", -370f).setDuration(1000).start();
        image.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnSignIn.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
                edtEmail.setVisibility(View.VISIBLE);
                edtPassword.setVisibility(View.VISIBLE);
                findViewById(R.id.txt).setVisibility(View.VISIBLE);
            }
        }, 1000);

    }


}
