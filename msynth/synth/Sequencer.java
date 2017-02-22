package msynth.synth;

import msynth.AdjustableValue;

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
    //private int[] steps = {0,0,7,0,5,3,0,0,2,0,-5,-12,-7,0,-2,0};
    //private int[] steps = {0,0,0,3,0,2,0,0,5,0,5,-12,7,12,0,3};
    private int currentStep;
    private AdjustableValue<Note> baseNote;
    private AdjustableValue<Integer> bpm;
    private Synth synth;
    private AdjustableValue<Boolean> isPlaying;
    private AdjustableValue<Double> noteLengthReciprocal;
    private Timer tm;
    private AdjustableValue<Integer> numberOfSteps;

    public Sequencer(Synth synth){
        steps = new int[16];
        Arrays.fill(steps, 0);
        this.synth = synth;
        currentStep = 0;
        baseNote = new AdjustableValue<>(new Note("C3"));
        bpm = new AdjustableValue<>(120);
        noteLengthReciprocal = new AdjustableValue<>(2d);
        isPlaying = new AdjustableValue<>(false);
    }

    public void setStep(int stepNo, int value){
        steps[stepNo] = value;
    }

    public void play(){

        double interval = MILLIS_IN_MINUTE/(noteLengthReciprocal.getValue() * bpm.getValue());

        isPlaying.setValue(true);

        tm = new Timer();
        tm.schedule(new TimerTask(){

            public void run(){

                int change = steps[currentStep];

                Note noteToPlay = baseNote.getValue().transpose(change);

                synth.playNote(noteToPlay);

                currentStep ++;
                if(currentStep == 16) currentStep = 0;

            }
        },(long)0,(long)interval);


    }

    public void stop(){
        tm.cancel();
        isPlaying.setValue(false);
    }

    public AdjustableValue<Boolean> isPlaying(){ return isPlaying;}

    public AdjustableValue<Integer> bpm(){ return bpm;}

    public int[] steps(){
        return steps;
    }

    public AdjustableValue<Note> baseNote(){ return baseNote;}

    public AdjustableValue<Double> noteLengthReciprocal(){return noteLengthReciprocal;}

}
