package com.tokopancing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.UUID;

public class menuAdmin {
    InputStreamReader isr = new InputStreamReader(System.in);    
    BufferedReader br = new BufferedReader(isr);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void promptEnterKey(BufferedReader br) throws IOException {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("  ║            Tekan \"ENTER\" untuk Melanjutkan...        ║");
        System.out.println("  ╚════════════════════════════════════════════════════════╝");
        br.readLine();
    }

    // ArrayList<produk> listProduk , ArrayList<joran> listJoran
    public void displayAdminMenu() throws IOException, SQLException{
        while (true) {
            clearScreen();
            System.out.println("""
                ╔════════════════════════════════════════════════╗
                ║              === Toko Pancing ===              ║
                ║  1. Tambah Data Barang                         ║
                ║  2. Lihat Data Barang                          ║
                ║  3. Ubah Data Barang                           ║
                ║  4. Hapus Data Barang                          ║
                ║  5. Konfirmasi Pembayaran                      ║
                ║  6. Daftar  Transaksi                          ║
                ║  0. Exit                                       ║
                ╚════════════════════════════════════════════════╝
                    """);
                    System.out.print("╔══════════════════════════╗\n");
                    System.out.print("║   Pilih Menu =           ║\n");
                    System.out.print("╚══════════════════════════╝\n");
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
                while (true) {
                    clearScreen();
                    System.out.println("""
                        ╔═══════════════════════════════╗
                        ║           Pilih Barang        ║
                        ╠═══════════════════════════════╣
                        ║   1. Joran                    ║
                        ║   2. Mata Kail                ║
                        ║   3. Senar                    ║
                        ║   4. Pelet                    ║
                        ║   0. Kembali                  ║
                        ╚═══════════════════════════════╝
                        """);
                        System.out.print("╔══════════════════════════════════════════╗\n");
                        System.out.print("║ Pilih Barang Yang Ingin Ditambahkan =    ║\n");
                        System.out.print("╚══════════════════════════════════════════╝\n");
                        String tambahAlat = br.readLine();
                    if (tambahAlat.equals("1")) {
                        tambahJoran();
                    } else if (tambahAlat.equals("2")) {
                        tambahMataKail();
                    } else if (tambahAlat.equals("3")) {
                        tambahSenar();
                    } else if (tambahAlat.equals("4")) {
                        tambahPelet();
                    } else if (tambahAlat.equals("0")) {
                        break;
                    }else{
                        continue;
                    }
                }
            }else if(pilih.equals("2")){
                displayProduk();
                promptEnterKey(br);
            }else if(pilih.equals("3")){
                ubahJoran(br);
            }else if (pilih.equals("4")) {
                deleteJoran(br);
            }else if(pilih.equals("5")){
                for (transaksi cekTransaksi : Main.listTransaksi) {
                    if (cekTransaksi.getStatus().equals("success_payment")) {
                        while (true ) {
                            System.out.println("\n=====      Daftar  Pembayaran    =====");
                            for (int i = 0; i < Main.listPembayaran.size(); i++) {
                                pembayaran currentPayment = Main.listPembayaran.get(i);
                                System.out.println("\n[" + (i + 1) + "]===========================================");
                                System.out.println("ID Pembayaran    : " + currentPayment.getIdPembayaran());
                                System.out.println("ID Transaksi     : " + currentPayment.getIdTransaksi());
                                System.out.println("Nominal Transfer : Rp." + currentPayment.getNominalTransfer());
                                System.out.println("==============  Penerima  =================");
                                System.out.println("Bank         : " + currentPayment.getBankPenerima());
                                System.out.println("Nama Pemilik : " + currentPayment.getNamaPenerima());
                                System.out.println("No Rekening  : " + currentPayment.getNoRekPenerima());
                                System.out.println("==============  Pengirim  =================");
                                System.out.println("Bank         : " + currentPayment.getBankPengirim());
                                System.out.println("Nama Pemilik : " + currentPayment.getNamaPengirim());
                                System.out.println("No Rekening  : " + currentPayment.getNoRekPengirim());
                                System.out.println("===========================================");
                            }
                            Integer choiceInput = null;
                            System.out.println("[ ketik '9999' untuk keluar ]");
                            System.out.print("Pilih Transaksi : ");
                            try {
                                choiceInput = Integer.parseInt(br.readLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Input tidak valid. Masukkan nomor transaksi.");
                            }

                            if (choiceInput.equals(9999)) {
                                break;
                            }else if (choiceInput > 0 && choiceInput <= Main.listPembayaran.size()) {
                                pembayaran selectedPayment = Main.listPembayaran.get(choiceInput - 1);
                                for (transaksi Transaksi : Main.listTransaksi) {
                                    if (Transaksi.getIdTransaksi().equals(selectedPayment.getIdTransaksi())) {
                                        Transaksi.setStatus("success_payment");
                                        Transaksi.updateStatus();
                                        break;
                                    }
                                }
                                break;
                            } else {
                                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            }    
                        }
                        break;
                    }else{
                        System.out.println("Tidak ada transaksi yang dapat di validasi");
                        br.readLine();
                        break;
                    }
                }
            }else if(pilih.equals("6")){
                displayTransaksi();
                br.readLine();
            }else if(pilih.equals("0")){
                break;
           }
        }
            
    }

