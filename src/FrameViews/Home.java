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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class Home extends JFrame implements ItemListener {
    JPanel cards; //a panel that uses CardLayout
    final static String home = "Home";
    final static String createNewSb = "Create New StoryBoard";
    final static String trackReq = "Track Requirements";
    static JPanel toolsPanel, buttonsPanel;
    static DrawingArea drawingArea;
    static JButton saveButton,exitButton, openButton, delButton,clrButton;
    static JLabel toolsLabel;
    static JSplitPane split1;
    static JSplitPane split2;
    static ImageIcon rectangleImage;
    static ImageIcon ovalImage;
    static ImageIcon arrowImage;
    static ImageIcon lineImage;
    static ImageIcon textboxImage;
    static JButton rectangleButton;
    static JButton ovalButton;
    static JButton arrowButton;
    static JButton lineButton;
    static JButton textboxButton;
    static int flag = 0;


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

        drawingArea = new DrawingArea();
        drawingArea.setBackground(Color.white);
        drawingArea.setPreferredSize(new Dimension(700,500));
        drawingArea.setLayout(new BorderLayout());
        drawingArea.setBorder(BorderFactory.createTitledBorder("Create New Storyboard"));

        saveButton = new JButton("Save");
        saveButton.addActionListener(drawingArea);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(drawingArea);
        openButton = new JButton("Open");
        openButton.addActionListener(drawingArea);
        clrButton = new JButton("Clear");
        clrButton.addActionListener(drawingArea);
        delButton = new JButton("Delete");
        delButton.addActionListener(drawingArea);
        buttonsPanel = new JPanel();
        buttonsPanel.add(openButton);
        buttonsPanel.add(clrButton);
        buttonsPanel.add(delButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(exitButton); 
        buttonsPanel.setPreferredSize(new Dimension(100,30));
        buttonsPanel.setBackground(Color.white);

        rectangleImage = createImageIcon("/res/rectangle.png");
        rectangleButton = new JButton(rectangleImage);
        rectangleButton.setActionCommand("Rect");
        rectangleButton.addActionListener(drawingArea);
        ovalImage = createImageIcon("/res/oval.png");
        ovalButton = new JButton(ovalImage);
        ovalButton.setActionCommand("Oval");
        ovalButton.addActionListener(drawingArea);
        lineImage = createImageIcon("/res/line.png");
        lineButton = new JButton(lineImage);
        lineButton.setActionCommand("Line");
        lineButton.addActionListener(drawingArea);
        arrowImage = createImageIcon("/res/arrow.png");
        arrowButton = new JButton(arrowImage);
        arrowButton.setActionCommand("Arrow");
        arrowButton.addActionListener(drawingArea);
        textboxImage = createImageIcon("/res/textbox.png");
        textboxButton = new JButton(textboxImage);
        textboxButton.setActionCommand("TextBox");
        //textboxButton.addActionListener(drawingArea);
        toolsLabel = new JLabel("Tools");
        toolsLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        toolsPanel = new JPanel();
        toolsPanel.setBackground(Color.white);
        toolsPanel.setPreferredSize(new Dimension(100,500));
        toolsPanel.setLayout(new GridLayout(6,1));
        toolsPanel.add(toolsLabel);
        toolsPanel.add(rectangleButton);
        toolsPanel.add(ovalButton);
        toolsPanel.add(lineButton);
        toolsPanel.add(arrowButton);
        toolsPanel.add(textboxButton);

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

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    class DrawingArea extends JComponent implements ActionListener {
        ArrayList<Shape> shapes = new ArrayList();

        Point startDrag, endDrag;
        Shape r = null;
        public DrawingArea() {
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (flag == 4){
                        r = new Line2D.Double(e.getPoint(), e.getPoint());
                        shapes.add(r);
                    }
                    else {
                        startDrag = new Point(e.getX(), e.getY());
                        endDrag = startDrag;
                    }
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    if(flag == 1)
                        r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    else if(flag == 2)
                        r = makeOval(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    else if(flag == 3)
                        r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    else if(flag == 4){
                        Line2D shape = (Line2D) r;
                        double angle = Math.atan2(shape.getY2() - shape.getY1(), shape.getX2() - shape.getX1());
                        int arrowHeight = 9;
                        int halfArrowWidth = 5;
                        Point2D end = shape.getP2();
                        Point2D aroBase = new Point2D.Double(
                                shape.getX2() - arrowHeight*Math.cos(angle),
                                shape.getY2() - arrowHeight*Math.sin(angle)
                        );
                        Point2D end1 = new Point2D.Double(
                                aroBase.getX()-halfArrowWidth*Math.cos(angle-Math.PI/2),
                                aroBase.getY()-halfArrowWidth*Math.sin(angle-Math.PI/2));
                        //locate one of the points, use angle-pi/2 to get the
                        //angle perpendicular to the original line(which was 'angle')

                        Point2D end2 = new Point2D.Double(
                                aroBase.getX()+halfArrowWidth*Math.cos(angle-Math.PI/2),
                                aroBase.getY()+halfArrowWidth*Math.sin(angle-Math.PI/2));
                        //same thing but with other side
                        shapes.add(new Line2D.Double(end1, end2));
                        shapes.add(new Line2D.Double(end, end2));
                        shapes.add(new Line2D.Double(end, end1));

                    }
                   if (r != null)
                       shapes.add(r);
                   startDrag = null;
                   endDrag = null;
                   r = null;
                   repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if(flag == 4){
                        Line2D shape = (Line2D)r;
                        shape.setLine(shape.getP1(), e.getPoint());
                    }
                    else {
                        endDrag = new Point(e.getX(), e.getY());
                    }
                    repaint();
                }
            });
        }

        public void paint(Graphics g) {
            Graphics2D g2= (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape r = null;
            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

            for (Shape s : shapes) {
                g2.setPaint(Color.BLACK);
                g2.draw(s);
            }

            if (startDrag != null && endDrag != null) {
                g2.setPaint(Color.LIGHT_GRAY);

                if (flag == 1)
                    r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                else if (flag == 2)
                    r = makeOval(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                else if (flag == 3)
                    r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                if (r != null)
                    g2.draw(r);
            }
        }

        private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
            return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }

        private Line2D.Double makeLine(int x1, int y1, int x2, int y2) {
            return new Line2D.Double(x1, y1, x2, y2);
        }

        private Ellipse2D.Float makeOval(int x1, int y1, int x2, int y2) {
            return new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }





        public void actionPerformed(ActionEvent evt) {
            // Called to respond to action events.  The three shape-adding
            // buttons have been set up to send action events to this canvas.
            // Respond by adding the appropriate shape to the canvas.
            String command = evt.getActionCommand();
            if (command.equals("Rect")) {
                flag = 1;
            } else if (command.equals("Oval")) {
                flag = 2;
            } else if (command.equals("Line")) {
                flag = 3;
            } else if (command.equals("Arrow"))
                flag = 4;
            /*else if(command.equals("TextBox"))
                //flag = 1;*/
            else if (command.equals("Open")) {
                flag = 0;
                doOpen();
            }
            else if (command.equals("Save")) {
                flag = 0;
                doSave();
            }
            else if (command.equals("Exit")) {
                System.exit(0);
            }
            else if (command.equals("Clear")) {
                // Remove all items from the ArrayList
                flag = 0;
                shapes.clear();
                repaint(); // Clear to current color.
            }
        }

        void doOpen() {
            // Carry out the Open command by letting the user specify
            // the file to be opened and trying to read an ArrayList
            // of Shapes from the file.  If no error occurs, the
            // shapes replace the current contents of the window.
            // The background color is also saved and restored.
            File file;  // The file that the user wants to open.
            JFileChooser fd; // A file dialog that let's the user specify the file.
            fd = new JFileChooser("."); // Open on current directory.
            fd.setDialogTitle("Open File...");
            int action = fd.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                // User canceled the dialog, or an error occurred.
                return;
            }
            file = fd.getSelectedFile();
            Object obj1;  // Object to be read from file.  Should be a color.
            Object obj2;  // Second object.  Should be an ArrayList of shapes
            try {
                ObjectInputStream in =
                        new ObjectInputStream(new FileInputStream(file));
                obj1 = in.readObject();
                obj2 = in.readObject();
                in.close();
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error while trying to read the file:\n" + e.getMessage());
                return;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Unexpected Data type found in file:\n" + e.getMessage());
                return;
            }
            try {
                setShapes(obj1,obj2);
            }
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this,
                        "File did not contain legal data for this program.");
            }
        } // end doOpen()

        void doSave() {
            // Carry out the Save command by letting the user specify
            // an output file and writing the background color and
            // the ArrayList of shapes to the file.
            File file;  // The file that the user wants to save.
            JFileChooser fd; // A file dialog that let's the user specify the file.
            fd = new JFileChooser(".");  // Open on current directory.
            fd.setDialogTitle("Save As File...");
            int action = fd.showSaveDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                // User has canceled, or an error occurred.
                return;
            }
            file = fd.getSelectedFile();
            if (file.exists()) {
                // If file already exists, ask before replacing it.
                action = JOptionPane.showConfirmDialog(this,
                        "Replace existing file?");
                if (action != JOptionPane.YES_OPTION)
                    return;
            }
            try {
                ObjectOutputStream out =
                        new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(getBackground());
                out.writeObject(shapes);
                out.close();
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error while trying to write the file:\n" + e.getMessage());
            }
        } // end doSave()

        private void setShapes(Object backgroundColor,Object newShapes)
                throws IllegalArgumentException {
            // Just to be called by doOpen().  First parameter should be
            // a color, which becomes the background color.  Second should
            // be an ArrayList of shapes, which replaces the current list.
            // Throws IllegalArgumentException if objects are not  of
            // of the correct types.
            if (backgroundColor == null || !(backgroundColor instanceof Color))
                throw new IllegalArgumentException(
                        "Invalid data types in file.");
            if (newShapes == null || !(newShapes instanceof ArrayList))
                throw new IllegalArgumentException(
                        "Invalid data type.  Expecting list of shapes.");
            ArrayList v = (ArrayList)newShapes;
            for (int i = 0; i < v.size(); i ++)
                if (!(v.get(i) instanceof Shape))
                    throw new IllegalArgumentException(
                            "Invalid data type in shape list.");
            shapes = v;
            setBackground((Color)backgroundColor);
        }
    }

        public void itemStateChanged(ItemEvent evt) {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, (String) evt.getItem());
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
