package RLE_Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RLE_ImgFinal {
    public static int picHeight;
    public static int picWidth;
    public static boolean monochrome;
    public   int[][]picPxlArr;
    public Color[][] colorPxlArray;
    public Object[][]decompressedColorPxlArr;
    public  String filename;
    public static ArrayList[] carray;//=new ArrayList<Object>();
    public Integer[][]alphavalues;
    public int BitsUsed;
    public static String FName;
    public static String compressionRatioRLEIMG;




    public  RLE_ImgFinal(BufferedImage image) throws AWTException, IOException {
            //imageCode++;
        FName="";



            System.out.println("constructor");



            picHeight=image.getHeight();
            picWidth=image.getWidth();

            System.out.println(image.getHeight());
            System.out.println(image.getWidth());



            alphavalues=new Integer[picHeight][];


            int c0=0, counter=0;
            for(int y=0;y<picHeight;y++){
                List<Integer> aValues=new ArrayList<Integer>(picWidth);

                for (int x=0; x<picWidth;x++){
                    Color c=new Color(image.getRGB(x,y));
                    //Color c=new Robot().getPixelColor(x,y);        this library is rediculously slow
                    //System.out.println("hi "+c0++);
                    int red=c.getRed();
                    int green=c.getGreen();
                    int blue=c.getBlue();
                    int alpha=c.getAlpha();
                    aValues.add(alpha);
                    //System.out.println((byte)red);


                        if ( green!=red || red!=blue){
                            counter++;

                        }
                    }
                    alphavalues[y]=  aValues.toArray(new Integer[aValues.size()]);


                    //int r=color.getRed();
            }




            if (counter==0){
                System.out.println("Monochrome pic");
                monochrome=true;

            }else{ System.out.println("Not monochrome picture"); monochrome=false; }


    }


    public  int[][] buildPxlArr(BufferedImage image) throws IOException, AWTException {


        //BufferedImage image=ImageIO.read(new File(name));
//        int[][] picArr = new int[image.getHeight()][image.getWidth()];
//        for(int y=0;y<image.getHeight();y++){
//
//            for (int x=0; x<image.getWidth();x++){
//
//                //using luminosity scaling factor
//                Color c=new Color(image.getRGB(x,y));
//
//                double red=c.getRed()*0.2126;
//                double green=c.getGreen()*0.7152;
//                double blue=c.getBlue()*0.0722;
//                //int L=(int) Math.rint(red+green+blue);//closest int
//                int L=c.getRed();
//                //System.out.println(L);
//                picArr[y][x]=L;
//            }
//
//        }
        //BufferedImage imageCreation=ImageIO.;

//        picPxlArr=picArr;
        //System.out.println(Arrays.deepToString(picPxlArr));


        //if (monochrome){
            Color[][] picArrColor = new Color[image.getHeight()][image.getWidth()];
            for(int y=0;y<image.getHeight();y++){

                for (int x=0; x<image.getWidth();x++){
                    //if (x+1<image.getWidth()) {
                        Color c = new Color(image.getRGB(x, y));
                    picArrColor[y][x]=c;
                        //Color c1 = new Color(image.getRGB(x + 1, y));
                        //if (c.toString().equals(c1.toString())) {

                        //    System.out.println(c+"                  "+c1);
                        //}
                    //}
                }
            }
            colorPxlArray=picArrColor;
        //}
        return picPxlArr;
    }
    



//    public String[][] compressingcArr(int[][] picArr){
//        String [][] tempArr=new String[picArr.length][picArr[0].length];
//
//        int y=0;String del="*";
//
//        while(y<picHeight){
//            int j=0;
//            for(int i=0;i<picWidth;i++){
//                int run=1;
//                while(i+1<picWidth && (picArr[y][i])==Integer.parseInt(String.valueOf(picArr[y][i+1] ))){
//
//                    run++;
//                    i++;
//
//                }
//
//                //System.out.println(Arrays.toString(picArr[0]));
//                //System.out.println(j);
//
//
//                if (run>2){
//                    //System.out.println(run+"                               "+y);
//                    //compressedArr[y][j]=String.valueOf(run)+del+String.valueOf(picArr[y][x]);
//                    //for (int o=0;0<tempArr[y].length;o++) {
//                        tempArr[y][j] = String.valueOf(run) + del + String.valueOf(picArr[y][i]);
//                        j++;
//                    //}
//                    //System.out.println(Arrays.toString(tempArr[0]));
//                }else{
//                    //System.out.println("here");
//                    //System.out.println(Arrays.toString(new int[]{picArr[y][i]}));
//                    //compressedArr[y][j]+= String.valueOf(picArr[y][i]);
//                    int l=0;
//                    while(l<run){
//                        tempArr[y][j]=String.valueOf(picArr[y][i]);
//
//                        //System.out.println("");
//                        l++;
//                        j++;
//
//                    }
//
//                }
//                //compressedArr[y][x]=
//
//
//            }
//            y++;
//        }
//        System.out.println("\n\n\nCompressed row:");
//        System.out.println(Arrays.toString(tempArr[0]));//CHECKS EACH COMPRESSED ROW
//        System.out.println("temp arr "+tempArr[1].length);
//
//        int p=0;
//        while(tempArr[0][p]!=null){
//            p++;
//        }
//        System.out.println("Length of arrrrrrr "+p);
//        ArrayList<Object>arrlistRow=new ArrayList<>();
//        ArrayList<?>[] compressedArr= new ArrayList<?>[picHeight];;
//        Object [][]compressedFinal=new Object[picHeight][];
//        ArrayList[] ar= new ArrayList[picHeight];
//        int lengthArr=0;
//        for (String[] s:tempArr    ) {
//            //System.out.println(Arrays.toString(s));
//            arrlistRow.clear();
//            for (String element:s ) {
//                if(element!= null && element.length()>0){
//                        if (element.contains("*")) {
//                            int i=element.indexOf("*");
//
//                            //arrlistRow.add(element);
//                            byte color= (byte)Integer.parseInt(element.substring(i+1,element.length()));//8 bits to print the bytes 0-255 need to get its unsigned value by binary  anding it with 0xFF
//                            short runLength=Short.parseShort(element.substring(0,i));//16 bits
//                            int c=color & 0xFF;//for displaying the bytes
//
//                            int r = runLength & 0xFFFF;
//                            compressedObject compO=new compressedObject<>(runLength,color);
//                            //System.out.println(compO.toString());
//                            arrlistRow.add(compO);
//                            //System.out.println(compO.getColor());
//
//
//                            //System.out.println(c+"     "+r);
//
//                        }else{
//                            //System.out.println(element);
//                            int e = Short.parseShort(element)& 0xFFFF;
//                            arrlistRow.add(e);
//                            //System.out.println(e);
//                            //int el=Short.parseShort(element)& 0xFFFF;
//                            //System.out.println(el);
//                        }
//                        //);
//                }
//
//            }
//            //System.out.println(arrlistRow.size());
////            Cloner cloner=new Cloner();
////            XX clone = cloner.deepClone(someObjectOfTypeXX);
//            int f=0;
//
//            while(f<arrlistRow.size()){
//                //System.out.println(arrlistRow.size());
//
//                ArrayList l=new ArrayList();
//                List list=new ArrayList();
//                if (arrlistRow.get(f) instanceof compressedObject){
//                     l.add(new compressedObject(((compressedObject) arrlistRow.get(f)).getRunlength(),((compressedObject) arrlistRow.get(f)).getColor()));
//                    list.add(new compressedObject(((compressedObject) arrlistRow.get(f)).getRunlength(),((compressedObject) arrlistRow.get(f)).getColor()));
//                     //System.out.println(arrlistRow.get(f));
//
//                }else{
//                    int fa= Integer.parseInt(arrlistRow.get(f).toString());
//                    l.add(arrlistRow.get(f));
//                    list.add(fa);
//                   // System.out.println(arrlistRow.get(f));
//                }
//                //System.out.println(Arrays.toString(l.toArray()));
//
//
//
//                f++;
//                if (f==arrlistRow.size()){
//                   //System.out.println("LLLLLLL "+list.toString()+ l.get(0).toString());
//                    //carray[lengthArr]= new ArrayList(l.size());
//                    //carray[lengthArr]=l;
//                }
//            }
//            //carray.add(new ArrayList<>(arrlistRow));
//            //System.out.println(Arrays.toString(carray));
//
//            compressedFinal[lengthArr]=new Object[arrlistRow.size()];
//
//            compressedFinal[lengthArr]=carray;
//            //System.out.println(compressedArr[lengthArr].size());
//            //System.out.println(Arrays.toString(new ArrayList[]{compressedArr[lengthArr]}));
//
//
//            //System.out.println();
//            //System.out.println(lengthArr); //does add rows
//            compressedArr[lengthArr]=arrlistRow;
//            //System.out.println(compressedArr.length);
//            //System.out.println("Arr  "+Arrays.toString(arrlistRow.toArray()));//prints compressed array row by row
//            //System.out.println(Arrays.deepToString(new ArrayList[]{compressedArr[lengthArr]}));//prints array compressed rows as an array of arraylists.
//            lengthArr++;
//
//
//            //System.out.println(arrlistRow.get(0));
//
////            if(compressedArr.length<1) {
////
////                compressedArr[0] = arrlistRow;
////            }else{
////                compressedArr[compressedArr.length]=arrlistRow;
////            }
//
//
//        }
//
//        int h=1;
//        while(h<5){
//            //System.out.println(compressedArr[0].get(0));
//            h++;
//        }
//        //System.out.println();
//        //carray=compressedArr;
//
//
//        String[][] stringArr=new String[picHeight][];
//
//
//
//        for(int s=0;s<tempArr.length;s++){
//
//            //int i=0;
//            String[] rows=tempArr[s];
//            List<String> list=new ArrayList<String>(rows.length);
//            for(int b=0;b<rows.length;b++){
//                if(rows[b]!=null){
//                    list.add(rows[b]);
//                }
//
//            }
//            stringArr[s]=  list.toArray(new String[list.size()]);
//            //System.out.println(Arrays.toString(list.toArray()));
//
//        }//System.out.println(Arrays.deepToString(stringArr));
//        //System.out.println(Arrays.deepToString(stringArr).replaceAll("],","\n"));
//        //System.out.println(Arrays.toString(stringArr[0]));
//
//        return stringArr;
//    }

    public ArrayList[]compressingColorArr(Color [][] arr){
        ArrayList[] compressedArr=new ArrayList[arr.length];
        //ArrayList[] rows=new ArrayList[arr.length];

        for (int y=0;y<arr.length;y++){
            ArrayList<Object> row=new ArrayList<>();
            for (int x=0;x<arr[y].length;x++){

                int run=1;
                Color c;
                while(x+1<picWidth && arr[y][x].toString().equals(arr[y][x+1].toString())){

                    run++;
                    x++;

                }
                //add each pixel components to row
                if (run>1){

                    row.add(new Cobject((short)run,arr[y][x]));
                }else{
                    row.add(arr[y][x]);
                }

                //add row to the compressed array





            }
            compressedArr[y]=row;
            //System.out.println(compressedArr[y].size());
        }
        //System.out.println(compressedArr.length); prints the right amount of rows
        //compares compressed array and original array for equality
        //System.out.println(compressedArr[0]);
        //System.out.println(Arrays.toString(arr[0]));
        return compressedArr;

    }


    public String[][] decompresser(String[][] compArray){
        String[][] decomArr=new String[picHeight][];
        int total=0;



        for (int y=0;y<compArray.length;y++      ) {
            String[] rows=new String [picWidth];
            List<String> list=new ArrayList<String>(rows.length);
            for (int x=0;x<compArray[y].length;x++){

                if(!compArray[y][x].contains("*")){

                    //list.addAll(Arrays.asList(rows));
                    //decomArr[y]=  list.toArray(new String[list.size()]);
                    //img.setRGB(x,y,Integer.parseInt(compArray[y][x]));
                    list.add(compArray[y][x]);
                    total+=8;

                }else{
                     int p=compArray[y][x].indexOf("*");
                     int run= Integer.valueOf(compArray[y][x].substring(0,p));
                        //System.out.println(run);
                     int rgb=Integer.parseInt(compArray[y][x].substring(p+1,compArray[y][x].length()));
                        //System.out.println(rgb);
                     int counter=0;
                     while(counter<run){
                         //System.out.println(counter);
                         //System.out.println(String.valueOf(rgb));
                         list.add(String.valueOf(rgb));
                         counter++;
                     }
                     total+=24;

                }


            }
            //System.out.println(list);
            decomArr[y]= list.toArray(new String[list.size()]);
        }


        System.out.println(Arrays.toString(decomArr[0]));



        BitsUsed=total;




        return decomArr;



    }

    public Color[][] decompressColor(ArrayList[] compressedArray){

        Color [][] decompressedColorArr=new Color[compressedArray.length][picWidth];
        int y=0;
        for (ArrayList row:compressedArray ) {
            int x=0;
            for ( Object e: row ) {

                if(e.getClass()==Cobject.class){
                    Cobject element=(Cobject)e;

//                    String []arr=element.toString().split("/w");
//                    int run=Short.parseShort(arr[0]);
//                    Color col= Color.decode(arr[1]);
                    int i=0;
                    while(i <element.getRun() ){

                        decompressedColorArr[y][x++]=element.getC();
                        i++;

                    }
                    //(element) -> Cobject

                    //System.out.println("found object:    "+element);
                   // System.out.println();
                }else{
                    decompressedColorArr[y][x++]= (Color) e;
                }

            }
            y++;
        }
        //System.out.println(Arrays.deepToString(decompressedColorArr));
        //System.out.println(decompressedColorArr[200].length);
        return decompressedColorArr;
    }


    public int efficiency(){


        System.out.println(BitsUsed);
        return BitsUsed;

    }

    public double efficiencyColor(ArrayList[] compressedArr){
        int usedBits=0;
        //iterates through each row represented by an array list which consists of either compressed ofbject(short, colour(object)) or colour object of the pixel (this is when there is no repetition of pixel)
        for (  ArrayList row : compressedArr  ) {

            for (   Object element  :  row   ) {

                if (element.getClass().equals(Cobject.class)){
                    usedBits+=16;  //bits for run type short =16bits 2 bytes
                    usedBits+=24;  //bits for 3 componets of rgb


                }else{
                    usedBits+=24;//bits for 3 components of rgb
                }

            }


        }

        System.out.println(usedBits);             // amount bits used in compressed array
        int bitsImg=picHeight*picWidth*24;        //bits per pixel * amount of pixels in pictures
        System.out.println(bitsImg);

        //space Saving
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        double saving= (1.00-((double)usedBits/(double)bitsImg))*100.00;

        //compression ratio
        DecimalFormat dfCR= new DecimalFormat("#.##");
        double ratio= (double)bitsImg/(double)usedBits;
        compressionRatioRLEIMG=dfCR.format(ratio)+" : 1";

        System.out.println( Double.valueOf(df.format(saving)));

        return Double.valueOf(df.format(saving));

    }

    public void rebuilding(String[][] decompressedArr) throws IOException {


        BufferedImage img=new BufferedImage(picWidth,picHeight,BufferedImage.TYPE_INT_RGB);

        File decompressedFile=new File("rleDecompressedImgFile");

        for(int y=0;y<decompressedArr.length;y++){

            for (int x=0;x<decompressedArr[y].length;x++){

                int l=Integer.parseInt(decompressedArr[y][x]);
                int red=  (int)Math.rint((double) l *0.2126);
                int green= (int) Math.rint((double) l*0.7152);
                int blue=  (int)Math.rint((double) l*0.0722);
                int alpha=alphavalues[y][x];


                Color c=new Color(l,l,l,alpha);
                int rgb=c.getRGB();

                img.setRGB(x,y,rgb);
            }
        }


        // retrieve image

        ImageIO.write(img, "png", decompressedFile);





    }

    public void rebuildColor(Color[][] decompressedArr) throws IOException {

        BufferedImage img=new BufferedImage(picWidth,picHeight,BufferedImage.TYPE_INT_RGB);


        File decompressedFile=new File("rleDeco"+FName+".png");
        //System.out.println("here");

        for (int y=0;y<decompressedArr.length;y++){

            for (int x=0; x<decompressedArr[y].length;x++){
                //Color c=decompressedArr[y][x];

                img.setRGB(x,y,decompressedArr[y][x].getRGB());
                //img.setRGB(x,y,c.getRGB());
            }
        }

        ImageIO.write(img,"PNG",decompressedFile);
    }


    public static void main(String[] args) throws IOException, AWTException {

        String filename="tiffImg.tiff";
        BufferedImage image=ImageIO.read(new File(filename));
        RLE_ImgFinal rle=new RLE_ImgFinal(image);
        rle.buildPxlArr(image);
        //System.out.println(imageCode);
        if (!monochrome){

            System.out.println("here");
            rle.rebuildColor(rle.decompressColor(rle.compressingColorArr(rle.colorPxlArray)));
            rle.efficiencyColor(rle.compressingColorArr(rle.colorPxlArray));
        }

        //System.out.println("compressed \n"+Arrays.deepToString(rle.compressingcArr(rle.picPxlArr)[0].toArray()).replaceAll("],","\n"));

        //System.out.println(Arrays.deepToString(rle.decompresser(rle.compressingcArr(rle.picPxlArr))));

       // rle.rebuilding(rle.decompresser(rle.compressingcArr(rle.picPxlArr)));
        //System.out.println(carray[0].size());
//        int j=0;
//        while (j<5){
//
//            System.out.println(Arrays.deepToString());
//            j++;
//
//        }
        //System.out.println("\n\n\nOriginal row:\n"+Arrays.toString(rle.picPxlArr[0]));
        //System.out.println(rle.picPxlArr[0].length);

//        int[][] arr=new int[][]{
//                {100,222,3},
//                {2,33,444}
//        };
//
//        System.out.println("\n\n\n"+(arr[0][2]));
//        System.out.println(Arrays.toString(arr[0]));
//        int p=3;
//        double k=9.8323;
//        System.out.println((int)(p*k));
        //System.out.println(rle.efficiency());


        int r=5;


        System.out.println((short)r);





    }




}
