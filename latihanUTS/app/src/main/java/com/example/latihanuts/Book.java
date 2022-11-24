package com.example.latihanuts;

public class Book {
    private String id, isbn, judul, kategori, deskripsi, harga;

    public Book(String isbn, String judul, String kategori, String deskripsi, String harga) {
        this.isbn = isbn;
        this.judul = judul;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }
    public Book(String id, String isbn, String judul, String kategori, String deskripsi, String harga) {
        this.id = id;
        this.isbn = isbn;
        this.judul = judul;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getHarga() {
        return harga;
    }


}
