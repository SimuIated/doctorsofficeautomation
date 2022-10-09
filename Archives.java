public class Archives {
    private Messages head;
    private int totalMessages;
    private int unreadMessages;

    public Archives(){
        this.head = null;
        this.totalMessages = 0;
        this.unreadMessages = 0;
    }

    public void addMessage(Messages newMessage)
    {
        if(totalMessages == 0)
        {
            this.head = newMessage;
        }
        else{
            Messages temp = head;
            temp = newMessage;
            temp.setNext(head);
            head = temp;
        }
        totalMessages++;
        unreadMessages++;
    }

    public void printAllMessages(){ //(doesnt read any message just prints the title, date, and if its been read or not)
        Messages temp = head;

        for(int i = 1; temp != null; i++)
        {
            System.out.printf("Message #%d\nDate:%10s\nTitle:%10s\nStatus:%10B\n",i ,temp.dateWriten(), temp.title(), temp.hasBeenRead());
        }
    }

    public String readMessage(int choice){ //just made one method (if you wanna read the most recent message just have choice = 1)
        Messages temp = head;
        if(choice <= totalMessages)
        {
            int iterations = 0;
            while(iterations < choice)
            {
                temp = temp.next();
            }
            unreadMessages--;
            return temp.readMessage();
        }
        else{
            System.out.println("There are not that many messagse!\nTry again");
        }
        return null;
    }

    public int unreadMessageCount(){return this.unreadMessages;}
    public int totalMessageCount(){return this.totalMessages;}
    
}