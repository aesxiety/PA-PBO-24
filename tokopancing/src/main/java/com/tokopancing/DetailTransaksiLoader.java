package com.tokopancing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiLoader {

    public static List<detailTransaksi> loadDetailTransaksi(String idTransaksi) {
        List<detailTransaksi> detailList = new ArrayList<>();

        String query = "SELECT * FROM detailtransaksi WHERE id_transaksi = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, idTransaksi);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String idDetail = rs.getString("id_detail");
                String idProduk = rs.getString("id_produk");
                String namaProduk = rs.getString("nama_produk");
                double hargaProduk = rs.getDouble("harga_produk");
                int jumlahProduk = rs.getInt("jumlah_produk");
                double subTotalPembayaran = rs.getDouble("subtotal_pembayaran");

                detailTransaksi detail = new detailTransaksi(idDetail, idTransaksi, idProduk, namaProduk, hargaProduk, jumlahProduk);
                detailList.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detailList;
    }
}
