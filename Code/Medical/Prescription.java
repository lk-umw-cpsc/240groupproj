package code.medical;

public class Prescription 
{
    
    private String name;
    private int quantity;
    private int refills;
    private String doctorName;
    
    public Prescription()
    {
        this.name = "";
        this.quantity = 0;
        this.refills = 0;
        this.doctorName = "";
    }

    public String getPrescriptionName()
    {
        return this.name;
    }

    public void setPrescriptionName(String name)
    {
        this.name = name;
    }

    public int getPrescriptionQuantity()
    {
        return this.quantity;
    }

    public void setPrescriptionQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getPrescriptionRefills()
    {
        return this.refills;
    }

    public void setPrescriptionRefills(int refills)
    {
        this.refills = refills;
    }

    public String getDoctorName()
    {
        return this.doctorName;
    }

    /*
    
    public void viewPrescriptions()
    {
        System.out.printf("%-30s" + "%4s" + "%4s" + "%30s\n", "" ,"Prescription", "Quantity", "Refills", "Prescriber");

        for (int i=0; i<prescriptionList.size(); i++) 
        {
            System.out.printf("%-30s" + "%4s" + "%4s" + "%30s\n", prescriptionList.get(i).getPrescriptionName(), prescriptionList.get(i).getPrescriptionQuantity(), prescriptionList.get(i).getPrescriptionRefills(), prescriptionList.get(i).getDoctorName());
        }
        
    }

    */
}
