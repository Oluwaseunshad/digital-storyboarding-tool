/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrameViews;

/**
 *
 * @author dakua
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Home implements ItemListener {
    JPanel cards; //a panel that uses CardLayout
    final static String home = "Home";
    final static String createNewSb = "Create New StoryBoard";
    final static String trackReq = "Track Requirements";
    static JPanel canvasPanel, toolsPanel, buttonsPanel;
    static Canvas drawingArea;
   // static ArrayList <String> tools = new ArrayList();
   // static String tools [] = new String[] {"Square, Arrow, Oval, Line"};
    static JButton saveButton, newButton,exitButton;
    static JSplitPane split1;
    static JSplitPane split2;
    private int drawingArea_width = 200,drawingArea_height =200;

    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = {home, createNewSb, trackReq};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        //Create the "cards".
        JPanel card1 = new JPanel();
        card1.add(new JTextField("Homepage", 20));

        JPanel card2 = new JPanel();
       // card2.setSize(300,300);
      
        saveButton = new JButton("Save");
        newButton = new JButton("New");
        exitButton = new JButton("Exit");
        
        buttonsPanel = new JPanel();
       // buttonsPanel.setLayout(new GridLayout(2, 1));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(newButton);
        buttonsPanel.add(exitButton);
        
        drawingArea = new Canvas();
       //// drawingArea.setSize(200,300);
       // drawingArea.setMinimumSize(new Dimension(200,300));
        drawingArea.setBackground(Color.green);
        drawingArea.setLayout(new BorderLayout(6, 10));
        drawingArea.setBorder(BorderFactory.createTitledBorder("Draw Here"));
         
        toolsPanel = new JPanel();
      //  toolsPanel.setMinimumSize(new Dimension(100,100));
        toolsPanel.setLayout(new BorderLayout(6, 10));
        split1 = new JSplitPane();
        split2 = new JSplitPane();
        split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawingArea, toolsPanel);
        //split1.resetToPreferredSizes();
      //  split1.setResizeWeight(1);
        split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, buttonsPanel); 
       // split2.resetToPreferredSizes();
       split2.setResizeWeight(1);
       // split2 .setMinimumSize(new Dimension(450,350));
       
        card2.add(split2);
       
        JPanel card3 = new JPanel();
        card3.add(new JTextField("Track Requirements", 20));

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, home);
        cards.add(card2, createNewSb);
        cards.add(card3, trackReq);

        pane.add(comboBoxPane, BorderLayout.WEST);
        pane.add(cards, BorderLayout.CENTER);
    }
    
   class Canvas extends JPanel{
         @Override
        public void paint(Graphics g){
            super.paint(g);
            
            g.drawRect(10, 20, 10, 20);
        }
   }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 400);
        //Create and set up the content pane.
        Home demo = new Home();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