    public void displayTransaksi(){
        for (int i = 0; i < Main.listTransaksi.size(); i++) {
            System.out.println("["+(i+1)+".]===========================================");
            System.out.println("ID Transaksi : " + Main.listTransaksi.get(i).getIdTransaksi());
            System.out.println("Pembeli      : " + Main.listTransaksi.get(i).getPembeli());
            System.out.println("Status       : " + Main.listTransaksi.get(i).getStatus());
            System.out.println("=============================================");
            System.out.println("Detail Transaksi:");
            for (detailTransaksi detail : Main.listTransaksi.get(i).getItems()) {
                System.out.println(" - " + detail.getNamaProduk());
                System.out.println(" \tHarga Rp."+detail.getHargaProduk()+ "x" 
                + detail.getJumlahProduk() + " = Rp." + detail.getSubTotalPembayaran());
            }
            System.out.println("=============================================");
            System.out.println("\nTotal Pembayaran: Rp " + Main.listTransaksi.get(i).getTotalPembayaran());
        }
    }

    public void tambahJoran()throws IOException{
        clearScreen();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Nama Joran      =               ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");

        String nama = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Merek Joran  =              ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String merek = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Harga Joran  =              ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double harga = Double.parseDouble(br.readLine());
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan bahan Mata Kail  =            ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String bahan = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan panjang Joran  =              ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");

        double panjang = Double.parseDouble(br.readLine());
        String idProduk = UUID.randomUUID().toString();
        joran newjoran = new joran(idProduk,nama, merek, harga,bahan,panjang);
        Main.listJoran.add(newjoran);
        // Simpan ke database
        newjoran.saveToDB();
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("  ║   Data Barang Berhasil Ditambahkan!      ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        promptEnterKey(br);
    }
    public void tambahMataKail()throws IOException{
        clearScreen();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Nama Mata Kail      =           ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String nama = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Merek Mata Kail      =          ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String merek = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Harga Mata Kail      =          ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double harga = Double.parseDouble(br.readLine());
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Ukuran Mata Kail      =         ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double ukuran = Double.parseDouble(br.readLine());

        String idProduk = UUID.randomUUID().toString();
        mataKail newMataKail = new mataKail(idProduk,nama, merek, harga,ukuran);
        Main.listMataKail.add(newMataKail);
        newMataKail.saveToDB();

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("  ║   Data Barang Berhasil Ditambahkan!      ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        promptEnterKey(br);
    }
   
    public void tambahSenar() throws IOException{
        clearScreen();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Nama Senar  =                   ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String nama = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Merek Senar  =                  ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String merek = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Harga Senar  =                  ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double harga = Double.parseDouble(br.readLine());
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Ukuran Senar  =                 ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double ukuran = Double.parseDouble(br.readLine());

        String idProduk = UUID.randomUUID().toString();
        senar newSenar  = new senar(idProduk,nama, merek, harga, ukuran);
        Main.listSenar.add(newSenar);
        newSenar.saveToDB();
    
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("  ║   Data Barang Berhasil Ditambahkan!      ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        promptEnterKey(br);
    }
    public void tambahPelet()throws IOException{
        clearScreen();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Nama Pelet  =                   ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String nama = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Merek Pelet  =                  ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String merek = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Harga Pelet  =                  ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double harga = Double.parseDouble(br.readLine());
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Rasa Pelet  =                   ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        String rasa = br.readLine();
        System.out.print("╔══════════════════════════════════════════╗\n");
        System.out.print("║ Masukkan Berat Pelet  =                  ║\n");
        System.out.print("╚══════════════════════════════════════════╝\n");
        double berat = Double.parseDouble(br.readLine());

        String idProduk = UUID.randomUUID().toString();
        pelet newPelet  = new pelet(idProduk,nama, merek, harga,rasa,berat);
        Main.listPelet.add(newPelet);
        newPelet.saveToDB();
    
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("  ║   Data Barang Berhasil Ditambahkan!      ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        promptEnterKey(br);
    }
    
    public void displayProduk() throws IOException {
        System.out.println("════════════════ List Joran ════════════════════");
        displayJoran();
        System.out.println("════════════════ List MataKail ════════════════════");
        displayMataKail();
        System.out.println("════════════════ List Senar ════════════════════");
        displaySenar();
        System.out.println("════════════════ List Pelet ════════════════════");
        displayPelet();
        promptEnterKey(br);
    }

    public static void displayJoran(){
        for (int i = 0; i < Main.listJoran.size(); i++) {
            System.out.println("["+(i+1)+".]"+"══════════════════════════════════════════════");
            System.out.println("Nama    = " + Main.listJoran.get(i).getNama());
            System.out.println("Merek   = " + Main.listJoran.get(i).getMerek());
            System.out.println("Harga   = " +"Rp "+ Main.listJoran.get(i).getHarga());
            System.out.println("Bahan   = " + Main.listJoran.get(i).getBahan());
            System.out.println("Panjang = " + Main.listJoran.get(i).getPanjang()+" inch");
        }  
        System.out.println("════════════════════════════════════════════════");
    }
    public static void displayMataKail(){
        for (int i = 0; i < Main.listMataKail.size(); i++) {
            System.out.println("["+(i+1)+".]"+"══════════════════════════════════════════════");
            System.out.println("Nama    = " + Main.listMataKail.get(i).getNama());
            System.out.println("Merek   = " + Main.listMataKail.get(i).getMerek());
            System.out.println("Harga   = " +"Rp "+ Main.listMataKail.get(i).getHarga());
            System.out.println("Ukuran   = " + Main.listMataKail.get(i).getUkuran());
        }  
        System.out.println("════════════════════════════════════════════════");
    }
    public static void displaySenar(){
        for (int i = 0; i < Main.listSenar.size(); i++) {
            System.out.println("["+(i+1)+".]"+"══════════════════════════════════════════════");
            System.out.println("Nama    = " + Main.listSenar.get(i).getNama());
            System.out.println("Merek   = " + Main.listSenar.get(i).getMerek());
            System.out.println("Harga   = " +"Rp "+ Main.listSenar.get(i).getHarga());
            System.out.println("Ketebalan   = " + Main.listSenar.get(i).getKetebalan() + " mm");
        }  
        System.out.println("════════════════════════════════════════════════");
    }
    public static void displayPelet(){
        for (int i = 0; i < Main.listPelet.size(); i++) {
            System.out.println("["+(i+1)+".]"+"══════════════════════════════════════════════");
            System.out.println("Nama    = " + Main.listPelet.get(i).getNama());
            System.out.println("Merek   = " + Main.listPelet.get(i).getMerek());
            System.out.println("Harga   = " +"Rp "+ Main.listPelet.get(i).getHarga());
            System.out.println("Rasa   = " + Main.listPelet.get(i).getRasa());
            System.out.println("Berat   = " + Main.listPelet.get(i).getBerat());
        }  
        System.out.println("════════════════════════════════════════════════");
    }

    public static void ubahJoran(BufferedReader br) throws IOException,SQLException {
        clearScreen();
        displayJoran();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Joran yang ingin diubah:                     ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");

        int choice = Integer.parseInt(br.readLine()) - 1;
        if (choice >= 0 && choice < Main.listJoran.size()) {
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Nama Baru Joran =                               ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String nama = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Merek Baru Joran =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String merek = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Harga Baru Joran =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double harga = Double.parseDouble(br.readLine());
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Bahan Joran Baru =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String bahan = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Panjang Baru Joran =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double panjang = Double.parseDouble(br.readLine());
            joran joranToUpdate = Main.listJoran.get(choice);
            //update array list
            joranToUpdate.setNama(nama);
            joranToUpdate.setMerek(merek);
            joranToUpdate.setHarga(harga);
            joranToUpdate.setBahan(bahan);
            joranToUpdate.setPanjang(panjang);
            //update database menggunakan fungsi
            controller.updateJoran(joranToUpdate);

            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Joran Berhasil Diubah!                ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Joran Tidak Valid!                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }
    
    public static void ubahMataKail(BufferedReader br) throws IOException,SQLException {
        clearScreen();
        displayMataKail();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Mata Kail yang ingin diubah:                 ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");

        int choice = Integer.parseInt(br.readLine()) - 1;
        if (choice >= 0 && choice < Main.listMataKail.size()) {
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Nama Baru Mata Kail =                           ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String nama = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Merek Baru Mata Kail =                          ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String merek = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Harga Baru Mata Kail =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════════╝\n");
            double harga = Double.parseDouble(br.readLine());
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Ukuran Mata Kail Baru =                         ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double ukuran = Double.parseDouble(br.readLine());
            mataKail mataKailToUpdate = Main.listMataKail.get(choice);
            //update array list
            mataKailToUpdate.setNama(nama);
            mataKailToUpdate.setMerek(merek);
            mataKailToUpdate.setHarga(harga);
            mataKailToUpdate.setUkuran(ukuran);
            //update database menggunakan fungsi
            controller.updateMataKail(mataKailToUpdate);

            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Mata Kail Berhasil Diubah!            ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Mata Kail Tidak Valid!            ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }

    public static void ubahSenar(BufferedReader br) throws IOException,SQLException {
        clearScreen();
        displaySenar();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Senar yang ingin diubah:                     ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");

        int choice = Integer.parseInt(br.readLine()) - 1;
        if (choice >= 0 && choice < Main.listSenar.size()) {
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Nama Baru Senar =                               ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String nama = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Merek Baru Senar =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String merek = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Harga Baru Senar =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double harga = Double.parseDouble(br.readLine());
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Ketebalan Senar Baru =                          ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double ketebalan = Double.parseDouble(br.readLine());
            senar senarToUpdate = Main.listSenar.get(choice);
            //update array list
            senarToUpdate.setNama(nama);
            senarToUpdate.setMerek(merek);
            senarToUpdate.setHarga(harga);
            senarToUpdate.setKetebalan(ketebalan);
            //update database menggunakan fungsi
            controller.updateSenar(senarToUpdate);

            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Senar Berhasil Diubah!                ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Senar Tidak Valid!                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }

    public static void ubahPelet(BufferedReader br) throws IOException,SQLException {
        clearScreen();
        displayPelet();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Pelet yang ingin diubah:                     ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");

        int choice = Integer.parseInt(br.readLine()) - 1;
        if (choice >= 0 && choice < Main.listPelet.size()) {
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Nama Baru Pelet =                               ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String nama = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Merek Baru Pelet =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String merek = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Harga Baru Pelet =                              ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double harga = Double.parseDouble(br.readLine());
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Rasa Pelet Baru =                               ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            String rasa = br.readLine();
            System.out.print("╔══════════════════════════════════════════════════════════╗\n");
            System.out.print("║ Masukkan Berat Pelet Baru =                               ║\n");
            System.out.print("╚══════════════════════════════════════════════════════════╝\n");
            double berat = Double.parseDouble(br.readLine());
            pelet peletToUpdate = Main.listPelet.get(choice);
            //update array list
            peletToUpdate.setNama(nama);
            peletToUpdate.setMerek(merek);
            peletToUpdate.setHarga(harga);
            peletToUpdate.setRasa(rasa);
            peletToUpdate.setBerat(berat);
            //update database menggunakan fungsi
            controller.updatePelet(peletToUpdate);

            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Pelet Berhasil Diubah!                ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Pelet Tidak Valid!                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }

    public void deleteJoran(BufferedReader br) throws IOException{
        clearScreen();
        displayJoran();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Joran yang ingin dihapus =                   ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");
        int choice = Integer.parseInt(br.readLine()) - 1;

        if (choice >= 0 && choice < Main.listJoran.size()) {
            controller.deleteJoran(Main.listJoran.get(choice));
            Main.listJoran.remove(choice);
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Joran Berhasil Dihapus!               ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor joran Tidak Valid!                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }
    
    public void deleteMataKail(BufferedReader br) throws IOException{
        clearScreen();
        displayMataKail();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Mata Kail yang ingin dihapus =               ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");
        int choice = Integer.parseInt(br.readLine()) - 1;

        if (choice >= 0 && choice < Main.listMataKail.size()) {
            controller.deleteMataKail(Main.listMataKail.get(choice));
            Main.listMataKail.remove(choice);
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Mata Kail Berhasil Dihapus!           ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Mata Kail Tidak Valid!            ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }
    
    public void deleteSenar(BufferedReader br) throws IOException{
        clearScreen();
        displaySenar();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Senar yang ingin dihapus =                   ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");
        int choice = Integer.parseInt(br.readLine()) - 1;

        if (choice >= 0 && choice < Main.listSenar.size()) {
            controller.deleteSenar(Main.listSenar.get(choice));
            Main.listSenar.remove(choice);
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Senar Berhasil Dihapus!               ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Senar Tidak Valid!                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }
    
    public void deletePelet(BufferedReader br) throws IOException{
        clearScreen();
        displayPelet();
        System.out.print("╔══════════════════════════════════════════════════════════╗\n");
        System.out.print("║ Pilih Nomor Pelet yang ingin dihapus =                   ║\n");
        System.out.print("╚══════════════════════════════════════════════════════════╝\n");
        int choice = Integer.parseInt(br.readLine()) - 1;

        if (choice >= 0 && choice < Main.listPelet.size()) {
            controller.deletePelet(Main.listPelet.get(choice));
            Main.listPelet.remove(choice);
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println(  "║             Data Pelet Berhasil Dihapus!               ║");
            System.out.println(  "╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        } else {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                Nomor Pelet Tidak Valid!                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            promptEnterKey(br);
        }
    }

}