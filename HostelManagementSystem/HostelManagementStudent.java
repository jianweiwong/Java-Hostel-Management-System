/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.hostelmanagementstudent;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class HostelManagementStudent {

    private static String firstName;
    private static String lastName;
    private static String tpNumber;
    private static String password;

    public static void main(String[] args) {
            ArrayList<String> MyAcceptedApplication = new ArrayList<>();
            MyAcceptedApplication = application.readMyApplication("TP000000", "Accepeted");
            System.out.println(MyAcceptedApplication);
    }

}
/*
        List<String> emptyRooms = readLinesFromFile("roomStatus.txt");
        for (String line : emptyRooms) {
            System.out.println(line);
        }
        // Read room details from room.txt and filter based on room status
        List<String> roomLines = readLinesFromFile("room.txt");
        for (String line : roomLines) {
            System.out.println(line);
        }
        List<String> filteredRoomLines = filterRoomLines(roomLines, emptyRooms);

        // Print the filtered room details
        System.out.println("split_____________________________________");
        for (String line : filteredRoomLines) {
            System.out.println(line);
        }
    }

    private static List<String> readLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }



    private static List<String> filterRoomLines(List<String> roomLines, List<String> emptyRooms) {
        List<String> filteredRoomLines = new ArrayList<>();
        for (String line : roomLines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 2) {
                String roomNumber = parts[1];
                if (emptyRooms.contains(roomNumber)) {
                    filteredRoomLines.add(line);
                }
            }
        }
        return filteredRoomLines;
    }
}
 */
//System.out.println("Hello World!");
//Student student = new Student("2", "2", "10-10-2002", "Male", "d2@", "2", "2", "2", "TP2", "2", "Student");
//Student.modifyProfile("TP1", "123" , "n2", "n2", "n2","n2", "n1", "n1", "n2","n1", "n1");
//student.register();
/*
         List<String> emptyRooms = readLinesFromFile("roomStatus.txt");

        // Read room details from room.txt and filter based on room status
        List<String> roomLines = readLinesFromFile("room.txt");
        List<String> filteredRoomLines = filterRoomLines(roomLines, emptyRooms);

        // Print the filtered room details
        for (String line : filteredRoomLines) {
            System.out.println(line);
        }
    }

    private static List<String> readLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static List<String> filterRoomLines(List<String> roomLines, List<String> emptyRooms) {
        List<String> filteredRoomLines = new ArrayList<>();
        for (String line : roomLines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 2) {
                String roomNumber = parts[1];
                if (emptyRooms.contains(roomNumber)) {
                    filteredRoomLines.add(line);
                }
            }
        }
        return filteredRoomLines;
    }
}
 *//*
        String[] accountDetails = {"John", "Doe", "Male", "1990-01-01", "john.doe@example.com", "1234567890", "123 Main St", "123456789", "TP123456", "password"};

        String[] vName = new String[accountDetails.length];
        String[] variableNames = {"stdFirstName", "stdLastName", "stdGender", "stdDOB", "stdEmail", "stdPhoneNo", "stdAddress", "stdIcNo", "stdTpNo", "stdPassword"};

        for (int i = 0; i < accountDetails.length; i++) {
            String variableName = variableNames[i];
            String value = accountDetails[i];
            String assignment = variableName + " = \"" + value + "\";";
            vName[i] = assignment;
            System.out.println(vName[i]);
        }
        /*
        // Call the retrieveData method and pass the TP number and password
        String[] accountDetails = Student.retrieveData("TP064298", "123");

        if (accountDetails != null) {
            // Assign the retrieved values to the local variables
            firstName = accountDetails[0];
            lastName = accountDetails[1];
            tpNumber = accountDetails[8];
            password = accountDetails[9];

            // Use the assigned values as desired
            System.out.println("Account details retrieved successfully:");
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("TP Number: " + tpNumber);
            System.out.println("Password: " + password);
        } else {
            System.out.println("Account not found.");
            // Handle the case when account details are not found
        }
 */
// Assign the retrieved data to instance variable

