package subslim.synth;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import subslim.AdjustableValue;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by konra on 11.02.2017.
 */
public class Sequencer {

    private static final double MILLIS_IN_MINUTE = 60000;
    public static final String[] noteRepresentations = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"};

    private int[] steps;
    private int currentStep;
    private Label[] stepLabels;
    private boolean[] activeSteps;

    private AdjustableValue<Integer> numberOfSteps;
    private AdjustableValue<Note> baseNote;
    private AdjustableValue<Integer> bpm;
    private AdjustableValue<Integer> noteLengthReciprocal;

    private AdjustableValue<Boolean> isPlaying;
    private Timer tm;
    private Synth synth;

    public Sequencer(Synth synth){

        steps = new int[16];
        Arrays.fill(steps, 0);

        this.synth = synth;
        currentStep = 0;

        baseNote = new AdjustableValue<>(new Note("C3"));
        bpm = new AdjustableValue<>(120);
        noteLengthReciprocal = new AdjustableValue<>(2);
        isPlaying = new AdjustableValue<>(false);
        numberOfSteps = new AdjustableValue<>(16);

        activeSteps = new boolean[16];
        Arrays.fill(activeSteps,true);
    }

    public void play(){

        double interval = MILLIS_IN_MINUTE/(noteLengthReciprocal.getValue() * bpm.getValue());

        isPlaying.setValue(true);

        tm = new Timer();

        tm.schedule(new TimerTask(){

            public void run(){

                int change = steps[currentStep];

                Note noteToPlay = baseNote.getValue().transpose(change);

                if(activeSteps[currentStep]) synth.playNote(noteToPlay);

                Task<Void> updateLight = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                if(currentStep == 0) {

                                    stepLabels[numberOfSteps.getValue()-1].setEffect(new Glow(1));
                                    stepLabels[numberOfSteps.getValue()-2].setEffect(null);

                                } else if (currentStep == 1){

                                    stepLabels[0].setEffect(new Glow(1));
                                    stepLabels[numberOfSteps.getValue()-1].setEffect(null);

                                } else {

                                    stepLabels[currentStep-1].setEffect(new Glow(1));
                                    stepLabels[currentStep-2].setEffect(null);
                                }
                            }
                        });
                        return null;
                    }
                };

                updateLight.run();

                currentStep ++;
                if(currentStep == numberOfSteps.getValue()) currentStep = 0;

            }
        },(long)0,(long)interval);


    }

    public void stop(){
        tm.cancel();
        isPlaying.setValue(false);
        currentStep = 0;
    }

    public AdjustableValue<Boolean> isPlaying(){ return isPlaying;}

    public AdjustableValue<Integer> bpm(){ return bpm;}

    public int[] steps(){
        return steps;
    }

    public AdjustableValue<Note> baseNote(){ return baseNote;}

    public AdjustableValue<Integer> noteLengthReciprocal(){return noteLengthReciprocal;}

    public AdjustableValue<Integer> numberOfSteps(){ return numberOfSteps;}

    public boolean[] activeSteps(){ return activeSteps;}

    public void setStepLabels(Label[] stepLabels){
        this.stepLabels = stepLabels;
    }

}
