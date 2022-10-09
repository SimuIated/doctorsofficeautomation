//import java.util.InputMismatchException;
import java.util.Scanner;
public class Vitals {

    private float weight;
    /*private float height;
    private float bodyTemp;
    private float bpSystHi;
    private float bpSystLo;
    private float bpDiasHi;
    private float bpDiasLo;*/
    private String date;
    private String time;
    private Vitals next;

    public Vitals(){
        updateVitals();
        this.next = null;
    }

    public void setNext(Vitals newVitals)
    {
        this.next = newVitals;
    }

    public void updateVitals(){

        Scanner input = new Scanner(System.in);
        System.out.print("Date:");
        date = input.nextLine();
        System.out.print("Time: ");
        time = input.nextLine();
        System.out.println("Please enter the Weight: ");
        weight = inputFilter(input);

        /*
        System.out.println("Please enter the Height: ");
        height = inputFilter(input);
        System.out.println("Please enter the Body Temperature: ");
        bodyTemp = inputFilter(input);
        System.out.println("Please enter the Blood Pressure Systolic Hi: ");
        bpDiasHi = inputFilter(input);
        System.out.println("Please enter the Blood Pressure Systolic Lo: ");
        bpDiasLo = inputFilter(input);
        System.out.println("Please enter the Blood Pressure Distolic Hi: ");
        bpSystHi = inputFilter(input);
        System.out.println("Please enter the Blood Pressure Distolic Lo: ");
        bpSystLo = inputFilter(input);
        */
    }

    public void printVitals(){
        //System.out.printf("Vitals:\nWeight: %.2f\nHeight: %.2f\nBody Temperature: %.2f\nBlood Pressure Systolic: %.2f-%.2f\nBlood Pressure Distolic: %.2f-%.2f\n",weight, height, bodyTemp, bpDiasHi, bpDiasLo, bpSystHi, bpSystLo);
        System.out.printf("Date:%s at %s\nVitals:\nWeight: %.2f\n", date, time, weight);
    }

    public Vitals next(){
        return this.next;
    }

    private float inputFilter(Scanner input)
    {
        String value = "";
        do{
            try
            {
                value = input.nextLine();
                if(value.equals("0"))
                {
                    return -1;
                }
                else
                {
                    return Float.parseFloat(value);
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("Invalid Input");
            }
        }while(!value.equals("0"));
        return -1;
    }
}