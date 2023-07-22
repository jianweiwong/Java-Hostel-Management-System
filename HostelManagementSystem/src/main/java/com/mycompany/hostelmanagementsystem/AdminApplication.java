/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hostelmanagementsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author wongj
 */
public abstract class AdminApplication extends roomManagement {

    public AdminApplication(String tpNo, String roomNo, String startMonth, String endMonth, int duration, double price, String applicationStatus, String refNo) {
        super(tpNo, roomNo, startMonth, endMonth, duration, price,
                applicationStatus, refNo);
    }

    public String getTpNo() {
        return tpNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setTpNo(String tpNo) {
        this.tpNo = tpNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public static void populateRoomTable(DefaultTableModel model, String[] wantedStatus) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("application.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] roomData = line.split("\\|");
                if (Arrays.asList(wantedStatus).contains(roomData[6])) {
                    // Exclude the corresponding room status entry from being written to the temporary file
                    if (roomData.length >= 4) {
                        String tpNo = roomData[0];
                        String roomNo = roomData[1];
                        String startMonth = roomData[2];
                        String endMonth = roomData[3];
                        String duration = roomData[4];
                        String price = roomData[5];
                        String applicationStatus = roomData[6];
                        String refNo = roomData[7];

                        model.addRow(new Object[]{tpNo, roomNo, startMonth, endMonth, duration, price, applicationStatus, refNo});
                    }
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void filterStudentTable(DefaultTableModel model, String searchText, JTable jTableUpdateDeleteStuInfo) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTableUpdateDeleteStuInfo.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText);
        sorter.setRowFilter(rowFilter);

    }

    public static void acceptStudentApplication(String referenceNumber, JTable jTableStudentApplication) {
        try {
            File file = new File("application.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] applicationData = line.split("\\|");
                if (applicationData[7].equals(referenceNumber)) {
                    // Update the AdminApplication status to 'Accepted'
                    applicationData[6] = "Accepted";
                }
                String updatedLine = String.join("|", applicationData);
                writer.write(updatedLine);
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated temporary file
            file.delete();
            tempFile.renameTo(file);

            JOptionPane.showMessageDialog(null, "Application accepted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh student AdminApplication table
            DefaultTableModel modelStudentApplication = (DefaultTableModel) jTableStudentApplication.getModel();
            modelStudentApplication.setRowCount(0);
            String[] wantedStatusRoomApplication = {"Pending"};
            populateRoomTable(modelStudentApplication, wantedStatusRoomApplication);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while accepting application.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void rejectStudentApplication(String referenceNumber, JTable jTableStudentApplication) {
        try {
            File file = new File("application.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] applicationData = line.split("\\|");
                if (applicationData[7].equals(referenceNumber)) {
                    // Update the AdminApplication status to 'Rejected'
                    applicationData[6] = "Rejected";
                }
                String updatedLine = String.join("|", applicationData);
                writer.write(updatedLine);
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated temporary file
            file.delete();
            tempFile.renameTo(file);

            JOptionPane.showMessageDialog(null, "Application rejected successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh student AdminApplication table
            DefaultTableModel modelStudentApplication = (DefaultTableModel) jTableStudentApplication.getModel();
            modelStudentApplication.setRowCount(0);
            String[] wantedStatusRoomApplication = {"Pending"};
            populateRoomTable(modelStudentApplication, wantedStatusRoomApplication);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while rejecting application.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
