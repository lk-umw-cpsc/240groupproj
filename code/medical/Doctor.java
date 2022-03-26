package code.medical;
import java.util.ArrayList;


public class Doctor
{
    
    private String name;
    private String lastVisit;
    private String phoneNumber;
    private ArrayList<Prescription> drPrescriptionList;  
    private String officeLocation;
    private String website;
    private String specialization;
    
    public Doctor()
    {
        this.name = "";
        this.lastVisit = "No Last Visit";
        this.phoneNumber = "No Phone Number";
        this.drPrescriptionList = new ArrayList<Prescription>();
        this.officeLocation = "No Location";
        this.website = "No Website";
        this.specialization = "No Specialization";
    }

    public Doctor(String name, String lastVisit, String phoneNumber, String officeLocation, String website, String specialization)
    {
        this.name = name;
        this.lastVisit = lastVisit;
        this.phoneNumber = phoneNumber;
        this.drPrescriptionList = new ArrayList<Prescription>();
        this.officeLocation = officeLocation;
        this.website = website;
        this.specialization = specialization;
    }


    public String getLastVisit()
    {
        return this.lastVisit;
    }

    public void setLastVisit(String lastVisit)
    {
        this.lastVisit = lastVisit;
    }

    public String getDoctorName()
    {
        return this.name;
    }

    public void setDoctorName(String name)
    {
        this.name = name;
    }

    public String getDoctorPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setDoctorPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getDoctorOfficeLocation()
    {
        return this.officeLocation;
    }

    public void setDoctorOfficeLocation(String officeLocation)
    {
        this.officeLocation = officeLocation;
    }

    public String getDoctorWebsite()
    {
        return this.website;
    }

    public void setDoctorWebsite(String website)
    {
        this.website = website;
    }

    public String getDoctorSpecialization()
    {
        return this.specialization;
    }

    public void setDoctorSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    public void addDrPrescription(Prescription prescription)
    {
        drPrescriptionList.add(prescription);
    }

    public void viewDoctorsPrescriptions()
    {
        System.out.printf("%-25s" + "%4s" + "%4s\n", "Prescription", "Quantity", "Refills");

        for (int i=0; i < drPrescriptionList.size(); i++) 
        {
            System.out.printf("%-25s" + "%4s" + "%4s\n", drPrescriptionList.get(i).getPrescriptionName(), drPrescriptionList.get(i).getPrescriptionQuantity(), drPrescriptionList.get(i).getPrescriptionRefills());
        }
        
    }

    public void listDrPrescriptions()
    {
        if (drPrescriptionList.size() > 0)
        {
            for (int i=0; i < drPrescriptionList.size(); i++) 
            {
                System.out.println(drPrescriptionList.get(i).getPrescriptionName());
            }
        }
        else
        {
            System.out.println("There are no prescriptions.");
        }
        
    }


    
}