package subslim;

import javafx.scene.control.Label;

/**
 * Created by konra on 21.02.2017.
 */
public class SequencerStepLabel {


    double currentCursorScreenPosition;
    int currentTransposeValue;
    int stepNo;


    public SequencerStepLabel(Label label, int stepNo, int[] sequencerSteps) {

        this.stepNo = stepNo;

        label.setOnMousePressed(event -> {
            currentCursorScreenPosition = event.getScreenY();
        });

        label.setOnMouseDragged(event -> {

            if (event.getScreenY() - currentCursorScreenPosition < -50 && currentTransposeValue < 12) {
                currentTransposeValue++;
                label.setText(Integer.toString(currentTransposeValue));
                currentCursorScreenPosition = event.getScreenY();
                sequencerSteps[stepNo] = currentTransposeValue;
            }

            if (event.getScreenY() - currentCursorScreenPosition > 50 && currentTransposeValue > -12) {
                currentTransposeValue--;
                label.setText(Integer.toString(currentTransposeValue));
                currentCursorScreenPosition = event.getScreenY();
                sequencerSteps[stepNo] = currentTransposeValue;
            }
        });

    }
}
