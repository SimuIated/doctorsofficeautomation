public class PatientList {
    private Patient head;
    private int patientCount;
    public PatientList(){
        head = null;
        patientCount = 0;
    }
    public PatientList(Patient newPatient){
        this.head = newPatient;
        patientCount = 1;
    }

    public void addPatient(Patient newPatient)
    {
        if(patientCount == 0)
        {
            this.head = newPatient;
        }
        else
        {
            Patient tempVar = head;
            while(tempVar.getNext() != null)
            {
                tempVar = tempVar.getNext();
            }
            tempVar.setNext(newPatient);
        }
        patientCount++;
    }

    public Patient findPatientbyName(String name)
    {
        Patient tempVar = head;
        while(tempVar != null)
        {
            if(tempVar.returnName().toLowerCase().equals(name.toLowerCase()))
            {
                return tempVar;
            }
            tempVar = tempVar.getNext();
        }
        return null;
    }

    public Patient findPatientbyID(int id)
    {
        Patient tempVar = head;
        while(tempVar != null)
        {
            if(tempVar.returnID() == id)
            {
                return tempVar;
            }
            tempVar = tempVar.getNext();
        }
        return null;
    }

    public void removePatient(Patient patient)
    {
        Patient tempVar = head;
        if(tempVar.returnID() == patient.returnID())
        {
            head = tempVar.getNext();
            return;
        }
        while(tempVar.getNext() != null)
        {
            if(tempVar.getNextPatient().returnID() == patient.returnID())
            {
                tempVar.setNext(tempVar.getNext().getNext());
                return;
            }
            tempVar = tempVar.getNext();
        }
    }

    public void printPatients(){
        Patient tempVar = head;
        while(tempVar != null)
        {
            tempVar.printPatient();
            tempVar = tempVar.getNext();
        }
    }
}