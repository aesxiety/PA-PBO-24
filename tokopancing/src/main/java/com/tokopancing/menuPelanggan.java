package com.tokopancing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class menuPelanggan {
    static InputStreamReader isr = new InputStreamReader(System.in);    
    static BufferedReader br = new BufferedReader(isr);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void promptEnterKey(BufferedReader br) throws IOException {
        System.out.println("");
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║            Tekan [ ENTER ] untuk Melanjutkan...        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        br.readLine();
    }

    public void displayPelangganMenu(String username) throws IOException, SQLException{
        while (true) {
            clearScreen();
            System.out.println("""
                    ╔════════════════════════════════════════════════╗
                    ║              === Toko Pancing ===              ║
                    ║  1. Tampilkan Produk                           ║
                    ║  2. Beli Produk                                ║
                    ║  3. Pembayaran                                 ║
                    ║  4. Riwayat Transaksi                          ║
                    ║  0. Logout                                     ║
                    ╚════════════════════════════════════════════════╝
                    """);
                    System.out.print("Pilih Menu = ");
                    String pilih;
                    try {
                        pilih = br.readLine();
                    } catch (IOException e) {
                        System.out.println("╔════════════════════════════════════════╗");
                        System.out.println("║  Terjadi kesalahan saat membaca input! ║");
                        System.out.println("╚════════════════════════════════════════╝");
                        promptEnterKey(br);
                        continue;
                    }
            if (pilih.equals("1")){
                tampilbarang();
                br.readLine();
            }else if(pilih.equals("2")){
                processTransaction(username, br);
            }else if(pilih.equals("3")){
                prosesPembayaran(username);
            }else if (pilih.equals("4")) {
                displayTransaksi(username);
                br.readLine();
            }else if(pilih.equals("0")){
                 break;
            }

        }
    }
    public static void tampilbarang () throws IOException{
        String pilihTampil;
        while (true){
            clearScreen();
            System.out.println("++================================++");
            System.out.println("++===== Menu Tampilkan Barang ====++");
            System.out.println("|| 1. Joran      |  4. Pelet      ||");
            System.out.println("|| 2. Mata kail  |  0. Kembali    ||");
            System.out.println("|| 3. Senar      |                ||");
            System.out.println("++================================++");
            System.out.print("Pilih Menu = ");
            pilihTampil = br.readLine();
            if(pilihTampil.equals("1")){
                menuAdmin.displayJoran();
                promptEnterKey(br);
            }else if (pilihTampil.equals("2")){
                menuAdmin.displayMataKail();
                promptEnterKey(br);
            }else if (pilihTampil.equals("3")){
                menuAdmin.displaySenar();
                promptEnterKey(br);
            }else if (pilihTampil.equals("4")){
                menuAdmin.displayPelet();
                promptEnterKey(br);   
            }else if (pilihTampil.equals("0")){
                break;
            }
        }
    }

    private static void displayTransaksi( String username)throws IOException {
        for(transaksi Transaksi : Main.listTransaksi){
            if (Transaksi.getPembeli().equals(username)){
                System.out.println("===========================================");
                System.out.println("ID Transaksi : " + Transaksi.getIdTransaksi());
                System.out.println("Pembeli      : " + Transaksi.getPembeli());
                System.out.println("Status       : " + Transaksi.getStatus());
                System.out.println("===========================================");
                System.out.println("Detail Transaksi:");
                for (detailTransaksi detail : Transaksi.getItems()) {
                    System.out.println(" - " + detail.getNamaProduk());
                    System.out.println(" \tHarga Rp."+detail.getHargaProduk()+ "x" 
                    + detail.getJumlahProduk() + " = Rp." + detail.getSubTotalPembayaran());
                }
                System.out.println("===========================================");
                System.out.println("\nTotal Pembayaran: Rp " + Transaksi.getTotalPembayaran());
                System.out.println("===========================================");
            }
        }
    }

    public static void prosesPembayaran(String user) throws IOException {
        transaksi pendingTransaksi = null;

        for (transaksi cekTransaksi : Main.listTransaksi) {
            if (cekTransaksi.getPembeli().equals(user) && cekTransaksi.getStatus().equals("waiting_payment")) {
                pendingTransaksi = cekTransaksi;
                break; 
            }
        }
        if (pendingTransaksi != null) {
            while (true) {
                String id_transaksi = null;
                Double total_bayar = null;
                
                for (transaksi Transaksi : Main.listTransaksi) {
                    if (Transaksi.getPembeli().equals(user)) {
                        id_transaksi = Transaksi.getIdTransaksi();
                        System.out.println("ID Transaksi: " + Transaksi.getIdTransaksi());
                        System.out.println("Pembeli: " + Transaksi.getPembeli());
                        System.out.println("===========================================");
                        System.out.println("Detail Transaksi:");
                        for (detailTransaksi detail : Transaksi.getItems()) {
                            System.out.println(" - " + detail.getNamaProduk());
                            System.out.println(" \tHarga Rp." + detail.getHargaProduk() + " x "
                                    + detail.getJumlahProduk() + " = Rp." + detail.getSubTotalPembayaran());
                        }
                        System.out.println("==================================================");
                        System.out.println("==================================================");
                        System.out.println("Total Pembayaran: Rp " + Transaksi.getTotalPembayaran());
                        total_bayar = Transaksi.getTotalPembayaran();
                    }
                }
    
                if (id_transaksi == null) {
                    System.out.println("No transactions found for user: " + user);
                    br.readLine();
                    break;
                }

                System.out.println("==================================================");
                System.out.println("  [1.Pembayaran] [2.Hapus Transaksi] [0.Kembali]  ");
                System.out.println("==================================================");
                System.out.print("Masukan Pilihan : ");
                String konfirmasiPembayaran = br.readLine();
                System.out.println("==================================================");
                
                if (konfirmasiPembayaran.equals("1")) {
                    finalToko newtoko = new finalToko();
                    clearScreen();
                    newtoko.displayPembayaran();
                    System.out.println("Pilih Pembayaran ke [1/2] :");
                    String pilihPembayaran = br.readLine();
                    if (pilihPembayaran.equals("1") || pilihPembayaran.equals("2")) {
                        clearScreen();
                        System.out.println("==================================================");
                        System.out.println("                Masukan Data Pembayaran           ");
                        System.out.println("==================================================");
                        System.out.print("Masukan Nama Bank     : ");
                        String bankPengirim = br.readLine();
                        System.out.print("Nama Pemilik Rekening : ");
                        String namaPengirim = br.readLine();
                        int nomorRekening = 0;
                        while (true) {
                            try {
                                System.out.print("Nomor Rekening        : ");
                                String nomorRekeningInput = br.readLine();
                                nomorRekening = Integer.parseInt(nomorRekeningInput);
                                break; // keluar dari loop jika berhasil
                            } catch (NumberFormatException e) {
                                System.out.println("Input nomor rekening tidak valid. Harap masukkan angka.");
                            }
                        }
                        System.out.println("==================================================");
                        
                        String idPembayaran = UUID.randomUUID().toString();
                        String bankPenerima = pilihPembayaran.equals("1") ? newtoko.getBank1() : newtoko.getBank2();
                        String namaPenerima = pilihPembayaran.equals("1") ? newtoko.getPenerima1() : newtoko.getPenerima2();
                        Integer noRekPenerima = pilihPembayaran.equals("1") ? newtoko.getNoRek1() : newtoko.getNoRek2();
                        
                        pembayaran bayar = new pembayaran(idPembayaran, id_transaksi, bankPenerima, namaPenerima, noRekPenerima, bankPengirim, namaPengirim,nomorRekening, total_bayar);
                        Main.listPembayaran.add(bayar);
                        bayar.savePembayaranToDB();
                        
                        // Update the transaction status
                        for (transaksi Transaksi : Main.listTransaksi) {
                            if (Transaksi.getIdTransaksi().equals(id_transaksi)) {
                                Transaksi.setStatus("waiting_validate");
                                Transaksi.updateStatus();
                                break;
                            }
                        }
                        clearScreen();
                        for (int i = 0; i < Main.listPembayaran.size(); i++) {
                            pembayaran currentPayment = Main.listPembayaran.get(i);
                            System.out.println("++========================================================++");
                            System.out.println("||                   Pembayaran Berhasil             ||");
                            System.out.println("||           Silahkan Tunggu Admin Memvalidasi       ||");
                            System.out.println("++========================================================++");
                            System.out.println(" ID Pembayaran    : " + currentPayment.getIdPembayaran());
                            System.out.println(" ID Transaksi     : " + currentPayment.getIdTransaksi());
                            System.out.println( " Nominal Transfer : Rp." + currentPayment.getNominalTransfer());
                            System.out.println(" Penerima  ================================================");
                            System.out.println("Bank         : " + currentPayment.getBankPenerima());
                            System.out.println("Nama Pemilik : " + currentPayment.getNamaPenerima());
                            System.out.println("No Rekening  : " + currentPayment.getNoRekPenerima());
                            System.out.println(" Pengirim  ================================================");
                            System.out.println("Bank         : " + currentPayment.getBankPengirim());
                            System.out.println("Nama Pemilik : " + currentPayment.getNamaPengirim());
                            System.out.println("No Rekening  : " + currentPayment.getNoRekPengirim());
                            System.out.println("===========================================================");
                        }
                        System.out.println("\n[press --> [Enter] ]");
                        br.readLine();
                        break; 
                    } else if (pilihPembayaran.equals("0")) {
                        break;
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                } else if (konfirmasiPembayaran.equals("2")) {
                    System.out.println("Transaksi akan dihapus");
                    deleteTransaksi(id_transaksi);
                    System.out.println("Transaksi berhasil dihapus");
                    br.readLine();
                    clearScreen();
                    break;
                } else if (konfirmasiPembayaran.equals("0")) {
                    break;
                } else {
                    System.out.println("Pilihan tidak valid.");
                }
            }
        }else{
            System.out.println("Tidak ada transaksi yang perlu dibayar");
            br.readLine();
        }
        



        // for (transaksi cekTransaksi : Main.listTransaksi) {
        //     System.out.println(cekTransaksi.getStatus());
        //     if (cekTransaksi.getStatus().equals("waiting_payment")) {
        //         while (true) {
        //             String id_transaksi = null;
        //             Double total_bayar = null;
                    
        //             for (transaksi Transaksi : Main.listTransaksi) {
        //                 if (Transaksi.getPembeli().equals(user)) {
        //                     id_transaksi = Transaksi.getIdTransaksi();
        //                     System.out.println("ID Transaksi: " + Transaksi.getIdTransaksi());
        //                     System.out.println("Pembeli: " + Transaksi.getPembeli());
        //                     System.out.println("===========================================");
        //                     System.out.println("Detail Transaksi:");
        //                     for (detailTransaksi detail : Transaksi.getItems()) {
        //                         System.out.println(" - " + detail.getNamaProduk());
        //                         System.out.println(" \tHarga Rp." + detail.getHargaProduk() + " x "
        //                                 + detail.getJumlahProduk() + " = Rp." + detail.getSubTotalPembayaran());
        //                     }
        //                     System.out.println("==================================================");
        //                     System.out.println("==================================================");
        //                     System.out.println("Total Pembayaran: Rp " + Transaksi.getTotalPembayaran());
        //                     total_bayar = Transaksi.getTotalPembayaran();
        //                 }
        //             }
        
        //             if (id_transaksi == null) {
        //                 System.out.println("No transactions found for user: " + user);
        //                 br.readLine();
        //                 break;
        //             }

        //             System.out.println("==================================================");
        //             System.out.println("  [1.Pembayaran] [2.Hapus Transaksi] [0.Kembali]  ");
        //             System.out.println("==================================================");
        //             System.out.print("Masukan Pilihan : ");
        //             String konfirmasiPembayaran = br.readLine();
        //             System.out.println("==================================================");
                    
        //             if (konfirmasiPembayaran.equals("1")) {
        //                 finalToko newtoko = new finalToko();
        //                 clearScreen();
        //                 newtoko.displayPembayaran();
        //                 System.out.println("Pilih Pembayaran ke [1/2] :");
        //                 String pilihPembayaran = br.readLine();
        //                 if (pilihPembayaran.equals("1") || pilihPembayaran.equals("2")) {
        //                     clearScreen();
        //                     System.out.println("==================================================");
        //                     System.out.println("                Masukan Data Pembayaran           ");
        //                     System.out.println("==================================================");
        //                     System.out.print("Masukan Nama Bank     : ");
        //                     String bankPengirim = br.readLine();
        //                     System.out.print("Nama Pemilik Rekening : ");
        //                     String namaPengirim = br.readLine();
        //                     int nomorRekening = 0;
        //                     while (true) {
        //                         try {
        //                             System.out.print("Nomor Rekening        : ");
        //                             String nomorRekeningInput = br.readLine();
        //                             nomorRekening = Integer.parseInt(nomorRekeningInput);
        //                             break; // keluar dari loop jika berhasil
        //                         } catch (NumberFormatException e) {
        //                             System.out.println("Input nomor rekening tidak valid. Harap masukkan angka.");
        //                         }
        //                     }
        //                     System.out.println("==================================================");
                            
        //                     String idPembayaran = UUID.randomUUID().toString();
        //                     String bankPenerima = pilihPembayaran.equals("1") ? newtoko.getBank1() : newtoko.getBank2();
        //                     String namaPenerima = pilihPembayaran.equals("1") ? newtoko.getPenerima1() : newtoko.getPenerima2();
        //                     Integer noRekPenerima = pilihPembayaran.equals("1") ? newtoko.getNoRek1() : newtoko.getNoRek2();
                            
        //                     pembayaran bayar = new pembayaran(idPembayaran, id_transaksi, bankPenerima, namaPenerima, noRekPenerima, bankPengirim, namaPengirim,nomorRekening, total_bayar);
        //                     Main.listPembayaran.add(bayar);
        //                     bayar.savePembayaranToDB();
                            
        //                     // Update the transaction status
        //                     for (transaksi Transaksi : Main.listTransaksi) {
        //                         if (Transaksi.getIdTransaksi().equals(id_transaksi)) {
        //                             Transaksi.setStatus("waiting_validate");
        //                             Transaksi.updateStatus();
        //                             break;
        //                         }
        //                     }
        //                     clearScreen();
        //                     for (int i = 0; i < Main.listPembayaran.size(); i++) {
        //                         pembayaran currentPayment = Main.listPembayaran.get(i);
        //                         System.out.println("++========================================================++");
        //                         System.out.println("||                   Pembayaran Berhasil             ||");
        //                         System.out.println("||           Silahkan Tunggu Admin Memvalidasi       ||");
        //                         System.out.println("++========================================================++");
        //                         System.out.println(" ID Pembayaran    : " + currentPayment.getIdPembayaran());
        //                         System.out.println(" ID Transaksi     : " + currentPayment.getIdTransaksi());
        //                         System.out.println( " Nominal Transfer : Rp." + currentPayment.getNominalTransfer());
        //                         System.out.println(" Penerima  ================================================");
        //                         System.out.println("Bank         : " + currentPayment.getBankPenerima());
        //                         System.out.println("Nama Pemilik : " + currentPayment.getNamaPenerima());
        //                         System.out.println("No Rekening  : " + currentPayment.getNoRekPenerima());
        //                         System.out.println(" Pengirim  ================================================");
        //                         System.out.println("Bank         : " + currentPayment.getBankPengirim());
        //                         System.out.println("Nama Pemilik : " + currentPayment.getNamaPengirim());
        //                         System.out.println("No Rekening  : " + currentPayment.getNoRekPengirim());
        //                         System.out.println("===========================================================");
        //                     }
        //                     System.out.println("\n[press --> [Enter] ]");
        //                     br.readLine();
        //                     break; 
        //                 } else if (pilihPembayaran.equals("0")) {
        //                     break;
        //                 } else {
        //                     System.out.println("Pilihan tidak valid.");
        //                 }
        //             } else if (konfirmasiPembayaran.equals("2")) {
        //                 System.out.println("Transaksi akan dihapus");
        //                 deleteTransaksi(id_transaksi);
        //                 System.out.println("Transaksi berhasil dihapus");
        //                 br.readLine();
        //                 clearScreen();
        //                 break;
        //             } else if (konfirmasiPembayaran.equals("0")) {
        //                 break;
        //             } else {
        //                 System.out.println("Pilihan tidak valid.");
        //             }
        //         }
        //         break;
        //     }else{
        //         System.out.println("Tidak ada transaksi yang perlu dibayar");
        //         br.readLine();
        //         break;
        //     }
        // }
    }

    public static void deleteTransaksi(String id_transaksi) {

        Main.listTransaksi.removeIf(transaksi -> transaksi.getIdTransaksi().equals(id_transaksi));
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM detailtransaksi WHERE id_transaksi = ?")) {
            stmt.setString(1, id_transaksi);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM transaksi WHERE id_transaksi = ?")) {
            stmt.setString(1, id_transaksi);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean userHasTransaction(String username) {
        for (transaksi trans : Main.listTransaksi) {
            if (trans.getPembeli().equals(username) && trans.getStatus().equals("waiting_payment")) {
                return true;
            }
        }
        return false;
    }

    public static void processTransaction(String username, BufferedReader br) throws IOException, SQLException {
        if (userHasTransaction(username)) {
            clearScreen();
            displayTransaksi(username);
            System.out.println("\n=======================================");
            System.out.println("       Anda sudah memiliki transaksi   "); 
            System.out.println("    Silahkan Lihat Menu Pembayaran Anda");
            System.out.println("            press --> [ Enter ]        ");
            System.out.println("=======================================");
            br.readLine();
        }else{
            String idTransaksi = UUID.randomUUID().toString();
            String status= "waiting_payment";
            transaksi newTransaksi = new transaksi(idTransaksi, username,status);
            while (true) {
                clearScreen();
                System.out.println("++================================++");
                System.out.println("++=====   Menu Beli Barang   =====++");
                System.out.println("|| 1. Joran      |  4. Pelet      ||");
                System.out.println("|| 2. Mata kail  |  0. Kembali    ||");
                System.out.println("|| 3. Senar      |                ||");
                System.out.println("++================================++");
                System.out.print("Pilih Menu = ");
    
                String pilihPembelian;
                try {
                    pilihPembelian = br.readLine();
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan saat membaca input!");
                    promptEnterKey(br);
                    continue;
                }
                switch (pilihPembelian) {
                    case "1":
                        handleJoranPurchase(newTransaksi, br);
                        break;
                    case "2":
                        handleMataKailPurchase(newTransaksi, br);
                        break;
                    case "3":
                        handleSenarPurchase(newTransaksi, br);
                        break;
                    case "4":
                        handlePeletPurchase(newTransaksi, br);
                        break;
                    case "0":
                        if (confirmTransaction(br)) {
                            Main.listTransaksi.add(newTransaksi);
                            newTransaksi.saveTransaksiToDB();
                        }
                        System.out.println("\n=======================================");
                        System.out.println("    Silahkan Lihat Menu Pembayaran Anda");
                        System.out.println("            press --> [ Enter ]        ");
                        System.out.println("=======================================");
                        br.readLine();
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                        promptEnterKey(br);
                }
        }
        }
    }

    private static void handleJoranPurchase(transaksi newTransaksi, BufferedReader br) throws IOException {
        menuAdmin.displayJoran();
        System.out.println("Masukan No Produk : ");
        try {
            int choice = Integer.parseInt(br.readLine()) - 1;
            if (choice >= 0 && choice < Main.listJoran.size()) {
                while (true) {
                    System.out.print("Masukan Jumlah Joran: ");
                    try {
                        int jmlhJoran = Integer.parseInt(br.readLine());
                        if (jmlhJoran <= 0) {
                            System.out.println("Jumlah joran harus lebih dari 0!");
                            continue;
                        }
                        String idDetail = UUID.randomUUID().toString();

                        detailTransaksi detail = new detailTransaksi(idDetail, newTransaksi.getIdTransaksi(),
                        Main.listJoran.get(choice).getIdProduk(),Main.listJoran.get(choice).getNama(),Main.listJoran.get(choice).getHarga(),jmlhJoran
                        );
                        newTransaksi.tambahItem(detail);
                        Main.listDetail.add(detail);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid! Harap masukkan angka.");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat membaca input!");
                    }
                }
            } else {
                System.out.println("Nomor Joran Tidak Valid!");
                promptEnterKey(br);
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Input tidak valid! Harap masukkan angka.");
            promptEnterKey(br);
        }
    }

    private static void handleMataKailPurchase(transaksi newTransaksi, BufferedReader br) throws IOException {
        menuAdmin.displayMataKail();
        System.out.println("Masukan No Produk : ");
        try {
            int choice = Integer.parseInt(br.readLine()) - 1;
            if (choice >= 0 && choice < Main.listMataKail.size()) {
                while (true) {
                    System.out.print("Masukan Jumlah Mata Kail: ");
                    try {
                        int jmlhMataKail = Integer.parseInt(br.readLine());
                        if (jmlhMataKail <= 0) {
                            System.out.println("Jumlah mata kail harus lebih dari 0!");
                            continue;
                        }
                        String idDetail = UUID.randomUUID().toString();
                        detailTransaksi detail = new detailTransaksi(
                            idDetail, newTransaksi.getIdTransaksi(),
                            Main.listMataKail.get(choice).getIdProduk(),
                            Main.listMataKail.get(choice).getNama(),
                            Main.listMataKail.get(choice).getHarga(),
                            jmlhMataKail
                        );
                        newTransaksi.tambahItem(detail);
                        Main.listDetail.add(detail);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid! Harap masukkan angka.");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat membaca input!");
                    }
                }
            } else {
                System.out.println("Nomor Mata Kail Tidak Valid!");
                promptEnterKey(br);
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Input tidak valid! Harap masukkan angka.");
            promptEnterKey(br);
        }
    }
    
    private static void handleSenarPurchase(transaksi newTransaksi, BufferedReader br) throws IOException {
        menuAdmin.displaySenar();
        System.out.println("Masukan No Produk : ");
        try {
            int choice = Integer.parseInt(br.readLine()) - 1;
            if (choice >= 0 && choice < Main.listSenar.size()) {
                while (true) {
                    System.out.print("Masukan Jumlah Senar: ");
                    try {
                        int jmlhSenar = Integer.parseInt(br.readLine());
                        if (jmlhSenar <= 0) {
                            System.out.println("Jumlah Senar harus lebih dari 0!");
                            continue;
                        }
                        String idDetail = UUID.randomUUID().toString();
                        detailTransaksi detail = new detailTransaksi(idDetail,
                            newTransaksi.getIdTransaksi(),
                            Main.listSenar.get(choice).getIdProduk(),
                            Main.listSenar.get(choice).getNama(),
                            Main.listSenar.get(choice).getHarga(),
                            jmlhSenar
                        );
                        newTransaksi.tambahItem(detail);
                        Main.listDetail.add(detail);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid! Harap masukkan angka.");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat membaca input!");
                    }
                }
            } else {
                System.out.println("Nomor Senar Tidak Valid!");
                promptEnterKey(br);
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Input tidak valid! Harap masukkan angka.");
            promptEnterKey(br);
        }
    }
    
    private static void handlePeletPurchase(transaksi newTransaksi, BufferedReader br) throws IOException {
        menuAdmin.displayPelet();
        System.out.println("Masukan No Produk : ");
        try {
            int choice = Integer.parseInt(br.readLine()) - 1;
            if (choice >= 0 && choice < Main.listPelet.size()) {
                while (true) {
                    System.out.print("Masukan Jumlah Pelet: ");
                    try {
                        int jmlhPelet = Integer.parseInt(br.readLine());
                        if (jmlhPelet <= 0) {
                            System.out.println("Jumlah Pelet harus lebih dari 0!");
                            continue;
                        }
                        String idDetail = UUID.randomUUID().toString();
                        detailTransaksi detail = new detailTransaksi(idDetail,
                            newTransaksi.getIdTransaksi(),
                            Main.listPelet.get(choice).getIdProduk(),
                            Main.listPelet.get(choice).getNama(),
                            Main.listPelet.get(choice).getHarga(),
                            jmlhPelet
                        );
                        newTransaksi.tambahItem(detail);
                        Main.listDetail.add(detail);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid! Harap masukkan angka.");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat membaca input!");
                    }
                }
            } else {
                System.out.println("Nomor Pelet Tidak Valid!");
                promptEnterKey(br);
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Input tidak valid! Harap masukkan angka.");
            promptEnterKey(br);
        }
    }

    private static boolean confirmTransaction(BufferedReader br) {
        while (true) {
                    System.out.print("\nSimpan Transaksi [y/t]: ");
                    try {
                        String konfirmasiTransaki = br.readLine().toLowerCase();
                        if (konfirmasiTransaki.equals("y")) {
                            return true;
                        } else if (konfirmasiTransaki.equals("t")) {
                            return false;
                        } else {
                            System.out.println("Pilihan tidak valid!");
                        }
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan saat membaca input!");
                    }
                }
            }
}
