import LZW.LZW;
import RLE_text.RLE;
import RLE_text.finalRLEtxt;
import huffman.HuffmansTree;
import huffman.Node;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import static huffman.HuffmansTree.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {


    @Test
    public void decompressionEqualityLZW(){
            String text= "hello my name is eliandro";
            LZW l=new LZW();
            l.compression(text);
            String dcom=l.decompress(l.listOfChIndex);
            assertEquals(true, text.equals(dcom));

        }

    @Test
    public void decompressionEqualityRUN(){
        try {
            String fileName="file.txt";
            finalRLEtxt rle=new finalRLEtxt();


            String decom=rle.decompress(rle.compress(fileName));
            //System.out.println(decom);
            String text=rle.originalText(fileName);
            //System.out.println(text);
            assertEquals(true,text.equals(decom));


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void decompressionEqualityHUFF() throws IOException {
        String filename="file.txt";
        HuffmansTree ht=new HuffmansTree();
        Node fRoot=buildHuffT((makeFreq(originalTextHT(filename))));

        Map<Character,String> map= assignEncoding(fRoot);
        efficiencyHT(originalTextHT(filename),map);
        String decoded=decode(exportBinaryString(),map);
        assertEquals(true,originalTextHT(filename).equals(decoded));



    }


}
