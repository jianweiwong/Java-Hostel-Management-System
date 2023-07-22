/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hostelmanagementsystem;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author wongj
 */
public abstract class admin extends user {

    public admin(String firstName, String lastName, String DOB, String gender, String email, String phoneNo, String address, String icNo, String tpNo, String password) {
        super(firstName, lastName, DOB, gender, email, phoneNo, address, icNo, tpNo, password);
    }
    public admin(){}
  
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDOB() { return DOB; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public String getPhone() { return phoneNo; }
    public String getAddress() { return address; }
    public String getIcNumber() { return icNo; }
    public String getTpNumber() { return tpNo; }
    public String getPassword() { return password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDOB(String DOB) { this.DOB = DOB; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phoneNo) { this.phoneNo = phoneNo; }
    public void setAddress(String address) { this.address = address; }
    public void setIcNumber(String icNo) { this.icNo = icNo; }
    public void setTpNumber(String tpNo) { this.tpNo = tpNo; }
    public void setPassword(String password) { this.password = password; }
    
   
    public void saveUserToFile(){};
    public abstract void saveStudentInformation(int selectedRow, JTextField firstNameField, JTextField lastNameField,
            JComboBox<String> dobDayComboBox, JComboBox<String> dobMonthComboBox, JComboBox<String> dobYearComboBox,
            JRadioButton femaleRadioButton, JRadioButton maleRadioButton, JTextField emailField, JTextField phoneNoField, JTextField addressField,
            JTextField icNoField, JTextField tpNoField, JTextField passwordField, JTable jTableUpdateDeleteStuInfo);
    
}
