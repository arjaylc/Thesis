
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Y460
 */

public class PosTagger{
    private static MaxentTagger tagger = new MaxentTagger("taggers/wsj-0-18-left3words-distsim.tagger");
    private static JFrame main;
    private static JPanel panel;
    private static JTextField file_input;
    private static JButton browse;
    private static JButton scan;
    private static JTextArea output;
    private static JFileChooser fc;
    private static JScrollPane s;
    private static JScrollBar sbv;
    private static File file;
    //private static ArrayList<String> words = new ArrayList<String>();
    private static String[] words;
    private static void init_components(){
        main = new JFrame();
        main.setSize(1010, 700);
        main.setVisible(true);
        main.setLayout(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        main.add(panel);
        panel.setBounds(0, 0, 980, 700);
        panel.setLayout(null);
        panel.setVisible(true);
        file_input = new JTextField();
        panel.add(file_input);
        file_input.setBounds(30, 30, 720, 30);
        file_input.setVisible(true);
        output = new JTextArea();
        s = new JScrollPane(output);
        sbv = new JScrollBar();
        s.setVerticalScrollBar(sbv);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(s);
        s.setBounds(25, 70, 945, 570);
        sbv.setVisible(true);
        output.setBounds(25, 70, 945, 570);
        output.setLineWrap(true);
        output.setEditable(false);
        output.setVisible(true);
        fc = new JFileChooser("C:\\Users\\Y460\\Desktop\\Stories (Processed)");
        browse = new JButton("Browse");
        panel.add(browse);
        browse.setBounds(755, 30, 100, 30);
        browse.setVisible(true);
        browse.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                int returnVal = fc.showOpenDialog(panel);                
                if(!output.getText().equals(""))
                    output.setText("");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    file_input.setText(file.getAbsolutePath());
                    //This is where a real application would open the file.
                    output.append("Opening: " + file.getName() + "." + "\n");
                } else {
                    output.append("Open command cancelled by user." + "\n");
                }
                output.setCaretPosition(output.getDocument().getLength());
            }
        });
        scan = new JButton("Scan");
        panel.add(scan);
        scan.setBounds(860, 30, 100, 30);
        scan.setVisible(true);
        scan.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String tag = tagger.tagString(testfile());
                words = tag.split(" ");
              
                    
                    try {
                        FileWriter fw = new FileWriter("C:\\Users\\RJ\\Desktop\\Stories(Tagged)\\"+file.getName(),true);
                        PrintWriter pw = new PrintWriter(fw);
                          for(int i = 0; i < words.length; i++){
                            pw.append(words[i] + " ");
                            output.append(words[i] + " ");
                            if(words[i].contains(".")||words[i].contains("?")||words[i].contains("!")){
                                pw.append("\n");
                                output.append("\n");
                            }
                          }
                        pw.close();
                    } catch (IOException ex) {
                        output.append("Failed to write to text file.");
                    }
                    
                
            } 
        });
    }
    public static String testfile(){
        if(!output.getText().equals(""))
            output.setText("");
        
        String test = "";
        String compiled = "";
        BufferedReader read;
        try{
            read = new BufferedReader(new FileReader(file_input.getText()));
            while((test = read.readLine()) != null){
                compiled = compiled.concat(test + "\n");
            }
        }catch(Exception e){
            compiled = "Operation failed.";
        }
        return compiled;
    }
    
    public static void main(String[] args) {
        init_components();
    }
}
