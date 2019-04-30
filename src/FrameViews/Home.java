package FrameViews;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;


public class Home implements ItemListener {
    JPanel cards; //a panel that uses CardLayout
    final static String home = "Home";
    final static String createNewSb = "Create New StoryBoard";
    final static String trackReq = "Track Requirements";
    ListTransferHandler lh;
    DefaultListModel list1Model = new DefaultListModel();
    DefaultListModel list2Model = new DefaultListModel();
    DefaultListModel list3Model = new DefaultListModel();
    static JList list1, list2, list3;
    static JPanel pan1, pan2, pan3;
    static ArrayList <String> toDoElements = new ArrayList();
    static ArrayList <String> inProgressElements = new ArrayList();
    static ArrayList <String> doneElements = new ArrayList();
    static Popup listOnePops;
    static Popup listTwoPops;
    static Popup listThreePops;
    static JPanel listOnePopUpPanel;
    static JPanel listTwoPopUpPanel;
    static JPanel listThreePopUpPanel;
    PopupFactory popupFactory;
    static JLabel popUpLabel;
    static JLabel listTwoPopUpLabel;
    static JLabel listThreePopUpLabel;


    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = {home, createNewSb, trackReq};
        lh = new ListTransferHandler();
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        //Create the "cards".
        JPanel card1 = new JPanel();
        card1.add(new JTextField("Homepage", 20));

        JPanel card2 = new JPanel();
        card2.add(new JTextField("Create New StoryBoard", 20));

