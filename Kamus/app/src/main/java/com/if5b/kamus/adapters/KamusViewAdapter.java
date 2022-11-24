package com.if5b.kamus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if5b.kamus.R;
import com.if5b.kamus.models.Kamus;

import java.util.ArrayList;

public class KamusViewAdapter extends RecyclerView.Adapter<KamusViewAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<Kamus> data = new ArrayList<>();

    public KamusViewAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void addItem(ArrayList<Kamus> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public KamusViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.tvTitle.setText(data.get(pos).getTitle());
        holder.tvDescription.setText(data.get(pos).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDesc);
        }
    }
}
