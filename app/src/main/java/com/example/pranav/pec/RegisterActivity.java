package com.example.pranav.pec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password, name, num, cnfpassword;
    Button reg;
    Spinner spinner;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    ImageView image;
    StorageReference storageReference;
    Uri downloaded;
    String designation;
    TextWatcher textWatcher = null;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        image = findViewById(R.id.signUpImage);
        cnfpassword = findViewById(R.id.signUpConfirmPassword);
        name = findViewById(R.id.signUpName);
        num = findViewById(R.id.signUpPhone);
        reg = findViewById(R.id.btnRegister);
        spinner = findViewById(R.id.signUpSpinner);
        downloaded = Uri.parse("android.resource://com.example.pranav.pec/drawable/add_image");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        final String[] spinnerArray = {"Student", "Teacher"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                designation = spinnerArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 100);
            }
        });
        reg.setEnabled(false);


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String icnfpassword = cnfpassword.getText().toString();
                String iemail = email.getText().toString();
                String ipassword = password.getText().toString();
                String inum = num.getText().toString();
                String iname = name.getText().toString();


                if (!ipassword.equals(icnfpassword) && !ipassword.isEmpty()) {
                    reg.setEnabled(false);
                    cnfpassword.setError("Not Matched");
                    return;
                }
                if (iemail.isEmpty() || ipassword.isEmpty() || inum.isEmpty() || iname.isEmpty()) {
                    reg.setEnabled(false);
                    return;

                } else {
                    reg.setEnabled(true);
                }


            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        password.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher);
        cnfpassword.addTextChangedListener(textWatcher);
        cnfpassword.addTextChangedListener(textWatcher);
        num.addTextChangedListener(textWatcher);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String icnfpassword = cnfpassword.getText().toString();
                final String iemail = email.getText().toString();
                String ipassword = password.getText().toString();
                final String inum = num.getText().toString();
                final String iname = name.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(iemail, ipassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.show();
                            User user = new User(iname, iemail, inum, downloaded.toString(), designation);
                            FirebaseDatabase.getInstance()
                                    .getReference("User").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid()).setValue(user);
                            progressDialog.dismiss();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(iname)
                                    .setPhotoUri(downloaded)
                                    .build();
                            FirebaseUser user1 = firebaseAuth.getCurrentUser();
                            user1.updateProfile(profile);

                            Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();

                        } else {

                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Please Check", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            progressDialog.show();
            Uri uri = data.getData();
            StorageReference child = storageReference.child("MyOne").child(id);

            child.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    downloaded = taskSnapshot.getDownloadUrl();

                    Picasso.with(RegisterActivity.this).load(downloaded).into(image);

                }
            });

        }
    }
}
