package RLE_Image;

import java.awt.*;

public class Cobject {
    private short run;
    private Color c;
    public Cobject(short run,Color c){
        this.run=run;
        this.c=c;
    }


    public short getRun() {
        return run;
    }

    public Color getC(){
        return c;
    }
    @Override
    public String toString(){

        return getRun()+"  "+getC();
    }
}
