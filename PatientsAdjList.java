public class PatientsAdjList {
    
    PatientList[] fullList = new PatientList[26];
    public PatientsAdjList(){
        for(int i = 0; i < fullList.length; i++)
        {
            fullList[i] = null;
        }
    }
    public void addNewPatient(Patient patient){
        int asciiNum = (int)(patient.returnName().toLowerCase().charAt(0));
        int pos = asciiNum-97;
        if(fullList[pos] == null)
        {
            fullList[pos] = new PatientList(patient);
        }
        else{
            fullList[pos].addPatient(patient);
        }
    }

    public void findbyID(int id){
        Patient found;
        for (PatientList patientList : fullList) {
            if(patientList != null)
            {
                found = patientList.findPatientbyID(id);
                if(found != null)
                {
                    found.printPatient();
                    return;
                }
            }
        }
        System.out.println("No patients with that ID");
    }

    public void findbyName(String name)
    {
        Patient found;
        int pos = (int)name.toLowerCase().charAt(0);
        if(fullList[pos] != null)
        {
            found = fullList[pos].findPatientbyName(name);
            if(found != null)
            {
                found.printPatient();
                return;
            }
        }
        System.out.println("No patients with that name");
    }

    public void makeNewPatient(){
        Patient newPatient = new Patient();
        addNewPatient(newPatient);
    }


    public void printFullList(){
        for (PatientList patientList : fullList) {
            if(patientList != null)
            {
                patientList.printPatients();
            }
            else{
                System.out.println("No patients yet");
            }
        }
    }
}