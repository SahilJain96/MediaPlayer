package mediaplayer;

import java.io.File;
import java.net.URL;
import javafx.util.Duration;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import  javafx.scene.media.MediaPlayer;


public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
    
    @FXML
    private Label label;
    
    @FXML
    private Button button;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*.mp4", "*.mp4");
    
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            filePath = file.toURI().toString();
            
            if (filePath!=null) {
                Media media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                
                DoubleProperty width;
                DoubleProperty height;
                width = mediaView.fitWidthProperty();
                height = mediaView.fitHeightProperty();
                
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                
                slider.setValue(mediaPlayer.getVolume()*100);
                slider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(slider.getValue()/100);
                    }
                });
               
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                        seekSlider.setValue(newValue.toSeconds());
                    }
                });
                
                seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                    }
                });
                
                mediaPlayer.play();
            }
    }
    
    @FXML
    private void playButton(ActionEvent event)
    {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
    @FXML
    private void pauseButton(ActionEvent event)
    {
        mediaPlayer.pause();
    }
    
    @FXML
    private void stopButton(ActionEvent event)
    {
        mediaPlayer.stop();
    }
    
    @FXML
    private void fastButton(ActionEvent event)
    {
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    private void fasterButton(ActionEvent event)
    {
        mediaPlayer.setRate(2.5);
    }
    
    @FXML
    private void slowButton(ActionEvent event)
    {
        mediaPlayer.setRate(0.75);
    }
    
    @FXML
    private void slowerButton(ActionEvent event)
    {
        mediaPlayer.setRate(0.5);
    }
    
    @FXML
    private void exitButton(ActionEvent event)
    {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
