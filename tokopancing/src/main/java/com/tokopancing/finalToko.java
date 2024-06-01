package com.tokopancing;

public final class finalToko {
    private final String Bank1 = "Mandiri";
    private final String Penerima1 = "Dwi Reza";
    private final Integer NoRek1 = 10203040; 
    private final String Bank2 = "BNI";
    private final String Penerima2 = "Ardhifa";
    private final Integer NoRek2 = 10203040;
    
    public void displayPembayaran(){
        System.out.println("===================================");
        System.out.println("  Tujuan Pembayaran Melalui Bank   ");
        System.out.println("===================================");
        System.out.println("Bank 1 ");
        System.out.println("Bank Tujuan  : " + Bank1);
        System.out.println("Nama Pemilik : " + Penerima1);
        System.out.println("No Rekening  : " + NoRek1);
        System.out.println("===================================");
        System.out.println("Bank 2 ");
        System.out.println("Bank Tujuan  : " + Bank2);
        System.out.println("Nama Pemilik : " + Penerima2);
        System.out.println("No Rekening  : " + NoRek2);
        System.out.println("===================================");        
    }
    public String getBank1() {
        return Bank1;
    }
    public String getBank2() {
        return Bank2;
    }
    public String getPenerima1() {
        return Penerima1;
    }
    public String getPenerima2() {
        return Penerima2;
    }
    public Integer getNoRek1() {
        return NoRek1;
    }
    public Integer getNoRek2() {
        return NoRek2;
    }

}
