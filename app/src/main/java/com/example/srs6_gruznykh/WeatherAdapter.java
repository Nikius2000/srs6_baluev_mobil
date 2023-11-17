package com.example.srs6_baluev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Weather> weather;
    private final Context context;

    public WeatherAdapter(Context context, List<Weather> weather) {
        this.inflater = LayoutInflater.from(context);
        this.weather = weather;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Weather w = weather.get(position);
        Picasso.get().load(w.getUrl()).fit().centerInside().into(holder.flagView);
        holder.nameView.setText(w.getDate());
        holder.capitalView.setText(w.getTemp());
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, capitalView;

        ViewHolder(View view) {
            super(view);
            flagView = view.findViewById(R.id.icon);
            nameView = view.findViewById(R.id.temp);
            capitalView = view.findViewById(R.id.condition);
        }
    }
}