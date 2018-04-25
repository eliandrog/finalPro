package RLE_text;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dave on 31/10/17.
 */
public class RLE {

    public byte [] lineBytes;
    public String line;
    public String binaryLine="";
    public String encoded ="";
    private final String DELIMITER="*";

    ArrayList<String> binary7bts=new ArrayList<String>();
    String zr="0";


    public RLE(File file){

        try {
            //Scanner reader = new Scanner(new File("file.txt"));
            Scanner reader = new Scanner(file);

            //Scan each line of file and turn each char into binary using getbytes()
            while(reader.hasNextLine()){
                line=(reader.nextLine());
                lineBytes=line.getBytes();
                //System.out.println(lineBytes);
                for (int byt :lineBytes){
                    //System.out.println(Integer.toBinaryString(byt));
                    //add 0s to binary values that do not have 7 bits
                    if ((Integer.toBinaryString(byt).length())<7 && Integer.toBinaryString(byt).length()>0){
                        String tempBits=Integer.toBinaryString(byt);
                        int length=tempBits.length();
                        int diff=7-length;
                        int i=0;
                        while(i<diff){
                            tempBits=zr+tempBits;
                            i++;
                        }
                        binaryLine+=tempBits;
                        //System.out.println(tempBits);

                        binary7bts.add((tempBits));

                    }else{binary7bts.add(Integer.toBinaryString(byt));

                        binaryLine+=Integer.toBinaryString(byt);
                    }


                    //binaryLines+=Integer.toBinaryString(byt);
                }
                binaryLine+="\n";

                ///System.out.println(line);
                ///System.out.println(binaryLines);
                //line=line+ reader.nextLine()+"\n";

            }
            reader.close();
            //System.out.println(binary7bts);


            //iterates through the array list and decodes the binary into a character
            int j=0;

            while (j<binary7bts.size()){
                int Bletter=Integer.parseInt(binary7bts.get(j));
                //System.out.println(Bletter);

                char letter= (char)Integer.parseInt(String.valueOf(Bletter),2) ;
                //System.out.println(letter);

                j++;
            }

            System.out.println(binaryLine);

            int k=0;

            for (int i = 0; i < binaryLine.length(); i++) {
                int runs = 1;
                while (i+1 < binaryLine.length() && binaryLine.charAt(i) == binaryLine.charAt(i+1)) {
                    runs++;
                    i++;
                }
                encoded=encoded+runs+DELIMITER+ binaryLine.charAt(i);
            }
            System.out.println(encoded);




        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error on opening the file");
        }

    }


}
