package RLE_text;


import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dave on 11/11/17.
 */
public class finalRLEtxt  {
    public static String compSqnc="";
    public  StringBuilder string;
    public static ArrayList<Integer>runLst=new ArrayList<Integer>();
    public static String decompSqnc="",fileN="";
    public static String compressionRatioRLEText;
    public String errormessage;

    public int noOfLines;
//public String compS="",compStringToFile;
    ////    public static int emptyLC=0;
    ////    public static boolean isLEmpty;
    ////    static char[] compressedStringCharArr;//private ArrayList<Object> ComObjects;

    public finalRLEtxt(){}

    public  String compress(String file)  {
        try {
            fileN=file.substring(0,file.indexOf('.'));   //gets the name part of file
            Scanner reader = new Scanner(new File(file));
            compSqnc="";
            //ComObjects=new ArrayList<Object>();
            //ComObjects.clear();
            noOfLines=0;
            //goes through every line of the file
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                boolean addedL=false;
               //gets repeated runs from each line as it goes through the characters in each line
                for (int i = 0; i < line.length(); i++) {
                    int run = 1;
                    while (i + 1 < line.length() && line.charAt(i) == line.charAt(i + 1)) {

                        run++;
                        i++;
                    }
                    //add char to new compressed string
                    int nos=run/9;
                    int rem=run%9;

                   //finding how many full 9*(char) there are
                    if(run>9){
                        String s3="";
                        String s2="9*";
                        for(int k=1;k<=nos;k++){
                            s3=s3+s2+line.charAt(i);
                        }
                        //adding compressed object when ful eg 9*g9*e
                        compSqnc+=s3;

                        //adding the remainder of a full compressed that is less than 3 as its not efficient to compress it
                        if(rem<3){
                            String charstoStr="";
                            for (int t=0;t<rem;t++){
                                charstoStr+=line.charAt(i);
                            }
                          compSqnc+= charstoStr;
                        }else {
                            //if remainder is more than 3 its effective to compresss it
                            compSqnc+=rem+"*"+line.charAt(i);
                        }
                        runLst.add(run);
                    }else if(run<10&& run>2){
                        compSqnc=compSqnc+run+"*"+line.charAt(i);
                    }else{
                        int p=0;
                        while (p<run){
                        compSqnc=compSqnc+line.charAt(i);
                        p++;
                        }
                    }

                }
                //adds a new line after reading a new line if its not longer than 2 lines
                //
//                if(!(emptyLC>0)){
//                compSqnc+="\n";
//                }
//                if(!addedL ){
//                    compSqnc+="\n";
//                }
                compSqnc+="\n";
                noOfLines++;
            }
            //System.out.println(Arrays.toString(ComObjects.toArray()));

            ///edit
            //remove last new line
//            ComObjects.remove(ComObjects.size()-1);
            //compress line repetition that isnt compressed

//            for (int p=0;p<ComObjects.size();p++){
//                int run=1;
//                while (p + 1 < ComObjects.size() &&  ComObjects.get(p) == ComObjects.get(p + 1)) {
//
//
//
//
//                    run++;
//
//                    p++;
//                }
//
//                if (run>1){
//                    System.out.println(run);
//                    //ComObjects.add(i-run+1,new RLECObject((byte)run,(byte)ComObjects.get(i).toString().charAt(0)));
//                    ComObjects.set(p-run+1,new RLECObject((byte)run,(byte)ComObjects.get(p).toString().charAt(0)));
//                    //System.out.print(Arrays.toString(ComObjects.toArray()));System.out.println("           Size ="+ComObjects.size()+"      i="+i);
//                    //System.out.println(i);
//                    int g=run,h= p;
//                    int removed=0;
//
//                    while(removed<run-1) {
//                        //removing the last element that was repeated
//                        //System.out.println(StringEscapeUtils.escapeJava(Arrays.toString(ComObjects.toArray())));
//                        //System.out.println(i);
//                        ComObjects.remove(p-run+2);
//
//                        removed++;
//
//
//                    }
//                }
//
//            }
//            System.out.println("Final:  "+Arrays.toString(ComObjects.toArray())+"    Size="+ComObjects.size());
//            ///finish edit






            //this compresses the empty lines if any
            try {

                //PrintWriter printWritter= new PrintWriter(stringWriter);

                //System.out.println(StringEscapeUtils.escapeJava(compSqnc));
                String copyCompSqnc= compSqnc;
                string=new StringBuilder();
                //System.out.println("length of string to append   "+string.length());
                if(string.length()>0){
                    while(string.length()>0){
                        string.delete(0,1);

                    }
                }


                //compressing empty lines or new lines chars
                for (int i=0;i<copyCompSqnc.length();i++){
                    int run=1;
                    while (i + 1 < copyCompSqnc.length() && copyCompSqnc.charAt(i) == copyCompSqnc.charAt(i + 1) && (int)copyCompSqnc.charAt(i)==10) { //ascii value for new line
//
                        run++;

                        i++;
                    }
                    //when run is between 4-9 as this can be compressed into more effective compression
                    if(run>3 && run<10) {
                        //char c = (int) 10;
                        String s = run + "*\n";
                        String substring = copyCompSqnc.substring((i + 1) - run, i+1);

                        string.append(s);
                        //string.append(StringEscapeUtils.escapeJava(copyCompSqnc.replaceFirst(substring,s)));
                        //System.out.println(StringEscapeUtils.escapeJava(string.append(substring).toString()));
                        //System.out.println(StringEscapeUtils.escapeJava(copyCompSqnc));
                        //System.out.println("the substring is "+StringEscapeUtils.escapeJava(substring));
                        //System.out.println(StringEscapeUtils.escapeJava(compSqnc.replaceFirst(substring, s)));
                        //System.out.println(run+"    "+StringEscapeUtils.escapeJava(substring));

                    }else if(run>9){
                        //breaking the eg 48*g into multiples of 9*g with the remainder ggg()
                        String s="";
                        String repStr="9*\n";
                        int rep=run/9;
                        int rem=run%9;
                        int j=0;
                        while(j<rep){
                            s=s+repStr;
                            j++;

                        }
                        //it only compresses remaining chars if there are more than 2
                        if(rem<3){
                            int l=0;
                            while(l<rem){
                                s=s+"\n";
                                l++;
                            }

                        }else {
                            s =s+rem + "*\n";
                        }
                        string.append(s);

                    }else if(run<4){
                        int j=0;
                        while(j<run){
                            System.out.println(copyCompSqnc.charAt(i));
                            string.append(copyCompSqnc.charAt(i));
                            j++;}
                    }
                }
                //System.out.println("text: "+StringEscapeUtils.escapeJava(string.toString()));
                //System.out.println((int)string.charAt(3));

                //System.out.println("Text in output text "+ co +StringEscapeUtils.escapeJava(text.toString()));


//            for (int i=0; i<compSqnc.length();i++){
//                int run=1;
//                String str="";
//                while (i + 1 < compSqnc.length() && compSqnc.charAt(i) == compSqnc.charAt(i + 1) && (int)compSqnc.charAt(i)==10) { //ascii value for new line
//
//                    run++;
//
//                    i++;
//                }
//
//                //to be more efficient more than 3 repeating characters have to be occuring
//                if(run>3){
//                    char c=(int)10;
//                    String s=run+"*"+c;
//                    String substring=compSqnc.substring((i+1)-run,i);
//                    //compSqnc.replaceFirst(compSqnc.substring(i-run,i),s);
//                    //System.out.println("the substring is "+StringEscapeUtils.escapeJava(substring));
//                    System.out.println(StringEscapeUtils.escapeJava(compSqnc.replaceFirst(substring,s)));
//
//                }

                //System.out.println(run+"  "+i);


                //System.out.println(compSqnc.substring(i-run,i));

                //}
                //System.out.println(compSqnc);
                String filename="rleCom.txt";
//            Scanner readNoLines=new Scanner(new FileReader(filename));
//            while(readNoLines.hasNextLine()){
//                String line=readNoLines.nextLine();
//
////                c1++;
//                //System.out.println(line);
//                //System.out.println(StringEscapeUtils.escapeJava(line));
//
//                if(!line.isEmpty()) {
//                    c++;
//
//                }
//
//
//
//            }

                //System.out.println("number of lines "+c1);

                //get path of project and make the file there                                                    m
                File currentDirFile = new File(".");  //find the path of the project then make the directory
                String helper = currentDirFile.getAbsolutePath();
                String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());

                //deleting file if exists
                System.out.println(fileN+filename);
                Path path = FileSystems.getDefault().getPath(currentDir, fileN+filename);
                boolean success= Files.deleteIfExists(path);

                //adding compressed string to a file and writing in it
                PrintWriter writer=new PrintWriter(fileN+filename,"UTF-8");
//                System.out.println(StringEscapeUtils.escapeJava("ddddddd"+string.toString()));
//                System.out.println("number of lines "+noOfLines);
                //if file has one character in it
//                if (noOfLines==1 && string.length()==1 && (char)10!=string.charAt(0)){
//                    //string.deleteCharAt(string.length()-1);
//                    System.out.println("maybe her");
//                }else if(noOfLines==1 && string.length()>0){
//                    string.deleteCharAt(string.length()-1);
//                    System.out.println("This one");
//                }else{
//                    System.out.println("how");
//                }
                writer.println(string.toString());
                writer.close();
                // remove the last appending new line char
                compSqnc=StringUtils.chomp(compSqnc);

                //if it gets to the end of it means no errors
                return string.toString();
            }catch(Exception E){
                E.printStackTrace();
                errormessage=("Error in output to file");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            errormessage="File does not exist or open as its not text only";
        }
        return null;
    }

    public  String decompress(String compressedString)  {
        int i=0;
        decompSqnc="";
        //iterating through the compressed string
        while(i<compressedString.length()){

            String subStr="";
            //checks if the pattern is digit*
            if( Character.isDigit(compressedString.charAt(i)) && compressedString.charAt(i+1)=='*' ){
                //decompresses the run into long text
                int charC=0;
                while( charC < Integer.parseInt(String.valueOf(compressedString.charAt(i)))){

                    subStr+=compressedString.charAt(i+2);
                    charC++;

                }
                //if its here it has checked for the 3 chars num/*/char so add 3 to i to et a new char not previously checked
                i=i+3;
            }else{
                subStr=String.valueOf(compressedString.charAt(i));
                i++;
            }
                decompSqnc+=subStr;
            }
        try {
            System.out.println("decompressseddd " + decompSqnc);
            //get path of project and make the file there
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();
            String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
            System.out.println(fileN);

            Path path = FileSystems.getDefault().getPath(currentDir, fileN + "rleDecom.txt");
            boolean success = Files.deleteIfExists(path);
            PrintWriter output = new PrintWriter(new File(currentDir + fileN+"rleDecom.txt"),"UTF-8");
            output.println(decompSqnc);
            System.out.println(StringEscapeUtils.escapeJava(decompSqnc));
            output.close();
        }catch (Exception e){
            e.printStackTrace();
            errormessage="Could not create file and decompress data to it";
        }
        //decompSqnc=StringUtils.chomp(decompSqnc);


        return decompSqnc;

    }

    public  String efficiency(String originalAlg, String compressedAlg){


        int cO=0,cC=0;
        //counts original characters
        for(char c:originalAlg.toCharArray()){
            cO++;
        }
        //counts characters in compression
        for (char ch:compressedAlg.toCharArray()){
            cC++;
        }
//        System.out.println(cO);
//        System.out.println(cC);
//        System.out.println(compressedAlg);


        //savings
        //space saving percentage
        double savings=(1-((double)cC/(double)cO))*100;

        //compression ratio
        DecimalFormat df= new DecimalFormat("#.##");
        double ratio= (double)cO/(double)cC;
        compressionRatioRLEText=df.format(ratio)+" : 1";
        //rounds to 3 decimal places
        String s=String.format("%,.3f",savings)+"%";

        return s;
    }

    public  String originalText(String filename) throws IOException {
        String text= "";
        char c;
        FileInputStream fis = new FileInputStream(filename);
        //reads next byte not empty
        while(fis.available() > 0){
            //goes through each byte and casts it to char which is added to text
            c = (char) fis.read();
            text=text+c;

        }
        //replaces the way new character is represented for ease of coding and matching
        text=text.replaceAll("(\r\n)","\n");
        //returm original text
        return text;
    }

    public static void main(String[] args) throws IOException {
        String filename="file.txt";
        File f=new File(filename);
        if (new Scanner(f).hasNextLine()){
            System.out.println("has a line");
        }else{
            System.out.println("it does not");
        };

        finalRLEtxt finalrle=new finalRLEtxt();
        finalrle.compress(filename);
        finalrle.decompress(String.valueOf(finalrle.string));

        finalrle.efficiency(finalrle.originalText(filename),String.valueOf(finalrle.string));
        System.out.println(StringEscapeUtils.escapeJava(finalrle.string.toString()));
        String d="ds\n";
        for (int i=0;i<d.length();i++){
            System.out.println(StringEscapeUtils.escapeJava(String.valueOf(d.charAt(i))));
        }
        //System.out.println(StringEscapeUtils.escapeJava(finalrle.originalText(filename)));



        ;
//        System.out.println(finalRLEtxt.decompress(finalRLEtxt.compress(filename)));
//        String ogT=originalText(filename);
//        String cTxt=compress(filename);
//        System.out.println(finalRLEtxt.efficiency(ogT,cTxt));
    }
//    public static void filePrinter(String compressedString){
//
//
////        try {
////            PrintWriter printer = new PrintWriter(new File("myTextFile.txt"));
////            printer.println("Your text is:" + text);
////            printer.close();
////        } catch (FileNotFoundException fnfe) {
////            fnfe.printStackTrace();
////        }
//
//    }

}
