package com.tokopancing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    
    public static ArrayList<produk> listProduk = new ArrayList<>();
    public static ArrayList<joran> listJoran = new ArrayList<>();
    public static ArrayList<mataKail> listMataKail = new ArrayList<>();
    public static ArrayList<senar> listSenar = new ArrayList<>();
    public static ArrayList<pelet> listPelet = new ArrayList<>();
    public static ArrayList<transaksi> listTransaksi = new ArrayList<>();
    public static ArrayList<detailTransaksi> listDetail = new ArrayList<>();
    public static ArrayList<pembayaran> listPembayaran = new ArrayList<>();
    public static ArrayList<users> akunUser = new ArrayList<>();
    
    public static String currentUser;

    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);    
        BufferedReader br = new BufferedReader(isr);
        
        try {
        Connection connection = DatabaseConnection.getConnection();
        controller dataController  = new controller(connection);

        //Load set data ke ArrayList
        controller.getAllUsers();
        controller.loadDataJoran();
        controller.loadDataSenar();
        controller.loadDataMataKail();
        controller.loadDataPelet();
        controller.loadDataTransaksi();
        controller.loadDetailTransaksi();
        controller.loadPembayaran();

        while (true) {

            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("============================================="); 
            System.out.println("          SELAMAT DATANG DI TOKO PANCING     ");
            System.out.println("=============================================");    
            System.out.println("   [1] REGISTER    [2] LOGIN    [0] EXIT     ");
            System.out.println("=============================================");    
            System.out.print("PILIH MENU : ");
            String option;
            try {
                option = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("=============================================");    
                System.out.println("         INIVALID OPTION, TRY AGAIN          ");
                System.out.println("=============================================");    
                br.readLine();
                continue;
            }

            if (option.equals("1")) {
                while (true) {
                    menuPelanggan.clearScreen();
                    System.out.println("=============================================");
                    System.out.println("         HALAMAN REGISTRASI PENGGUNA         ");
                    System.out.println("=============================================");
                    System.out.print("Enter username: ");
                    String username = br.readLine();
                    System.out.print("Enter password: ");
                    String password = br.readLine();
                    
                    try {
                        dataController.register(username, password, "pelanggan");
                        System.out.println("=============================================");
                        System.out.println("User registered successfully!");
                        System.out.println("=============================================");
                        break; 
                    } catch (IllegalArgumentException e) {
                        System.out.println("=============================================");
                        System.out.println("Username sudah ada, silakan coba lagi.");
                        System.out.println("=============================================");
                        menuPelanggan.promptEnterKey(br);
                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("=============================================");
                        System.out.println("Terjadi kesalahan, silakan coba lagi.");
                        System.out.println("=============================================");
                        break;
                    }
                }
                    
                } else if (option.equals("2")) {
                    System.out.println("=============================================");
                    System.out.println("         HALAMAN LOGIN PENGGUNA              ");
                    System.out.println("=============================================");
                    // Login
                    System.out.print("Enter username: ");
                    String username = br.readLine();
                    System.out.print("Enter password: ");
                    String password = br.readLine();

                    users user = dataController.login(username, password);

                    if (user != null) {
                        if (user.getRole().equals("pelanggan")) {
                            currentUser = user.getUsername();
                            menuPelanggan mnu = new menuPelanggan();
                            mnu.displayPelangganMenu(currentUser);
                        } else if(user.getRole().equals("admin")){
                            menuAdmin adminmenu = new menuAdmin();
                            adminmenu.displayAdminMenu();
                        }else{
                            System.out.println("user not found");
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }

                } else if (option.equals("0")) {
                    System.out.println("Exiting...");
                    break;
                } else if (option.equals("3")) {
                    // controller.updateUser();
                } else {
                    continue;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}