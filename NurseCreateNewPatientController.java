package application.controller;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.classes.ListLoader;
import application.classes.Messages;
import application.classes.Nurse;
import application.classes.Patient;
import application.classes.PatientList;
import application.classes.endoffile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NurseCreateNewPatientController implements Initializable {
	// Initialize the U.I
	private Stage	stage;
	private Scene	scene;
	private Parent	root;
	
	// Initialize PatientList
	private int[] 		patientBirthday = new int[3]; // 0 - Day | 1 - Month | 2 - Year
	private PatientList	list 			= new PatientList();
	private Patient		newPatient		= new Patient();
	private boolean		patientBPCheck 	= false;
	
	//Initialize auxiliary array lists to store the inputs for the 3 text fields with '+ ADD' buttons
	private ArrayList<String> healthIssAddButtonList = new ArrayList<String>();
	private ArrayList<String> prevPrescMedAddButtonList = new ArrayList<String>();
	private ArrayList<String> immHistAddButtonList = new ArrayList<String>();

	// Set the string for css
	String css = this.getClass().getResource("/application/css/application.css").toExternalForm();
	
	// Initialize controls
	@FXML TextField name 				= new TextField();
	@FXML TextField birthday 			= new TextField();
	@FXML TextField emailContact 		= new TextField();
	@FXML TextField phoneContact 		= new TextField();
	@FXML TextField healthIssues 		= new TextField();
	@FXML TextField prescribedMeds 		= new TextField();
	@FXML TextField immunization 		= new TextField();
	@FXML TextField pharmacyEmail 		= new TextField();
	
	@FXML Label 	nameLabel 			= new Label();
	@FXML Label 	birthdayLabel 		= new Label();
	@FXML Label 	contactInformation 	= new Label();
	@FXML Label 	healthIssuesLabel 	= new Label();
	@FXML Label 	prescribedMedsLabel	= new Label();
	@FXML Label 	immunizationLabel 	= new Label();
	@FXML Label 	pharmacyLabel 		= new Label();
	@FXML Label 	invalid 			= new Label();
	@FXML Label		vitalsLabelWarning	= new Label();
	@FXML Button 	addVitals 			= new Button();
		
	// Set the proper format for a date
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	private SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	private SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	
	// Set the day today
	private Date dateToday = new Date();
	private int yearToday = Integer.parseInt(yearFormat.format(dateToday));
	private int monthToday = Integer.parseInt(monthFormat.format(dateToday));
	private int dayToday = Integer.parseInt(dayFormat.format(dateToday));
	private Nurse nurse;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		addVitals.getStyleClass().add("buttonLockedAddVitals");
		System.out.println(addVitals.getStyleClass().toString());
		try
		{
			// Set/Initialize the list from an object in read file.
			list = (new ListLoader()).loadPatientListFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchToNursePortal(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/NursePortal.fxml"));
		root = loader.load();
		
		NursePortalController controller = loader.getController();
		controller.setNurse(nurse);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, 1280, 800);
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
	
	public void createProfile(ActionEvent event) throws IOException, ClassNotFoundException
	{	
		// Create Patient
		newPatient = new Patient();
		
		// Add Patient Info (as long as all of the checker functions return 'true')
		if (checkAll()) 
		{
			String contentText = "";
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
			Image icon = new Image("application/images/icon.png");
			stage.getIcons().add(icon);
			
			confirmation.setTitle("Create Patient");
			confirmation.setHeaderText("Confirm the information for the patient below: ");
			confirmation.setGraphic(new ImageView(this.getClass().getResource("../images/invisibleLogo.png").toString()));
			
			// Properly capitalize the name
			String 	fullName = name.getText();
			int 	idx = fullName.lastIndexOf(' ');
			String 	firstName = (fullName.substring(0, idx)).toLowerCase();
			String 	lastName  = (fullName.substring(idx + 1)).toLowerCase();
			firstName = capitalizeName(firstName);
			lastName = capitalizeName(lastName);
			
			// Set the BP check variable according to birth date
			bpCheck();
			
			// Print to the confirmation pane
			contentText += "Name: " + firstName + " " + lastName;
			contentText += "\nBirthday: " + birthday.getText();
			contentText += "\nAge: " + age();
			contentText += "\nEmail: " + emailContact.getText();
			contentText += "\nPhone Number: " + phoneContact.getText();
			contentText += "\nPrevious Health Issues: ";
			for (int i = 0; i < healthIssAddButtonList.size(); i++)
			{
				contentText += healthIssAddButtonList.get(i) + ",";
			}
			contentText += "\nPrevious Prescribed Medication: ";
			for (int i = 0; i < prevPrescMedAddButtonList.size(); i++)
			{
				contentText += prevPrescMedAddButtonList.get(i) + ",";
			}
			contentText += "\nImmunization History: ";
			for (int i = 0; i < immHistAddButtonList.size(); i++)
			{
				contentText += immHistAddButtonList.get(i) + ",";
			}
			contentText += "\nPharmacy: " + pharmacyEmail.getText();
			
			confirmation.setContentText(contentText);
			
			DialogPane dialogPane = confirmation.getDialogPane();
			dialogPane.getStylesheets().add( getClass().getResource("../css/dialogPane.css").toExternalForm());
			Optional<ButtonType> result = confirmation.showAndWait();
			ButtonType button = result.orElse(ButtonType.CANCEL);

			if (button == ButtonType.OK) 
			{
				// Set the patient name
				newPatient.setName(new String[] {firstName, lastName});
				
				// Set the patient birthdate
				newPatient.setBirthdate(birthday.getText());
				
				// Set the patient email contact and phone contact
				newPatient.setEmail(emailContact.getText());
				newPatient.setPhone(phoneContact.getText());
				
				// Add to list of previous health issues
				newPatient.setHealthIssues(healthIssAddButtonList);

				// Add to list of previous prescribed meds
				newPatient.setPrescribedMeds(prevPrescMedAddButtonList);
				
				// Add to list of immunization history
				newPatient.setImmunization(immHistAddButtonList);
				
				// Set the pharmacy email
				newPatient.setPharmacyContact(pharmacyEmail.getText());
				
				// Write patient object to file
				ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("data/patientList.txt"));
				
				// Add and write the new patient to the list
				list.addPatient(newPatient);
				objOut.writeObject(list);
				
				// End with an object of endoffile
				objOut.writeObject(new endoffile());
				objOut.close();
			} else {
			    // Canceled
			}
			

		}
		else 
		{
			invalid.setText("Invalid (Check all info)");
		}
	}
	
	public void addVitalCSS() 
	{
		if(checkAll()) 
		{
			addVitals.getStyleClass().remove(1);
			addVitals.getStyleClass().add("clearSubButton");
		}
	}
	public void addVitals(ActionEvent event)
	{
		if(!checkAll()) 
		{
			vitalsLabelWarning.setText("Complete required text field first.");
		}
		else 
		{
			
		}
	}
	
	// ------------------------------------------ Checker functions ------------------------------------------ //

	public boolean checkAll() 
	{
		// Divided so it highlights each invalid fields
		boolean nameCheck = nameCheck();
		boolean birthdayCheck = birthdayCheck();
		boolean contactCheck = contactCheck(emailContact, phoneContact);
		boolean pharmacyContactCheck = pharmacyContactCheck(pharmacyEmail); 
		boolean allCheck = nameCheck && birthdayCheck && contactCheck && pharmacyContactCheck;
		
		return allCheck;
	}
	// Checks if the name is valid
	public boolean nameCheck() 
	{
		String fullName = name.getText();
		int idx = fullName.lastIndexOf(' ');
		
		boolean valid = false;
		
		// When idx = -1, string has only 1 name word
		if((idx == -1) || fullName.isBlank() || fullName.isEmpty()) 
		{
			valid = false;
		}
		else 
		{
			valid = true;
		}
		
		highlight(name, valid);
		return valid;
	}
	
	public boolean birthdayCheck()
	{	
		String dateGiven = birthday.getText();
		boolean valid = false;
		
		// If empty text field, do not create the patient
		if (dateGiven.isEmpty())
		{
			valid = false;
		}
		
		// If text field has a "/" in it, the birthday is likely properly formatted
		if (dateGiven.contains("/"))
		{
			try {
				// Try to parse the date given to the format
		        Date date = dateFormat.parse(dateGiven);
		        
		        // Get info from date for calculating age
		        int year = Integer.parseInt(yearFormat.format(date));
		        int month = Integer.parseInt(monthFormat.format(date));
		        int day = Integer.parseInt(dayFormat.format(date));
		        
				if (dateGiven.equals(dateFormat.format(date)))
		        {
					// If birth date isn't greater than the year today and age isn't 18 or over
					if(year < yearToday && year > yearToday - 18) 
					{
						valid = true;
						patientBirthday[0] = day;
						patientBirthday[1] = month;
						patientBirthday[2] = year;
					}
		        }
				
			} catch (ParseException e) {	valid = false;	}
		}
		else
		{
			valid = false;
		}
		
		highlight(birthday, valid);
		return valid;
	}
	
	public boolean contactCheck(TextField tfieldE, TextField tfieldP)
	{
		boolean valid = false;
		
		// If either field is empty, return false
		if (tfieldE.getText().isEmpty() || tfieldP.getText().isEmpty())
		{
			valid = false;
		}
		
		// Make sure that the email field has an "@"
		if (tfieldE.getText().contains("@"))
		{
			valid = true;
		}
		// Make sure there are no spaces in the phone number field
		if (tfieldP.getText().contains(" "))
		{
			valid = false;
		}
		// Make sure the phone number has a "-"
		if (tfieldP.getText().contains("-"))
		{
			valid = true;
		}
		else
		{
			valid = false;
		}	
		highlight(tfieldE, valid);
		highlight(tfieldP, valid);
		return valid;
	}
	
	public boolean pharmacyContactCheck(TextField tfield)
	{
		boolean valid = false;
		// If field is empty, return false
		if (tfield.getText().isEmpty())
		{
			valid = false;
		}
		// Make sure that the email field has an "@"
		if (tfield.getText().contains("@"))
		{
			valid = true;
		}
		// Otherwise, criteria is not met
		else
		{
			valid = false;
		}
		highlight(tfield, valid);
		return valid;
	}

	public void bpCheck()
	{
		int patientAge = age();
		int BP_AGE = 12;
		
		if(patientAge >= BP_AGE) 
		{
			this.patientBPCheck = true;
		}
		else 
		{
			this.patientBPCheck =  false;
		}
	}
	
	public int age() 
	{
		// Birthday info
		int day = patientBirthday[0];
		int month = patientBirthday[1];
		int year = patientBirthday[2];
		
		int age = yearToday - year;
		
		if(month < monthToday) 
		{
			age++;
		}
		else if(month == monthToday) 
		{
			if(day <= dayToday) 
			{
				age++;
			}
		}

		return age;
	}
	// ------------------------------------------ End of Checker functions ------------------------------------------ //
	
	// Highlight function that highlights text fields
	public void highlight(TextField tfield, boolean valid) 
	{	
		if(!valid) 
		{
			// Default style class size for text fields is 2
			if(tfield.getStyleClass().size() > 2) 
			{
				tfield.getStyleClass().remove(tfield.getStyleClass().size() - 1);
			}
			tfield.getStyleClass().add("setInvalid");
		}
		else 
		{
			// Default style class size for text fields is 2
			if(tfield.getStyleClass().size() > 2) 
			{
				tfield.getStyleClass().remove(tfield.getStyleClass().size() - 1);
				tfield.getStyleClass().add("setValid");
			}
		}
	}
	
	// Load the patientList from the serialized text file
	public void readPatients() throws IOException, ClassNotFoundException 
	{	
		// Read patient from file
		ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("data/patientList.txt"));
		Object patientList = null;

		try 
		{
			// If the file doesn't start with endoffile marker
			if ((patientList = objIn.readObject()) instanceof endoffile == false)
			{
				// Print the patients
				((PatientList)patientList).printPatients();
			}
		} catch(EOFException e) 
		{
			// Skip if there's no data in the file
		}
		
		objIn.close();
	}
	
	public String capitalizeName(String name)
	{  
	    String words[] = name.split("\\s");
	    String capitalizeWord = "";  
	    
	    for(String w:words)
	    {  
	        String first = w.substring(0,1);  
	        String afterfirst = w.substring(1);  
	        
	        capitalizeWord += first.toUpperCase() + afterfirst + " ";  
	    }
	    
	    return capitalizeWord.trim();  
	}

	public void setNurse(Nurse nurse)
	{
		this.nurse = nurse;;
	}  
	
	// Handle the '+ ADD' button presses
	public void addHealth(ActionEvent event) 
	{
		System.out.println("Add Health");
		// add to auxiliary arrayList that will be used to add to the patient's 'health issues' array list 
		healthIssAddButtonList.add(healthIssues.getText());
	}
	
	// Handle the '+ ADD' button presses
	public void addMeds(ActionEvent event) 
	{
		System.out.println("Add Meds");
		// add to auxiliary arrayList that will be used to add to the patient's 'previous medication' array list 
		this.prevPrescMedAddButtonList.add(prescribedMeds.getText());
	}
	
	// Handle the '+ ADD' button presses
	public void addImmunization(ActionEvent event) 
	{
		System.out.println("Add Immunization");
		// add to auxiliary arrayList that will be used to add to the patient's 'immunization' array list 
		this.immHistAddButtonList.add(immunization.getText());
	}
}