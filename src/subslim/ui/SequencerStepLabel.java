package subslim.ui;

import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * Created by konra on 21.02.2017.
 */
public class SequencerStepLabel {

    public static ArrayList<SequencerStepLabel> steps = new ArrayList<>();

    private double currentCursorScreenPosition;
    private int currentTransposeValue;
    private int stepNo;
    private Label label;
    private int[] sequencerSteps;


    private SequencerStepLabel(Label label, int stepNo, int[] sequencerSteps) {

        this.stepNo = stepNo;
        this.label = label;
        this.sequencerSteps = sequencerSteps;

        bindStep();
    }

    private void bindStep(){

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

    public void setTranspose(int transpose){
        currentTransposeValue = transpose;
        sequencerSteps[stepNo] = currentTransposeValue;
        label.setText(Integer.toString(currentTransposeValue));
    }

    public int currentTranspose(){
        return currentTransposeValue;
    }

    public static SequencerStepLabel createAndBind(Label label, int stepNo, int[] sequencerSteps){

        SequencerStepLabel ssl = new SequencerStepLabel(label, stepNo,sequencerSteps);

        steps.add(ssl);

        return ssl;
    }

}
