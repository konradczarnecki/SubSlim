package msynth.synth;

import msynth.AdjustableValue;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by konra on 11.02.2017.
 */
public class Sequencer {

    private int[] steps = {0,0,7,0,5,3,0,0,2,0,-5,-12,-7,0,-2,0};
    //private int[] steps = {0,0,0,3,0,2,0,0,5,0,5,-12,7,12,0,3};
    private int currentStep;
    private String baseNote;
    private AdjustableValue<Integer> bpm;
    private Synth synth;
    private boolean isPlaying;
    private double noteLength;
    private Timer tm;

    public Sequencer(Synth synth){
        //steps = new int[16];
        //Arrays.fill(steps, 0);
        this.synth = synth;
        currentStep = 0;
        baseNote = "A3";
        bpm = new AdjustableValue<>(120);
        noteLength = 2;
    }

    public void setStep(int stepNo, int value){
        steps[stepNo] = value;
    }

    public void play(){

        double interval = 60000/(noteLength* bpm.getValue());

        String notesOrder = "CcDdEFfGgAaH";

        isPlaying = true;

        tm = new Timer();
        tm.schedule(new TimerTask(){

            public void run(){

                int octave = Integer.parseInt(baseNote.substring(1,2));
                String noteLetter = baseNote.substring(0,1);
                int noteNumber = notesOrder.indexOf(noteLetter);

                int change = steps[currentStep];

                int octaveChange = 0;

                if(change >= 0 && Math.abs(change) >= 12) octaveChange = (int) Math.floor(change/12);
                else if (change < 0 && Math.abs(change) >= 12) octaveChange = (int) Math.ceil(change/12);

                change = change % 12;

                octave += octaveChange;

                noteNumber = noteNumber + change;

                if(noteNumber >= 12) {
                    noteNumber = noteNumber % 12;
                    octave++;
                }

                String noteToPlay = notesOrder.substring(noteNumber,noteNumber+1) + octave;


                synth.playNote(noteToPlay);

                currentStep ++;
                if(currentStep == 16) currentStep = 0;


            }
        },0l,(long)interval);


    }

    public void stop(){
        tm.cancel();
        isPlaying = false;
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public AdjustableValue<Integer> bpm(){ return bpm;}

}
