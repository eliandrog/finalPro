package huffman;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by egonca on 07/12/2017.
 */
public class HuffmansTree  {

    public static HashMap<Character,Integer> mapFreq=new HashMap<>();
    public PriorityQueue<Node>PQueue=new PriorityQueue<>();
    public ArrayList<Node> arrNodes=new ArrayList<Node>();
    public static StringBuilder compressionBits;
    public static double uncompressedBits;
    public static String compressionRatio;
    public static String decompressedHT;
    public static String fileName;
    public static Map<Character,String>decompressionMAp;



    public HuffmansTree(){}


    public static HashMap<Character, Integer> makeFreq(String lines){
        //HashMap<Character,Integer> mapFreq=new HashMap<Character,Integer>();
        //makes frequency of every character
        if (lines.length()==0){
            if(mapFreq.containsKey((char)12)){
                int oldv=mapFreq.get((char)12);
                mapFreq.put((char)12,oldv+1);
            }
        }else{
           char[] charArr= lines.toCharArray();
            for(char c:charArr){
                if(!mapFreq.containsKey(c)){
                    mapFreq.put(c,1);
                }else{
                    int oldv=mapFreq.get(c);
                    mapFreq.put(c,oldv+1);
                }
            }
        }

        //sorting map out
        List<Map.Entry<Character,Integer>> list= new LinkedList<>(mapFreq.entrySet());
        list.sort(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)));

        //makes linkedHash map which retain order in which you add it in
        HashMap<Character,Integer>sortedmap= new LinkedHashMap<>();
        //addes sorted list elements to the linked hash map making it sorted by def.
        for (Map.Entry<Character,Integer> entry:list){
            sortedmap.put(entry.getKey(),entry.getValue());
        }
        mapFreq=sortedmap;


        return mapFreq;


    }

