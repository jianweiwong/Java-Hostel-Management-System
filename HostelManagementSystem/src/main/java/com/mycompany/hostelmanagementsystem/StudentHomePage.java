/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.hostelmanagementsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class StudentHomePage extends javax.swing.JFrame {

    String RoomNumber;

    String TP;
    String PW;
    int totalARoom;
    int totalApplication;
    String ActiveRoomNumber;
    String ActiveRefNo;
    String selectedRoom;
    String selectedrefNo;

    /**
     * Creates new form StudentHomePage
     */
    public StudentHomePage() {
        initComponents();
        LoadCredential();
        LoadTable();
        LoadMyApplicationTable();
        LoadMyRoom();
    }

    private void LoadCredential() {
        this.TP = "TP000002";
        this.PW = "2";
        String[] accountDetails = Student.retrieveData(TP, PW);
        System.out.println("im here" + accountDetails[2]);

        //firstName_txt.setText(accountDetails[0]);
        String stdFirstName = "", stdLastName = "", stdGender = "", stdDOB = "", stdEmail = "", stdPhoneNo = "", stdAddress = "", stdIcNo = "", stdTpNo = "", stdPassword = "";
        String[] vName = {stdFirstName, stdLastName, stdDOB, stdGender, stdEmail, stdPhoneNo, stdAddress, stdIcNo, stdTpNo, stdPassword};
        JLabel[] lName = {firstName_txt, lastName_txt, DOB_txt, Gender_txt, Email_txt, phNo_txt, addr_txt, icNo_txt, tpNo_txt};

        for (int i = 0; i < accountDetails.length - 2; i++) {
            vName[i] = accountDetails[i];
            (lName[i]).setText(vName[i]);
            System.out.println("LName" + i + "ready: " + vName[i]);

        }

    }

    private void LoadCredential(String TP, String PW) {

        this.TP = TP;
        this.PW = PW;
        System.out.println("1");
        Student newstudent = new Student(TP, PW);
        String[] accountDetails = newstudent.retrieveData(TP, PW);
        System.out.println("CAMEBACK" + accountDetails[0]);
        System.out.println("2");

        initComponents();
        //firstName_txt.setText(accountDetails[0]);
        String stdFirstName = "", stdLastName = "", stdDOB = "", stdGender = "",
                stdEmail = "", stdPhoneNo = "", stdAddress = "", stdIcNo = "", stdTpNo = "", stdPassword = "";
        String[] vName = {stdFirstName, stdLastName, stdDOB, stdGender, stdEmail,
            stdPhoneNo, stdAddress, stdIcNo, stdTpNo, stdPassword};
        JLabel[] lName = {firstName_txt, lastName_txt, DOB_txt, Gender_txt,
            Email_txt, phNo_txt, addr_txt, icNo_txt, tpNo_txt};

        for (int i = 0; i < accountDetails.length - 2; i++) {
            vName[i] = accountDetails[i];
            (lName[i]).setText(vName[i]);
        }
        tpNo_txt.setText(TP);
        System.out.println("TP");
        newstudent.setFirstName(stdFirstName);
        newstudent.setLastName(stdLastName);
        newstudent.setDOB(stdDOB);
        newstudent.setGender(stdGender);
        newstudent.setEmail(stdEmail);
        newstudent.setPhoneNo(stdPhoneNo);
        newstudent.setAddress(stdAddress);
        newstudent.setIcNo(stdIcNo);

    }

    private void LoadTable() {
        totalARoom = 0;
        DefaultTableModel model = (DefaultTableModel) jt.getModel();
        model.setRowCount(0);
        //String[] columns = {"Type", "Room Number", "Price", "Description"};
        String[] columns = {"Room Type", "Room Number", "Price"};
        model.setColumnIdentifiers(columns);
        ArrayList<String> emptyRoomsDetails = new ArrayList<>();
        emptyRoomsDetails = room.RoomDetails("all");
        /* 
        for (String roomDetails : emptyRoomsDetails) { //load all column into table
            String[] rowData = roomDetails.split("\\|");
            model.addRow(rowData);
        }
         */
        for (String roomDetails : emptyRoomsDetails) {
            String[] rowData = roomDetails.split("\\|");
            // Extracting only the required columns (Room Type, Room Number, and Price)
            String[] filteredRowData = {rowData[0], rowData[1], rowData[2]};
            model.addRow(filteredRowData);
            totalARoom += 1;
        }

        jt.getTableHeader().setReorderingAllowed(false); // Disable column dragging
        jt.setDefaultEditor(Object.class, null); // Make cells non-editable
        jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        totalRoom_txt.setText(String.valueOf(totalARoom));
        //CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
        //jt.setDefaultRenderer(Object.class, cellRenderer);
    }

    private void LoadMyApplicationTable() {
        ActiveRoomNumber = null;
        ActiveRoom_txt.setText("No Active Room");
        totalApplication = 0;
        DefaultTableModel model = (DefaultTableModel) jtApp.getModel();
        model.setRowCount(0); // this is to restart the table when this method is called.
        //String[] columns = {"Type", "Room Number", "Price", "Description"};
        String[] columns = {"Room", "Start", "End", "Duration", "Price(RM)", "Status", "ref No"};
        model.setColumnIdentifiers(columns);
        ArrayList<String> myapplication = new ArrayList<>();
        myapplication = application.readMyApplication(TP, "All");

        /* 
        for (String roomDetails : emptyRoomsDetails) { //load all column into table
            String[] rowData = roomDetails.split("\\|");
            model.addRow(rowData);
        }
         */
        for (String roomDetails : myapplication) {
            String[] rowData = roomDetails.split("\\|");
            // Extracting only the required columns (Room Type, Room Number, and Price)
            String[] filteredRowData = {rowData[1], rowData[2], rowData[3], rowData[4], rowData[5], rowData[6], rowData[7]};
            if (rowData[6].equals("Accepted")) {
                ActiveRoomNumber = rowData[1];
                ActiveRoom_txt.setText(ActiveRoomNumber);
            }
            model.addRow(filteredRowData);
            totalApplication += 1;
        }

        jtApp.getTableHeader().setReorderingAllowed(false); // Disable column dragging
        jtApp.setDefaultEditor(Object.class, null); // Make cells non-editable
        jtApp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        totalApplication_txt.setText(String.valueOf(totalApplication));
        //CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
        //jt.setDefaultRenderer(Object.class, cellRenderer);
        dlt_btn.setVisible(false);
    }

    private void LoadMyRoom() {
        if (ActiveRoomNumber != null) {
            MyRoomTitle_txt.setText("ACTIVE ROOM :                " + ActiveRoomNumber);
            cdApplication_btn.setVisible(false);
            noroomLabel_txt.setVisible(false);
            cdRoom_btn.setVisible(false);
            noroomLabel2_txt.setVisible(false);
            MyRoomLabel.setVisible(true);
            MyRoomLabel1.setVisible(true);
            MyRoomLabel2.setVisible(true);
            MyRoomLabel3.setVisible(true);
            MyRoomLabel4.setVisible(true);
            MyRoomStatus_txt.setVisible(true);
            MyRoomDuration_txt.setVisible(true);
            MyRefNo_txt.setVisible(true);
            MyRoomCO_txt.setVisible(true);
            MyRoomCI_txt.setVisible(true);
            MyRoomIconLabel.setVisible(true);
            MyRoomCO_btn.setVisible(true);
            MyRefNo_txt.setVisible(true);

            ArrayList<String> MyAcceptedApplication = new ArrayList<>();
            MyAcceptedApplication = application.readMyApplication(TP, "Accepeted");
            String ActiveData = MyAcceptedApplication.get(0); // Get the first (and only) element from the ArrayList
            String[] Activefield = ActiveData.split("\\|");
            if (Activefield[6].equals("Accepted")) {
                MyRoomStatus_txt.setText("Active ");
            }

            MyRoomDuration_txt.setText(Activefield[4]);
            MyRefNo_txt.setText(Activefield[7]);
            ActiveRefNo = Activefield[7];
            MyRoomCI_txt.setText(Activefield[2]);
            MyRoomCO_txt.setText(Activefield[3]);

        } else {
            MyRoomTitle_txt.setText("NO ACTIVE ROOM FOUND");
            cdApplication_btn.setVisible(true);
            noroomLabel_txt.setVisible(true);
            cdRoom_btn.setVisible(true);
            noroomLabel2_txt.setVisible(true);
            MyRoomLabel.setVisible(false);
            MyRoomLabel1.setVisible(false);
            MyRoomLabel2.setVisible(false);
            MyRoomLabel3.setVisible(false);
            MyRoomLabel4.setVisible(false);
            MyRoomStatus_txt.setVisible(false);
            MyRoomDuration_txt.setVisible(false);
            MyRefNo_txt.setVisible(false);
            MyRoomCO_txt.setVisible(false);
            MyRoomIconLabel.setVisible(false);
            MyRoomCO_btn.setVisible(false);
            MyRoomCI_txt.setVisible(false);

            MyRefNo_txt.setVisible(false);

        }

    }

    public StudentHomePage(String TP, String PW) {
        this.TP = TP;
        this.PW = PW;
        LoadCredential(TP, PW);
        LoadTable();
        LoadMyApplicationTable();
        LoadMyRoom();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        MyHomePage = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MyProfileLogOut_btn = new javax.swing.JButton();
        MODIFY = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        firstName_txt = new javax.swing.JLabel();
        icNo_txt = new javax.swing.JLabel();
        tpNo_txt = new javax.swing.JLabel();
        lastName_txt = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        a = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        phNo_txt = new javax.swing.JLabel();
        DOB_txt = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Email_txt = new javax.swing.JLabel();
        Gender_txt = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        addr_txt = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        RoomLogOut_btn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        firstName_txt1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        totalRoom_txt = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        icNo_txt1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jt = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        RoomNo = new javax.swing.JLabel();
        icNo_txt4 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        RoomPrice = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        RoomType = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        ApplicationRecordLogOut_btn = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        firstName_txt2 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        totalApplication_txt = new javax.swing.JLabel();
        ActiveRoom_txt = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        scrollApplication = new javax.swing.JScrollPane();
        jtApp = new javax.swing.JTable();
        jlabel1 = new javax.swing.JLabel();
        myRoomNo_txt = new javax.swing.JLabel();
        jlabel3 = new javax.swing.JLabel();
        myFee_txt = new javax.swing.JLabel();
        jlabel5 = new javax.swing.JLabel();
        myRefNo_txt = new javax.swing.JLabel();
        jlabel2 = new javax.swing.JLabel();
        myStatus_txt = new javax.swing.JLabel();
        jlabel4 = new javax.swing.JLabel();
        myRemarks_txt = new javax.swing.JLabel();
        clear_btn = new javax.swing.JButton();
        myRemarks_txt1 = new javax.swing.JLabel();
        dlt_btn = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        MyRoomLogOut_btn = new javax.swing.JButton();
        MyRoomTitle_txt = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        MyRoomCO_btn = new javax.swing.JButton();
        MyRoomIconLabel = new javax.swing.JLabel();
        MyRoomLabel = new javax.swing.JLabel();
        MyRefNo_txt = new javax.swing.JLabel();
        MyRoomLabel2 = new javax.swing.JLabel();
        MyRoomStatus_txt = new javax.swing.JLabel();
        MyRoomLabel3 = new javax.swing.JLabel();
        MyRoomCO_txt = new javax.swing.JLabel();
        MyRoomLabel1 = new javax.swing.JLabel();
        MyRoomDuration_txt = new javax.swing.JLabel();
        noroomLabel_txt = new javax.swing.JLabel();
        cdApplication_btn = new javax.swing.JButton();
        noroomLabel2_txt = new javax.swing.JLabel();
        cdRoom_btn = new javax.swing.JButton();
        MyRoomLabel4 = new javax.swing.JLabel();
        MyRoomCI_txt = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MyHomePage.setBackground(new java.awt.Color(255, 255, 255));
        MyHomePage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MyHomePage.setForeground(new java.awt.Color(255, 102, 0));
        MyHomePage.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        MyHomePage.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setText("MY PROFILE");

        MyProfileLogOut_btn.setBackground(new java.awt.Color(236, 107, 38));
        MyProfileLogOut_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyProfileLogOut_btn.setForeground(new java.awt.Color(255, 255, 255));
        MyProfileLogOut_btn.setText("LOG OUT");
        MyProfileLogOut_btn.setBorder(null);
        MyProfileLogOut_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        MyProfileLogOut_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        MyProfileLogOut_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyProfileLogOut_btnActionPerformed(evt);
            }
        });

        MODIFY.setBackground(new java.awt.Color(236, 107, 38));
        MODIFY.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MODIFY.setForeground(new java.awt.Color(255, 255, 255));
        MODIFY.setText("MODIFY");
        MODIFY.setBorder(null);
        MODIFY.setMaximumSize(new java.awt.Dimension(90, 20));
        MODIFY.setMinimumSize(new java.awt.Dimension(90, 20));
        MODIFY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MODIFYActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MODIFY, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(MyProfileLogOut_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addComponent(MODIFY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MyProfileLogOut_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel3.setText("First Name");

        jLabel5.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel5.setText("Last Name");

        jLabel10.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel10.setText("IC Number");

        jLabel11.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel11.setText("TP Number");

        firstName_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        firstName_txt.setText("LIM");

        icNo_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        icNo_txt.setText("011210-14-8945");

        tpNo_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        tpNo_txt.setForeground(new java.awt.Color(255, 102, 0));
        tpNo_txt.setText("TP064298");

        lastName_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        lastName_txt.setText("LIP HOW");

        jPanel6.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        jPanel17.setBackground(new java.awt.Color(255, 102, 0));

        a.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        a.setText("Birthday");

        jLabel4.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel4.setText("BASIC INFOMRATION");

        jLabel2.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel2.setText("CONTACT INFOMRATION");

        jLabel8.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel8.setText("Phone Number");

        phNo_txt.setFont(new java.awt.Font("Segoe UI Variable", 2, 22)); // NOI18N
        phNo_txt.setForeground(new java.awt.Color(255, 255, 255));
        phNo_txt.setText("+60182198219");

        DOB_txt.setFont(new java.awt.Font("Segoe UI Variable", 2, 22)); // NOI18N
        DOB_txt.setForeground(new java.awt.Color(255, 255, 255));
        DOB_txt.setText("10-10-2010");

        jLabel7.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel7.setText("Gender");

        jLabel6.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel6.setText("Email");

        Email_txt.setFont(new java.awt.Font("Segoe UI Variable", 2, 22)); // NOI18N
        Email_txt.setForeground(new java.awt.Color(255, 255, 255));
        Email_txt.setText("liow10.lim@gmail.com");

        Gender_txt.setFont(new java.awt.Font("Segoe UI Variable", 2, 22)); // NOI18N
        Gender_txt.setForeground(new java.awt.Color(255, 255, 255));
        Gender_txt.setText("Male");

        jLabel9.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel9.setText("Address");

        addr_txt.setFont(new java.awt.Font("Segoe UI Variable", 2, 18)); // NOI18N
        addr_txt.setForeground(new java.awt.Color(255, 255, 255));
        addr_txt.setText("No 1, Jalan Dua Tiga, Tamen Empat Lima, Seri Enam Tujuh 89900, Puluhan, Selangor");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(addr_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(phNo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(Email_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Gender_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DOB_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(237, 237, 237))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(49, 49, 49)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(a))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phNo_txt)
                    .addComponent(DOB_txt))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Email_txt)
                    .addComponent(Gender_txt))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(addr_txt)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(tpNo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(lastName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(icNo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstName_txt)
                            .addComponent(lastName_txt))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tpNo_txt)
                            .addComponent(icNo_txt)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MyHomePage.addTab("MY PROFILE", jPanel2);

        jPanel5.setBackground(new java.awt.Color(247, 247, 247));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel12.setText("ROOM AVAILABLE");

        RoomLogOut_btn.setBackground(new java.awt.Color(236, 107, 38));
        RoomLogOut_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        RoomLogOut_btn.setForeground(new java.awt.Color(255, 255, 255));
        RoomLogOut_btn.setText("LOG OUT");
        RoomLogOut_btn.setBorder(null);
        RoomLogOut_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        RoomLogOut_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        RoomLogOut_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoomLogOut_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RoomLogOut_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addComponent(RoomLogOut_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 102, 0));
        jLabel13.setText("ASIA PACIFIC UNIVERSITY");

        firstName_txt1.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        firstName_txt1.setText("APU HOSTEL");

        jLabel14.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 102, 0));
        jLabel14.setText("Information");

        jPanel9.setBackground(new java.awt.Color(255, 102, 0));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Total Room Available ");

        totalRoom_txt.setBackground(new java.awt.Color(255, 255, 255));
        totalRoom_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        totalRoom_txt.setForeground(new java.awt.Color(255, 255, 255));
        totalRoom_txt.setText("100");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Enquiry");

        icNo_txt1.setBackground(new java.awt.Color(255, 255, 255));
        icNo_txt1.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        icNo_txt1.setForeground(new java.awt.Color(255, 255, 255));
        icNo_txt1.setText("03-4700 4700");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(totalRoom_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icNo_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(185, 185, 185))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalRoom_txt)
                    .addComponent(icNo_txt1))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel17.setText("APU HOSTEL is located within APU’s Campus in Technology Park Malaysia.");

        jLabel18.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel18.setText("The APU HOSTEL is equipped with 24/7 security and access card control.");

        jLabel19.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel19.setText("Students staying in the APU HOSTEL will have access to campus facilities ");

        jPanel10.setBackground(new java.awt.Color(255, 102, 0));

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        jt.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jt.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jt);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 7, Short.MAX_VALUE)
        );

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Double Click To Select // Room Number Selected:");

        RoomNo.setBackground(new java.awt.Color(255, 255, 255));
        RoomNo.setFont(new java.awt.Font("Segoe UI Variable", 1, 100)); // NOI18N
        RoomNo.setForeground(new java.awt.Color(255, 255, 255));

        icNo_txt4.setBackground(new java.awt.Color(255, 255, 255));
        icNo_txt4.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        icNo_txt4.setForeground(new java.awt.Color(255, 255, 255));
        icNo_txt4.setText("RM");

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText(" / Month");

        RoomPrice.setBackground(new java.awt.Color(255, 255, 255));
        RoomPrice.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        RoomPrice.setForeground(new java.awt.Color(255, 255, 255));

        jButton4.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 102, 0));
        jButton4.setText("Make Booking");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        RoomType.setBackground(new java.awt.Color(255, 255, 255));
        RoomType.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        RoomType.setForeground(new java.awt.Color(255, 255, 255));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RoomType)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(RoomNo)
                        .addContainerGap(481, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(icNo_txt4)
                .addGap(14, 14, 14)
                .addComponent(RoomPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(RoomType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(icNo_txt4)
                        .addComponent(RoomPrice)
                        .addComponent(jLabel26)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstName_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addGap(16, 16, 16))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addComponent(firstName_txt1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        MyHomePage.addTab("ROOM", jPanel1);

        jPanel13.setBackground(new java.awt.Color(247, 247, 247));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel20.setText("APPLICATION RECORD");

        ApplicationRecordLogOut_btn.setBackground(new java.awt.Color(255, 102, 0));
        ApplicationRecordLogOut_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        ApplicationRecordLogOut_btn.setForeground(new java.awt.Color(255, 255, 255));
        ApplicationRecordLogOut_btn.setText("LOG OUT");
        ApplicationRecordLogOut_btn.setBorder(null);
        ApplicationRecordLogOut_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        ApplicationRecordLogOut_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        ApplicationRecordLogOut_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplicationRecordLogOut_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ApplicationRecordLogOut_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addComponent(ApplicationRecordLogOut_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 102, 0));
        jLabel22.setText("ASIA PACIFIC UNIVERSITY: APU HOSTEL");

        firstName_txt2.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        firstName_txt2.setText("MY APPLICATION");

        jLabel24.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel24.setText("View your submitted application and its current status here. ");

        jLabel27.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel27.setText("Welcome, this page showcasing your hostel application status.");

        jLabel28.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel28.setText("Track your status effortlessly on the application submitted.     ");

        jLabel29.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 102, 0));
        jLabel29.setText("Information");

        jPanel15.setBackground(new java.awt.Color(255, 102, 0));

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Total Application Submitted ");

        totalApplication_txt.setBackground(new java.awt.Color(255, 255, 255));
        totalApplication_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        totalApplication_txt.setForeground(new java.awt.Color(255, 255, 255));
        totalApplication_txt.setText("100");

        ActiveRoom_txt.setBackground(new java.awt.Color(255, 255, 255));
        ActiveRoom_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        ActiveRoom_txt.setForeground(new java.awt.Color(255, 255, 255));
        ActiveRoom_txt.setText("No Active Room");

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Active Room");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(totalApplication_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActiveRoom_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ActiveRoom_txt)
                    .addComponent(totalApplication_txt))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 102, 0));

        jtApp.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jtApp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtApp.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtAppMouseClicked(evt);
            }
        });
        scrollApplication.setViewportView(jtApp);

        jlabel1.setBackground(new java.awt.Color(255, 255, 255));
        jlabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jlabel1.setForeground(new java.awt.Color(255, 255, 255));
        jlabel1.setText("Room Number Booked");

        myRoomNo_txt.setBackground(new java.awt.Color(255, 255, 255));
        myRoomNo_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        myRoomNo_txt.setForeground(new java.awt.Color(255, 255, 255));
        myRoomNo_txt.setText("  ");

        jlabel3.setBackground(new java.awt.Color(255, 255, 255));
        jlabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jlabel3.setForeground(new java.awt.Color(255, 255, 255));
        jlabel3.setText("Fee Paid (RM)");

        myFee_txt.setBackground(new java.awt.Color(255, 255, 255));
        myFee_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        myFee_txt.setForeground(new java.awt.Color(255, 255, 255));
        myFee_txt.setText("  ");

        jlabel5.setBackground(new java.awt.Color(255, 255, 255));
        jlabel5.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jlabel5.setForeground(new java.awt.Color(255, 255, 255));
        jlabel5.setText("Reference");

        myRefNo_txt.setBackground(new java.awt.Color(255, 255, 255));
        myRefNo_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        myRefNo_txt.setForeground(new java.awt.Color(255, 255, 255));

        jlabel2.setBackground(new java.awt.Color(255, 255, 255));
        jlabel2.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jlabel2.setForeground(new java.awt.Color(255, 255, 255));
        jlabel2.setText("Status");

        myStatus_txt.setBackground(new java.awt.Color(255, 255, 255));
        myStatus_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 36)); // NOI18N
        myStatus_txt.setForeground(new java.awt.Color(255, 255, 255));
        myStatus_txt.setText("  ");

        jlabel4.setBackground(new java.awt.Color(255, 255, 255));
        jlabel4.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jlabel4.setForeground(new java.awt.Color(255, 255, 255));
        jlabel4.setText("Remarks");

        myRemarks_txt.setBackground(new java.awt.Color(255, 255, 255));
        myRemarks_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        myRemarks_txt.setForeground(new java.awt.Color(255, 255, 255));
        myRemarks_txt.setText("**The fee paid have been redunded to account paid.");

        clear_btn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        clear_btn.setForeground(new java.awt.Color(255, 102, 0));
        clear_btn.setText("CLEAR DETAILS");
        clear_btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        clear_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        clear_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        clear_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_btnActionPerformed(evt);
            }
        });

        myRemarks_txt1.setBackground(new java.awt.Color(255, 255, 255));
        myRemarks_txt1.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        myRemarks_txt1.setForeground(new java.awt.Color(255, 255, 255));
        myRemarks_txt1.setText("Select the table to view details.");

        dlt_btn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        dlt_btn.setForeground(new java.awt.Color(255, 102, 0));
        dlt_btn.setText("DELETE");
        dlt_btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        dlt_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        dlt_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        dlt_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlt_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(scrollApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel16Layout.createSequentialGroup()
                                    .addComponent(myRoomNo_txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(61, 61, 61)
                                    .addComponent(myRefNo_txt))
                                .addGroup(jPanel16Layout.createSequentialGroup()
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jlabel2)
                                        .addComponent(jlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(myFee_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                        .addComponent(jlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(myStatus_txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(43, 43, 43)
                                    .addComponent(jlabel5)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                    .addComponent(clear_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dlt_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(myRemarks_txt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(myRemarks_txt1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlabel1)
                            .addComponent(jlabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(myRoomNo_txt)
                            .addComponent(myRefNo_txt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myStatus_txt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myFee_txt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlabel4)
                        .addGap(18, 18, 18)
                        .addComponent(myRemarks_txt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clear_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dlt_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scrollApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myRemarks_txt1)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(firstName_txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(346, 346, 346))
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel29))
                .addGap(12, 12, 12)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28))
                    .addComponent(firstName_txt2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MyHomePage.addTab("APPLICATION RECORD", jPanel11);

        jPanel19.setBackground(new java.awt.Color(247, 247, 247));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel32.setText("MY ROOM");

        MyRoomLogOut_btn.setBackground(new java.awt.Color(255, 102, 0));
        MyRoomLogOut_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyRoomLogOut_btn.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomLogOut_btn.setText("LOG OUT");
        MyRoomLogOut_btn.setBorder(null);
        MyRoomLogOut_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        MyRoomLogOut_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        MyRoomLogOut_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyRoomLogOut_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MyRoomLogOut_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addComponent(MyRoomLogOut_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MyRoomTitle_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        MyRoomTitle_txt.setText("NO ACTIVE ROOM FOUND");

        jLabel33.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 102, 0));
        jLabel33.setText("ASIA PACIFIC UNIVERSITY: APU HOSTEL");

        jPanel21.setBackground(new java.awt.Color(255, 102, 0));

        MyRoomCO_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        MyRoomCO_btn.setForeground(new java.awt.Color(255, 0, 0));
        MyRoomCO_btn.setText("CHECK OUT NOW");
        MyRoomCO_btn.setBorder(null);
        MyRoomCO_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        MyRoomCO_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        MyRoomCO_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyRoomCO_btnActionPerformed(evt);
            }
        });

        MyRoomIconLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\OneDrive - Asia Pacific University\\DG Y2\\Semester 1\\OODJ\\A FINAL ASSIGNMENT\\HostelManagementSystem\\src\\main\\java\\com\\mycompany\\hostelmanagementsystem\\APU HOSTEL.png")); // NOI18N
        MyRoomIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MyRoomIconLabelMouseClicked(evt);
            }
        });

        MyRoomLabel.setBackground(new java.awt.Color(255, 255, 255));
        MyRoomLabel.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyRoomLabel.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomLabel.setText("Status");

        MyRefNo_txt.setBackground(new java.awt.Color(255, 255, 255));
        MyRefNo_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        MyRefNo_txt.setForeground(new java.awt.Color(255, 255, 255));
        MyRefNo_txt.setText("03-2023");

        MyRoomLabel2.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyRoomLabel2.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomLabel2.setText("Check In Month-Year");

        MyRoomStatus_txt.setBackground(new java.awt.Color(255, 255, 255));
        MyRoomStatus_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        MyRoomStatus_txt.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomStatus_txt.setText("ACTIVE");

        MyRoomLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyRoomLabel3.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomLabel3.setText("Check Out Month-Year");

        MyRoomCO_txt.setBackground(new java.awt.Color(255, 255, 255));
        MyRoomCO_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        MyRoomCO_txt.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomCO_txt.setText("06-2023");

        MyRoomLabel1.setBackground(new java.awt.Color(255, 255, 255));
        MyRoomLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyRoomLabel1.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomLabel1.setText("Duration (Month)");

        MyRoomDuration_txt.setBackground(new java.awt.Color(255, 255, 255));
        MyRoomDuration_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        MyRoomDuration_txt.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomDuration_txt.setText("3");

        noroomLabel_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        noroomLabel_txt.setForeground(new java.awt.Color(255, 255, 255));
        noroomLabel_txt.setText("CHECK APPLICATION HERE");

        cdApplication_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        cdApplication_btn.setForeground(new java.awt.Color(255, 102, 0));
        cdApplication_btn.setText("APPLICATION");
        cdApplication_btn.setBorder(null);
        cdApplication_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        cdApplication_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        cdApplication_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdApplication_btnActionPerformed(evt);
            }
        });

        noroomLabel2_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        noroomLabel2_txt.setForeground(new java.awt.Color(255, 255, 255));
        noroomLabel2_txt.setText("RESERVE A NEW ROOM");

        cdRoom_btn.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        cdRoom_btn.setForeground(new java.awt.Color(255, 102, 0));
        cdRoom_btn.setText("ROOM");
        cdRoom_btn.setBorder(null);
        cdRoom_btn.setMaximumSize(new java.awt.Dimension(90, 20));
        cdRoom_btn.setMinimumSize(new java.awt.Dimension(90, 20));
        cdRoom_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdRoom_btnActionPerformed(evt);
            }
        });

        MyRoomLabel4.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MyRoomLabel4.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomLabel4.setText("Ref No");

        MyRoomCI_txt.setBackground(new java.awt.Color(255, 255, 255));
        MyRoomCI_txt.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        MyRoomCI_txt.setForeground(new java.awt.Color(255, 255, 255));
        MyRoomCI_txt.setText("03-2023");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(MyRoomCO_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(MyRoomIconLabel)
                                .addGap(38, 38, 38)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(MyRoomLabel2)
                                            .addComponent(MyRoomStatus_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(MyRoomLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(MyRoomLabel4)
                                            .addComponent(MyRoomCI_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(MyRoomLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(MyRoomCO_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(MyRoomLabel3)
                                            .addComponent(MyRoomDuration_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(34, 34, 34))
                                    .addComponent(MyRefNo_txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(noroomLabel2_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cdRoom_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(noroomLabel_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cdApplication_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 12, Short.MAX_VALUE)))))
                .addGap(26, 26, 26))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noroomLabel_txt)
                    .addComponent(cdApplication_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noroomLabel2_txt)
                    .addComponent(cdRoom_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MyRoomLabel)
                            .addComponent(MyRoomLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MyRoomStatus_txt)
                            .addComponent(MyRoomDuration_txt))
                        .addGap(18, 18, 18)
                        .addComponent(MyRoomLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MyRefNo_txt)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(MyRoomLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MyRoomCO_txt))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(MyRoomLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MyRoomCI_txt))))
                    .addComponent(MyRoomIconLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(MyRoomCO_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MyRoomTitle_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addGap(12, 12, 12)
                .addComponent(MyRoomTitle_txt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MyHomePage.addTab("MY ROOM", jPanel18);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MyHomePage))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MyHomePage, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MODIFYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MODIFYActionPerformed
        StudentModifyProfile modifyProfileForm = new StudentModifyProfile(TP, PW);
        modifyProfileForm.setVisible(true);
        this.dispose();          // TODO add your handling code here:
    }//GEN-LAST:event_MODIFYActionPerformed

    private void MyProfileLogOut_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyProfileLogOut_btnActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        }
    }//GEN-LAST:event_MyProfileLogOut_btnActionPerformed

    private void RoomLogOut_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoomLogOut_btnActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        }
    }//GEN-LAST:event_RoomLogOut_btnActionPerformed

    private void jtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMouseClicked
        int i = jt.getSelectedRow();
        TableModel model = jt.getModel();
        RoomNo.setText(model.getValueAt(i, 1).toString());
        RoomType.setText(model.getValueAt(i, 0).toString());
        RoomPrice.setText(model.getValueAt(i, 2).toString());

