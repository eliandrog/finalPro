package huffman;

import java.io.File;
import java.util.Map;

import static huffman.HuffmansTree.*;

/**
 * Created by egonca on 07/12/2017.
 */
public class Main {
    public static void main(String[] args) {
        String fileName="file.txt";
        try {
            File file = new File(fileName);
            String str="i swdsldsadolaaq\n" +
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
            System.out.println(str.length());
            //HuffmansTree huff= new HuffmansTree(),huff1=new HuffmansTree();
            //huff.sortMap(huff.makeFreq(str));
            //System.out.println(huff.makeFreq(str));

            //System.out.println(huff.mapFreq.entrySet());
            //System.out.println("\n");
            //System.out.println(huff1.makeMinHeap(huff1.sortMap(huff1.makeFreq(str))));

            //System.out.println(huff1.arrNodes.toString());
           // huff1.arrNodes.remove(1);


            Node fRoot= buildHuffT((makeFreq(str)));

            Map<Character,String>map= HuffmansTree.assignEncoding(fRoot);
            System.out.println(map.toString());
            //System.out.println(fRoot);
            //System.out.println(map);
            //HuffmanCompressionResult s=HuffmansTree.compression(str);
            //System.out.println(s);
            String compressionBits="";
            for(int i=0;i<str.length();i++){

                compressionBits+=map.get((char)str.charAt(i));
            }
            System.out.println("Number of bits used when compressed = "+compressionBits.length());
            System.out.println(compressionBits);
            System.out.println("number of bits used when uncompressed = "+str.length()*8);

            System.out.println("saving= "+(((double)compressionBits.length()/(double)str.length()*8))+"%");
        }catch (Exception e){
            System.out.println("File not found");
            e.printStackTrace();
        }

    }
}