//    public Map<Character, Integer> sortMap(HashMap<Character, Integer> map){
//        List<Map.Entry<Character,Integer>> list= new LinkedList<>(map.entrySet());
//        Collections.sort(list,Collections.reverseOrder((o1, o2) -> o1.getValue().compareTo(o2.getValue())));
//
//        Map<Character,Integer>sortedmap= new LinkedHashMap<>();
//        for (Map.Entry<Character,Integer> entry:list){
//            sortedmap.put(entry.getKey(),entry.getValue());
//        }
//
//        return sortedmap;
//    }

    public ArrayList<Node> makeMinHeap(HashMap<Character,Integer> map){
        Iterator<Map.Entry<Character, Integer>> it=map.entrySet().iterator();
        PriorityQueue<Node> pQueue = new PriorityQueue<>();
         //arrNodes = new Node[map.size()];
        int i=0;
        while(it.hasNext()){
            Map.Entry<Character,Integer> pair= it.next();
            pQueue.add(new Node(pair.getKey(),pair.getValue(),null,null));
            arrNodes.add((Node) pair);
            //System.out.println(pair.getKey());
            //Node n;
            //Node node=new Node(pair.getKey(), pair.getValue());
            //System.out.println(node.toString());
            //arrNodes.add(new Node(pair.getKey(),pair.getValue(),null,null));

            //PQueue.add(new Node(pair.getKey(), pair.getValue());
           //pQueue.add(node);
            //System.out.println(node.toString());
            //i++;
        }
        //System.out.println((arrNodes).get(0).c);

        PQueue=(pQueue);


        return arrNodes;
    }

    public static Node buildHuffT(HashMap<Character, Integer> freqMap){

        Iterator<Map.Entry<Character, Integer>> it=freqMap.entrySet().iterator();
        PriorityQueue<Node> pQueue = new PriorityQueue<>();
        //iterate thorugh map and make Node out of the elements in the map
        for (Map.Entry<Character,Integer> e:freqMap.entrySet()
             ) {

            Node n=new Node(e.getKey(),e.getValue(),null,null);
            //add nodes to priority queue
            pQueue.add(n);
        }

        //size of priority queue is one
        if (pQueue.size()==1){

            pQueue.add(new Node('\0',1,null,null));
        }
        //else iterate through the pqueue and get the smallest 2 values and make a a node out of it wh=ith the left and right children
        //adding the frequencies together to make the parent node frequency
        while(pQueue.size()>1){
            //because my list increases from right to left so bigger numbers are always after
//            Node left=listOfNodes.get(1);
//            listOfNodes.remove(listOfNodes.size()-1);
//            Node right=listOfNodes.get(listOfNodes.size()-1);
//            listOfNodes.remove(listOfNodes.size()-1);
//            Node parent=new Node('\0',left.freq+right.freq,left,right);
//            listOfNodes.add(0,parent);
            //System.out.println("P:"+parent.freq);
            //System.out.println(listOfNodes.size());
//            if (listOfNodes.size()==1){
//                freqOfFirstRChild=parent.freq;
//                System.out.println(freqOfFirstRChild);
//            }


            //second method
            Node leftPQ=pQueue.poll();//gets the least valued node
            Node rightPQ=pQueue.poll();

            Node parentPQ=new Node('\0',leftPQ.freq+rightPQ.freq,leftPQ,rightPQ); //special char '\0' means its not a leaf node
            //add parent node to priority queue
            pQueue.add(parentPQ);


        }

        //return last value which is the final node
        return pQueue.poll() ;
    }

    public ArrayList<Node> getArrNodes( ArrayList<Node> currentListOfNodes) {



        return arrNodes;
    }

    public static Map<Character, String> assignEncoding(Node root){

        Map<Character,String>charBinaryTable=new HashMap<>();
        //populates map
        assignEncoding(root,"",charBinaryTable);

        return charBinaryTable;
    }

    private static void assignEncoding(Node root, String s, Map<Character, String> map) {

        if (!root.isNodeLeaf()){
            assignEncoding(root.leftChild,s+"0",map);
            assignEncoding(root.rightChild,s+"1",map);

        }else{
            map.put(root.c,s);

        }
    }

    public static Map<Character,String> Decompress(Node FinalNode){
        HashMap<Character,String>decompressedAssignCodes=new HashMap<>();


        Decompress(FinalNode, "",decompressedAssignCodes);
        decompressionMAp=decompressedAssignCodes;
        return decompressedAssignCodes;
    }

    private static void Decompress(Node finalNode, String s, HashMap<Character, String> decompressedAssignCodes) {

        if (!finalNode.isNodeLeaf()){
            //move to left child and add 0 or 1 to the right child make up the code until leaf is reached
            Decompress(finalNode.leftChild,s+"0",decompressedAssignCodes);
            Decompress(finalNode.rightChild, s+"1", decompressedAssignCodes);
        }else{
            //adds leaf node of the string to the map
            System.out.println((int)finalNode.c+"     "+finalNode.c);
            decompressedAssignCodes.put(finalNode.c,s);
        }
    }



