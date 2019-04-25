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
    static JPanel toolsPanel, buttonsPanel;
    static Canvas drawingArea;
   // static ArrayList <String> tools = new ArrayList();
   // static String tools [] = new String[] {"Square, Arrow, Oval, Line"};
    static JButton saveButton, newButton,exitButton;
    static JLabel toolsLabel;
    static JSplitPane split1;
    static JSplitPane split2;
    static ImageIcon rectangleImage;
    static ImageIcon ovalImage;
    static ImageIcon arrowImage;
    static ImageIcon lineImage;
    static ImageIcon textboxImage;
    static JLabel rectangleLabel;
    static JLabel ovalLabel;
    static JLabel arrowLabel;
    static JLabel lineLabel;
    static JLabel textboxLabel;

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
      
        saveButton = new JButton("Save");
        newButton = new JButton("New");
        exitButton = new JButton("Exit");
        buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(newButton);
        buttonsPanel.add(exitButton); 
        buttonsPanel.setPreferredSize(new Dimension(100,30));
        buttonsPanel.setBackground(Color.white);
        
        drawingArea = new Canvas();
        drawingArea.setBackground(Color.white);
        drawingArea.setPreferredSize(new Dimension(700,500));
        drawingArea.setLayout(new BorderLayout());
        drawingArea.setBorder(BorderFactory.createTitledBorder("Create New Storyboard"));  
        
        rectangleImage = new ImageIcon(this.getClass().getResource("rectangle.png"));
        rectangleLabel = new JLabel(rectangleImage);
        ovalImage = new ImageIcon(this.getClass().getResource("oval.png"));
        ovalLabel = new JLabel(ovalImage);
        lineImage = new ImageIcon(this.getClass().getResource("line.png"));
        lineLabel = new JLabel(lineImage);
        arrowImage = new ImageIcon(this.getClass().getResource("arrow.png"));
        arrowLabel = new JLabel(arrowImage);
        textboxImage = new ImageIcon(this.getClass().getResource("textbox.png"));
        textboxLabel = new JLabel(textboxImage);
        toolsLabel = new JLabel("Tools");
        toolsLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        toolsPanel = new JPanel();
        toolsPanel.setBackground(Color.white);
        toolsPanel.setPreferredSize(new Dimension(100,500));
        toolsPanel.setLayout(new GridLayout(6,1));
        toolsPanel.add(toolsLabel);
        toolsPanel.add(rectangleLabel);
        toolsPanel.add(ovalLabel);
        toolsPanel.add(lineLabel);
        toolsPanel.add(arrowLabel);
        toolsPanel.add(textboxLabel);
        
        split1 = new JSplitPane();
        split1.setLeftComponent(drawingArea);
        split1.setRightComponent(toolsPanel);
        split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, buttonsPanel);
  
        card2.add(split2);
        card2.setBackground(Color.yellow);
       
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
