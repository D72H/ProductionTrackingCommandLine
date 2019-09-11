package com.company;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //stores the data points generated from the reports to work with before printing the final production report.
        ArrayList<ProcessingData>  processingData = new ArrayList<>();
        ArrayList<ShippingData> shippingData = new ArrayList<>();

        System.out.println("What do you want.");

        Scanner commandLine = new Scanner(System.in);

        while(!commandLine.nextLine().toLowerCase().equals("exit")) {
            System.out.println("What do you want");

            if(commandLine.nextLine().equals("read")){
                readProcessingReports(new File("C:" +File.separator +"Sync"+File.separator+"TTW-Shared"+File.separator+"Employees"+File.separator+"Matthew"+File.separator+"MSBShips"+File.separator+"ProcessingReports"), processingData);
                readShippingReports(new File("C:" +File.separator +"Sync"+File.separator+"TTW-Shared"+File.separator+"Employees"+File.separator+"Matthew"+File.separator+"MSBShips"+File.separator+"ShippingReports"), shippingData);
                processingData.forEach((n -> System.out.println(n.toString())));
                shippingData.forEach((n -> System.out.println(n.toString())));
            }

        }
    }
    //reads the processing reports and calls the method to generate the date, value, initials object.
    public static void readProcessingReports(File file, ArrayList<ProcessingData> processingData) throws IOException {
        //if the directory exists and is not empty make an array of all files in the directory.
        if(file!=null && file.exists()){
            File[] listOfFiles = file.listFiles();
            //make sure the array is not null.
            if(listOfFiles!=null){
                //begin stepping through each file stored in the array and submit them to the buffered reader.
                for (int k = 0; k < listOfFiles.length; k++) {
                    //check to make sure each file in the array exists.
                    if (listOfFiles[k].isFile()) {
                        BufferedReader TSVFile = new BufferedReader(new FileReader(listOfFiles[k]));

                        //Storage for each line while they are parsed.
                        String dataRow = TSVFile.readLine(); // Read first line.
                        //Read Line by line splitting on the tabs in the .tsv file.
                        while (dataRow != null) {
                            String[] dataArray = dataRow.split("\t");

                            processingData.add(new ProcessingData(parseInitials(dataArray[11]), LocalDate.parse(dataArray[12]), Integer.parseInt(dataArray[10])));

                            dataRow = TSVFile.readLine();
                        }
                    }
                }
            }
        }
    }
    //reads the shipping reports and calls the method to generate the date, value pairs. only need to read the first line of each report.
    public static void readShippingReports(File file, ArrayList<ShippingData> shippingData) throws IOException {
//if the directory exists and is not empty make an array of all files in the directory.
        if(file!=null && file.exists()){
            File[] listOfFiles = file.listFiles();
            //make sure the array is not null.
            if(listOfFiles!=null){
                //begin stepping through each file stored in the array and submit them to the buffered reader.
                for (int k = 0; k < listOfFiles.length; k++) {
                    //check to make sure each file in the array exists.
                    if (listOfFiles[k].isFile()) {
                        BufferedReader TSVFile = new BufferedReader(new FileReader(listOfFiles[k]));
                        //Storage for each line while they are parsed.
                        String dataRow = TSVFile.readLine(); // Read first line.
                        //splitting on the tabs in the .tsv file.
                            String[] dataArray = dataRow.split("\t");
                            shippingData.add(new ShippingData(LocalDate.parse(dataArray[4]), Integer.parseInt(dataArray[3])));
                    }
                }
            }
        }
    }

    //takes the comma separated string of initials of who processed and approved the item and separates out the supervisor.
    // FIXME: 9/11/2019 not getting the right column
    public static String parseInitials(String rawInitials){
        String[] initials = rawInitials.split(",");
        StringBuilder builder = new StringBuilder();

        //remove the last set of initials since they are always the supervisor initials.
        for(int i = 0; i < initials.length-1; i++){
            builder.append(initials[i] + ",");
        }
        return builder.toString().toLowerCase();
    }
}