//    public static HuffmanCompressionResult compression(String lines){
//       //Node root= buildHuffT(makeMinHeap((makeFreq(lines))));
//      Node root=HuffmansTree.buildHuffT((HuffmansTree.makeFreq(lines)));
//      Map<Character,String>charEnconding=assignEncoding(root);
//
//
//        return new HuffmanCompressionResult(charCode(lines, charEnconding), root);
//
//    }

    public static String charCode(String lines, Map<Character, String> characterEncoder){
        StringBuilder compressedCode= new StringBuilder();
        for(int i=0;i<lines.length();i++){
            compressedCode.append(characterEncoder.get(lines.charAt(i)));
        }
        return String.valueOf(compressedCode);
    }


    public static String originalTextHT(String filename) throws IOException {
        fileName=filename;
        String text= "";
        char c;
        int i=0;
        FileInputStream fis = new FileInputStream(filename);
        while(fis.available() > 0){
            c = (char) fis.read();
            text=text+c;


        }
        text=text.replaceAll("(\r\n)","\n");
        return text;
    }

    public static String efficiencyHT(String originalAlg,Map<Character,String> mymap){
        int cO=originalAlg.length();
        String compressedBits="";

        compressionBits= new StringBuilder();
        for(int i=0;i<originalAlg.length();i++){

            compressionBits.append(mymap.get(originalAlg.charAt(i)));
        }
        //System.out.println(cO);
        //System.out.println(cC);

        //number of bits in uncompressed file
        uncompressedBits=(double)cO*(double)8;  //x8 because these are original number of character times amount of bits per character

        //space savings
        double savings=(1-((double)compressionBits.length()/((double)cO*8)))*100;

        //compression ratio
        DecimalFormat df= new DecimalFormat("#.##");
        double ratio= uncompressedBits/(double)compressionBits.length();
        compressionRatio=df.format(ratio)+" : 1";

        System.out.println(compressionBits.length() +" Bitssss     "+ uncompressedBits);

        return String.format("%,.3f",savings)+"%";
    }



    public static String exportBinaryString(){
        //exporting encoded data to file
        String filename="huffmansencondedData";
        try {
            //get path of project and make the file there
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();
            String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());

            Path path=  FileSystems.getDefault().getPath(currentDir, filename+fileName);
            boolean success= Files.deleteIfExists(path);
            PrintWriter file=new PrintWriter(new File(currentDir+filename+fileName,"UTF-8"));
            file.println(String.valueOf(compressionBits));
            //System.out.println(compressionBits);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(compressionBits);
    }

    public static String decode(String encodedBinaryString,Map<Character,String> map){

        int i=0;
        String binaryChar = "",compressBits="";
        //System.out.println(map.entrySet().toString());
        //System.out.println(map.get(String.valueOf("1")).charAt(0));
        while(i<encodedBinaryString.length()){
            binaryChar+=String.valueOf(encodedBinaryString.charAt(i));


            if(map.containsValue(binaryChar)){
                String character=String.valueOf(getKeyFromValue(map,binaryChar));
                //System.out.println(getKeyFromValue(map,binaryChar));
                //System.out.println(character);
                if(character.isEmpty()){}else{decompressedHT+=character;}
                decompressedHT=decompressedHT.replaceFirst("null","");

                //compressBits+=character;
                //decompressedHT.append(String.valueOf(map.get(binaryChar)));
//                //System.out.println(String.valueOf(map.get(binaryChar).charAt(0)));
                binaryChar="";
                i++;
            }
            else{i++;}




        }
        //System.out.println(decompressedHT);

        try {
            //get path of project and make the file there
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();
            String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());

            //Decoded data printing it to a file
            //change the path of the given file sytem if compiled in different environment
            Path path=  FileSystems.getDefault().getPath(currentDir, "HuffmansDecodedData"+fileName);
            boolean success= Files.deleteIfExists(path);
            PrintWriter fileo=new PrintWriter(new File(currentDir+"HuffmansDecodedData"+fileName));
            fileo.println(decompressedHT);
            fileo.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Error on printing to file");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return String.valueOf(decompressedHT);
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }



//    static class HuffmanCompressionResult {
//        static String compressedData;
//        static Node root;
//
//        HuffmanCompressionResult(String s, Node root) {
//            compressedData=s;
//            HuffmanCompressionResult.root =root;
//        }
//    }



    public static void main(String[] args) throws IOException {
        String text = HuffmansTree.originalTextHT("file.txt");
        Node fRoot = HuffmansTree.buildHuffT(HuffmansTree.makeFreq(text));
        Map<Character, String> map = HuffmansTree.assignEncoding(fRoot);
        System.out.println(map.entrySet());
        String efficiency2 = HuffmansTree.efficiencyHT(text, map);
        String codedData = HuffmansTree.exportBinaryString();
        HuffmansTree.Decompress(fRoot);
        HuffmansTree.decode(codedData, decompressionMAp);
        char s='?';
        System.out.println("\n\n\n\n\n"+(char)195);
    }

//    @Override
//    public int compare(Map.Entry<> o1, o2) {
//        if(o1)
//        return 0;
//    }
}
