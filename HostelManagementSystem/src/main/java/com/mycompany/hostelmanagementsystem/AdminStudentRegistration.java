/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hostelmanagementsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author wongj
 */

public class AdminStudentRegistration extends admin {

    public AdminStudentRegistration(String firstName, String lastName, String DOB, String gender, String email, String phoneNo, String address, String icNo, String tpNo, String password) {
        super(firstName, lastName, DOB, gender, email, phoneNo, address, icNo, tpNo, password);
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

    public String getFormattedData() {
        return firstName + "|" + lastName + "|" + DOB + "|" + gender + "|" + email + "|"
                + phoneNo + "|" + address + "|" + icNo + "|" + tpNo + "|" + password;
    }

    // Implementing the abstract method from superclass
    public static void saveUserToFile(AdminStudentRegistration newStudent) {
        if (!validateInputs(newStudent)) {
            JOptionPane.showMessageDialog(null, "Please fill in all the required fields.", "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isDuplicateRecord(newStudent.getEmail(), newStudent.getPhone(), newStudent.getIcNumber(), newStudent.getTpNumber())) {
            JOptionPane.showMessageDialog(null, "A user with the provided credentials already exists.", "Duplicate Record", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isValidTpNumber(newStudent.getTpNumber())) {
            JOptionPane.showMessageDialog(null, "Invalid TP Number. TP Number should start with 'TP' followed by 6 digits.", "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(newStudent.getEmail())) {
            JOptionPane.showMessageDialog(null, "Invalid email format. Email should be in the format xxx@email.com.", "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(newStudent.getPhone())) {
            JOptionPane.showMessageDialog(null, "Invalid Phone Number format. Phone Number should be in the format xx-xxxxxxx.", "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            FileWriter writer = new FileWriter("user.txt", true);
            writer.write(newStudent.getFormattedData() + "\n");
            writer.close();

            JOptionPane.showMessageDialog(null, "User information saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while saving user information.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static boolean validateInputs(AdminStudentRegistration newStudent) {
        return !newStudent.firstName.isEmpty() && !newStudent.lastName.isEmpty() && !newStudent.email.isEmpty()
                && !newStudent.phoneNo.isEmpty() && !newStudent.address.isEmpty() && !newStudent.icNo.isEmpty()
                && !newStudent.tpNo.isEmpty() && !newStudent.password.isEmpty();
    }

    public static boolean isDuplicateRecord(String email, String phoneNo, String icNo, String tpNo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData.length >= 9) {
                    String existingEmail = userData[4];
                    String existingPhoneNo = userData[5];
                    String existingIcNo = userData[7];
                    String existingTpNo = userData[8];

                    if (existingEmail.equals(email) || existingPhoneNo.equals(phoneNo)
                            || existingIcNo.equals(icNo) || existingTpNo.equals(tpNo)) {
                        reader.close();
                        return true; // Duplicate record found
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // No duplicate record found
    }

    // Validating tpNo input
    private static boolean isValidTpNumber(String tpNo) {
        return tpNo.matches("^TP\\d{6}$");
    }

    // Validating email input
    private static boolean isValidEmail(String email) {
        return email.matches("^\\S+@\\S+\\.com$");
    }

    // Validating phoneNo input
    private static boolean isValidPhoneNumber(String phoneNo) {
        return phoneNo.matches("^\\d{2,}-\\d{7,}$");
    }

    public static void clearFields(JComboBox<String> jComboBoxStuRegDOBDay, JComboBox<String> jComboBoxStuRegDOBMonth,
            JComboBox<String> jComboBoxStuRegDOBYear, JRadioButton jRadioButtonStuRegFemale,
            JRadioButton jRadioButtonStuRegMale, JTextField jTextFieldStuRegAddress,
            JTextField jTextFieldStuRegEmail, JTextField jTextFieldStuRegFirstName,
            JTextField jTextFieldStuRegICNumber, JTextField jTextFieldStuRegLastName,
            JTextField jTextFieldStuRegPassword, JTextField jTextFieldStuRegConfirmPassword,
            JTextField jTextFieldStuRegPhoneNumber, JTextField jTextFieldStuRegTPNumber) {
        jComboBoxStuRegDOBDay.setSelectedIndex(0);
        jComboBoxStuRegDOBMonth.setSelectedIndex(0);
        jComboBoxStuRegDOBYear.setSelectedIndex(0);
        jRadioButtonStuRegFemale.setSelected(false);
        jRadioButtonStuRegMale.setSelected(false);
        jTextFieldStuRegAddress.setText("");
        jTextFieldStuRegEmail.setText("");
        jTextFieldStuRegFirstName.setText("");
        jTextFieldStuRegICNumber.setText("");
        jTextFieldStuRegLastName.setText("");
        jTextFieldStuRegPassword.setText("");
        jTextFieldStuRegConfirmPassword.setText("");
        jTextFieldStuRegPhoneNumber.setText("");
        jTextFieldStuRegTPNumber.setText("");
    }

    @Override
    public void register() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modifyProfile(String TP, String PW, String newFirstName, String newLastName, String newDOB, String newGender, String newEmail, String newPhoneNo, String newAddress, String newICNo, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saveStudentInformation(int selectedRow, JTextField firstNameField, JTextField lastNameField, JComboBox<String> dobDayComboBox, JComboBox<String> dobMonthComboBox, JComboBox<String> dobYearComboBox, JRadioButton femaleRadioButton, JRadioButton maleRadioButton, JTextField emailField, JTextField phoneNoField, JTextField addressField, JTextField icNoField, JTextField tpNoField, JTextField passwordField, JTable jTableUpdateDeleteStuInfo) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



}
