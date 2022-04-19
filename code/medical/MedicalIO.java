package code.medical;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * This class was supposed to integrate the medical IO tab but I ran out of time -Joon
 */
public class MedicalIO 
{
    private static ArrayList<Prescription> prescriptionList = new ArrayList<Prescription>();  
    private static ArrayList<Doctor> doctorList = new ArrayList<Doctor>();  

    /**
     * This ran medicalTest for testing purposes
     * @throws FileNotFoundException if no file is found
     */
    public static void medicalTest() throws FileNotFoundException
    {

        FileInputStream fileInName = new FileInputStream("240GROUPPROJ/../data/Doctors.txt");
        Scanner fileIn = new Scanner(fileInName);

        //this deletes the first line which contains   Name||LastVisit||PhoneNumber||officeLocation||website||specialization
        String newLine = fileIn.nextLine();
        while (fileIn.hasNextLine())
        {
            newLine = fileIn.nextLine();
            String[] parser = newLine.split("###");
            Doctor temp = new Doctor(parser[0], parser[1], parser[2],parser[3], parser[4], parser[5]);
            doctorList.add(temp);
        }
        
        fileIn.close();

    }

    /**
     * This would run to build the medical data at the begging of program start
     */
    public void buildMedical()
    {
        try {
            // TO-DO: handle exceptions within each of these method calls
            buildDoctorList();
            buildPrescriptionList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectDrPrescriptions();
        listPrescriptions();

    }

    /**
     * System print out of doctors data
     */
    public static void listDoctors()
    {
        for (Doctor doctor : doctorList)
        {
            System.out.println(doctor.getDoctorName());
            System.out.println(doctor.getLastVisit());
            System.out.println(doctor.getDoctorPhoneNumber());
            System.out.println(doctor.getDoctorOfficeLocation());
            System.out.println(doctor.getDoctorWebsite());
            System.out.println(doctor.getDoctorSpecialization());
        }
    }

    /**
     * List of prescriptions by doctors
     */
    public static void listPrescriptions()
    {
        System.out.printf("%-30s" + "%-14s" + "%-20s" + "%25s\n" ,"Prescription", "Quantity", "Refills", "Prescriber");

        for (int i=0; i < prescriptionList.size(); i++) 
        {
            System.out.printf("%-30s" + "%-14s" + "%-20s" + "%25s\n", prescriptionList.get(i).getPrescriptionName(), prescriptionList.get(i).getPrescriptionQuantity(), prescriptionList.get(i).getPrescriptionRefills(), prescriptionList.get(i).getDoctorName());
        }
        
    }

    /**
     * connects a dr to their prescriptions 
     */
    public static void connectDrPrescriptions()
    {
        for (Doctor doctor : doctorList)
        {
            for (int i = 0; i < prescriptionList.size(); i++)
            {
                if (doctor.getDoctorName().equals(prescriptionList.get(i).getDoctorName()))
                {
                    doctor.addDrPrescription(prescriptionList.get(i));
                    //System.out.println(doctor.getDoctorName() + " prescribed " + prescriptionList.get(i).getPrescriptionName());
                }

            }

            //System.out.println("Printing out doctors and prescriptions");
            //doctor.listDrPrescriptions();

        }

    }

    /**
     * Builds the doctors list from that Doctors.txt file
     * @throws FileNotFoundException throws if no doctors.txt
     */
    public static void buildDoctorList() throws FileNotFoundException
    {

        FileInputStream fileInName = new FileInputStream("240GROUPPROJ/../data/Doctors.txt");
        Scanner fileIn = new Scanner(fileInName);

        //this deletes the first line which contains   Name###LastVisit###PhoneNumber###officeLocation###website###specialization
        String newLine = fileIn.nextLine();
        while (fileIn.hasNextLine())
        {
            newLine = fileIn.nextLine();
            String[] parser = newLine.split("###");
            Doctor temp = new Doctor(parser[0], parser[1], parser[2],parser[3], parser[4], parser[5]);
            doctorList.add(temp);
        }
        fileIn.close();

    }
    
    /**
     * Builds the prescription list from the prescriptions .txt
     * @throws FileNotFoundException throws if there's no prescriptions.txt
     */
    public static void buildPrescriptionList() throws FileNotFoundException
    {

        FileInputStream fileInName = new FileInputStream("240GROUPPROJ/../data/Prescriptions.txt");
        Scanner fileIn = new Scanner(fileInName);

        //this deletes the first line which contains   Prescription###Qty###Refill###DoctorName
        String newLine = fileIn.nextLine();
        while (fileIn.hasNextLine())
        {
            newLine = fileIn.nextLine();
            String[] parser = newLine.split("###");
            Prescription temp = new Prescription(parser[0], Integer.parseInt(parser[1]), Integer.parseInt(parser[2]),parser[3]);
            prescriptionList.add(temp);
        }
        fileIn.close();

    }

}
