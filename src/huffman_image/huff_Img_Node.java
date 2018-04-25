package huffman_image;

import java.awt.*;
import java.util.Comparator;

public class huff_Img_Node implements Comparable<huff_Img_Node> {
    public Color colour;
    public int freq;
    public huff_Img_Node leftChild,righgtChild;

    huff_Img_Node(Color c,int freq,huff_Img_Node leftChild,huff_Img_Node rightChild){

        colour=c;
        this.freq=freq;
        this.leftChild=leftChild;
        this.righgtChild=rightChild;


    }


    public Color getColour() {
        return colour;
    }

    public huff_Img_Node getLeftChild() {
        return leftChild;
    }

    public huff_Img_Node getRighgtChild() {
        return righgtChild;
    }

    public int getFreq() {
        return freq;
    }
    public boolean isLeaf(){
        return this.leftChild==null && this.righgtChild==null;
    }

    @Override
    public int compareTo(huff_Img_Node o2) {
        return this.freq-o2.freq;
    }

    @Override
    public String toString(){


        return "    "+getFreq();
    }
}
