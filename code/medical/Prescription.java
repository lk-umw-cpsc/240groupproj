package code.medical;

/**
 * This class holds constructors getters and setters for prescriptions
 */
public class Prescription 
{
    
    private String name;
    private int quantity;
    private int refills;
    private String doctorName;
    
    /**
     * Default constructor that applies default settings
     */
    public Prescription()
    {
        this.name = "";
        this.quantity = 0;
        this.refills = 0;
        this.doctorName = "";
    }

    /**
     * Constructor that receives params to build a prescription
     * @param name Medical or common name of medicine
     * @param quantity quantity left
     * @param refills refills left
     * @param doctorName Prescribing doctors name first and last
     */
    public Prescription(String name, int quantity, int refills, String doctorName)
    {
        this.name = name;
        this.quantity = quantity;
        this.refills = refills;
        this.doctorName = doctorName;
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

    

    

}
