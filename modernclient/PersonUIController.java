package modernclient;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modernclient.model.Person;
import modernclient.model.SampleData;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonUIController implements Initializable {

    @FXML    private TextField firstnameTextField;
    @FXML    private TextField lastnameTextField;
    @FXML    private TextArea notesTextArea;
    @FXML    private Button removeButton;
    @FXML    private Button createButton;
    @FXML    private Button updateButton;
    @FXML    private ListView<Person> listView;
    @FXML   private DatePicker datePicker;
    @FXML   private MenuItem menuExit;
    @FXML   private MenuItem menuAbout;
    @FXML   private ComboBox gender;
    @FXML   private Canvas imageCanvas;
    @FXML   private AnchorPane anchorPane;

    private Image profilePicture;
    GraphicsContext graphicsContext;
    private ContextMenu contextMenu;




    private final ObservableList<Person> personList = FXCollections.observableArrayList(Person.extractor);
    // Observable objects returned by extractor (applied to each list element) are listened for changes and
    // transformed into "update" change of ListChangeListener.

    private Person selectedPerson;
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private ChangeListener<Person> personChangeListener;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        graphicsContext=imageCanvas.getGraphicsContext2D();

        // Disable the Remove/Edit buttons if nothing is selected in the ListView control
        removeButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not()));
        createButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNotNull());

        SampleData.fillSampleData(personList);

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<Person> sortedList = new SortedList<>(personList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) -> {
            int result = p1.getLastname().compareToIgnoreCase(p2.getLastname());
            if (result == 0) {
                result = p1.getFirstname().compareToIgnoreCase(p2.getFirstname());
            }
            return result;
        });

        listView.setItems(sortedList);


        listView.getSelectionModel().selectedItemProperty().addListener(personChangeListener = (observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
            // newValue can be null if nothing is selected
            selectedPerson = newValue;

            // Boolean property modifiedProperty tracks whether the user has changed any of the
            //three text controls in the form. We reset this flag after each ListView selection and use
            //this property in a bind expression to control the Update button’s disable property.

            if (newValue != null) {
                // Populate controls with selected Person
//                firstnameTextField.setText(selectedPerson.getFirstname());
//                lastnameTextField.setText(selectedPerson.getLastname());
//                notesTextArea.setText(selectedPerson.getNotes());
//                datePicker.setValue(selectedPerson.getBirthDate());
//                gender.setValue(selectedPerson.getGender());

                graphicsContext.drawImage(new javafx.scene.image.Image(getClass().getResource("resources")+"/images/"+selectedPerson.getImage(),100, 100, false, false), 0, 0);
                for(int i=0; i<100; i++){
                    for (int j=0; j<100; j++){
                        if(Math.sqrt(Math.pow(i-50,2)+Math.pow(j-50,2))>50){
                            graphicsContext.getPixelWriter().setColor(i, j, Color.TRANSPARENT);

                        }
                    }
                }

            } else {
//                firstnameTextField.setText("");
//                lastnameTextField.setText("");
//                notesTextArea.setText("");
//                datePicker.setValue(null);
                graphicsContext.clearRect(0,0,100,100);
            }
            modifiedProperty.set(false);
        });

        // Pre-select the first item
        listView.getSelectionModel().selectFirst();

        menuAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage popUpWindow=new Stage();
                popUpWindow.initModality(Modality.APPLICATION_MODAL);
                popUpWindow.setTitle("About App");
                Label content= new Label();
                content.setText("App to handle data of the employees. \n"+
                        "Date of creation: March 16, 2021 by Nurlan Nogoibaev \n"+
                        "Contact info: \n"+
                        "email: nurlan.nogoibaev_2021@ucentralasia.org\n"+
                        "Phone number: +996771294945");
                VBox layout=new VBox(10);
                layout.getChildren().addAll(content);
                layout.setAlignment(Pos.TOP_LEFT);
                Scene scene=new Scene(layout);
                popUpWindow.setScene(scene);
                popUpWindow.showAndWait();

            }

        });
        menuExit.setOnAction((ActionEvent t)-> System.exit(0));
        menuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        anchorPane.setOnMousePressed(event ->{
            System.out.println("NNN");
        });

        anchorPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                if (contextMenu != null) contextMenu.hide();
                contextMenu  = new ContextMenu();
                MenuItem firstName = new MenuItem("First name: " + listView.getSelectionModel().getSelectedItem().getFirstname());
                MenuItem lastName = new MenuItem("Last name: " + listView.getSelectionModel().getSelectedItem().getLastname());
                MenuItem notes = new MenuItem("Notes: " + listView.getSelectionModel().getSelectedItem().getNotes());
                MenuItem birthDate = new MenuItem("Birth Date: " + listView.getSelectionModel().getSelectedItem().getBirthDate().toString());
                MenuItem gender = new MenuItem("Gender: " + listView.getSelectionModel().getSelectedItem().getGender());
                contextMenu.getItems().addAll(firstName, lastName, notes, birthDate, gender);
                contextMenu.show(anchorPane, event.getScreenX(), event.getScreenY());
            }
        });


    }

    @FXML
    private void handleKeyAction(KeyEvent keyEvent) {
        modifiedProperty.set(true);
    }

//    @FXML
//    private void handleDateAndGenderUpdate(){
//        modifiedProperty.set(true);
//    }

    @FXML
    private void createButtonAction(ActionEvent actionEvent) {
        System.out.println("Create");
        Person person = new Person(firstnameTextField.getText(), lastnameTextField.getText(), notesTextArea.getText(), (String) gender.getValue(), datePicker.getValue());
//        personList.add(person);
        boolean found=false;
        for(int i=0; i<personList.size();i++){
            if(person.equals(personList.get(i))){
                found=true;
                break;
            }

        }
        if(!found){
            personList.add(person);
        }
        found=false;

        // and select it
        listView.getSelectionModel().select(person);
    }

    @FXML
    private void removeButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove " + selectedPerson);
        personList.remove(selectedPerson);
    }

    @FXML
    private void updateButtonAction(ActionEvent actionEvent) {
        System.out.println("Update " + selectedPerson);
        Person p = listView.getSelectionModel().getSelectedItem();
        listView.getSelectionModel().selectedItemProperty().removeListener(personChangeListener);
        p.setFirstname(firstnameTextField.getText());
        p.setLastname(lastnameTextField.getText());
        p.setNotes(notesTextArea.getText());
        p.setBirthDate(datePicker.getValue());
        p.setGender((String) gender.getValue());

        listView.getSelectionModel().selectedItemProperty().addListener(personChangeListener);
        modifiedProperty.set(true);
}

    public void handleDateAndGenderUpdate(MouseEvent mouseEvent) {
        modifiedProperty.set(true);
    }
}
