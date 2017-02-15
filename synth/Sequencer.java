package synth;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by konra on 11.02.2017.
 */
public class Sequencer {

    private int[] steps;
    private int currentStep;
    private String baseNote;
    private int bpm;
    private Synth synth;
    boolean isPlaying;
    Timer tm;

    public Sequencer(Synth synth){
        steps = new int[16];
        Arrays.fill(steps, 0);
        this.synth = synth;
        currentStep = 0;
        baseNote = "A4";
        bpm = 120;
    }

    public void setStep(int stepNo, int value){
        steps[stepNo] = value;
    }

    public void play(){

        double interval = 60000/(2* bpm);

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

    public double getBPM(){
        return bpm;
    }

    public void setBPM(int bpm){
        this.bpm = bpm;
    }

}
