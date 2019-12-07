package com.example.pranav.pec;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentDashboard extends Fragment {

    Button btn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Update> list;
    MyAdapter adapter;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String dis;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btn = v.findViewById(R.id.addNotice);
        recyclerView = v.findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("Please Wait......");
        progressDialog.show();

        DatabaseReference one = databaseReference.child("User").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        one.child("designation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dis = dataSnapshot.getValue(String.class);
                Toast.makeText(getActivity(), dis, Toast.LENGTH_SHORT).show();
                if (dis.equals("Teacher")) {
                    btn.setVisibility(View.VISIBLE);

                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        DatabaseReference two = databaseReference.child("Update");
        two.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Update p = dataSnapshot1.getValue(Update.class);
                    list.add(p);
                }


                adapter = new MyAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
                int position = recyclerView.getAdapter().getItemCount() - 1;
                recyclerView.scrollToPosition(position);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(Chat.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    public void dialog() {
        Notice log = new Notice();
        log.show(getFragmentManager(), "login");


    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
