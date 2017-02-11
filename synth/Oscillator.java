package synth;

import synth.waves.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by konra on 06.02.2017.
 */
public class Oscillator implements Module {

    Wave wave;
    boolean hold;
    Module out;
    int octave;

    public Oscillator(Wave wave){

        this.wave = wave;
        octave = 0;
    }

    public synchronized void start(double frequency){

        wave.setFrequency(frequency*Math.pow(2d,octave));

        hold = true;

        Thread oscillatorLoop = new Thread(new Runnable(){

            public void run(){

                int sampleNo = 0;
                double[] buffer = new double[Synth.bufferSize];

                while(hold){

                    for(int i = 0; i < Synth.bufferSize; i++){

                        buffer[i] = wave.getSample(sampleNo + i);

                    }

                    out.sendBuffer(buffer);
                    sampleNo += Synth.bufferSize;
                }

            }
        });

        oscillatorLoop.start();
    }

    public void stop(){
        hold = false;
    }

    public synchronized void stopIn(int releaseTime){

        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            public void run(){
                hold = false;
            }
        }, releaseTime);

    }

    public void sendBuffer(double[] buffer){

    }

    public void setOutput(Module module){
        out = module;
    }

    public void setWave(Wave wave){

        this.wave = wave;
    }

    public void setOctave(int octave){
        this.octave = octave;
    }


}
