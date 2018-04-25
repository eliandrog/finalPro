package RLE_text;

public class RLECObject {
    private byte run;
    private byte character;

    RLECObject(byte run,byte character){
        this.run=run;
        this.character=character;

    }

    public byte getRun() {
        return run ;
    }


    public byte getCharacter() {
        return character;
    }
    @Override
    public String toString(){
        //Character.valueOf((char) (getCharacter() & 0xff)).toString()
        return String.valueOf(getRun()& 0xff);
    }
}
