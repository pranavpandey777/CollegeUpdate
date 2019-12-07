package com.example.pranav.pec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notice extends AppCompatDialogFragment {
    EditText title, body;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;


    Button btn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notice, null);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        title = view.findViewById(R.id.title);
        body = view.findViewById(R.id.body);
        btn = view.findViewById(R.id.btnUpdate);
        builder.setView(view);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ititle = title.getText().toString();
                String ibody = body.getText().toString();

                DatabaseReference data = databaseReference.child("Update").push();
                data.child("Title").setValue(ititle);
                data.child("Body").setValue(ibody);

                dismiss();


            }
        });


        return builder.create();


    }
}
