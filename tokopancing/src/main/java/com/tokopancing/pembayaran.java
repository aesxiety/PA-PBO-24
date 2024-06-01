package com.tokopancing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class pembayaran {
    private String idPembayaran;
    private String idTransaksi;
    private String bankPenerima;
    private String namaPenerima;
    private Integer noRekPenerima;
    private String bankPengirim;
    private String namaPengirim;
    private Integer noRekPengirim;
    private double nominalTransfer;

    public pembayaran( String idPembayaran,String idTransaksi,String bankPenerima,
    String namaPenerima,Integer noRekPenerima,String bankPengirim,
    String namaPengirim,Integer noRekPengirim, double nominalTransfer
    ){
        this.idPembayaran = idPembayaran;
        this.idTransaksi = idTransaksi;
        this.bankPenerima = bankPenerima;
        this.namaPenerima = namaPenerima;
        this.noRekPenerima = noRekPenerima;
        this.bankPengirim = bankPengirim;
        this.namaPengirim = namaPengirim;
        this.noRekPengirim = noRekPengirim;
        this.nominalTransfer = nominalTransfer;
    }

    public void savePembayaranToDB() {
    String query = "INSERT INTO pembayaran (id_pembayaran, id_transaksi, bank_penerima, nama_penerima, norek_penerima, bank_pengirim, nama_pengirim,norek_pengirim, nominal_transfer) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, idPembayaran);
        stmt.setString(2, idTransaksi);
        stmt.setString(3, bankPenerima);
        stmt.setString(4, namaPenerima);
        stmt.setInt(5, noRekPenerima);
        stmt.setString(6, bankPengirim);
        stmt.setString(7, namaPengirim);
        stmt.setInt(8, noRekPengirim);
        stmt.setDouble(9, nominalTransfer);

        stmt.executeUpdate();
        System.out.println("Data successfully saved to database.");

    } catch (SQLException e) {
            e.printStackTrace();
    }
    }
    public String getIdPembayaran() {
        return idPembayaran;
    }
    public String getIdTransaksi() {
        return idTransaksi;
    }
    public String getBankPenerima() {
        return bankPenerima;
    }
    public String getNamaPenerima() {
        return namaPenerima;
    }
    public Integer getNoRekPenerima() {
        return noRekPenerima;
    }
    public String getBankPengirim() {
        return bankPengirim;
    }
    public String getNamaPengirim() {
        return namaPengirim;
    }
    public Integer getNoRekPengirim() {
        return noRekPengirim;
    }
    public double getNominalTransfer() {
        return nominalTransfer;
    }
    
}
