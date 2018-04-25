package huffman_image;


import huffman.Node;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.Buffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class huff_img {
    public String error;
    public Map<Color,Integer>freqMap;
    public Map<Color,String>decompressionMap;
    public  Color[][] ogImage,decomIMG ;
    public String[][] compImg;
    public int picHeight,picWidth;
    public  String format,FILENAME,compressionRatioHFIMG;
    public int totalBitsUsed;
    public Color[][] dImg;


    public huff_img(String filename){
        ;
        try {
            //opens picture and makes frequency map of the rbg value of each pixel
            File f=new File(filename);
            BufferedImage img= ImageIO.read(f);
            freqMap= new HashMap<Color, Integer>();
            picHeight=img.getHeight();
            picWidth=img.getWidth();
            Color[][]image=new Color[picHeight][picWidth];
            format=filename.substring(filename.indexOf('.')+1,filename.length()).toUpperCase();
            FILENAME=filename.substring(0,filename.indexOf('.'));

            for (int y=0;y<picHeight;y++){

                for (int x=0;x<picWidth;x++){

                    Color c=new Color(img.getRGB(x,y));

                    //scaling factor to 8bit color space 255(maximum value of color)/ RRRGGGBB   8 possible values for red 8 possible values for green and 4 for blue
//                    int red = (c.getRed() * 8) / 255;
//                    int green = (c.getGreen()* 8) / 255;
//                    int blue = (c.getBlue() * 4) / 255;
//
                    //bit shifting so you can
                    //int eightBitColor=( (red & 224) <<  16 )|((green & 28)  <<11)|((blue & 3)<< 6 );
//                    int eightBitColor= ((c.getRed() >> 5) << 5 )+ ((c.getGreen() >> 5) << 2)+ ((c.getBlue() >>6) ) ;
//                    Color eightBitColorFrom24=new Color(eightBitColor);
//                    System.out.println(eightBitColorFrom24);


                    //System.out.println(c);
                    image[y][x]=c;
                    if(!freqMap.containsKey(c)){
                        freqMap.put(c,1);
                    }else{
                        freqMap.put(c,freqMap.get(c)+1);
                    }
                }
            }
            System.out.println(freqMap.size());


            ogImage = image;

        } catch (IOException e) {
            //error message
            e.printStackTrace();
            error="File could not be opened,open another one";

        }

    }

    //builds the tree and returns one node which consists of all the nodes
    public huff_Img_Node buildHFT(){

        //makes Priority queue
        Iterator<Map.Entry<Color,Integer>>mapIterator=freqMap.entrySet().iterator();
        PriorityQueue<huff_Img_Node> pQ=new PriorityQueue<huff_Img_Node>();
        while(mapIterator.hasNext()){
            Map.Entry<Color,Integer> n=mapIterator.next();
            huff_Img_Node node= new huff_Img_Node(n.getKey(),n.getValue(),null,null);
            pQ.add(node);
        }

        for (huff_Img_Node ob: pQ             ) {



        }

        if (pQ.size()==1){
            pQ.add(new huff_Img_Node(null,pQ.poll().freq,null,null));

        }



        //makes the smallest 2 nodes into a parent node and adding that to the Priorityqueue till there is only 2 left
        int i=0;
        while(pQ.size()>1){
            //return least valued nodes
            huff_Img_Node left=pQ.poll();
            huff_Img_Node right=pQ.poll();
            //System.out.println(left.freq+"          "+right.freq);

            //Color c = new Color(1);
            //makes parent node with the two lowest values in PQ and addest it to pQ which then putts it
            huff_Img_Node pNode=new huff_Img_Node(null,left.freq+right.freq,left,right);
            pQ.add(pNode);



            //System.out.println("size of "+pQ);

        }


        return pQ.poll();
    }

    //recursive method to assign bitcode to each color node
    public static Map<Color, String> assignEncoding(huff_Img_Node root){
        System.out.println("Assigning Codes");
        Map<Color,String>charBinaryTable=new HashMap<>();
        //populates map
        assignEncoding(root,"",charBinaryTable);

        return charBinaryTable;
    }



    public static void assignEncoding(huff_Img_Node root, String s, Map<Color, String> map){

        //if traverse to the left assign 0 if right assign 1 untill leafnode reached and code added to map to each color node
        if(!root.isLeaf()){
            //System.out.println("before cyclic : s");
            assignEncoding(root.leftChild,s+"0",map);
            //System.out.println("inbetween cyclic : s");
            assignEncoding(root.righgtChild,s+"1",map);
            //System.out.println("after cyclic : s");


        }else if(root.isLeaf()){

            map.put(root.colour,s);

            //System.out.println("leaf bitcode  "+root.freq+ "    "+map.get(root.colour)+"              "+root.leftChild+"         " +root.righgtChild);
        }
    }

    public String[][] encodeImage(Color[][] originalImage,Map<Color,String>encodedMap){
        String[][]compressedImage=new String[picHeight][picWidth];

        for (int y=0;y<picHeight;y++){

            for (int x=0;x<picWidth;x++){

                String code=encodedMap.get(originalImage[y][x]);
                compressedImage[y][x]=code;


            }
        }
        compImg=compressedImage;
        //System.out.println(Arrays.toString(compImg[0]));


        return compressedImage;

    }

    public  Color[][]  decompress(huff_Img_Node rootNode  ){
        Map<Color,String>decomMap=new HashMap<>();

        Color[][] decomImg=new Color[picHeight][picWidth];
//        for (Map.Entry<Color,Integer>el:freqMap.entrySet()){
//            if (el.getValue()2){
//                System.out.println(el.getKey());
//            }
//        }
        //System.out.println(freqMap.entrySet());
        for (int y=0; y<picHeight;y++){

            for (int x=0;x<picWidth;x++){
                int charAt=0;
                String codeTraversing="";
                decompress(compImg[y][x],rootNode,decomMap,charAt,codeTraversing);//System.out.println("here");
                decomImg[y][x]= (Color) getKeyFromValue(decomMap,  compImg[y][x]);
            }

        }
        //System.out.println(decomImg[0][0]);
        decompressionMap=decomMap;

        dImg=decomImg;
        //System.out.println(Arrays.deepToString(new Set[]{decomMap.entrySet()}).replaceAll(", ","\n"));
        return decomImg ;
    }
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }


    public void decompress(String code,huff_Img_Node rootNode,Map<Color,String>map,int i, String codeTraversing){
//        int i=0;

//        while(i<code.length()){
            //traverse through tree if left assign 0 to the codeTraversing and if right assign one to codeTraversing
            //this will
            int j=0;
            //traversing through the large tree to get to each leaf node represented by each pixel code
            if (!rootNode.isLeaf() ){

                if (code.charAt(i)=='0'){
                    //s+=0;
                    rootNode=rootNode.leftChild;
                    i++;
                    decompress(code,rootNode,map,i,codeTraversing+0);
                    //System.out.println("left "+rootNode);
                }else if(code.charAt(i)=='1'){
                    //s+=1;
                    rootNode=rootNode.righgtChild;
                    i++;
                    decompress(code,rootNode,map,i,codeTraversing+1);
                    //System.out.println("right:  "+rootNode);
                }

            }else if(rootNode.isLeaf()){
                //System.out.println("Eliandro");
                map.put(rootNode.getColour(),codeTraversing);
                //s="";
                //j++;
                //System.out.println(rootNode.getColour());
                System.out.println(map.size());

            }
            //System.out.println(s);
            //i++;
//        }


    }

    public static boolean areCodesPrefix(Map<Color,String>map){

        List<String>list=new ArrayList<>();
        for (Map.Entry<Color,String> element: map.entrySet() ) {
            list.add(element.getValue());
        }

        for (Map.Entry<Color,String> element: map.entrySet() ) {
            String el=element.getValue();

            for (int i=0;i<list.size();i++ ) {
                if (list.get(i).equals(el)){


                }else{
                    String sub=list.get(i);
                    if (el.length()>=sub.length()){
//                        System.out.println(el);
//                        System.out.println(sub);
                        if ( el.contains(sub) && el.substring(0,sub.length()).equals(sub)){
                            //System.out.println(el);
                            //System.out.println(sub);
                            return false;
                        }
                    }


                }
            }

        }

        return true;
    }

    public void rebuildImage(String[][] compressedImage) throws IOException {

        BufferedImage img = new BufferedImage(picWidth,picHeight,BufferedImage.TYPE_INT_RGB);

        //delete File if exists
        Path path = FileSystems.getDefault().getPath("C:\\Users\\elian\\Documents\\FinalYearProject", "HuffImageDecom."+format.toLowerCase());
        boolean success= Files.deleteIfExists(path);
        System.out.println("File deleted"+success);
        File fileName=new File(FILENAME+"HuffImageDecom."+format.toLowerCase());
        decomIMG=new Color[picHeight][picWidth];


        for (int y=0; y<picHeight;y++){

            for (int x=0;x<picWidth;x++){
                //Color c= (Color) getKeyFromValue(decompressionMap,compressedImage[y][x]);

                //assert c != null;
                //img.setRGB(x,y, c.getRGB());
                //decomIMG[y][x]=c;
                img.setRGB(x,y,dImg[y][x].getRGB());
            }

        }
        try {
            System.out.println(format);
            //ImageIO.write(img,format,fileName);
            //edit
            ImageIO.write(img,format,fileName);
            //finishe edit
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldnt rebuild the image");
        }


    }

    public double efficiency(String[][] encodedImg){

        int originalBitsUsed=0;
        totalBitsUsed=0;


        // iterates through each pixel retrieves code and adds no. of bits used to total
        for (int y=0;y<picHeight;y++   ) {
            for (int x=0; x<picWidth;x++){

                totalBitsUsed+=encodedImg[y][x].length();    //adds the coded bits length of encoded pixel to total amount of bits used
                originalBitsUsed+=24;

            }
        }

        System.out.println(totalBitsUsed);
        System.out.println(originalBitsUsed);
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        double saving= (1.00-((double)totalBitsUsed/(double)originalBitsUsed))*100.00;
        System.out.println();

        //compression ration
        DecimalFormat dfCR= new DecimalFormat("#.##");
        double ratio= (double)originalBitsUsed/(double)totalBitsUsed;
        compressionRatioHFIMG=dfCR.format(ratio)+" : 1";


        return Double.valueOf(df.format(saving));
    }










    public static void main(String[] args) throws IOException {
        huff_img huff=new huff_img("pngImg2.png");
        huff_Img_Node finalNode=huff.buildHFT();
        Map<Color,String>bitCodeMap=assignEncoding(finalNode);      //makes map og the final node tree and its given bitcode
        huff.encodeImage(huff.ogImage, bitCodeMap );
        huff.decompress(finalNode);
        //System.out.println(bitCodeMap.entrySet());
        //Testing to see if the tree made makes prefix codes
        //System.out.println(areCodesPrefix(bitCodeMap));


        //testing if compression map and decompression maps have the same elements so the rebuilding of the image will be the same and lossless compression occurs
//        System.out.println("\n\n\n\nComparing compression map to decompression map***************************");
//        for (Map.Entry<Color,String> elemnt: bitCodeMap.entrySet()             ) {
//
//            if (huff.decompressionMap.containsKey(elemnt.getKey())){
//                if (!elemnt.getValue().equals(huff.decompressionMap.get(elemnt.getKey()))){
//                    System.out.println("the maps arent equal");
//                };
//            }
//        }
        //Map<Color,String>ma=new HashMap<>();
//        ma.put(new Color(1),"1");
//        ma.put(new Color(2),"000000000000000000000");
//        ma.put(new Color(2),"01000000000000000000");
        //System.out.println(areCodesPrefix(ma));
        //System.out.println(huff.freqMap.keySet() );
        int i=0;
        System.out.println("decompressing image");
        huff.rebuildImage(huff.compImg);
        System.out.println("efficiency ");
        huff.efficiency(huff.compImg);

        String name="Finished";


        System.out.println(name);

        //test to see if the images are all the same

//        System.out.println("ARE the images the same");
//        int error=0;
//        for (int y=0;y<huff.picHeight;y++){
//
//            for (int x=0;x<huff.picWidth;x++){
//
//                if (huff.ogImage[y][x].equals(huff.decomIMG[y][x])){}else{
//                    error++;
//
//                }            }
//        }
//        if (error>0){
//            System.out.println("Not the same images");
//        }



    }


}
