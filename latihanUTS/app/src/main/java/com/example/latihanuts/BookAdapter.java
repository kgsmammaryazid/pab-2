package com.example.latihanuts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Book> arrayBook;

    public BookAdapter(Context ctx, ArrayList<Book> arrayBook) {
        this.ctx = ctx;
        this.arrayBook = arrayBook;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(arrayBook.get(position).getId());
        holder.tvIsbn.setText(arrayBook.get(position).getIsbn());
        holder.tvJudul.setText(arrayBook.get(position).getJudul());
        holder.tvKategori.setText(arrayBook.get(position).getKategori());
        holder.tvDeskripsi.setText(arrayBook.get(position).getDeskripsi());
        holder.tvHarga.setText(arrayBook.get(position).getHarga());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "ID anda =" + arrayBook.get(holder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder messageWindow = new AlertDialog.Builder(ctx);
                messageWindow.setMessage("Konfirmasi Delete");
                messageWindow.setTitle("Attention ! ");
                messageWindow.setCancelable(true);
                messageWindow.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                messageWindow.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
                long result = databaseHelper.deleteBookById(arrayBook.get(holder.getAdapterPosition()));

                if(result == -1) {
                    Toast.makeText(ctx, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ctx, "Data Berhasil di hapus", Toast.LENGTH_SHORT).show();
                    if(holder.getAdapterPosition() == 0) {
                        MainActivity.dataPosition = 0;
                    }
                    else {
                        MainActivity.dataPosition = holder.getAdapterPosition() - 1;
                    }
                    // Resume ke MainActivity
                    ((MainActivity) ctx).onResume();
                }
                    }
                });
                messageWindow.show();
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, EditActivity.class);
                intent.putExtra("keyId", arrayBook.get(holder.getAdapterPosition()).getId());
                intent.putExtra("keyIsbn", arrayBook.get(holder.getAdapterPosition()).getIsbn());
                intent.putExtra("keyJudul", arrayBook.get(holder.getAdapterPosition()).getJudul());
                intent.putExtra("keyKategori", arrayBook.get(holder.getAdapterPosition()).getKategori());
                intent.putExtra("keyDeskripsi", arrayBook.get(holder.getAdapterPosition()).getDeskripsi());
                intent.putExtra("keyHarga", arrayBook.get(holder.getAdapterPosition()).getHarga());
                intent.putExtra("keyPosition", holder.getAdapterPosition());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayBook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvIsbn, tvJudul, tvKategori, tvDeskripsi, tvHarga;
        ImageView ivDelete, ivEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvIsbn = itemView.findViewById(R.id.tvISBN);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvKategori = itemView.findViewById(R.id.tvKategori);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            ivDelete = itemView.findViewById(R.id.btnDelete);
            ivEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
