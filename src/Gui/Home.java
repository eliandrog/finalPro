package Gui;

import LZW.LZW;
import RLE_Image.RLE_ImgFinal;
import RLE_text.RLE;
import RLE_text.finalRLEtxt;
import huffman.HuffmansTree;
import huffman.Node;
import huffman_image.huff_Img_Node;
import huffman_image.huff_img;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static LZW.LZW.compressionRatioLZW;
import static RLE_Image.RLE_ImgFinal.compressionRatioRLEIMG;
import static RLE_text.finalRLEtxt.compressionRatioRLEText;
import static huffman.HuffmansTree.compressionRatio;
import static huffman_image.huff_img.assignEncoding;

/**
 * Created by dave on 28/10/17.
 */
public class Home extends JFrame{
    public final int x=1400;
    public final int y=500;
    public JPanel txtBxPnl,rdBtnsPnl, textLbl,dropDwnLst,centerPanel;
    public JTextField txtBx;
    public JButton sumbitBtn,help;
    public JRadioButton txtRdBtn,picRdBtn;
    public ButtonGroup rdBtnsGroup;
    public JLabel text,text2,text3,resultCompare,errorMessage,keyArea;
    public boolean open;
    public  static File helpFilePath =new File("C:\\Users\\elian\\Documents\\FinalYearProject\\Help.txt"); //needs to be changed to appropriate path if project chnages environmnet


    public final JComboBox<String> dl1,dl3 ;
    public final JComboBox<String> dl2 ;

    //public List<Integer>efficiencyList=



    public Home(){
        //pack();
        setSize(x,y);
        setTitle("Data Compression Algorithms ");

        //initialising Panels
        txtBxPnl= new JPanel();
        rdBtnsPnl=new JPanel();
        textLbl=new JPanel();
        //textLbl.setLayout(new BoxLayout(textLbl,BoxLayout.Y_AXIS));//new GridLayout(20,1));
        textLbl.setLayout(new BoxLayout(textLbl, BoxLayout.Y_AXIS));
        dropDwnLst=new JPanel(new BorderLayout());
        centerPanel=new JPanel();


        //initialising Labels
        text=new JLabel("Efficiency: ");
        text2=new JLabel("Efficiency: ");
        text3=new JLabel("Efficiency: ");
        resultCompare=new JLabel("Most efficient algorithm(comparing chosen)");
        errorMessage=new JLabel("No Errors");
        keyArea=new JLabel("<html>Key<br>" +
                "Efficiency--> The percentage of memory that an algorithm has made redundant<br>" +
                "Compression Ratio--> The ratio that one unit(bit) is represented by the algorithm<br>" +
                "(eg: 5:1 means 5 bits of uncompressed data is now represented by 1 compressed bit<br>" +
                "Unit of measurement is Bits <br>" +
                "The higher the ratio the better the algorithm compression </html>");
        JLabel chose=new JLabel("Choose Algorithms:");
        //subBtnPnl= new JPanel();


        //initialising Text field, Text Area , buttons and radio buttons
        txtBx=new JTextField("",25);
        sumbitBtn=new JButton("Submit");
        help=new JButton("Help" );
        help.setToolTipText("<html>1-> Select Picture or Text (eg: if you want to compress a text file press Text)<br>" +
                            "2-> Select different Algorithms <br>" +
                            "3-> Type the name of your file with its extension (eg: file.txt  or file.png)<br>" +
                            "4-> Press \"Submit\" button <br>" +
                            "5-> Find out which Algorithm is the best for your file and the compressed and decompressed Files.</html>");

        txtRdBtn=new JRadioButton("Text");
        picRdBtn=new JRadioButton("Picture");
        rdBtnsGroup=new ButtonGroup();
        //keyArea=new JTextArea("Key\n\nEfficiency--> The percentage of memory that an algorithm has made redundant\n\n(eg: 5:1 means  )",8,1);




        ////border for jpanels (Black)
        txtBxPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rdBtnsPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dropDwnLst.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));




        //group the radio buttons so only one can be selected at anytime
        rdBtnsGroup.add(txtRdBtn);
        rdBtnsGroup.add(picRdBtn);

        //making the drop down list options
        String[] choices1 = { "Run Length Encoding","Huffmans coding","LZW"};
        String[] choices2 = { "Run Length Encoding","Huffmans coding","LZW"};
        String[] choices3 = { "Run Length Encoding","Huffmans coding","LZW"};

        //adding the possible choices to a drop down list
        dl1 = new JComboBox<String>(choices1);
        dl2 = new JComboBox<String>(choices2);
        dl3 = new JComboBox<String>(choices3);

        //setting it visible
        dl1.setVisible(true);
        dl2.setVisible(true);
        dl3.setVisible(true);

        //adding all components to particular JPanels
        txtBxPnl.add(txtBx);
        txtBxPnl.add(sumbitBtn);
        txtBxPnl.add(help);
        rdBtnsPnl.add(txtRdBtn);
        rdBtnsPnl.add(picRdBtn);
        dropDwnLst.add(chose,BorderLayout.NORTH);
        //ropDwnLst.add(new JLabel("2323223defskpdfksfsdfsdfsdfsdf"));
        dropDwnLst.add(dl1,BorderLayout.WEST);
        dropDwnLst.add(dl2,BorderLayout.CENTER);
        dropDwnLst.add(dl3,BorderLayout.EAST);
        //dropDwnLst.add(errorMessage);
        centerPanel.add(errorMessage);
        textLbl.add(text);
        textLbl.add(text2);
        textLbl.add(text3);
        textLbl.add(resultCompare);
        textLbl.add(Box.createVerticalGlue());
        textLbl.add(keyArea);

