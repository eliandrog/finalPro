package Gui;//package RLE_text;

import Gui.Home;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by dave on 06/11/17.
 */
public class Main_methods {
    public static void main(String[] args) throws InterruptedException, IOException {
        Home home= new Home();
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setVisible(true);
//        String fileName="file.txt";
//        File file=new File(fileName);
//        FileInputStream fis = new FileInputStream(file);
//        finalRLEtxt rle= new finalRLEtxt();
//        System.out.println("Compressed File:");
//        String rleCom=finalRLEtxt.compress(fileName);
//        System.out.println(StringEscapeUtils.escapeJava(rleCom));
//        System.out.println("\n\n*************************************************************\n");
//        System.out.println("Decompressed file");
//        String rleDeCom=finalRLEtxt.decompress(rleCom);
//
//        System.out.println(rleDeCom);
//        System.out.println("finished\n\n");
//
//        String text= "";
//        char c;
//        int i=0;
//        while(fis.available() > 0){
//            c = (char) fis.read();
//            text=text+c;
//
//
//        }
//        text=text.replaceAll("(\r\n)","\n");
//
//
//
//        System.out.println(finalRLEtxt.efficiency(text,rleCom));
//
//









    }


}
