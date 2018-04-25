package RLE_text;

import Gui.Home;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by dave on 28/10/17.
 */
public class main {
    public static void main(String[] args) throws IOException {
        Home home= new Home();
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setVisible(true);

        //Scanner scn=new Scanner(System.in);
        //String filename=scn.next();
        //System.out.println(filename);
//        String line="";
//        byte [] lineBytes;
//        String binaryLines="";
//        boolean bol=true;
//        ArrayList<String> binary7bts=new ArrayList<String>();
//        //File fileName=new File(filename);
//        String zr="0";
//
//
//        String curDir = new File(".").getAbsolutePath();
//        System.out.println("Current sys dir: " + curDir);
//
//        try {
//            Scanner reader = new Scanner(new File("file.txt"));
//
//
//             while(reader.hasNextLine()){
//                 line=(reader.nextLine());
//                 lineBytes=line.getBytes();
//                 for (int byt :lineBytes){
//                     //System.out.println(Integer.toBinaryString(byt));
//
//                     if ((Integer.toBinaryString(byt).length())<7 && Integer.toBinaryString(byt).length()>0){
//                         String tempBits=Integer.toBinaryString(byt);
//                         int length=tempBits.length();
//                         int diff=7-length;
//                         int i=0;
//                         while(i<diff){
//                             tempBits=zr+tempBits;
//                             i++;
//                         }
//                         System.out.println(tempBits);
//
//                         binary7bts.add((tempBits));
//
//                     }else{binary7bts.add(Integer.toBinaryString(byt));
//                          }
//
//
//                     //binaryLines+=Integer.toBinaryString(byt);
//                 }
//                 binaryLines+="\n";
//
//                 ///System.out.println(line);
//                 ///System.out.println(binaryLines);
//                 //line=line+ reader.nextLine()+"\n";
//
//             }
//             reader.close();
//            System.out.println(binary7bts);
//
//            int j=0;
//
//             while (j<binary7bts.size()){
//                 int Bletter=Integer.parseInt(binary7bts.get(j));
//                 System.out.println(Bletter);
//
//                 char letter= (char)Integer.parseInt(String.valueOf(Bletter),2) ;
//                 System.out.println(letter);
//
//                 j++;
//             }
//
//
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("error on opening the file");
//        }








//        byte[] word_byte= word.getBytes();
//        System.out.println(Arrays.toString(word_byte));
//        String word_binary="";
//
//        for (int bts : word_byte){
//            word_binary += Integer.toBinaryString(bts);
//
//
//        }
//        System.out.println(word_binary);
//
//        String compress="";int c;


//        for (int i=0;i <word_byte.length;i++){
//            c=1;
//            //int c1=0;
//            System.out.println(word_byte[i]);
//            System.out.println(i);
//            if(i!=word_byte.length-1){
//                while(word_byte[i]==word_byte[i+1]){
//
//                    //System.out.println(word_byte[i]);
//                    //System.out.println(word_byte[i+1]);
//
//                    c++;
//                    i++;
//                }
//                System.out.println("letter "+Byte.toString(word_byte[i])+" counts "+c);
//            }else{
//                System.out.println("im in the penultimate");
//
//            }
//
//
//        }

    }
}
