//package sample;
//
//import javafx.animation.Animation;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.IOException;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//
//public class DigitalClockController extends Label {
//
//    private static DateTimeFormatter SHORT_TIME_FORMATTER =       DateTimeFormatter.ofPattern("HH:mm:ss");
//
//    public DigitalClockController() {
//        bindToTime();
//    }
//
//    // the digital clock updates once a second.
//    private void bindToTime() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
//                event -> setText(LocalTime.now().format(SHORT_TIME_FORMATTER))),
//                new KeyFrame(Duration.seconds(1)));
//
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//    }
//}


package sample;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.net.URL;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event. *;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class Controller implements Initializable {
    @FXML private Label CurrentTime;

    @FXML private ComboBox<Integer> HourBox;

    @FXML private ComboBox<Integer> MinBox;

    @FXML private ImageView image;

    private Timer newTimer;


    @Override
    public void initialize (URL url, ResourceBundle rb) {
        Timeline timeline = new Timeline (new KeyFrame (Duration.millis (1000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle (ActionEvent actionEvent) {
                        LocalTime now = LocalTime.now ();
                        String txt = String.format ("% 02d:% 02d:% 02d", now.getHour (), now.getMinute (), now.getSecond ());
                        CurrentTime.setText (txt);

                        if ((alarmTimeInMin() == clockTimeInMin())) {
                            image.setVisible(true);
                            animateNode(image);
                        }
                    }
                }
        ));
        timeline.setCycleCount (Timeline.INDEFINITE);
        timeline.play ();


        FileInputStream input = null;
        try {
            input = new FileInputStream("src/image/alarm_clock_PNG85.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image2 = new Image(input);
        image.setImage(image2);

        image.setVisible(false);

        LocalTime now = LocalTime.now ();
        String txt = String.format ("% 02d:% 02d:% 02d", now.getHour (), now.getMinute (), now.getSecond ());
        CurrentTime.setText (txt);

        MinBox.getItems().addAll(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
                43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59
        );

        HourBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23);


    }
    static void animateNode(Node node) {

        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), node);
        transition.setFromX(0);
        transition.setToX(2.5);
        transition.playFromStart();

    }

    private int alarmTimeInMin() {
        int hours = HourBox.getValue();
        return MinBox.getValue() + hours * 60;
    }

    private int clockTimeInMin() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // if it's after noon
        if(cal.get(Calendar.AM_PM) == Calendar.PM) {
            int clockHour = cal.get(Calendar.HOUR);
            int clockTotalMin = (clockHour + 12)*60 + cal.get(Calendar.MINUTE);

            if(clockTotalMin >= 1440)
                clockTotalMin = cal.get(Calendar.MINUTE);
            return clockTotalMin;
        }
        else {
            // if it's before noon
            int clockHour = cal.get(Calendar.HOUR);
            return clockHour*60+cal.get(Calendar.MINUTE);
        }
    }
}