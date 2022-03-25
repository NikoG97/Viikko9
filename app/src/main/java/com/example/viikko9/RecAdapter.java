package com.example.viikko9;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {

    ArrayList<FinnkinoMovie> data;
    Context context;

    public RecAdapter(Context cntx, ArrayList<FinnkinoMovie> fkmovie) {
        context = cntx;
        data = fkmovie;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.movieView.setText(data.get(position).getMovieTitle());
        holder.timeView.setText(data.get(position).getStartTime());
        holder.auditView.setText(data.get(position).getAuditorium());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movieView, timeView, auditView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieView = itemView.findViewById(R.id.movieView);
            timeView = itemView.findViewById(R.id.timeView);
            auditView =itemView.findViewById(R.id.auView);

        }
    }
}