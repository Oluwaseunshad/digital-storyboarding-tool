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
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class Home implements ItemListener {
    JPanel cards; //a panel that uses CardLayout
    final static String home = "Home";
    final static String createNewSb = "Create New StoryBoard";
    final static String trackReq = "Track Requirements";
    static JPanel toolsPanel, buttonsPanel;
    //static Canvas drawingArea;
    //static PaintSurface drawingArea;
    static DrawingArea drawingArea;
   // static ArrayList <String> tools = new ArrayList();
   // static String tools [] = new String[] {"Square, Arrow, Oval, Line"};
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
    MyShapes clickedShape = null;

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
        textboxButton.addActionListener(drawingArea);
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


    class DrawingArea extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

        ArrayList shapes = new ArrayList(); // holds a list of the shapes that are displayed on the canvas
        ArrayList<Shape> theShapes = new ArrayList();
        Point startDrag, endDrag;
        DrawingArea() {
            // Constructor: set background color to white set up listeners to respond to mouse actions
            setBackground(Color.WHITE);
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paintComponent(Graphics g) {
            // In the paint method, all the shapes in ArrayList are
            // copied onto the canvas.
            super.paintComponent(g);
            int top = shapes.size();
            for (int i = 0; i < top; i++) {
                MyShapes s = (MyShapes) shapes.get(i);
                s.draw(g);
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
            for(Shape s: theShapes){
                g2.setPaint(Color.BLACK);
                g2.draw(s);
            }

            if (startDrag != null && endDrag != null && flag != 0) {
                g2.setPaint(Color.LIGHT_GRAY);
                if (flag == 2) {
                    Shape r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    g2.draw(r);
                } else if (flag == 3) {
                    Shape r = createArrowShape(startDrag, endDrag);
                    g2.draw(r);
                }
            }

        }




        public void actionPerformed(ActionEvent evt) {
            // Called to respond to action events.  The three shape-adding
            // buttons have been set up to send action events to this canvas.
            // Respond by adding the appropriate shape to the canvas.

                String command = evt.getActionCommand();
                if (command.equals("Rect"))
                    addShape(new RectShape());
                else if (command.equals("Oval"))
                    addShape(new OvalShape());
                else if (command.equals("RoundRect"))
                    addShape(new RoundRectShape());
                else if (command.equals("Line"))
                    flag = 2;
                else if (command.equals("Arrow"))
                    flag = 3;
                else if(command.equals("TextBox"))
                    flag = 1;
                else if (command.equals("Open")) {
                    doOpen();
                }
                else if (command.equals("Save")) {
                    doSave();
                }
                else if (command.equals("Exit")) {
                    System.exit(0);
                }
                else if (command.equals("Clear")) {
                    // Remove all items from the ArrayList
                    shapes.clear();
                    theShapes.clear();
                    repaint(); // Clear to current color.
                }
                else if (clickedShape != null) {
                    // Process command to delete a shape.
                    if (command.equals("Delete"))
                        shapes.remove(clickedShape);
                }

        }

        void addShape(MyShapes shape) {
            // Add the shape to the canvas, and set its size/position and color.
            // The shape is added at the top-left corner, with size 80-by-50.
            // Then redraw the canvas to show the newly added shape.
            shape.reshape(3,3,80,50);
            shapes.add(shape);
            repaint();
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
                if (!(v.get(i) instanceof MyShapes))
                    throw new IllegalArgumentException(
                            "Invalid data type in shape list.");
            shapes = v;
            setBackground((Color)backgroundColor);
        }


        // -------------------- This rest of this class implements dragging ----------------------

        MyShapes shapeBeingDragged = null;  // This is null unless a shape is being dragged.
        // A non-null value is used as a signal that dragging
        // is in progress, as well as indicating which shape
        // is being dragged.

        int prevDragX;  // During dragging, these record the x and y coordinates of the
        int prevDragY;  //    previous position of the mouse.
        final int CIRCLESIZE = 20;

        private Point lineBegin = new Point(0, 0); // point where line starts
        public void mousePressed(MouseEvent evt) {
            // User has pressed the mouse.  Find the shape that the user has clicked on, if
            // any.  If there is a shape at the position when the mouse was clicked, then
            // start dragging it.  If the user was holding down the shift key, then bring
            // the dragged shape to the front, in front of all the other shapes.
            int x = evt.getX();  // x-coordinate of point where mouse was clicked
            int y = evt.getY();  // y-coordinate of point
            if (flag == 1){
                if(evt.isMetaDown())
                    setForeground(getBackground());
                else
                    setForeground(Color.black);
                lineBegin.move(evt.getX(), evt.getY());
            }
            else if(flag == 2 || flag == 3){
                startDrag = new Point(evt.getX(), evt.getY());
                endDrag = startDrag;
                repaint();
            }
            else {
                clickedShape = null;
                for (int i = shapes.size() - 1; i >= 0; i--) {  // check shapes from front to back
                    //MyShapes s = shapes[i];
                    MyShapes s = (MyShapes) shapes.get(i);
                    if (s.containsPoint(x, y)) {
                        clickedShape = s;
                        break;
                    }
                }
                if (evt.isShiftDown()) { // s should be moved on top of all the other shapes
                    shapes.remove(clickedShape);
                    shapes.add(clickedShape);
                    repaint();
                } else {
                    shapeBeingDragged = clickedShape;
                    prevDragX = x;
                    prevDragY = y;
                }
            }
        }

        public void mouseDragged(MouseEvent evt) {
            // User has moved the mouse.  Move the dragged shape by the same amount.
            int x = evt.getX();
            int y = evt.getY();

            if(flag == 1){
                Graphics g = getGraphics();
                if(evt.isMetaDown())
                    g.fillOval(x - (CIRCLESIZE/2), y - (CIRCLESIZE/2), CIRCLESIZE, CIRCLESIZE);
                else
                    g.drawLine(lineBegin.x, lineBegin.y, x, y);
                lineBegin.move(x,y);
            }
            else if(flag == 2 || flag == 3){
                endDrag = new Point(evt.getX(), evt.getY());
                repaint();
            }
            else if (shapeBeingDragged != null && flag == 0) {
                shapeBeingDragged.moveBy(x - prevDragX, y - prevDragY);
                prevDragX = x;
                prevDragY = y;
                repaint();      // redraw canvas to show shape in new position
            }
        }

        public void mouseReleased(MouseEvent evt) {
            // User has released the mouse.  Move the dragged shape, then set
            // shapeBeingDragged to null to indicate that dragging is over.
            int x = evt.getX();
            int y = evt.getY();
            if(flag == 0 ){
                if (shapeBeingDragged != null) {
                    shapeBeingDragged.moveBy(x - prevDragX, y - prevDragY);
                    //shapes.remove(shapeBeingDragged);
                    repaint();
                }
                shapeBeingDragged = null;
            }
            else if(flag == 1){
                Shape r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                theShapes.add(r);
                startDrag = null;
                endDrag = null;
                repaint();
            }
            else if(flag == 2){
                Shape r = createArrowShape(startDrag, endDrag);
                theShapes.add(r);
                startDrag = null;
                endDrag = null;
                repaint();
            }
        }

        public void mouseEntered(MouseEvent evt) { }   // Other methods required for MouseListener and
        public void mouseExited(MouseEvent evt) { }    //              MouseMotionListener interfaces.
        public void mouseMoved(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }

        private Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
            return new Line2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }

        public Shape createArrowShape(Point fromPt, Point toPt) {
            Polygon arrowPolygon = new Polygon();
            arrowPolygon.addPoint(-6,1);
            arrowPolygon.addPoint(3,1);
            arrowPolygon.addPoint(3,3);
            arrowPolygon.addPoint(6,0);
            arrowPolygon.addPoint(3,-3);
            arrowPolygon.addPoint(3,-1);
            arrowPolygon.addPoint(-6,-1);


            Point midPoint = midpoint(fromPt, toPt);

            double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

            AffineTransform transform = new AffineTransform();
            transform.translate(midPoint.x, midPoint.y);
            double ptDistance = fromPt.distance(toPt);
            double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
            transform.scale(scale, scale);
            transform.rotate(rotate);

            return transform.createTransformedShape(arrowPolygon);
        }

        private Point midpoint(Point p1, Point p2) {
            return new Point((int)((p1.x + p2.x)/2.0),
                    (int)((p1.y + p2.y)/2.0));
        }

    }  // end class DrawingArea



    // ------- Nested class definitions for the abstract MyShapes class and three -----
    // -------------------- concrete subclasses of MyShapes. --------------------------


    static abstract class MyShapes {

        // A class representing shapes that can be displayed on a ShapeCanvas.
        // The subclasses of this class represent particular types of shapes.
        // When a shape is first constructed, it has height and width zero
        // and a default color of white.

        int left, top;      // Position of top left corner of rectangle that bounds this shape.
        int width, height;  // Size of the bounding rectangle.
        Color color = Color.white;  // Color of this shape.

        void reshape(int left, int top, int width, int height) {
            // Set the position and size of this shape.
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }

        void moveBy(int dx, int dy) {
            // Move the shape by dx pixels horizontally and dy pixels vertically
            // (by changing the position of the top-left corner of the shape).
            left += dx;
            top += dy;
        }


        boolean containsPoint(int x, int y) {
            // Check whether the shape contains the point (x,y).
            // By default, this just checks whether (x,y) is inside the
            // rectangle that bounds the shape.  This method should be
            // overridden by a subclass if the default behavior is not
            // appropriate for the subclass.
            if (x >= left && x < left+width && y >= top && y < top+height)
                return true;
            else
                return false;
        }

        abstract void draw(Graphics g);
        // Draw the shape in the graphics context g.
        // This must be overriden in any concrete subclass.

    }  // end of class MyShapes



    static class RectShape extends MyShapes {
        // This class represents rectangle shapes.
        void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(left,top,width,height);
            g.setColor(Color.black);
            g.drawRect(left,top,width,height);
        }
    }


    static class OvalShape extends MyShapes {
        // This class represents oval shapes.
        void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(left,top,width,height);
            g.setColor(Color.black);
            g.drawOval(left,top,width,height);
        }
        boolean containsPoint(int x, int y) {
            // Check whether (x,y) is inside this oval, using the
            // mathematical equation of an ellipse.  This replaces the
            // definition of containsPoint that was inherited from the
            // MyShapes class.
            double rx = width/2.0;   // horizontal radius of ellipse
            double ry = height/2.0;  // vertical radius of ellipse
            double cx = left + rx;   // x-coord of center of ellipse
            double cy = top + ry;    // y-coord of center of ellipse
            if ( (ry*(x-cx))*(ry*(x-cx)) + (rx*(y-cy))*(rx*(y-cy)) <= rx*rx*ry*ry )
                return true;
            else
                return false;
        }
    }


    static class RoundRectShape extends MyShapes {
        // This class represents rectangle shapes with rounded corners.
        // (Note that it uses the inherited version of the
        // containsPoint(x,y) method, even though that is not perfectly
        // accurate when (x,y) is near one of the corners.)
        void draw(Graphics g) {
            g.setColor(color);
            g.fillRoundRect(left,top,width,height,width/3,height/3);
            g.setColor(Color.black);
            g.drawRoundRect(left,top,width,height,width/3,height/3);
        }
    }

 /*   static class ArrowShape extends MyShapes {
        Polygon arrowHead = new Polygon();
        AffineTransform tx = new AffineTransform();
        Line2D.Double line = new Line2D.Double(0,0,100,100);
        void draw(Graphics g2d) {
            arrowHead.addPoint(0, 5);
            arrowHead.addPoint( -5, -5);
            arrowHead.addPoint( 5,-5);
            tx.setToIdentity();
            double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
            tx.translate(line.x2, line.y2);
            tx.rotate((angle-Math.PI/2d));

            Graphics2D g = (Graphics2D) g2d.create();
            g.setTransform(tx);
            g.fill(arrowHead);
            g.dispose();
        }

    }

    static class LineShape extends MyShapes {
        // This class represents rectangle shapes.
        void draw(Graphics g) {
            g.setColor(color);
            //g.fillRect(left,top,width,height);
            g.setColor(Color.black);
            g.drawLine(left,top,width,height);
        }
    }
    **/

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
