package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Timer timer = new Timer();

    @FXML
    private Button back;

    @FXML
    private Label counter;

    @Inject
    public LoadingScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    void back(ActionEvent event) {
        mainCtrl.showHomeScreen();
        timer.cancel();
        timer = new Timer();
        counter.setText("3");
    }

    public void countdown() {

        TimerTask task = new TimerTask() {
            int second = 2;

            @Override
            public void run() {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText(String.valueOf(second--));
                    }
                });

                if(second == 0) {
                    cancel();
                    mainCtrl.showQuestionScreen();
                    counter.setText("3");
                }
            }
        };

        timer.schedule(task, 1000, 1000);
    }

}
