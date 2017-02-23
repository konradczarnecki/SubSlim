package subslim;

import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * Created by konra on 21.02.2017.
 */
public class SequencerStepLabel {

    public static ArrayList<SequencerStepLabel> steps = new ArrayList<>();

    double currentCursorScreenPosition;
    int currentTransposeValue;
    int stepNo;
    Label label;
    int[] sequencerSteps;


    public SequencerStepLabel(Label label, int stepNo, int[] sequencerSteps) {

        this.stepNo = stepNo;
        this.label = label;
        this.sequencerSteps = sequencerSteps;

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

        steps.add(this);

    }

    public void setTranspose(int transpose){
        currentTransposeValue = transpose;
        sequencerSteps[stepNo] = currentTransposeValue;
        label.setText(Integer.toString(currentTransposeValue));
    }

    public int currentTranspose(){
        return currentTransposeValue;
    }

}
