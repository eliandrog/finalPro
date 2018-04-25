package huffman;

import java.util.Map;

/**
 * Created by egonca on 07/12/2017.
 */
public class Node implements  Comparable<Node>{

    public char c;
    public int freq;
    Node leftChild,rightChild;


    Node(char c,int freq,Node leftChild,Node rightChild){
        //Node node=new Node(c,freq,left,right);
        this.c=c;
        this.freq=freq;
        this.leftChild=leftChild;
        this.rightChild=rightChild;
    }


    public boolean isNodeLeaf(){

        return this.leftChild==null && this.rightChild==null;
    }

    public int getFreq() {
        return freq;
    }

    public char getC() {
        return c;
    }


    @Override
    public String toString(){
        return "Node: "+c+" freq: "+freq;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(freq,o.freq);
    }

    






}
