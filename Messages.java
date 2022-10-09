import java.util.Scanner;
public class Messages {
    private String patient;
    private String doctor;
    private String nurse;
    private String title;
    private String message;
    private boolean read;
    private String date;
    private Messages nextMessage;

    public Messages(String doctor, String nurse, String patient, Scanner input)
    {
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
        this.nextMessage = null;
        read = false;
        writeMessage(input);
    }
    public Messages(String doctor, String patient, Scanner input)
    {
        this.patient = patient;
        this.doctor=doctor;
        this.nurse = null;
        this.nextMessage = null;
        read = false;
        writeMessage(input);
    }

    public void writeMessage(Scanner input){
        System.out.print("Date:");
        this.date = input.nextLine();
        System.out.print("Title:");
        this.title = input.next();
        System.out.println("Message:");
        message = input.nextLine();
    }

    public String readMessage(){
        this.read = true;
        return message;
    }

    public void setNext(Messages message)
    {
        this.nextMessage = message;
    }

    public String title(){return this.title;}
    public String dateWriten(){return this.date;}
    public Messages next(){return this.nextMessage;}
    public String patient(){return this.patient;}
    public String doctor(){return this.doctor;}
    public String nurse(){return this.nurse;}
    public boolean hasBeenRead(){return this.read;}
}