// TODO add your handling code here:
    }//GEN-LAST:event_jtMouseClicked

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        RoomNumber = RoomNo.getText();
        if (RoomNumber.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select a room.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println(RoomNumber + TP + PW);
            StudentBooking BookingForm = new StudentBooking(RoomNumber, TP, PW);
            BookingForm.setVisible(true);
            this.dispose();           // TODO add your handling code here:
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ApplicationRecordLogOut_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplicationRecordLogOut_btnActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        }

    }//GEN-LAST:event_ApplicationRecordLogOut_btnActionPerformed

    private void clearAll() {
        myRoomNo_txt.setText("");
        myStatus_txt.setText("");
        myFee_txt.setText("");
        myRefNo_txt.setText("");
        myRoomNo_txt.setVisible(false);
        myStatus_txt.setVisible(false);
        myFee_txt.setVisible(false);
        myRefNo_txt.setVisible(false);
        jlabel1.setVisible(false);
        jlabel2.setVisible(false);
        jlabel3.setVisible(false);
        jlabel4.setVisible(false);
        jlabel5.setVisible(false);
        myRemarks_txt.setVisible(false);
        dlt_btn.setVisible(false);
        jtApp.clearSelection();
    }
    private void clear_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_btnActionPerformed

        clearAll();
    }//GEN-LAST:event_clear_btnActionPerformed

    private void jtAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtAppMouseClicked
        int i = jtApp.getSelectedRow();
        TableModel model = jtApp.getModel();
        myRoomNo_txt.setText(model.getValueAt(i, 0).toString());
        selectedRoom = (model.getValueAt(i, 0).toString());
        System.out.println(selectedRoom);
        myStatus_txt.setText(model.getValueAt(i, 5).toString());
        myFee_txt.setText(model.getValueAt(i, 4).toString());
        myRefNo_txt.setText(model.getValueAt(i, 6).toString());
        selectedrefNo = (model.getValueAt(i, 6).toString());
        System.out.println(selectedrefNo);
        myRoomNo_txt.setVisible(true);
        myStatus_txt.setVisible(true);
        myFee_txt.setVisible(true);
        myRefNo_txt.setVisible(true); // TODO add your handling code here:
        jlabel1.setVisible(true);
        jlabel2.setVisible(true);
        jlabel3.setVisible(true);
        jlabel4.setVisible(true);
        jlabel5.setVisible(true);
        myRemarks_txt.setVisible(true);
        if ((model.getValueAt(i, 5).equals("Pending"))) {
            dlt_btn.setVisible(true);
            myRemarks_txt.setText("Pending Approval From Admin");
        } else if ((model.getValueAt(i, 5).equals("Accepted"))) {
            dlt_btn.setVisible(false);
            myRemarks_txt.setText("-");
        } else {
            dlt_btn.setVisible(false);
            myRemarks_txt.setText("**The fee paid have been refunded to account paid.");
        }


    }//GEN-LAST:event_jtAppMouseClicked

    private void dlt_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlt_btnActionPerformed
        int YesOrNo = JOptionPane.showConfirmDialog(null, "Do You Want To Delete the Application Selected: " + myRoomNo_txt.getText() + "?", "Cancel Application", JOptionPane.YES_NO_OPTION);
        if (YesOrNo == JOptionPane.YES_OPTION) {
            System.out.println("Proceed delete");
            application.DeleteApplication(TP, selectedRoom, selectedrefNo, "Rejected");
            roomStatus.modifyRoomStatus(selectedRoom, selectedrefNo, "Available");//MODIFY CODE
            LoadMyApplicationTable();
            LoadTable();

            clearAll();
        } else {
            System.out.println("Proceed nothing");
        }
    }//GEN-LAST:event_dlt_btnActionPerformed

    private void MyRoomCO_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyRoomCO_btnActionPerformed
        int YesOrNo = JOptionPane.showConfirmDialog(null, "Do You Want To Check Out The Current Room: " + myRoomNo_txt.getText() + "?", "Room Check Out", JOptionPane.YES_NO_OPTION);
        if (YesOrNo == JOptionPane.YES_OPTION) {
            System.out.println("Proceed check out");
            application.DeleteApplication(TP, ActiveRoomNumber, ActiveRefNo, "CheckOut");
            roomStatus.modifyRoomStatus(ActiveRoomNumber, ActiveRefNo, "Available");//MODIFY CODE
            LoadMyApplicationTable();
            LoadTable();
            LoadMyRoom();

            clearAll();
        } else {
            System.out.println("Proceed nothing");
        }                // TODO add your handling code here:
    }//GEN-LAST:event_MyRoomCO_btnActionPerformed

    private void MyRoomIconLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyRoomIconLabelMouseClicked
        System.out.println("ouch");        // TODO add your handling code here:
    }//GEN-LAST:event_MyRoomIconLabelMouseClicked

    private void cdApplication_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdApplication_btnActionPerformed
        MyHomePage.setSelectedIndex(2);               // TODO add your handling code here:
    }//GEN-LAST:event_cdApplication_btnActionPerformed

    private void cdRoom_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdRoom_btnActionPerformed
        MyHomePage.setSelectedIndex(1);         // TODO add your handling code here:
    }//GEN-LAST:event_cdRoom_btnActionPerformed

    private void MyRoomLogOut_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyRoomLogOut_btnActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        }

    }//GEN-LAST:event_MyRoomLogOut_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentHomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActiveRoom_txt;
    private javax.swing.JButton ApplicationRecordLogOut_btn;
    private javax.swing.JLabel DOB_txt;
    private javax.swing.JLabel Email_txt;
    private javax.swing.JLabel Gender_txt;
    private javax.swing.JButton MODIFY;
    private javax.swing.JTabbedPane MyHomePage;
    private javax.swing.JButton MyProfileLogOut_btn;
    private javax.swing.JLabel MyRefNo_txt;
    private javax.swing.JLabel MyRoomCI_txt;
    private javax.swing.JButton MyRoomCO_btn;
    private javax.swing.JLabel MyRoomCO_txt;
    private javax.swing.JLabel MyRoomDuration_txt;
    private javax.swing.JLabel MyRoomIconLabel;
    private javax.swing.JLabel MyRoomLabel;
    private javax.swing.JLabel MyRoomLabel1;
    private javax.swing.JLabel MyRoomLabel2;
    private javax.swing.JLabel MyRoomLabel3;
    private javax.swing.JLabel MyRoomLabel4;
    private javax.swing.JButton MyRoomLogOut_btn;
    private javax.swing.JLabel MyRoomStatus_txt;
    private javax.swing.JLabel MyRoomTitle_txt;
    private javax.swing.JButton RoomLogOut_btn;
    private javax.swing.JLabel RoomNo;
    private javax.swing.JLabel RoomPrice;
    private javax.swing.JLabel RoomType;
    private javax.swing.JLabel a;
    private javax.swing.JLabel addr_txt;
    private javax.swing.JButton cdApplication_btn;
    private javax.swing.JButton cdRoom_btn;
    private javax.swing.JButton clear_btn;
    private javax.swing.JButton dlt_btn;
    private javax.swing.JLabel firstName_txt;
    private javax.swing.JLabel firstName_txt1;
    private javax.swing.JLabel firstName_txt2;
    private javax.swing.JLabel icNo_txt;
    private javax.swing.JLabel icNo_txt1;
    private javax.swing.JLabel icNo_txt4;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JLabel jlabel4;
    private javax.swing.JLabel jlabel5;
    private javax.swing.JTable jt;
    private javax.swing.JTable jtApp;
    private javax.swing.JLabel lastName_txt;
    private javax.swing.JLabel myFee_txt;
    private javax.swing.JLabel myRefNo_txt;
    private javax.swing.JLabel myRemarks_txt;
    private javax.swing.JLabel myRemarks_txt1;
    private javax.swing.JLabel myRoomNo_txt;
    private javax.swing.JLabel myStatus_txt;
    private javax.swing.JLabel noroomLabel2_txt;
    private javax.swing.JLabel noroomLabel_txt;
    private javax.swing.JLabel phNo_txt;
    private javax.swing.JScrollPane scrollApplication;
    private javax.swing.JLabel totalApplication_txt;
    private javax.swing.JLabel totalRoom_txt;
    private javax.swing.JLabel tpNo_txt;
    // End of variables declaration//GEN-END:variables
}
