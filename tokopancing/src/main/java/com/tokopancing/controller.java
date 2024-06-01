package com.tokopancing;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class controller {
    private static Connection connection;
    static InputStreamReader isr = new InputStreamReader(System.in);    
    static BufferedReader br = new BufferedReader(isr);

    public controller(Connection connection) {
        controller.connection = connection;
    }
    public boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void register(String username, String password, String role) throws SQLException {
        if (isUsernameTaken(username)) {
            throw new IllegalArgumentException("Username sudah ada");
        }
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();

            users newUser = new users(username, password, role);
            Main.akunUser.add(newUser);
        }
    }

    // mengambil semua data akun dari database
    public static void getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            Main.akunUser.add(new users(
                // resultSet.getInt("id_user"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("role"))
            );
        }
        
    }
    // Login user
    public users login(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new users( 
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
                }
            }
        }
        return null;
    }
    public static void loadDataTransaksi() {
        try {
            String query = "SELECT * FROM transaksi";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                transaksi trans = new transaksi(
                    resultSet.getString("id_transaksi"),
                    resultSet.getString("pembeli"),
                    resultSet.getString("status_transaksi")
                );
                Main.listTransaksi.add(trans);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadDetailTransaksi() throws SQLException {
        String query = "SELECT * FROM detailtransaksi";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            detailTransaksi detail = new detailTransaksi(
                resultSet.getString("id_detail"),
                resultSet.getString("id_transaksi"),
                resultSet.getString("id_produk"),
                resultSet.getString("nama_produk"),
                resultSet.getDouble("harga_produk"),
                resultSet.getInt("jumlah_produk")
            );
            Main.listDetail.add(detail);
        }

        for (transaksi trans : Main.listTransaksi) {
            for (detailTransaksi detail : Main.listDetail) {
                if (detail.getIdTransaksi().equals(trans.getIdTransaksi())) {
                    trans.tambahItem(detail);
                }
            }
        }
    }

    // Method untuk memuat data pembayaran dari database
    public static void loadPembayaran() {
        String query = "SELECT * FROM pembayaran";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pembayaran payment = new pembayaran(
                        resultSet.getString("id_pembayaran"),
                        resultSet.getString("id_transaksi"),
                        resultSet.getString("bank_penerima"),
                        resultSet.getString("nama_penerima"),
                        resultSet.getInt("norek_penerima"),
                        resultSet.getString("bank_pengirim"),
                        resultSet.getString("nama_pengirim"),
                        resultSet.getInt("norek_pengirim"),
                        resultSet.getDouble("nominal_transfer")
                );
                Main.listPembayaran.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataJoran() throws SQLException{
        String query = """
            SELECT 
            produk.id_produk, produk.nama, produk.merek, 
            produk.harga, joran.bahan, joran.panjang
            FROM 
                produk
            JOIN 
                joran
            ON 
                produk.id_produk = joran.id_produk; 
            """;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
    
        while (resultSet.next()) {
            Main.listJoran.add(new joran(
                resultSet.getString("id_produk"),
                resultSet.getString("nama"),
                resultSet.getString("merek"),
                resultSet.getDouble("harga"),
                resultSet.getString("bahan"),
                resultSet.getDouble("panjang"))
            );
        }
    }
    public static void loadDataMataKail() throws SQLException{
        String query = """
            SELECT 
            produk.id_produk, produk.nama, produk.merek, 
            produk.harga, matakail.ukuran
            FROM 
                produk
            JOIN 
                matakail
            ON 
                produk.id_produk = matakail.id_produk; 
            """;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
    
        while (resultSet.next()) {
            Main.listMataKail.add(new mataKail(
                resultSet.getString("id_produk"),
                resultSet.getString("nama"),
                resultSet.getString("merek"),
                resultSet.getDouble("harga"),
                resultSet.getDouble("ukuran"))
                );
            }
        }
        
    public static void loadDataSenar() throws SQLException{
        String query = """
        SELECT 
        produk.id_produk, produk.nama, produk.merek, 
        produk.harga, senar.ketebalan
        FROM 
            produk
        JOIN 
            senar
        ON 
            produk.id_produk = senar.id_produk; 
        """;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Main.listSenar.add(new senar(
                resultSet.getString("id_produk"),
                resultSet.getString("nama"),
                resultSet.getString("merek"),
                resultSet.getDouble("harga"),
                resultSet.getDouble("ketebalan"))
                );
            }
        }
    public static void loadDataPelet() throws SQLException{
        String query = """
        SELECT 
        produk.id_produk, produk.nama, produk.merek, 
        produk.harga, pelet.rasa, pelet.berat
        FROM 
            produk
        JOIN 
            pelet
        ON 
            produk.id_produk = pelet.id_produk; 
        """;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Main.listPelet.add(new pelet(
                resultSet.getString("id_produk"),
                resultSet.getString("nama"),
                resultSet.getString("merek"),
                resultSet.getDouble("harga"),
                resultSet.getString("rasa"),
                resultSet.getDouble("berat"))
                );
            }
        }
    
    //Update item
    public static void updateJoran(joran jrn)throws SQLException{
        String updateProduk = "UPDATE produk SET nama=?,merek=?,harga=? WHERE id_produk=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProduk)) {
            preparedStatement.setString(1, jrn.getNama());
            preparedStatement.setString(2, jrn.getMerek());
            preparedStatement.setDouble(3, jrn.getHarga());
            preparedStatement.setString(4, jrn.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        String updateJoran = "UPDATE joran SET bahan = ?, panjang = ? WHERE id_produk = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateJoran)) {
            preparedStatement.setString(1, jrn.getBahan());
            preparedStatement.setDouble(2, jrn.getPanjang());
            preparedStatement.setString(3, jrn.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMataKail(mataKail mtKail)throws SQLException{
        String updateProduk = "UPDATE produk SET nama=?,merek=?,harga=? WHERE id_produk=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProduk)) {
            preparedStatement.setString(1, mtKail.getNama());
            preparedStatement.setString(2, mtKail.getMerek());
            preparedStatement.setDouble(3, mtKail.getHarga());
            preparedStatement.setString(4, mtKail.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        String updateMataKail = "UPDATE matakail SET ukuran = ? WHERE id_produk = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateMataKail)) {
            preparedStatement.setDouble(1, mtKail.getUkuran());
            preparedStatement.setString(2, mtKail.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSenar(senar snr)throws SQLException{
        String updateProduk = "UPDATE produk SET nama=?,merek=?,harga=? WHERE id_produk=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProduk)) {
            preparedStatement.setString(1, snr.getNama());
            preparedStatement.setString(2, snr.getMerek());
            preparedStatement.setDouble(3, snr.getHarga());
            preparedStatement.setString(4, snr.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        String updateSenar = "UPDATE senar SET ketebalan = ? WHERE id_produk = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSenar)) {
            preparedStatement.setDouble(1, snr.getKetebalan());
            preparedStatement.setString(2, snr.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePelet(pelet plt)throws SQLException{
        String updateProduk = "UPDATE produk SET nama=?,merek=?,harga=? WHERE id_produk=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProduk)) {
            preparedStatement.setString(1, plt.getNama());
            preparedStatement.setString(2, plt.getMerek());
            preparedStatement.setDouble(3, plt.getHarga());
            preparedStatement.setString(4, plt.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        String updatePelet = "UPDATE pelet SET rasa = ?, berat=? WHERE id_produk = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updatePelet)) {
            preparedStatement.setString(1, plt.getRasa());
            preparedStatement.setDouble(2, plt.getBerat());
            preparedStatement.setString(3, plt.getIdProduk());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Delete item
    public static void deleteJoran(joran jrn) {
        String deleteJoranQuery = "DELETE FROM joran WHERE id_produk = ?";
        String deleteProdukQuery = "DELETE FROM produk WHERE id_produk = ?";
        
        try (PreparedStatement deleteJoranStmt = connection.prepareStatement(deleteJoranQuery);
             PreparedStatement deleteProdukStmt = connection.prepareStatement(deleteProdukQuery)) {
            // Delete from joran table
            deleteJoranStmt.setString(1, jrn.getIdProduk());
            deleteJoranStmt.executeUpdate();
    
            // Delete from produk table
            deleteProdukStmt.setString(1, jrn.getIdProduk());
            deleteProdukStmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMataKail(mataKail mtKail) {
        String deleteMataKailQuery = "DELETE FROM mataKail WHERE id_produk = ?";
        String deleteProdukQuery = "DELETE FROM produk WHERE id_produk = ?";
        
        try (PreparedStatement deleteMataKailStmt = connection.prepareStatement(deleteMataKailQuery);
             PreparedStatement deleteProdukStmt = connection.prepareStatement(deleteProdukQuery)) {
            deleteMataKailStmt.setString(1, mtKail.getIdProduk());
            deleteMataKailStmt.executeUpdate();
    
            deleteProdukStmt.setString(1, mtKail.getIdProduk());
            deleteProdukStmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSenar(senar snr) {
        String deleteSenarQuery = "DELETE FROM senar WHERE id_produk = ?";
        String deleteProdukQuery = "DELETE FROM produk WHERE id_produk = ?";
        
        try (PreparedStatement deleteSenarStmt = connection.prepareStatement(deleteSenarQuery);
             PreparedStatement deleteProdukStmt = connection.prepareStatement(deleteProdukQuery)) {
            deleteSenarStmt.setString(1, snr.getIdProduk());
            deleteSenarStmt.executeUpdate();
    
            deleteProdukStmt.setString(1, snr.getIdProduk());
            deleteProdukStmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePelet(pelet plt) {
        String deletePeletQuery = "DELETE FROM pelet WHERE id_produk = ?";
        String deleteProdukQuery = "DELETE FROM produk WHERE id_produk = ?";
        
        try (PreparedStatement deletePeletStmt = connection.prepareStatement(deletePeletQuery);
             PreparedStatement deleteProdukStmt = connection.prepareStatement(deleteProdukQuery)) {
            deletePeletStmt.setString(1, plt.getIdProduk());
            deletePeletStmt.executeUpdate();
    
            deleteProdukStmt.setString(1, plt.getIdProduk());
            deleteProdukStmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // // membaca data pada database berdasarkan id
    // public users getUsersById(int id) throws SQLException {
    //     String query = "SELECT * FROM users WHERE id = ?";
    //     try 
    //         (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //         preparedStatement.setInt(1, id);
    //         try
    //         (ResultSet resultSet = preparedStatement.executeQuery()) {
    //         if (resultSet.next()) {
    //             return new users(resultSet.getInt("id"), 
    //             resultSet.getString("username"),
    //             resultSet.getString("password"),
    //             resultSet.getString("role"));
    //         }
    //     }
    // }
    // return null;
    // }
}