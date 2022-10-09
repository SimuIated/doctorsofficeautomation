import java.util.Scanner;

public class Doctor extends Employee{

    private PatientList patients;
    private Patient currentPatient;
    private int numberOfPatients;

    public Doctor(int employeeId, String employeeName){
        super(employeeId, employeeName);
        this.patients = new PatientList();
        this.currentPatient = null;
        this.numberOfPatients = 0;
    }

    public void setCurrentPatient(int patientID){this.currentPatient = patients.findPatientbyID(patientID);}

    public void addPatient(Patient newPatient){
        patients.addPatient(newPatient);
        numberOfPatients++;
    }

    public void writeMessagetoCurrentPatient(){
        Scanner input = new Scanner(System.in);
        Messages newMessage = new Messages(getName(), currentPatient.returnName(), input);
        getMessages().addMessage(newMessage);
        currentPatient.addMessage(newMessage);
    }
    
    public void checkCurrentPatient(){currentPatient.getPatientInfo();}
    public void printPatients(){this.patients.printPatients();}
    public int patientCount(){return this.numberOfPatients;}
}