//        JPanel p = new JPanel();
//        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//        //p.add(text);
//        p.add(text2);
//        p.add(text3);
//        p.add(resultCompare);
//        p.add(Box.createVerticalGlue()); // This will expand/contract as needed.
//        p.add(keyArea);

        //add(p,BorderLayout.EAST);


        //adding panels to frame with its particular position
        add(txtBxPnl ,BorderLayout.SOUTH);
        add(rdBtnsPnl,BorderLayout.BEFORE_FIRST_LINE);
        add(textLbl,BorderLayout.EAST);
        add(dropDwnLst,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        //add(txtRdBtn,BorderLayout.BEFORE_FIRST_LINE);
        //add(picRdBtn,BorderLayout.WEST);
        //add(subBtnPnl);
        //add(txtBxPnl,new BorderLayout().CENTER);



        //add(txtBxPnl, BorderLayout.CENTER);


        //created and instantiated when buttons are pressed

        ButtonHandler handler=new ButtonHandler(this);
        sumbitBtn.addActionListener(handler);
        HelpButtonListener handlerHelp=new HelpButtonListener(this);
        help.addActionListener(handlerHelp);

        //listener to the radio buttons
        RadioButtonActionListener radioListener=new RadioButtonActionListener(this);
        txtRdBtn.addActionListener(radioListener);
        picRdBtn.addActionListener(radioListener);

        //display colours
        Color d=new Color(38,170,251,100);
        rdBtnsPnl.setBackground(d);
        textLbl.setBackground(d);
        centerPanel.setBackground(d);
        dropDwnLst.setBackground(d);
        txtBxPnl.setBackground(d);






    }


    class ButtonHandler implements ActionListener{

        Home theGUI;

        ButtonHandler(Home gui){

            theGUI=gui;
        }


        public void actionPerformed(ActionEvent e){
            submit:
            if(e.getSource()==theGUI.sumbitBtn){
                theGUI.errorMessage.setText("No Error");
                theGUI.repaint();
                System.out.println("here");
                //test for text files (later)
                String filename=theGUI.txtBx.getText().trim();

                //finding radio button chosen
                String rdBtnChsn="";
                if(theGUI.picRdBtn.isSelected()){
                    rdBtnChsn="P";
                }else if(theGUI.txtRdBtn.isSelected()){
                    rdBtnChsn="T";
                }

                if(rdBtnChsn=="T"){
                    boolean sameChoice=false;
                    outerloop:
                    try {

                        //System.out.println(String.valueOf(theGUI.dl1.getSelectedItem().toString()).equals(String.valueOf(theGUI.dl2.getSelectedItem())) +"       "+theGUI.dl3.getSelectedItem().toString());

                        //get chosen algorithms from dropdown lists
                        String choice1= (String) theGUI.dl1.getSelectedItem();
                        String choice2= (String) theGUI.dl2.getSelectedItem();
                        String choice3= (String) theGUI.dl3.getSelectedItem();

                        System.out.format("1: %s , 2: %s  , 3: %s",choice1,choice2,choice3);

                        //prints error message if there are 2 of the same algorithm
                        if (    choice1.equals(choice2 ) ||
                                choice1.equals(choice3)  ||
                                choice2.equals(choice3)) {


                            //empty the efficiency text
                            theGUI.text.setText("Efficiency:");
                            theGUI.text2.setText("Efficiency:");
                            theGUI.text3.setText("Efficiency:");




                            System.out.println("its the same");
                            theGUI.errorMessage.setText("Please do not choose an algorithm more than once");
                            theGUI.repaint();



                            //break submit;
                        } else {
                         //if all the algorithms are different
                            theGUI.errorMessage.setText("No errors");
                            theGUI.repaint();

                            //making sure user can dynamically see the error message

                            //making sure structure of file excepted is (text).txt only
                            System.out.println("\n"+filename);

                            String pattern="^\\b(.+)(\\.txt)\\b$";
                            Pattern r=Pattern.compile(pattern);
                            Matcher m=r.matcher(filename);


                            //if its not the structure prints error message
                             if (!m.find()){
                                theGUI.errorMessage.setText("Please provide the name of a file with \".txt \" extension eg: file.txt");
                                theGUI.repaint();
//                                if (filename.substring(0,filename.indexOf('.')).length()>0 ) {                       // only (text) prior to .
//                                    if ( filename.substring(filename.indexOf('.'), filename.length()).equals(".txt")) {
//                                            //if its not the structure print error message
//
//                                          theGUI.errorMessage.setText("Please provide a file with \".txt \" extension");theGUI.errorMessage.repaint();
//                                        System.out.println("error in this");
//                                          break submit;
//                                    }
//
//
//                                }else{
//                                    theGUI.errorMessage.setText("Please provide name of the file following the extension .txt");
//                                    System.out.println("im in the else");
//                                    break submit;
                                //}
                            }else{

//                                if (errorMessage.getText().length() > 0) {
//                                    theGUI.remove(errorMessage);
//                                    //theGUI.repaint();
//                                }
                                 theGUI.errorMessage.setText("No Errors");
                                 theGUI.repaint();
                                 System.out.println("Its not the same");
                                 //theGUI.errorMessage.setText("compressing");
                                 //RLE algorithm
                                 String RunEfficiency = "";
                                 finalRLEtxt rle=new finalRLEtxt();
                                 rle.compress(filename);
                                 theGUI.errorMessage.setText(rle.errormessage);
                                 theGUI.repaint();
                                 rle.decompress(rle.string.toString());
                                 //System.out.println(finalRLEtxt.string);
                                 RunEfficiency = rle.efficiency(rle.originalText(filename), rle.string.toString());
                                 String ratioRLE=compressionRatioRLEText;





                                //Huffmans compression
                                String text = HuffmansTree.originalTextHT(filename);
                                Node fRoot = HuffmansTree.buildHuffT(HuffmansTree.makeFreq(text));
                                Map<Character, String> map = HuffmansTree.assignEncoding(fRoot);
                                String efficiency2 = HuffmansTree.efficiencyHT(text, map);
                                String codedData = HuffmansTree.exportBinaryString();
                                HuffmansTree.decode(codedData, map);
                                String rationHF=compressionRatio;



                                //lzw compression
                                LZW lzw = new LZW();
    //                                System.out.println(lzw.map.entrySet());
    //                                System.out.println(lzw.map.get("a"));
                                System.out.println(lzw.compression(text));
                                lzw.decompress(lzw.listOfChIndex);
                                lzw.writoToFile(filename);
                                String efficiency3 = lzw.efficiency(text);
                                String ratioLZW=compressionRatioLZW;
                                System.out.println(lzw.Binarylist);


                                //getting the efficiency of each compression algorithm
                                String resultText = String.format("The efficiency of Run-Length-Encoding is %s ", RunEfficiency);
                                String resultText2 = String.format("The efficiency of Huffmans compression is %s ", efficiency2);
                                String resultText3 = String.format("The efficiency of LZW is %s ", efficiency3);
                                String betterAlgorithm = " The most efficient Algorithm(s) is(are) :  ";
    //                                System.out.println(RunEfficiency);
    //                                System.out.println(efficiency2);
    //                                System.out.println(efficiency3);

                                //finding which compression is the most efficient
                                HashMap<String,Double> algorithmsPercentile= new HashMap<String,Double>();
                                //algorithmsPercentile.put("s",4.4);
                                //System.out.println(RunEfficiency.substring(0,RunEfficiency.length()-2));

                                //adding each algorith with the respective effectiveness
                                algorithmsPercentile.put("Run-Length-Encoding",Double.valueOf(RunEfficiency.substring(0,RunEfficiency.length()-1)));
                                algorithmsPercentile.put("Huffmans compression",Double.valueOf(efficiency2.substring(0,efficiency2.length()-1)));
                                algorithmsPercentile.put("LZW",Double.valueOf(efficiency3.substring(0,efficiency3.length()-1)));

                                //getting the best efficiency
                                Double bestEfficiency=Collections.max(algorithmsPercentile.entrySet(),Comparator.comparingDouble(Map.Entry ::getValue)).getValue();

                                //betterAlgorithm+= String.valueOf(Collections.max(algorithmsPercentile.entrySet(),Comparator.comparingDouble(Map.Entry::getValue)).getKey());
                                //System.out.println("best efficiency: "+bestEfficiency);
                                //System.out.println(betterAlgorithm);



                                //finding the algorithm(s) that match the efficiency
                                String equalAlgorithms="";
                                int i=0;
                                for (Map.Entry element : algorithmsPercentile.entrySet()){                        //iterating through all the algorithms efficiency

                                    if (element.getValue()==bestEfficiency && i<1){                                // getting the first algorith that is equal to it                    eg  RLE
                                        equalAlgorithms+=element.getKey();
                                        i++;
                                    }else if(element.getValue()==bestEfficiency && i>0  ){                        //gets a second one only if one algorithm is already the same         eg  RLE or LZW
                                        equalAlgorithms+=" or "+element.getKey();
                                        i++;
                                    }
                                }

                                if (equalAlgorithms.length()>0){                                              // adds the algorithms if one is added
                                    betterAlgorithm+=equalAlgorithms;
                                }

    //                                if (Double.valueOf(RunEfficiency.substring(0, RunEfficiency.length() - 1)) < Double.valueOf(efficiency2.substring(0, RunEfficiency.length() - 1))) {
    //                                    if (Double.valueOf(efficiency3.substring(0, efficiency3.length() - 1)) > Double.valueOf(efficiency2.substring(0, efficiency2.length() - 1))) {
    //                                        betterAlgorithm += String.valueOf(theGUI.dl3.getSelectedItem());
    //                                    } else {
    //                                        betterAlgorithm += String.valueOf(theGUI.dl2.getSelectedItem());
    //                                    }
    //
    //                                    theGUI.resultCompare.setText(betterAlgorithm);
    //
    //
    //                                } else if (Double.valueOf(RunEfficiency.substring(0, RunEfficiency.length() - 1)) > Double.valueOf(efficiency2.substring(0, RunEfficiency.length() - 1))) {
    //                                    if (Double.valueOf(efficiency3.substring(0, efficiency3.length() - 1)) > Double.valueOf(RunEfficiency.substring(0, RunEfficiency.length() - 1))) {
    //                                        betterAlgorithm += String.valueOf(theGUI.dl3.getSelectedItem());
    //                                    } else {
    //                                        betterAlgorithm += String.valueOf(theGUI.dl1.getSelectedItem());
    //                                    }
    //                                    theGUI.resultCompare.setText(betterAlgorithm);
    //                                } else {
    //                                    theGUI.resultCompare.setText("Both the Algorithms have performed the same efficiency percentage.");
    //                                }

                                System.out.println(resultText+"\n"+resultText2+"\n"+resultText3);
                                // text about effectiveness and compression ratio of each algorithm
                                theGUI.text.setText(resultText+ "  Compression Ratio: "+ratioRLE);
                                theGUI.text2.setText(resultText2+ "  Compression Ratio: "+rationHF);
                                theGUI.text3.setText(resultText3+ " Compression Ratio: "+ratioLZW);
                                theGUI.resultCompare.setText(betterAlgorithm);
                                theGUI.repaint();


                                break submit;
                            }
                        }
                        //theGUI.repaint();
                    }catch (Exception excep){
                            excep.printStackTrace();
                    }
//                        while(!sameChoice){
//                            System.out.println("you have the same algoritm more than once");
//                        }



                    //Run lengths encoding

                }else if (rdBtnChsn=="P"){
                    //disable one of the options
                    //theGUI.dl3.setEnabled(false);

                    try {

                        //if the chosen algorithms are the same
                        if (theGUI.dl1.getSelectedItem().equals(theGUI.dl2.getSelectedItem())){
                            System.out.println("repetition");
                            theGUI.errorMessage.setText("Please do not choose an algorithm more than once");
                            theGUI.repaint();

                        }else {

                            try {
                                    System.out.println("here again");
                                    //type of formats accepted
                                    String pattern="^(.+)(\\.tiff|\\.png|\\.bmp|\\.jpg|\\.jpeg)$";
                                    Pattern r=Pattern.compile(pattern);
                                    Matcher m=r.matcher(filename);




                                    //if for is found compress the image with the different algorithms
                                    if( m.find()){


                                        //run length encoding image
                                        //gets image , builds the 2d array of each pixel, analyses the data and compresses, and rebuilds the decompressed array
                                        theGUI.errorMessage.setText("Open the file");
                                        BufferedImage image = ImageIO.read(new File(filename));
                                        RLE_ImgFinal rle = new RLE_ImgFinal(image);
                                        theGUI.errorMessage.setText("Compressing Run Length Encoding");
                                        rle.buildPxlArr(image);
                                        rle.rebuildColor(rle.decompressColor(rle.compressingColorArr(rle.colorPxlArray)));
                                        theGUI.errorMessage.setText("Finished Run Length");

                                        //gets the efficiency of the compressed array in comparrison to the original image
                                        String efficiency = String.valueOf(rle.efficiencyColor(rle.compressingColorArr(rle.colorPxlArray)));
                                        theGUI.text.setText("The efficiency of Run Length encoding is " + efficiency + "%   Compression Ratio:  "+compressionRatioRLEIMG);
                                        theGUI.repaint();






                                        //Huffmans encoding Image
                                        theGUI.errorMessage.setText("Compressing Image and assigning codes to pixels");theGUI.repaint();
                                        huff_img huff = new huff_img(filename);
                                        huff_Img_Node finalNode = huff.buildHFT();
                                        Map<Color, String> bitCodeMap = assignEncoding(finalNode);      //makes map og the final node tree and its given bitcode
                                        huff.encodeImage(huff.ogImage, bitCodeMap);
                                        theGUI.errorMessage.setText(" Rebuilding the image may take a few minutes");theGUI.repaint();
                                        huff.decompress(finalNode);

                                        huff.rebuildImage(huff.compImg);
                                        String efficiencyHUFF = String.valueOf(huff.efficiency(huff.compImg));
                                        theGUI.errorMessage.setText("Finished Compressing all algorithms");
                                        theGUI.repaint();

                                        theGUI.text2.setText("The efficiency of Huffmans Encoding is " + efficiencyHUFF + "%     Compression Ratio:  "+huff.compressionRatioHFIMG);
                                        theGUI.repaint();







                                        //finding the most effective algorithm
                                        String result="";
                                        if (Double.valueOf(efficiencyHUFF)>Double.valueOf(efficiency)){

                                            result="The most efficient Algorithm is : Huffmans encoding";


                                        }else if (Double.valueOf(efficiencyHUFF)<Double.valueOf(efficiency)){
                                            result="The most efficient Algorithm is : Run Length Encoding ";

                                        }else if(Double.valueOf(efficiencyHUFF)==Double.valueOf(efficiency)){
                                            result="Both the Huffmans Encoding and Run Length Encoding are as effective";
                                        }


                                        theGUI.resultCompare.setText(result);
                                        theGUI.repaint();

                                    }else{
                                        theGUI.errorMessage.setText("make sure to select file with .tiff or .bmp or .jpg or .jpeg or .png");
                                        theGUI.repaint();
                                    }

                            }catch(IOException  exc){
                                exc.printStackTrace();
                                theGUI.errorMessage.setText("Could not open this file choose another");
                                theGUI.repaint();
                                //break submit;
                            }



                            //enable the third
                            //theGUI.dl3.setEnabled(true);

                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                        theGUI.errorMessage.setText("Please do not choose equal algorithms simultaneously");
                        theGUI.repaint();


                    }


                }else {


                    //if text or picture is not selected
                    theGUI.errorMessage.setText("Select Text or Picture");
                    theGUI.repaint();
                }

            }

        }

    }


    class RadioButtonActionListener implements ActionListener {

        Home theGui;

        RadioButtonActionListener(Home gui){
            theGui=gui;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JRadioButton button = (JRadioButton) event.getSource();

            if (button == theGui.txtRdBtn) {

                //if the text radio button is selected
                theGui.dl3.setEnabled(true);
                if(theGui.dl1.getItemCount()==3 && theGui.dl2.getItemCount()==3){

                }else if(theGui.dl1.getItemCount()==2 && theGui.dl2.getItemCount()==2){
                    theGui.dl1.addItem("LZW");
                    theGui.dl2.addItem("LZW");
                    //resets the text so it does not confuse the user with the old compression results
                    theGui.text.setText("Efficiency: ");
                    theGui.text2.setText("Efficiency: ");
                    theGui.text3.setText("Efficiency: ");
                    theGui.resultCompare.setText("Most efficient algorithm(comparing chosen)");
                    theGui.errorMessage.setText("No errors");
                }

                theGui.repaint();



            } else if (button == theGui.picRdBtn) {

                // if the picture radio button is selected
                theGui.dl3.setEnabled(false);
                if(theGui.dl1.getItemCount()==3 && theGui.dl2.getItemCount()==3){
                    //removing the text only algorithm
                    theGui.dl1.removeItemAt(2);
                    theGui.dl2.removeItemAt(2);

                    //resetting the results text so the old results dont get confusing to the end user
                    theGui.text.setText("Efficiency: ");
                    theGui.text2.setText("Efficiency: ");
                    theGui.text3.setText("");
                    theGui.resultCompare.setText("Most efficient algorithm(comparing chosen)");
                    theGui.errorMessage.setText("No errors");
                }

                theGui.repaint();


//                String filename=theGui.txtBx.getText();
//                if(!(filename.substring(0,filename.indexOf('.')).length()>0)){
//
//                    if (!filename.substring(filename.indexOf('.'), filename.length()).equals(".tiff")) {
//
//                        theGui.errorMessage.setText("Please provide a file with \" .tiff , .png , .bmp \" extension");
//                    }
//                    theGui.errorMessage.setText("Please provide a filename with \" .tiff , .png , .bmp\" extension ");
//                }
            }

        }
    }

    class HelpButtonListener implements ActionListener{

        Home theGui;

        HelpButtonListener(Home gui){

            theGui=gui;

        }

        @Override
        public void actionPerformed(ActionEvent ae){
            //button handler for help button
            if (ae.getSource()==theGui.help){
                System.out.println("i am");
                // help document appears when its not opened already
                if (helpFilePath.canWrite()){
                    System.out.println("here");
                    if (Desktop.isDesktopSupported()) {
                        try {
                            System.out.println("here");
                            Desktop.getDesktop().edit(helpFilePath);
                            open=true;

                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    } else {
                        theGui.errorMessage.setText("Can not open the help file");
                    }
                }

            }
        }
    }


}
