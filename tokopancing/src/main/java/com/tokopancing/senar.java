package com.tokopancing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class senar extends produk {
    private double ketebalan ;
    public senar(String idProduk, String nama, String merek, double harga, double ketebalan) {
        super(idProduk, nama, merek, harga);
        this.ketebalan = ketebalan;
    }

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
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO senar (id_produk, ketebalan) VALUES (?, ?)")) {
                pstmt.setString(1, getIdProduk());
                pstmt.setDouble(2, getKetebalan());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getKetebalan() {
        return ketebalan;
    }
    public void setKetebalan(double ketebalan) {
        this.ketebalan = ketebalan;
    }
    
}
