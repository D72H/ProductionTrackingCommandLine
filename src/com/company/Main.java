package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //stores the data points generated from the reports to work with before printing the final production report.
        ArrayList<ProcessingData>  processingData = new ArrayList<>();
        ArrayList<ShippingData> shippingData = new ArrayList<>();



        Scanner commandLine = new Scanner(System.in);


        System.out.println("What do you want");

        if(commandLine.nextLine().equals("read")){
            readProcessingReports(new File("C:" +File.separator +"Sync"+File.separator+"TTW-Shared"+File.separator+"Employees"+File.separator+"Matthew"+File.separator+"MSBShips"+File.separator+"ProcessingReports"), processingData);
            readShippingReports(new File("C:" +File.separator +"Sync"+File.separator+"TTW-Shared"+File.separator+"Employees"+File.separator+"Matthew"+File.separator+"MSBShips"+File.separator+"ShippingReports"), shippingData);
            processingData.forEach((n -> System.out.println(n.toString())));
            shippingData.forEach((n -> System.out.println(n.toString())));

            System.out.println("\n");
            combineShippingDataByDate(shippingData).forEach((n -> System.out.println(n.toString())));
            System.out.println("\n");
            combineProcessingDataByDate(processingData).forEach((n -> System.out.println(n.toString())));
            //combineProcessingByDateAndName();

            printProcessingProductionData(combineProcessingDataByDate(processingData));
            printShippingProductionData(combineShippingDataByDate(shippingData));
        }



    }

    private static ArrayList<ProcessingData> combineProcessingDataByDate(ArrayList<ProcessingData> processingData) {
        ArrayList<ProcessingData> list = new ArrayList<>();

        for (ProcessingData shippingDatum : processingData) {
            Boolean added = false;
            if (list.isEmpty()) {
                list.add(shippingDatum);
            } else{
                for (ProcessingData data : list) {
                    if (data.getDate().equals(shippingDatum.getDate())) {
                        data.setUnitsProcessed(data.getUnitsProcessed() + shippingDatum.getUnitsProcessed());
                        added = true;
                        break;
                    }
                }
                if(!added)
                    list.add(shippingDatum);
            }
        }
        return list;
    }

    private static void combineProcessingByDateAndName() {
    }

    private static ArrayList<ShippingData> combineShippingDataByDate(ArrayList<ShippingData> shippingData) {
        ArrayList<ShippingData> list = new ArrayList<>();

        for (ShippingData shippingDatum : shippingData) {
            Boolean added = false;
            if (list.isEmpty()) {
                list.add(shippingDatum);
            } else{
                for (ShippingData data : list) {
                    if (data.getDate().equals(shippingDatum.getDate())) {
                        data.setUnitsShipped(data.getUnitsShipped() + shippingDatum.getUnitsShipped());
                        added = true;
                        break;
                    }
                }
                if(!added)
                list.add(shippingDatum);
            }
        }
        return list;
    }

    private static void printShippingProductionData(ArrayList<ShippingData> list) throws IOException {
        String objectPath = "C:" +File.separator +"Sync"+File.separator+"TTW-Shared"+File.separator+"Employees"+File.separator+"Matthew"+File.separator+"MSBShips"+File.separator+"data";

        File customDir = new File(objectPath);

        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
        } else {
            System.out.println(customDir + " was not created");
        }

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(objectPath + File.separator + "ShippingProductionData.tsv")));
        StringBuilder shippingProductionData = new StringBuilder();
        list.forEach((n -> shippingProductionData.append(n.toString() + "\n")));
        writer.write(shippingProductionData.toString());
        writer.close();

    }

    private static void printProcessingProductionData(ArrayList<ProcessingData> list) throws IOException {
        String objectPath = "C:" +File.separator +"Sync"+File.separator+"TTW-Shared"+File.separator+"Employees"+File.separator+"Matthew"+File.separator+"MSBShips"+File.separator+"data";

        File customDir = new File(objectPath);

        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
        } else {
            System.out.println(customDir + " was not created");
        }

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(objectPath + File.separator + "ProcessingProductionData.tsv")));
        StringBuilder processingProductionData = new StringBuilder();
        list.forEach((n -> processingProductionData.append(n.toString() + "\n")));
        writer.write(processingProductionData.toString());
        writer.close();

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
