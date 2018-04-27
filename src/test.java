import LZW.LZW;
import RLE_text.finalRLEtxt;
import huffman.HuffmansTree;
import huffman.Node;
import huffman_image.huff_Img_Node;
import huffman_image.huff_img;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

import static huffman.HuffmansTree.*;
import static huffman_image.huff_img.areCodesPrefix;
import static huffman_image.huff_img.assignEncoding;
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

        Map<Character,String> map= ht.assignEncoding(fRoot);
        efficiencyHT(originalTextHT(filename),map);
        String decoded=decode(exportBinaryString(),map);
        assertEquals(true,originalTextHT(filename).equals(decoded));



    }

    @Test
    public void arecodesPrefixHFMIM(){


        try {

        //Huffmans encoding Image
        huff_img huff = new huff_img("image.tiff");
        huff_Img_Node finalNode = huff.buildHFT();
        Map<Color, String> bitCodeMap = assignEncoding(finalNode);      //makes map og the final node tree and its given bitcode
        huff.encodeImage(huff.ogImage, bitCodeMap);
        huff.decompress(finalNode);
        huff.rebuildImage(huff.compImg);
        assertEquals(true,areCodesPrefix(bitCodeMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
