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
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
// Abstraction class: RoomManagement
public abstract class roomManagement {

    protected String roomType;
    protected String roomNo;
    protected double price;
    protected String description;
    protected String tpNo;
    protected String startMonth;
    protected String endMonth;
    protected int duration;
    protected String applicationStatus;
    protected String refNo;
    protected String roomStatus;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTpNo() {
        return tpNo;
    }

    public void setTpNo(String tpNo) {
        this.tpNo = tpNo;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    // Attribute variables for class RoomManagement
    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }


    public roomManagement(String roomType, String roomNo, double price, String description) {

        this.roomType = roomType;
        this.roomNo = roomNo;
        this.price = price;
        this.description = description;

    }

    public roomManagement(String tpNo, String roomNo, String startMonth, String endMonth, int duration, double price,
            String applicationStatus, String refNo) {

        this.tpNo = tpNo;
        this.roomNo = roomNo;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.duration = duration;
        this.price = price;
        this.applicationStatus = applicationStatus;
        this.refNo = refNo;

    }

    public roomManagement(String roomNo, String roomStatus) {

        this.roomNo = roomNo;
        this.roomStatus = roomStatus;

    }

    public static ArrayList<String> RoomDetails() {

        String roomDetailsFilePath = "room.txt";
        ArrayList<String> AllRoomDetailsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(roomDetailsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                AllRoomDetailsList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AllRoomDetailsList;

    }

}

// Subclass: Room
class room extends roomManagement {


    public room(String roomType, String roomNo, double price, String description) {
        super(roomType, roomNo, price, description);
    }// Add this line to call the superclass constructor // Additional code for the subclass constructor if needed } 

    // Implementing the abstract method from superclass
    //this is to read every room details, or return spefic room details
    public static ArrayList<String> RoomDetails(String mode) {
        if (mode == "all") {
            ArrayList<String> emptyRoomsList = ReadEmptyRoom();
            String roomDetailsFilePath = "room.txt";
            ArrayList<String> matchingRoomDetailsList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(roomDetailsFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] roomData = line.split("\\|");
                    String roomNumber = roomData[1];

                    if (emptyRoomsList.contains(roomNumber)) {
                        matchingRoomDetailsList.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return matchingRoomDetailsList;
        } else {
            String RoomNumber = mode;
            ArrayList<String> matchingRoomDetailsList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("room.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] roomData = line.split("\\|");

                    if (roomData[1].equals(RoomNumber)) {
                        matchingRoomDetailsList.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return matchingRoomDetailsList;
        }
    }

    private static ArrayList<String> ReadEmptyRoom() {
        String filePath = "roomStatus.txt"; // Replace with the actual file path
        ArrayList<String> emptyRoomsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("roomStatus.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] roomData = line.split("\\|");
                if (roomData.length == 2 && roomData[1].equals("Available")) {
                    emptyRoomsList.add(roomData[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emptyRoomsList;
    }
}

// Subclass: Application
class application extends roomManagement {

    public application(String tpNo, String roomNo, String startMonth, String endMonth, int duration, double price,
            String applicationStatus, String refNo) {
        super(tpNo, roomNo, startMonth, endMonth, duration, price,
                applicationStatus, refNo);
    } // Add this line to call the superclass constructor // Additional code for the subclass constructor if needed } 

    public static void addApplication(String dataSubmit) {
        String applicationFilePath = "application.txt"; // Replace with the actual file path

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(applicationFilePath, true))) {
            bw.write(dataSubmit);
            System.out.println("DONE");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    public static void DeleteApplication(String TP, String RoomNo, String refNo, String Mode) {
        String applicationFilePath = "application.txt";

        try {
            File inputFile = new File(applicationFilePath);

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder stringBuilder = new StringBuilder();

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\|");

                if (parts.length >= 8) {
                    String currentTP = parts[0];
                    String currentRoomNo = parts[1];
                    String currentRefNo = parts[7];

                    if (Mode.equals("Rejected")) {
                        if (currentTP.equals(TP) && currentRoomNo.equals(RoomNo) && currentRefNo.equals(refNo)) {
                            parts[6] = "Rejected";
                        }

                    } else if (Mode.equals("CheckOut")) {
                        if (currentTP.equals(TP) && currentRoomNo.equals(RoomNo) && currentRefNo.equals(refNo)) {
                            parts[6] = "CheckOut";
                        }
                    }

                    stringBuilder.append(String.join("|", parts)).append(System.lineSeparator());
                } else {
                    // Handle improperly formatted line (not enough elements)
                    System.out.println("Skipping line: " + currentLine);
                }
            }

            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
            writer.write(stringBuilder.toString());
            writer.close();

            System.out.println("Application status modified successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred while modifying the application status.");
            e.printStackTrace();
        }
    }

    public static boolean checkOngoingApplication(String TP) {
        String applicationFilePath = "application.txt"; // Replace with the actual file path

        try (BufferedReader br = new BufferedReader(new FileReader(applicationFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] applicationData = line.split("\\|");

                if (applicationData[0].equals(TP) && (applicationData[6].equals("Pending") || applicationData[6].equals("Accepted"))) {
                    return true; // Found ongoing or pending application
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // No ongoing or pending application found
    }

    public static ArrayList<String> readMyApplication(String TP, String Mode) {

        String filePath = "application.txt"; //
        ArrayList<String> myApplication = new ArrayList<>();
        ArrayList<String> myAcceptedRoom = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] myApplicationData = line.split("\\|");
                if (myApplicationData[0].equals(TP)) {
                    myApplication.add(line);
                    if (myApplicationData[6].equals("Accepted")) {
                        myAcceptedRoom.add(line);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Reading User Application gone wrong");
        }
        if (Mode.equals("All")) {
            return myApplication;
        } else {
            return myAcceptedRoom;

        }

    }

}

// Subclass: RoomStatus
class roomStatus extends roomManagement {

    // Constructor for class RoomStatus
    public roomStatus(String roomNo, String roomStatus) {
        super(roomNo, roomStatus);
    }

    // Implementing the abstract method from superclass
    public static void modifyRoomStatus(String RoomNumber, String refNo, String Mode) {
        String roomStatusFilePath = "roomStatus.txt"; // Replace with the actual file path
        if (Mode == "Unavailable") {
            try (BufferedReader br = new BufferedReader(new FileReader(roomStatusFilePath))) {
                ArrayList<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] roomData = line.split("\\|");

                    if (roomData[0].equals(RoomNumber)) {
                        line = roomData[0] + "|Unavailable";
                    }
                    lines.add(line);
                }

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(roomStatusFilePath))) {
                    for (String updatedLine : lines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                    System.out.println("DONE");
                    JOptionPane.showMessageDialog(null, ("Room  " + RoomNumber + " Reserved.    \n\nStatus :   PENDING    \n\nRef No :   " + refNo + "    \n\n*Track your status in Application History."), "Payment Succeccful", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR");
            }

        } else if (Mode == "Available") {
            try (BufferedReader br = new BufferedReader(new FileReader(roomStatusFilePath))) {
                ArrayList<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] roomData = line.split("\\|");

                    if (roomData[0].equals(RoomNumber)) {
                        line = roomData[0] + "|Available";
                    }
                    lines.add(line);
                }

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(roomStatusFilePath))) {
                    for (String updatedLine : lines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                    System.out.println("DONE");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR");
            }    

        }

    }

}
