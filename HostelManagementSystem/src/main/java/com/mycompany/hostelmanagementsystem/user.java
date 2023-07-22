/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hostelmanagementsystem;

/**
 *
 * @author User
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class user {

    protected String firstName;
    protected String lastName;
    protected String DOB;
    protected String gender;
    protected String email;
    protected String phoneNo;
    protected String address;
    protected String icNo;
    protected String tpNo;
    protected String password;

    public user(String firstName, String lastName, String DOB, String gender, String email,
            String phoneNo, String address, String icNo, String tpNo, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.gender = gender;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.icNo = icNo;
        this.tpNo = tpNo;
        this.password = password;

    }

    public user(String tpNo, String password) {
        this.tpNo = tpNo;
        this.password = password;
    }
    public user(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getTpNo() {
        return tpNo;
    }

    public void setTpNo(String tpNo) {
        this.tpNo = tpNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void register();

    public abstract boolean modifyProfile(String TP, String PW, String newFirstName, String newLastName, String newDOB,
            String newGender, String newEmail, String newPhoneNo, String newAddress,
            String newICNo, String newPassword);


}

class Student extends user {

    private static String filepath = "user.txt";

    public Student(String firstName, String lastName, String DOB, String gender, String email, String phoneNo,
            String address, String icNo, String tpNo, String password) {
        super(firstName, lastName, DOB, gender, email, phoneNo, address, icNo, tpNo, password);

    }

    public Student(String tpNo, String password) {
        super(tpNo, password);
    }

    public Student() {
        super("", "", "", "", "", "", "", "", "", "");
        // Set default values for the instance variables
    }

    //To check if the dupiacation of data existed in the user.txt while registrating student.
    public boolean checkDuplicateReg() {
        ArrayList<String> credentials = new ArrayList<>();
        ArrayList<String> duplicateCredentials = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            System.out.println("tracking where error");
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData.length >= 9) {
                    credentials.add(userData[4]); // email
                    credentials.add(userData[5]); // phoneNo
                    credentials.add(userData[7]); // icNo
                    credentials.add(userData[8]); // tpNo
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading user data from file.");
            e.printStackTrace();
            return true;
        }
        if (credentials.contains(email)) {
            duplicateCredentials.add("Email");
        }
        if (credentials.contains(phoneNo)) {
            duplicateCredentials.add("Phone number");
        }
        if (credentials.contains(icNo)) {
            duplicateCredentials.add("IC number");
        }
        if (credentials.contains(tpNo)) {
            duplicateCredentials.add("TP number");
        }
        if (!duplicateCredentials.isEmpty()) {
            StringBuilder message = new StringBuilder("A user with the following credential(s) already exists:\n");
            for (String duplicateCredential : duplicateCredentials) {
                message.append("- ").append(duplicateCredential).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString(),
                    "Duplicate Credential", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    //after checking checkDuplicateReg=false, write registration data into user.txt
    public boolean writeToFile() {
        String userData = firstName + "|" + lastName + "|" + DOB + "|" + gender + "|"
                + email + "|" + phoneNo + "|" + address + "|" + icNo + "|" + tpNo + "|" + password + "\n";
        try {
            FileWriter writer = new FileWriter(filepath, true);
            writer.write(userData);
            writer.close();
            System.out.println("User data written to file successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while writing user data to file.");
            e.printStackTrace();
            return false;
        }
    }

    //after writeToFile()=true, pop up message to inform their credential
    public void retrieveDataInform() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            String latestUserData = "";
            while ((line = reader.readLine()) != null) {
                latestUserData = line;
            }
            reader.close();

            String[] userData = latestUserData.split("\\|");
            StringBuilder message = new StringBuilder("Account Registration Successful\n");
            message.append("First Name: ").append(userData[0]).append("\n");
            message.append("Last Name: ").append(userData[1]).append("\n");
            message.append("DOB: ").append(userData[2]).append("\n");
            message.append("Gender: ").append(userData[3]).append("\n");
            message.append("Email: ").append(userData[4]).append("\n");
            message.append("Phone Number: ").append(userData[5]).append("\n");
            message.append("Address: ").append(userData[6]).append("\n");
            message.append("IC Number: ").append(userData[7]).append("\n");
            message.append("TP Number: ").append(userData[8]).append("\n");

            JOptionPane.showMessageDialog(null, message.toString(),
                    "Account Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.out.println("An error occurred while reading user data from file.");
            e.printStackTrace();
        }
    }

    //register method 
    @Override
    public void register() {
        if (checkDuplicateReg()) {
            System.out.println("Registration failed due to duplicate credentials.");
            return;
        }
        if (writeToFile()) {
            System.out.println("Student registered successfully.");
            JOptionPane.showMessageDialog(null, "Registration Successful!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            retrieveDataInform();
        } else {
            System.out.println("Registration failed due to an error while writing data to file.");
        }
    }

    //retrieve data for MyProfile
    public static String[] retrieveData(String tpNumber, String password) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            String foundUserData = null;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                String tpNo = userData[8];
                String userPassword = userData[9];

                if (tpNo.equals(tpNumber) && userPassword.equals(password)) {
                    foundUserData = line;
                    System.out.println(foundUserData);

                    break;
                }
            }
            reader.close();

            if (foundUserData != null) {
                String[] userData = foundUserData.split("\\|");
                System.out.println(userData);
                System.out.println(Arrays.toString(userData));

                // Return the account details as a String array
                return userData;

            } else {
                // Return null if account not found
                return null;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading user data from file.");
            e.printStackTrace();
            // Return null or throw an exception based on your error handling strategy
            return null;
        }
    }

    //modify own profile
    @Override
    public boolean modifyProfile(String TP, String PW, String newFirstName, String newLastName, String newDOB,
            String newGender, String newEmail, String newPhoneNo, String newAddress,
            String newICNo, String newPassword) {

        List<String> linesStore = new ArrayList<>();
        String originalLine = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            boolean modified = false; // Flag to track if any modification was made

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                String tpNo = userData[8];
                String userPassword = userData[9];

                if (tpNo.equals(TP) && userPassword.equals(PW)) {
                    originalLine = line;
                    // Modify the line with new data
                    line = newFirstName + "|" + newLastName + "|" + newDOB + "|" + newGender + "|"
                            + newEmail + "|" + newPhoneNo + "|" + newAddress + "|" + newICNo + "|" + TP + "|"
                            + newPassword;

                }

                linesStore.add(line);
            }

            reader.close();
            if (isDuplicateExist(newEmail, newICNo, newPhoneNo, originalLine)) {
                System.out.println("Duplicate credentials found. Cannot modify the profile.");
                return false;
            }

            // Write the modified data back to the file
            Files.write(Paths.get(filepath), linesStore);
            System.out.println("Profile modified successfully.");
            JOptionPane.showMessageDialog(null, "Profile modified successfully. Back to profile",
                    "Account Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while modifying the profile.");
            e.printStackTrace();
        }
        return false;
    }

    //when modifying own profile,check EMAIL,IC or Phone Number pre-existed in the user.txt BUT exclude the original line(line to be modify) for duplication checking
    private static boolean isDuplicateExist(String email, String icNo, String phoneNo, String originalLine) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(originalLine)) { // Exclude the original line from comparison
                    String[] userData = line.split("\\|");
                    String existingEmail = userData[4];
                    String existingIcNo = userData[7];
                    String existingPhoneNo = userData[5];

                    if (existingEmail.equals(email) || existingIcNo.equals(icNo) || existingPhoneNo.equals(phoneNo)) {
                        reader.close();
                        String message = "Duplicate credential found:\n\n";
                        if (existingEmail.equals(email)) {
                            message += "Email: " + email + "\n";
                        }
                        if (existingIcNo.equals(icNo)) {
                            message += "IC Number: " + icNo + "\n";
                        }
                        if (existingPhoneNo.equals(phoneNo)) {
                            message += "Phone Number: " + phoneNo + "\n";
                        }
                        JOptionPane.showMessageDialog(null, message, "Duplicate Credential", JOptionPane.WARNING_MESSAGE);
                        return true; // Duplicated credential found
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // No duplicated credential found
    }
}
