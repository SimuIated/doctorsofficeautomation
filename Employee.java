public class Employee {
    private int employeeId;
    private String employeeName;
    private Archives messages;

    public Employee(int employeeId, String employeeName){
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.messages = new Archives();
    }


    public Archives getMessages(){return this.messages;}
    public String getName(){return employeeName;}
    public int getId() {return employeeId; }
    public void print(){System.out.printf("Employee name: %s\nEmployeeID:%d\n",employeeName, employeeId );}
}