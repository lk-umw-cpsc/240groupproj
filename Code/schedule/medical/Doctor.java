package code.schedule.medical;
import java.util.ArrayList;


public class Doctor
{
    
    private String lastVisit;
    private String name;
    private String phoneNumber;
    private ArrayList<Prescription> prescriptionList;  
    private String officeLocation;
    private String website;
    private String specialization;
    
    public Doctor()
    {
        this.lastVisit = "No Last Visit";
        this.name = "";
        this.phoneNumber = "";
        this.prescriptionList = new ArrayList<Prescription>();
        this.officeLocation = "";
        this.website = "No Website";
        this.specialization = "";
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

    public void addPrescription(Prescription prescription)
    {
        prescriptionList.add(prescription);
    }

    public void viewDoctorPrescriptions()
    {
        System.out.printf("%-30s" + "%4s" + "%4s", "" ,"Prescription", "Quantity", "Refills");

        for (int i=0; i<prescriptionList.size(); i++) 
        {
            System.out.printf("%-30s" + "%4s" + "%4s", prescriptionList.get(i).getPrescriptionName(), prescriptionList.get(i).getPrescriptionQuantity(), prescriptionList.get(i).getPrescriptionRefills());
        }
        
    }
    
}
