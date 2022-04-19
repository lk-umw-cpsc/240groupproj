package code.medical;
import java.util.ArrayList;

/**
 * This tab creates doctors for the program
 */
public class Doctor
{
    private String name;
    private String lastVisit;
    private String phoneNumber;
    private ArrayList<Prescription> drPrescriptionList;  
    private String officeLocation;
    private String website;
    private String specialization;
    
    /**
     * This is the default constructor that creates a basic Dr
     */
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

    /**
     * This is a doctor constructor that accepts specific params 
     * Default to creating doctors requires this information
     * @param name Name of doctor First last
     * @param lastVisit Last visit by string (may be converted to calender sheet)
     * @param phoneNumber Phone number
     * @param officeLocation Street address
     * @param website website if available
     * @param specialization focus (PCP, orthodontist)
     */
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

    /**
     * This retrieves all doctors prescriptions from list
     */
    public void viewDoctorsPrescriptions()
    {
        System.out.printf("%-25s" + "%4s" + "%4s\n", "Prescription", "Quantity", "Refills");

        for (int i=0; i < drPrescriptionList.size(); i++) 
        {
            System.out.printf("%-25s" + "%4s" + "%4s\n", drPrescriptionList.get(i).getPrescriptionName(), drPrescriptionList.get(i).getPrescriptionQuantity(), drPrescriptionList.get(i).getPrescriptionRefills());
        }
        
    }

    /**
     * this lists a specific doctors prescriptions
     */
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
