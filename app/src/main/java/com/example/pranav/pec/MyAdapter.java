package com.example.pranav.pec;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Update> messages;

    public MyAdapter(Context c, ArrayList<Update> p) {
        context = c;
        messages = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rowtitle.setText(messages.get(position).getTitle());
        holder.rowbody.setText(messages.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rowtitle, rowbody;

        public MyViewHolder(View itemView) {
            super(itemView);
            rowtitle = itemView.findViewById(R.id.rowTitle);
            rowbody = itemView.findViewById(R.id.rowBody);

        }

    }
}
