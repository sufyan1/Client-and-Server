package year5;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

// J panel

public class MyPanel extends JPanel {
    private JButton Load;
    private JButton Reverse;
    private JButton Reverse_words;
    private JButton Count;
    private JTextArea LoadArea;
    private JTextArea ReverseLine;
    private String filename;
    private BufferedReader br;
    private JTextArea jcomp7;
    private JTextArea jcomp8;
	protected int counting;
	protected int j;
	
// My panel
    public MyPanel() {
        //construct components
        Load = new JButton ("Load");
        Reverse = new JButton ("Reverse");
        Reverse_words = new JButton ("Reverse_words");
        Count = new JButton ("Count");
        LoadArea = new JTextArea (5, 5);
        ReverseLine = new JTextArea (5, 5);
        jcomp7 = new JTextArea (5, 5);
        jcomp8 = new JTextArea (5, 5);

        //adjust size and set layout
        setPreferredSize (new Dimension (1706, 567));
        setLayout (null);

        //add components
        add (Load);
        add (Reverse);
        add (Reverse_words);
        add (Count);
        add (LoadArea);
        add (ReverseLine);
        add (jcomp7);
        add (jcomp8);

        //set component bounds (only needed by Absolute Positioning)
        Load.setBounds (0, 0, 160, 55);
        Reverse.setBounds (305, 0, 150, 55);
        Reverse_words.setBounds (665, 0, 160, 55);
        Count.setBounds (995, 0, 160, 55);
        LoadArea.setBounds (0, 55, 300, 875);
        ReverseLine.setBounds (305, 55, 300, 875);
        jcomp7.setBounds (665, 55, 300, 875);
        jcomp8.setBounds (995, 55, 300, 875);
    //add action listener
        
        
        //1
        
        Load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	loadWords(); 
            }
        });

    //2
        
    
    Reverse.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        	ReverseLine();
         }
    });
    //3
    Reverse_words.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        	Reversewords();           
        }
    });
    //4
    
    Count.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		
    		CountWords();
    	}
    });

    }
// add action listener
    
    
void loadWords() {
    SwingWorker<String, Void> worker1 = new SwingWorker<String, Void>() {

        @Override
        protected String doInBackground() throws Exception {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            filename = file.getAbsolutePath();
            // System.out.print(filename);


    return filename;
}

@Override
protected void done() {
    try {
         String line = get(); //get() retrieves the return value from doInBackground()
         try {
        	    FileReader reader = new FileReader(line);
        	    br = new BufferedReader(reader);
        	    LoadArea.read(br, null);
        	    LoadArea.requestFocus();
        	    br.close();
        	    Load.setText("File Loaded");
        	       
        	    } catch (Exception e) {
        	        JOptionPane.showMessageDialog(null, e);
        	    }
        	    
         
    } catch (Exception e) {
         //this is where you handle any exceptions that occurred in the
         //doInBackground() method
    }
}

};//swing worker
    worker1.execute();
}       
void ReverseLine() {
    //SwingWorker<Boolean, Void> worker2 = new SwingWorker<Boolean, Void>() {
    	SwingWorker<StringBuilder, Void> worker2 = new SwingWorker<StringBuilder, Void>() {
        @Override
        protected StringBuilder doInBackground() throws Exception {
        	StringBuilder sb = null;
        	
        	try {
                BufferedReader input = new BufferedReader(new FileReader(filename));
                ArrayList list = new ArrayList();
                String line;

                while ((line = input.readLine()) != null) {
                    list.add(line);
                    sb = new StringBuilder(line.length() + 1);
                    String [] words = line.split(" ");
for (int i = words.length - 1; i >= 0; i--) {
   sb.append(words[i]).append(' ');
   
}
sb.setLength(sb.length());  // Strip trailing space
ReverseLine.append(sb+"\n");

}
input.close();

   
    } 
        	catch (IOException e){
        JOptionPane.showMessageDialog(null, e);
    }
        	
        	Reverse.setText("Lines Reversed");
 return sb;   
}
@Override
protected void done(){
	
	try {
		StringBuilder sb = get(); //get() retrieves the return value from doInBackground()
		//String [] words = line.split(" ");
		
		ReverseLine.append(sb+"\n");
   } catch (Exception e) {
        
   }
	
}//done

};//swing worker
            worker2.execute();
}
//void count words
void Reversewords() {
    SwingWorker<StringBuilder, Void> worker3 = new SwingWorker<StringBuilder, Void>() {
    	StringBuilder sb =null;	   
		@Override
        protected StringBuilder doInBackground() throws Exception {
        	try {
                BufferedReader input = new BufferedReader(new FileReader(filename));
                ArrayList list = new ArrayList();
                String line;
                

                while ((line = input.readLine()) != null) {
                    list.add(line);
                    sb = new StringBuilder(line.length() + 1);
               
                    String[] words = line.split(" ");// to split the words 
                    
for (int i = 1; i <= words.length - 1; i++) {//sb.append(words[i]).append(' ');
j=i-1;
sb.append(words[i]).append(' ').append(words[j]).append(' ');
        i++;	
    }
//return sb;
jcomp7.append(line+"\n");
}      
input.close();


            } catch (IOException e){
                JOptionPane.showMessageDialog(null, e);
            }
			       	
        	return sb;
}

		

protected void done() {
    try {
         StringBuilder line = get(); //get() retrieves the return value from doInBackground()
         jcomp7.append(line+"\n");
    } catch (Exception e) {
         //this is where you handle any exceptions that occurred in the
         //doInBackground() method
    }
}
};//swing worker
            worker3.execute();
}

void CountWords(){
	SwingWorker<String, Void> worker4 = new SwingWorker<String, Void>() {
		String formatted = "";
		@Override
        protected String doInBackground() throws Exception {
        	//if box #1 has text then continue
if (! LoadArea.getText().isEmpty()) {
	//read the text from box #1
String text = LoadArea.getText();

//convert text to lowercase for simplification
text = text.toLowerCase();
//remove punctuation from text to prevent errors counting words
List<String> wordsList = Arrays.asList(text.split("[^a-zA-Z0-9']+"));
//creating map and iterator
//[^a-zA-Z0-9']+
HashMap<String, Integer> map = new HashMap<>();
Iterator<String> iterator = wordsList.iterator();
	
//loop through all of the text
   while (iterator.hasNext()){
        String word = iterator.next();
        //if word is already stored, increment its counter value
if (map.containsKey(word)) {
    map.put(word, map.get(word));
} else {
	//store word as new key in map with value of 1
            map.put(word, 1);
           
			counting++;
        }        				
   }
   for (HashMap.Entry<String, Integer> entry : map.entrySet()){
	   formatted = formatted + entry.getKey() + "=" + entry.getValue()+"\n";
   }
 //iterate over the hashmap, adding each key/value pair to a String
   
	}

 //passes the name of the file to process()
    return formatted;
}



protected void done() {
    try {
         String formatted = get(); //get() retrieves the return value from doInBackground()
         jcomp8.setText(formatted+"\ntotal words"+counting);
    } catch (Exception e) {
         //this is where you handle any exceptions that occurred in the
         //doInBackground() method
    }
}
	};//swing worker
            worker4.execute();
}
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	 JFrame frame = new JFrame ("MyPanel");
         	        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
         	        frame.getContentPane().add (new MyPanel());
         	        frame.pack();
         	        frame.setVisible (true);
                    new MyPanel().setVisible(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



