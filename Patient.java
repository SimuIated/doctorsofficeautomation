import java.util.Scanner;
public class Patient{
    
    private int id; //
    private String name; //
    private Vitals allVitals; //
    private String[] info = new String[3]; // 0-phone, 1-email, 2-birthdate
    private Archives messages; //
    private Patient next; //

    public Patient()
    {
        next = null;
        Scanner input = new Scanner(System.in);
        updateInformation(input);
        this.allVitals = new Vitals();
        this.next = null;
        this.messages = null;
    }

    public void updateInformation(Scanner input){
        System.out.println("Print Patient Name:");
        this.name = input.nextLine();
        System.out.println("Print Patient ID");
        this.id = Integer.parseInt(input.nextLine()); //whyd i do this???
        //input.nextLine();
        System.out.println("Print Patient Birthdate");
        this.info[2] = input.nextLine();
        System.out.println("Print Patient Email");
        this.info[1] = input.nextLine();
        System.out.println("Print Patient Phone");
        this.info[0] = input.nextLine();
    }

    public void printPatient() //FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFINISH LATERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        allVitals.printVitals();
        
    }

    public void printAllVitals()
    {
        Vitals temp = allVitals;
        while(temp != null)
        {
            temp.printVitals();
            temp = temp.next();
        }
    }
    //setters
    public void setNext(Patient next) {this.next = next;}

    public void editPatientInfo(Scanner input) {
        for(int i = 0; i < info.length; i++)
        {
            System.out.println(i + ". " + info[i]);
        }
        System.out.println("What would you like to change?");
        int choice = input.nextInt();
        System.out.println("What is the new information?");
        input.nextLine();
        String change = input.nextLine();
        this.info[choice] = change;
    }

    public void addMessage(Messages newMessage)
    {
        this.messages.addMessage(newMessage);
    }

    public void newVitals(){
        Vitals newVitals = new Vitals();
        newVitals.setNext(allVitals);
        allVitals = newVitals;
    }

    //getters
    //public ArrayList<Vitals> getAllVitals(){return allVitals;}
    public void getPatientInfo(){
        System.out.println("Name:" + this.name);
        System.out.println("ID: " + this.id);
        System.out.println("Vitals:");
        allVitals.printVitals();
    }
    
    public Vitals recentVitals() {return this.allVitals;}
    public String returnName() {return this.name;}
    public int returnID() {return this.id;}
    public Patient getNext(){return this.next;}
    public String returnInfo(int choice){return this.info[0];}
    public Patient getNextPatient() {return this.next;}
    public Archives getMessages() {return this.messages;}
    
}