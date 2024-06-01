package com.tokopancing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class pelet extends produk{
    private String rasa;
    private double berat;

    public pelet(String idProduk, String nama, String merek, double harga, String rasa,double berat) {
        super(idProduk, nama, merek, harga);
        this.rasa = rasa;
        this.berat = berat;
    }

    @Override
    public void saveToDB() {
        try (Connection conn = DatabaseConnection.getConnection()){
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO produk (id_produk,nama, merek, harga) VALUES (?,?, ?, ?)")) {
                pstmt.setString(1, getIdProduk());
                pstmt.setString(2, getNama());
                pstmt.setString(3, getMerek());
                pstmt.setDouble(4, getHarga());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pelet (id_produk, rasa ,berat) VALUES (?, ?,?)")) {
                pstmt.setString(1, getIdProduk());
                pstmt.setString(2, getRasa());
                pstmt.setDouble(3,getBerat());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tampil(){
        System.out.println("Merek = " + getMerek());
    }

    // @Override
    // public void tampil(boolean detail) {
    //     if (detail){
    //         System.out.println("Pelet:");
    //         System.out.println("Nama = " + getNama());
    //         System.out.println("Harga = " + getHarga());
    //         System.out.println("Rasa = " + getRasa());
    //     } else {
    //         tampil();
    //     }
    // }
    
    public String getRasa() {
        return rasa;
    }
    public double getBerat() {
        return berat;
    }
    public void setRasa(String rasa) {
        this.rasa = rasa;
    }
    public void setBerat(double berat) {
        this.berat = berat;
    }
};