        JPanel card3 = new JPanel();
        for (int i = 0; i < toDoElements.size(); i++) {
            list1Model.addElement(toDoElements.get(i));
        }
        for (int i = 0; i < inProgressElements.size(); i++) {
            list2Model.addElement(inProgressElements.get(i));
        }
        for (int i = 0; i < doneElements.size(); i++) {
            list3Model.addElement(doneElements.get(i));
        }
        popupFactory = new PopupFactory();
        popUpLabel = new JLabel("");
        listTwoPopUpLabel = new JLabel("");
        listThreePopUpLabel = new JLabel("");
        // create a new button
        JButton listOneButton = new JButton("OK");
        listOneButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                String d = e.getActionCommand();
                // if ok buton is pressed hide the popup
                if (d.equals("OK")) {
                    listOnePops.hide();

                    // create a popup
                    listOnePops = popupFactory.getPopup(list1, listOnePopUpPanel, 180, 100);
                }
            }
        });
        JButton listTwoButton = new JButton("OK");
        listTwoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                String d = e.getActionCommand();
                // if ok buton is pressed hide the popup
                if (d.equals("OK")) {
                    listTwoPops.hide();

                    // create a popup
                    listTwoPops = popupFactory.getPopup(list2, listTwoPopUpPanel, 180, 100);
                }
            }
        });
        JButton listThreeButton = new JButton("OK");
        listThreeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                String d = e.getActionCommand();
                // if ok buton is pressed hide the popup
                if (d.equals("OK")) {
                    listThreePops.hide();

                    // create a popup
                    listThreePops = popupFactory.getPopup(list3, listThreePopUpPanel, 180, 100);
                }
            }
        });
        listOnePopUpPanel = new JPanel();
        listOnePopUpPanel.add(popUpLabel);
        listOnePopUpPanel.add(listOneButton);
        listOnePopUpPanel.setLayout(new GridLayout(2, 1));
        list1 = new JList(list1Model);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp1 = new JScrollPane(list1);
        sp1.setPreferredSize(new Dimension(400, 400));
        list1.setDragEnabled(true);
        list1.setTransferHandler(lh);
        list1.setDropMode(DropMode.ON_OR_INSERT);
        listOnePops = popupFactory.getPopup(list1, listOnePopUpPanel, 180, 100);
        pan1 = new JPanel(new BorderLayout());
        pan1.add(sp1, BorderLayout.CENTER);
        pan1.setBorder(BorderFactory.createTitledBorder("To-Do"));

        listTwoPopUpPanel = new JPanel();
        listTwoPopUpPanel.add(listTwoPopUpLabel);
        listTwoPopUpPanel.add(listTwoButton);
        listTwoPopUpPanel.setLayout(new GridLayout(2, 1));
        list2 = new JList(list2Model);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setDragEnabled(true);
        JScrollPane sp2 = new JScrollPane(list2);
        sp2.setPreferredSize(new Dimension(400, 400));
        list2.setTransferHandler(lh);
        list2.setDropMode(DropMode.INSERT);
        listTwoPops = popupFactory.getPopup(list2, listTwoPopUpPanel, 180, 100);
        DefaultListModel model2 = (DefaultListModel) list2.getModel();
        model2.addListDataListener(new ListTwoDataListener());
        pan2 = new JPanel(new BorderLayout());
        pan2.add(sp2, BorderLayout.CENTER);
        pan2.setBorder(BorderFactory.createTitledBorder("In Progress"));

        listThreePopUpPanel = new JPanel();
        listThreePopUpPanel.add(listThreePopUpLabel);
        listThreePopUpPanel.add(listThreeButton);
        listThreePopUpPanel.setLayout(new GridLayout(2, 1));
        list3 = new JList(list3Model);
        list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list3.setDragEnabled(true);
        JScrollPane sp3 = new JScrollPane(list3);
        sp3.setPreferredSize(new Dimension(400, 400));
        list3.setTransferHandler(lh);
        list3.setDropMode(DropMode.INSERT);
        listThreePops = popupFactory.getPopup(list3, listThreePopUpPanel, 180, 100);
        DefaultListModel model3= (DefaultListModel) list3.getModel();
        model3.addListDataListener(new ListThreeDataListener());
        pan3 = new JPanel(new BorderLayout());
        pan3.add(sp3, BorderLayout.CENTER);
        pan3.setBorder(BorderFactory.createTitledBorder("Done"));

        list1.setCellRenderer(getRenderer());
        list2.setCellRenderer(getRenderer());
        list3.setCellRenderer(getRenderer());
        card3.add(pan1);
        card3.add(pan2);
        card3.add(pan3);


        list1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                Connection connection = null;
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection=DriverManager.getConnection(
                            "jdbc:mysql://localhost/mydb?user=cs6640&password=12345678&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                } catch(Exception e){ System.out.println(e);}

                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("index: " + index);
                    String s = (String) list.getSelectedValue();
                    System.out.println("Value Selected: " + s);
                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT src FROM requirements WHERE name = ?");
                        statement.setString(1, s);
                        ResultSet rs = statement.executeQuery();
                        while (rs.next())
                            popUpLabel.setText(rs.getString(1));
                        listOnePops.show();
                        connection.close();
                    }catch(Exception e){ System.out.println(e);}

                } else if (evt.getClickCount() == 3) {

                    // Triple-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("index: " + index);
                }
            }
        });

        list2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                Connection connection = null;
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection=DriverManager.getConnection(
                            "jdbc:mysql://localhost/mydb?user=cs6640&password=12345678&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                } catch(Exception e){ System.out.println(e);}

                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("index: " + index);
                    String s = (String) list.getSelectedValue();
                    System.out.println("Value Selected: " + s);
                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT src FROM requirements WHERE name = ?");
                        statement.setString(1, s);
                        ResultSet rs = statement.executeQuery();
                        while (rs.next())
                            listTwoPopUpLabel.setText(rs.getString(1));
                        listTwoPops.show();
                        connection.close();
                    }catch(Exception e){ System.out.println(e);}

                } else if (evt.getClickCount() == 3) {

                    // Triple-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("index: " + index);
                }
            }
        });

        list3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                Connection connection = null;
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection=DriverManager.getConnection(
                            "jdbc:mysql://localhost/mydb?user=cs6640&password=12345678&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                } catch(Exception e){ System.out.println(e);}

                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("index: " + index);
                    String s = (String) list.getSelectedValue();
                    System.out.println("Value Selected: " + s);
                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT src FROM requirements WHERE name = ?");
                        statement.setString(1, s);
                        ResultSet rs = statement.executeQuery();
                        while (rs.next())
                            listThreePopUpLabel.setText(rs.getString(1));
                        listThreePops.show();
                        connection.close();
                    }catch(Exception e){ System.out.println(e);}

                } else if (evt.getClickCount() == 3) {

                    // Triple-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    System.out.println("index: " + index);
                }
            }
        });

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, home);
        cards.add(card2, createNewSb);
        cards.add(card3, trackReq);
        pane.setPreferredSize(new Dimension(500, 200));
        pane.add(comboBoxPane, BorderLayout.WEST);
        pane.add(cards, BorderLayout.CENTER);


    }

    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                return listCellRendererComponent;
            }
        };
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
        //frame.setSize(500, 400);
        //Create and set up the content pane.
        Home demo = new Home();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection(
                    "jdbc:mysql://localhost/mydb?user=cs6640&password=12345678&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery("select name from requirements where status='to-do'");
            PreparedStatement ps = connection.prepareStatement("select name from requirements where status='inProgress'");
            PreparedStatement ps2 = connection.prepareStatement("select name from requirements where status='done'");
            ResultSet rs2=ps.executeQuery();
            ResultSet rs3=ps2.executeQuery();
            while(rs.next())
                toDoElements.add(rs.getString(1));
            while(rs2.next())
                inProgressElements.add(rs2.getString(1));
            while(rs3.next())
                doneElements.add(rs3.getString(1));
            connection.close();
           /* PreparedStatement ps = connection.prepareStatement("CREATE DATABASE mydb");
            int result = ps.executeUpdate();
            System.out.print(result);*/
        }catch(Exception e){ System.out.println(e);}
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


    class ListTwoDataListener implements ListDataListener {
        String addedContenttoList2 = "";
        public void intervalAdded(ListDataEvent evt) {
            DefaultListModel model = (DefaultListModel) evt.getSource();
            int start = evt.getIndex0();
            int end = evt.getIndex1();
            int count = end - start + 1;
            Object item = null;

            for (int i = start; i <= end; i++) {
                item = model.getElementAt(i);
            }
            System.out.println("intervalAdded: " + start +
                    ", " + end);
            addedContenttoList2 = item.toString();
            System.out.println(addedContenttoList2);
            //make changes to database from here
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection(
                        "jdbc:mysql://localhost/mydb?user=cs6640&password=12345678&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                PreparedStatement statement = connection.prepareStatement("UPDATE requirements SET status='inProgress' WHERE name = ?");
                statement.setString(1, addedContenttoList2);
                statement.executeUpdate();
                connection.close();
           /* PreparedStatement ps = connection.prepareStatement("CREATE DATABASE mydb");
            int result = ps.executeUpdate();
            System.out.print(result);*/
            }catch(Exception e){ System.out.println(e);}
        }

        public void intervalRemoved(ListDataEvent evt) {
            int start = evt.getIndex0();
            int end = evt.getIndex1();
            int count = end - start + 1;
            System.out.println("intervalRemoved: " + start +
                    ", " + end);
        }

        public void contentsChanged(ListDataEvent evt) {
            DefaultListModel model = (DefaultListModel) evt.getSource();
            int start = evt.getIndex0();
            int end = evt.getIndex1();
            int count = end - start + 1;
            for (int i = start; i <= end; i++) {
                Object item = model.getElementAt(i);
            }
            System.out.println("contentsChanged: " + start +
                    ", " + end);
        }
    }

    class ListThreeDataListener implements ListDataListener {
        String addedContenttoList3 = "";
        public void intervalAdded(ListDataEvent evt) {
            DefaultListModel model = (DefaultListModel) evt.getSource();
            int start = evt.getIndex0();
            int end = evt.getIndex1();
            int count = end - start + 1;
            Object item = null;

            for (int i = start; i <= end; i++) {
                item = model.getElementAt(i);
            }
            System.out.println("intervalAdded: " + start +
                    ", " + end);
            addedContenttoList3 = item.toString();
            System.out.println(addedContenttoList3);
            //make changes to database from here
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection=DriverManager.getConnection(
                        "jdbc:mysql://localhost/mydb?user=cs6640&password=12345678&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                PreparedStatement statement = connection.prepareStatement("UPDATE requirements SET status='Done' WHERE name = ?");
                statement.setString(1, addedContenttoList3);
                statement.executeUpdate();
                connection.close();
           /* PreparedStatement ps = connection.prepareStatement("CREATE DATABASE mydb");
            int result = ps.executeUpdate();
            System.out.print(result);*/
            }catch(Exception e){ System.out.println(e);}
        }

        public void intervalRemoved(ListDataEvent evt) {
            int start = evt.getIndex0();
            int end = evt.getIndex1();
            int count = end - start + 1;
            System.out.println("intervalRemoved: " + start +
                    ", " + end);
        }

        public void contentsChanged(ListDataEvent evt) {
            DefaultListModel model = (DefaultListModel) evt.getSource();
            int start = evt.getIndex0();
            int end = evt.getIndex1();
            int count = end - start + 1;
            for (int i = start; i <= end; i++) {
                Object item = model.getElementAt(i);
            }
            System.out.println("contentsChanged: " + start +
                    ", " + end);
        }
    }
}