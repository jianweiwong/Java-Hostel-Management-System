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
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author wongj
 */
public class AdminRoom extends roomManagement {

    public AdminRoom(String roomType, String roomNo, double price, String description) {

        super(roomType, roomNo, price, description);

    }
    

    public String getRoomType() {
        return roomType;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void populateRoomTable(DefaultTableModel model) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("room.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] roomData = line.split("\\|");
                if (roomData.length >= 4) {
                    String roomType = roomData[0];
                    String roomNumber = roomData[1];
                    String roomPrice = roomData[2];
                    String description = roomData[3];

                    model.addRow(new Object[]{roomType, roomNumber, roomPrice, description});
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRoomInfo(String roomType, String roomNumberBlock, String roomFloor, String roomTypeRoomNumber,
            String roomPrice, String description) {

        if (isRoomNumberExists(roomNumberBlock, roomFloor, roomTypeRoomNumber)) {
            JOptionPane.showMessageDialog(null, "Room number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String roomInfo = roomType + "|" + roomNumberBlock + "-" + roomFloor + "-" + roomTypeRoomNumber + "|" + roomPrice + "|" + description;

        try {
            File file = new File("room.txt");
            FileWriter writer = new FileWriter(file, true); // Append mode

            // Write the AdminRoom information to the file
            writer.write(roomInfo);
            writer.write(System.lineSeparator()); // Add a new line

            writer.close();

            JOptionPane.showMessageDialog(null, "Room added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while adding room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isRoomNumberExists(String roomNumberBlock, String roomFloor, String roomTypeRoomNumber) {
        try {
            File file = new File("room.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] roomData = line.split("\\|");

                // Check if the AdminRoom number exists
                if (roomData.length >= 2 && roomData[1].equals(roomNumberBlock + "-" + roomFloor + "-" + roomTypeRoomNumber)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void filterStudentTable(DefaultTableModel model, String searchText, JTable jTableUpdateDeleteStuInfo) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTableUpdateDeleteStuInfo.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText);
        sorter.setRowFilter(rowFilter);

    }

    public static void handleTableClick(javax.swing.JTable table, javax.swing.JComboBox<String> roomTypeComboBox,
            javax.swing.JComboBox<String> roomNumberBlockComboBox, javax.swing.JComboBox<String> roomFloorComboBox,
            javax.swing.JComboBox<String> roomTypeRoomNumberComboBox, javax.swing.JComboBox<String> roomPriceComboBox,
            javax.swing.JTextArea descriptionTextArea) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String roomType = model.getValueAt(selectedRow, 0).toString();
            String roomNumberBlock = model.getValueAt(selectedRow, 1).toString();
            String roomPrice = model.getValueAt(selectedRow, 2).toString();
            String description = model.getValueAt(selectedRow, 3).toString();

            roomTypeComboBox.setSelectedItem(roomType);
            roomNumberBlockComboBox.setSelectedItem(roomNumberBlock);

            String[] roomParts = roomNumberBlock.split("-");
            if (roomParts.length == 3) {
                String block = roomParts[0];
                String floor = roomParts[1];
                String roomNumber = roomParts[2];

                roomNumberBlockComboBox.setSelectedItem(block);
                roomFloorComboBox.setSelectedItem(floor);
                roomTypeRoomNumberComboBox.setSelectedItem(roomNumber);
            }
            // Set other field values accordingly
            roomPriceComboBox.setSelectedItem(roomPrice);
            descriptionTextArea.setText(description);
        }
    }

    public static void updateRoomInfo(int selectedRow, JComboBox<String> roomTypeComboBox,
            JComboBox<String> roomBlockComboBox, JComboBox<String> roomFloorComboBox,
            JComboBox<String> roomNumberComboBox, JComboBox<String> roomPriceComboBox,
            JTextArea descriptionTextArea, JTable jTableRoomInfo) {

        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            File file = new File("room.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int currentRow = 0;

            while ((line = reader.readLine()) != null) {
                if (currentRow == selectedRow) {
                    // Edit the selected row with updated data
                    String[] rowData = line.split("\\|");

                    rowData[0] = (String) roomTypeComboBox.getSelectedItem();
                    rowData[1] = (String) (String) (roomBlockComboBox.getSelectedItem() + "-" + roomFloorComboBox.getSelectedItem() + "-" + roomNumberComboBox.getSelectedItem());
                    rowData[2] = (String) roomPriceComboBox.getSelectedItem();
                    rowData[3] = descriptionTextArea.getText();

                    // Reconstruct the updated line
                    line = String.join("|", rowData);
                }

                writer.write(line);
                writer.newLine();
                currentRow++;
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated temporary file
            file.delete();
            tempFile.renameTo(file);

            JOptionPane.showMessageDialog(null, "Data saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Refresh AdminRoom information table
        DefaultTableModel modelRoomInfo = (DefaultTableModel) jTableRoomInfo.getModel();
        modelRoomInfo.setRowCount(0);
        populateRoomTable(modelRoomInfo);
    }

    public static void addRoomStatusInfo(String roomNumberBlock, String roomFloor, String roomTypeRoomNumber) {

        if (isRoomStatusExists(roomNumberBlock, roomFloor, roomTypeRoomNumber)) {
            JOptionPane.showMessageDialog(null, "Room status already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String roomInfo = roomNumberBlock + "-" + roomFloor + "-" + roomTypeRoomNumber + "|" + "Available";

        try {
            File file = new File("roomStatus.txt");
            FileWriter writer = new FileWriter(file, true); // Append mode

            // Write the AdminRoom information to the file
            writer.write(roomInfo);
            writer.write(System.lineSeparator()); // Add a new line

            writer.close();

            JOptionPane.showMessageDialog(null, "Room status added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while adding room status.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isRoomStatusExists(String roomNumberBlock, String roomFloor, String roomTypeRoomNumber) {
        try {
            File file = new File("roomStatus.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] roomData = line.split("\\|");

                // Check if the AdminRoom number exists
                if (roomData.length >= 1 && roomData[0].equals(roomNumberBlock + "-" + roomFloor + "-" + roomTypeRoomNumber)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void deleteRoomInfo(int selectedRow, JTable jTableRoomInfo) {
        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            File roomInfoFile = new File("room.txt");
            File tempRoomInfoFile = new File("tempRoom.txt");

            BufferedReader reader = new BufferedReader(new FileReader(roomInfoFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempRoomInfoFile));

            String line;
            int currentRow = 0;

            while ((line = reader.readLine()) != null) {
                if (currentRow != selectedRow) {
                    // Exclude the selected row from being written to the temporary AdminRoom info file
                    writer.write(line);
                    writer.newLine();
                }
                currentRow++;
            }

            reader.close();
            writer.close();

            // Replace the original AdminRoom info file with the updated temporary file
            roomInfoFile.delete();
            tempRoomInfoFile.renameTo(roomInfoFile);

            // Delete corresponding AdminRoom status entry
            DefaultTableModel modelRoomInfo = (DefaultTableModel) jTableRoomInfo.getModel();
            String combineRoomNumber = modelRoomInfo.getValueAt(selectedRow, 1).toString();
            String[] roomParts = combineRoomNumber.split("-");
            String roomNumberBlockParts = roomParts[0];
            String roomFloorParts = roomParts[1];
            String roomTypeRoomNumberParts = roomParts[2];
            String roomNumber = roomNumberBlockParts + "-" + roomFloorParts + "-" + roomTypeRoomNumberParts;

            File roomStatusFile = new File("roomStatus.txt");
            File tempRoomStatusFile = new File("tempRoomStatus.txt");

            BufferedReader statusReader = new BufferedReader(new FileReader(roomStatusFile));
            BufferedWriter statusWriter = new BufferedWriter(new FileWriter(tempRoomStatusFile));

            String statusLine;

            while ((statusLine = statusReader.readLine()) != null) {
                String[] roomData = statusLine.split("\\|");
                if (!roomData[0].equals(roomNumber)) {
                    // Exclude the corresponding AdminRoom status entry from being written to the temporary file
                    statusWriter.write(statusLine);
                    statusWriter.newLine();
                }
            }

            statusReader.close();
            statusWriter.close();

            // Replace the original AdminRoom status file with the updated temporary file
            roomStatusFile.delete();
            tempRoomStatusFile.renameTo(roomStatusFile);

            JOptionPane.showMessageDialog(null, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while deleting record.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Refresh AdminRoom information table
        DefaultTableModel modelRoomInfo = (DefaultTableModel) jTableRoomInfo.getModel();
        modelRoomInfo.setRowCount(0);
        populateRoomTable(modelRoomInfo);
    }

}
