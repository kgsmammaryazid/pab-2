package com.if5b.myfirstcrudsql;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private Context ctx;
    private ArrayList arrayId, arrayTitle, arrayAuthor, arrayYear;

    public BookAdapter(Context ctx, ArrayList arrayId, ArrayList arrayTitle, ArrayList arrayAuthor, ArrayList arrayYear) {
        this.ctx = ctx;
        this.arrayId = arrayId;
        this.arrayTitle = arrayTitle;
        this.arrayAuthor = arrayAuthor;
        this.arrayYear = arrayYear;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View view = layoutInflater.inflate(R.layout.card, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.tvId.setText(arrayId.get(position).toString());
        holder.tvTitle.setText(arrayTitle.get(position).toString());
        holder.tvAuthor.setText(arrayAuthor.get(position).toString());
        holder.tvYear.setText(arrayYear.get(position).toString());

        holder.cvBuku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder messageWindow = new AlertDialog.Builder(ctx);
                messageWindow.setMessage("Pilih perintah yang diinginkan!");
                messageWindow.setTitle("Perhatian ! ");
                messageWindow.setCancelable(true);

                messageWindow.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyDatabaseHelper myDB = new MyDatabaseHelper(ctx);
                        long result = myDB.deleteBookById(arrayId.get(position).toString());

                        if(result == -1) {
                            Toast.makeText(ctx, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ctx, "Data Berhasil di hapus", Toast.LENGTH_SHORT).show();
                            if(position == 0) {
                                MainActivity.dataPosition = 0;
                            }
                            else {
                                MainActivity.dataPosition = position - 1;
                            }
                            dialogInterface.dismiss();
                            ((MainActivity) ctx).onResume();
                        }
                    }
                });
                messageWindow.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ctx, EditActivity.class);
                        intent.putExtra("keyId", arrayId.get(position).toString());
                        intent.putExtra("keyTitle", arrayTitle.get(position).toString());
                        intent.putExtra("keyAuthor", arrayAuthor.get(position).toString());
                        intent.putExtra("keyYear", arrayYear.get(position).toString());
                        intent.putExtra("keyPosition", position);
                        ctx.startActivity(intent);
                    }
                });
                messageWindow.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayId.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvTitle, tvAuthor, tvYear;
        CardView cvBuku;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvJudul);
            tvAuthor = itemView.findViewById(R.id.tvPenulis);
            tvYear = itemView.findViewById(R.id.tvTahun);
            cvBuku = itemView.findViewById(R.id.cvBuku);
        }
    }
}
