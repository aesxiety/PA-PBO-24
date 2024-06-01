package com.tokopancing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class transaksi {
    private String idTransaksi;
    private String pembeli;
    private List<detailTransaksi> items;
    private double totalPembayaran;
    private String status; 

    public transaksi(String idTransaksi, String pembeli,String status) {
        this.idTransaksi = idTransaksi;
        this.pembeli = pembeli;
        this.items = new ArrayList<>();
        this.totalPembayaran = 0.0;
        this.status = status;
    }

    public void tambahItem(detailTransaksi detail) {
        items.add(detail);
        hitungTotalPembayaran();
    }

    private void hitungTotalPembayaran() {
        totalPembayaran = items.stream().mapToDouble(detailTransaksi::getSubTotalPembayaran).sum();
    }

    public void saveTransaksiToDB() throws SQLException {
        // 1. Prepared statement for transaksi table
        Connection conn = DatabaseConnection.getConnection();
        String sqlTransaksi = "INSERT INTO transaksi (id_transaksi, pembeli, total_pembayaran,status_transaksi) VALUES (?, ?, ?,?)";
        PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi);

        try {
            psTransaksi.setString(1, idTransaksi);
            psTransaksi.setString(2, pembeli);
            psTransaksi.setDouble(3, totalPembayaran);
            psTransaksi.setString(4, status);
            psTransaksi.executeUpdate();

            String sqlDetail = "INSERT INTO detailtransaksi (id_detail,id_transaksi, id_produk, nama_produk, harga_produk, jumlah_produk, subtotal_pembayaran) VALUES (?,?, ?, ?, ?, ?, ?)";
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);

            for (detailTransaksi detail : items) {
                String idDetail = UUID.randomUUID().toString();
                psDetail.setString(1, idDetail);
                psDetail.setString(2, detail.getIdTransaksi());
                psDetail.setString(3, detail.getIdProduk());
                psDetail.setString(4, detail.getNamaProduk());
                psDetail.setDouble(5, detail.getHargaProduk());
                psDetail.setInt(6, detail.getJumlahProduk());
                psDetail.setDouble(7, detail.getSubTotalPembayaran());

                psDetail.addBatch(); 
            }
            psDetail.executeBatch();

        } catch (SQLException e) {
            throw e;
        }
    }
    public void updateStatus(){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE transaksi SET status_transaksi = ? WHERE id_transaksi = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, getStatus());
            ps.setString(2, getIdTransaksi());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public String getIdTransaksi() {
        return idTransaksi;
    }
    public String getPembeli() {
        return pembeli;
    }
    public List<detailTransaksi> getItems() {
        return items;
    }
    public double getTotalPembayaran() {
        return totalPembayaran;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}


