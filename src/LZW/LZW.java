package LZW;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LZW {

    public Map<String,Integer>map;
    public int mapSize;
    public List<Integer> listOfChIndex=new ArrayList<>();
    public List<String>Binarylist=new ArrayList<>();
    public String decompressedText;
    public static String compressionRatioLZW;



    public LZW(){

        //creating the initial map chars 0-255
        mapSize=256;

        map=new HashMap<String,Integer>();
        for (int i=0;i<mapSize;i++){
            map.put(String.valueOf((char)i),i);
        }

    }

    public String compression(String uncompressed){
        String previous="";
        String Current="";
        String newString="";

        for (int i=0;i<uncompressed.length();i++){


            Current=uncompressed.substring(i,i+1);

            newString=previous+Current;


            if (map.containsKey(newString)){

                previous=newString;

            }else if(!map.containsKey(newString)){

                map.put(newString,mapSize++);
                listOfChIndex.add(map.get(previous));
                previous=Current;
            }
        }
        listOfChIndex.add(map.get(previous));  //adding last string of char


        //}



        return String.valueOf(listOfChIndex);
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String efficiency(String uncompressed){

        for(int i:listOfChIndex){                                  //adds all chars used from the compressed list to a list which converts each string char into binary
            String binary=Integer.toBinaryString( i);
            Binarylist.add(binary);

        }
        int largest=0;
        for (String s:Binarylist){

            if (s.length()>largest){
                largest=s.length();

            }

        }//System.out.println(largest);
        for (int i=0; i<Binarylist.size();i++ ) {                           //makes sure each string char is the same binary format


            while (Binarylist.get(i).length()!=largest){
                Binarylist.set(i,"0"+Binarylist.get(i));

            }
        }
        if(uncompressed.length()>0&& largest<8){
            largest=8;

        }

        System.out.println(largest);
        int bitsUsed=Binarylist.size()*largest;
        int originalBits=uncompressed.length()*8;
        System.out.println(bitsUsed);
        System.out.println(originalBits);

        double savings=(1-((double)bitsUsed/(double)originalBits))*100;
        System.out.println(savings);

        //compression ration
        DecimalFormat df= new DecimalFormat("#.##");
        double ratio= (double)originalBits/(double)bitsUsed;
        compressionRatioLZW=df.format(ratio)+" : 1";

        return String.format("%,.3f",savings)+"%";
    }

    public String decompress( List<Integer> compressedChars  ){
        HashMap<Integer,String> initialMap=new HashMap<>();
        int mapSize=256;
        for (int i=0;i<256;i++){
            String c=String.valueOf((char) i);
            initialMap.put(i,c);
        }

        decompressedText="";
       String newWord;

        int previousWordIndex=(compressedChars.get(0));
        String previousWord=initialMap.get(previousWordIndex);
        //decompressedText+=currentWord;


        //System.out.println(Arrays.toString(compressedChars.toArray()));
        for (int i=1;i< compressedChars.size();i++) {
//            previousWord=currentWord;
//            String previousWordIndex= String.valueOf(compressedChars.get(i-1));
//            currentWordIndex=compressedChars.get(i);
//            currentWord=initialMap.get(currentWordIndex);
//            System.out.println("current word: "+previousWord);
//
//            //if (i+1<compressedChars.size()){
//            //currentWord= getKeyByValue(initialMap,compressedChars.get(i+1));
//
//                if (initialMap.containsValue(currentWord)){
//                    decompressedText+=currentWord;
//                    String P=previousWord;
//                    String C=String.valueOf(currentWord.charAt(0));
//                    String newString=P+C;
//                    initialMap.put(initialMap.size()+1,newString);
//                    //System.out.println(String.valueOf((char)decimalChar));
//
//                }
//                else{
//                    String P=previousWord;
//                    String C= String.valueOf(currentWord.charAt(0));
//                    currentWord=P+C;
//                    decompressedText+=currentWord;
//
//
//                    //System.out.println("dont know this string char");
//                }
//            //}
            String currentWord=initialMap.get(compressedChars.get(i));
            int result;
            if (initialMap.containsValue(previousWord+currentWord)){
                result=getKeyByValue(initialMap,previousWord+currentWord);
            }else{
                result=-1;
            }
            if (result!=-1){
                previousWord+=currentWord;
                newWord=null;
            }else{

                if (!(currentWord==null)){
                    newWord=previousWord+currentWord.substring(0,1);
                    initialMap.put(initialMap.size(),newWord);
                    decompressedText+=previousWord;
                    previousWord=currentWord;
                }else{
                    newWord=previousWord+previousWord.substring(0,1);
                    initialMap.put(initialMap.size(),newWord);
                    decompressedText+=previousWord;
                    previousWord=currentWord=newWord;
                }

            } //System.out.println(decompressedText);
        }
        if (previousWord!=null){
            decompressedText+=previousWord;
        }

        return decompressedText;
    }

    public void writoToFile(String file){

        try {

            //get path of project and make the file there
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();
            String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());

            //delete File if exists
            Path path = FileSystems.getDefault().getPath(currentDir, "LZWDecom"+file);
            boolean success= Files.deleteIfExists(path);

            //write decompressed text to file
            PrintWriter writer=new PrintWriter(new File("LZWDecom"+file));
            writer.println(decompressedText);
            writer.close();


            //delete File if it exists
            Path path2=FileSystems.getDefault().getPath(currentDir, currentDir+"LZWCom"+file);
            boolean success2=Files.deleteIfExists(path2);

            //write compressed chars(respective index) to file
            PrintWriter writer2=new PrintWriter(new File(currentDir+"LZWCom"+file));
            writer2.println(Arrays.toString(listOfChIndex.toArray()));
            writer2.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }











    public static void main(String[] args) {
        String c= "i swdsldsadolaaq\n" +
                "\n" +
                "\n" +
                "fftvbbnnbnm,m,,.k..louiuyrter4356898000-\n" +
                "\n" +
                "ssssssssss\n" +
                "AAAAAAAAAAAAAAAAAdssAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa\n" +
                "hggggggggggggggg\n" +
                "\n" +
                "\n" +
                "\n" +
                "assdsssdsdaasa";
        LZW lzw=new LZW();
        //System.out.println(lzw.map.entrySet());
        //System.out.println(lzw.map.get("a"));
        System.out.println(lzw.compression(c));
        System.out.println("efficiency: "+lzw.efficiency(c));
        System.out.println(lzw.Binarylist);
        for (int i:lzw.listOfChIndex    ) {
            //System.out.println(i);//getKeyByValue(lzw.map,i));

        }
        System.out.println("*********************************************************************");
        System.out.println("Decompression");
        lzw.decompress(lzw.listOfChIndex);


        int d=3;
        int f=d;
        f=f+1;
        System.out.println(f);
        System.out.println(d);





    }






}
