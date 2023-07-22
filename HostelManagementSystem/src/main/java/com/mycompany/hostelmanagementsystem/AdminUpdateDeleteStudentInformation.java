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
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author wongj
 */
public class AdminUpdateDeleteStudentInformation extends admin {

    public AdminUpdateDeleteStudentInformation(String firstName, String lastName, String DOB, String gender, String email, String phoneNo, String address, String icNo, String tpNo, String password) {
        super(firstName, lastName, DOB, gender, email, phoneNo, address, icNo, tpNo, password);
    }

    public AdminUpdateDeleteStudentInformation() {
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getDOB() {
        return DOB;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phoneNo;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getIcNumber() {
        return icNo;
    }

    @Override
    public String getTpNumber() {
        return tpNo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPhone(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setIcNumber(String icNo) {
        this.icNo = icNo;
    }

    @Override
    public void setTpNumber(String tpNo) {
        this.tpNo = tpNo;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    private static final String FILE_PATH = "user.txt";

    //private static final String TP_PREFIX = "TP";
    public static void populateStudentTable(DefaultTableModel model) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData.length <= 10) {
                    String firstName = userData[0];
                    String lastName = userData[1];
                    String dob = userData[2];
                    String gender = userData[3];
                    String email = userData[4];
                    String phoneNo = userData[5];
                    String address = userData[6];
                    String icNo = userData[7];
                    String tpNo = userData[8];
                    String password = userData[9];

                    model.addRow(new Object[]{firstName, lastName, dob, gender, email, phoneNo, address, icNo, tpNo, password});
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void populateStudentTable() {
        DefaultTableModel model = new DefaultTableModel();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData.length <= 10) {
                    String firstName = userData[0];
                    String lastName = userData[1];
                    String dob = userData[2];
                    String gender = userData[3];
                    String email = userData[4];
                    String phoneNo = userData[5];
                    String address = userData[6];
                    String icNo = userData[7];
                    String tpNo = userData[8];
                    String password = userData[9];

                    model.addRow(new Object[]{firstName, lastName, dob, gender, email, phoneNo, address, icNo, tpNo, password});
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleTableClick(javax.swing.JTable table, javax.swing.JTextField firstNameTextField,
            javax.swing.JTextField lastNameTextField, javax.swing.JComboBox<String> dobDayComboBox,
            javax.swing.JComboBox<String> dobMonthComboBox, javax.swing.JComboBox<String> dobYearComboBox,
            javax.swing.JRadioButton maleRadioButton, javax.swing.JRadioButton femaleRadioButton,
            javax.swing.JTextField emailTextField, javax.swing.JTextField phoneNoTextField,
            javax.swing.JTextField addressTextField, javax.swing.JTextField icNoTextField,
            javax.swing.JTextField tpNoTextField, javax.swing.JTextField passwordTextField) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String firstName = model.getValueAt(selectedRow, 0).toString();
            String lastName = model.getValueAt(selectedRow, 1).toString();
            String dob = model.getValueAt(selectedRow, 2).toString();
            String gender = model.getValueAt(selectedRow, 3).toString();
            String email = model.getValueAt(selectedRow, 4).toString();
            String phoneNo = model.getValueAt(selectedRow, 5).toString();
            String address = model.getValueAt(selectedRow, 6).toString();
            String icNo = model.getValueAt(selectedRow, 7).toString();
            String tpNo = model.getValueAt(selectedRow, 8).toString();
            String password = model.getValueAt(selectedRow, 9).toString();

            firstNameTextField.setText(firstName);
            lastNameTextField.setText(lastName);
            // Set other field values accordingly

            String[] dobParts = dob.split("-");
            if (dobParts.length == 3) {
                String day = dobParts[0];
                String month = dobParts[1];
                String year = dobParts[2];

                dobDayComboBox.setSelectedItem(day);
                dobMonthComboBox.setSelectedItem(month);
                dobYearComboBox.setSelectedItem(year);
            }

            if (gender.equals("Male")) {
                maleRadioButton.setSelected(true);
                femaleRadioButton.setSelected(false);
            } else {
                maleRadioButton.setSelected(false);
                femaleRadioButton.setSelected(true);
            }
            emailTextField.setText(email);
            phoneNoTextField.setText(phoneNo);
            addressTextField.setText(address);
            icNoTextField.setText(icNo);
            tpNoTextField.setText(tpNo);
            passwordTextField.setText(password);
        }
    }

    @Override
    public void saveStudentInformation(int selectedRow, JTextField firstNameField, JTextField lastNameField,
            JComboBox<String> dobDayComboBox, JComboBox<String> dobMonthComboBox, JComboBox<String> dobYearComboBox,
            JRadioButton femaleRadioButton, JRadioButton maleRadioButton, JTextField emailField, JTextField phoneNoField, JTextField addressField,
            JTextField icNoField, JTextField tpNoField, JTextField passwordField, JTable jTableUpdateDeleteStuInfo) {

        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            File file = new File("user.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int currentRow = 0;

            while ((line = reader.readLine()) != null) {
                if (currentRow == selectedRow) {
                    // Edit the selected row with updated data
                    String[] rowData = line.split("\\|");

                    rowData[0] = firstNameField.getText();
                    rowData[1] = lastNameField.getText();
                    rowData[2] = (String) (dobDayComboBox.getSelectedItem() + "-" + dobMonthComboBox.getSelectedItem() + "-" + dobYearComboBox.getSelectedItem());
                    rowData[3] = femaleRadioButton.isSelected() ? "Female" : "Male";
                    rowData[4] = emailField.getText();
                    rowData[5] = phoneNoField.getText();
                    rowData[6] = addressField.getText();
                    rowData[7] = icNoField.getText();
                    rowData[8] = tpNoField.getText();
                    rowData[9] = passwordField.getText();

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

        // Refresh student information table
        DefaultTableModel modelUpdateDeleteStuInfo = (DefaultTableModel) jTableUpdateDeleteStuInfo.getModel();
        modelUpdateDeleteStuInfo.setRowCount(0);
        populateStudentTable(modelUpdateDeleteStuInfo);
    }

    public static void filterStudentTable(DefaultTableModel model, String searchText, JTable jTableUpdateDeleteStuInfo) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTableUpdateDeleteStuInfo.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText);
        sorter.setRowFilter(rowFilter);
    }

    public static void deleteStudentInformation(int selectedRow, JTable jTableUpdateDeleteStuInfo) {
        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            File file = new File("user.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int currentRow = 0;

            while ((line = reader.readLine()) != null) {
                if (currentRow != selectedRow) {
                    // Exclude the selected row from being written to the temporary file
                    writer.write(line);
                    writer.newLine();
                }
                currentRow++;
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated temporary file
            file.delete();
            tempFile.renameTo(file);

            JOptionPane.showMessageDialog(null, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while deleting record.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Refresh student information table
        DefaultTableModel modelUpdateDeleteStuInfo = (DefaultTableModel) jTableUpdateDeleteStuInfo.getModel();
        modelUpdateDeleteStuInfo.setRowCount(0);
        populateStudentTable(modelUpdateDeleteStuInfo);
    }

    @Override
    public void register() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modifyProfile(String TP, String PW, String newFirstName, String newLastName, String newDOB, String newGender, String newEmail, String newPhoneNo, String newAddress, String newICNo, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
