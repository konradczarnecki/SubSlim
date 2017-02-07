package synth;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Created by konra on 06.02.2017.
 */
public class Oscillator implements Module {

    WaveShape wave;
    Synth synth;
    boolean hold;
    int outputModuleCode;

    public Oscillator(WaveShape wave, Synth synth){

        this.wave = wave;
        this.synth = synth;
        hold = true;

    }

    public void start(int frequency){


        wave.setFrequency(frequency);

        Thread oscillatorLoop = new Thread(new Runnable(){

            public void run(){

                int sampleNo = 0;
                double[] buffer = new double[Synth.bufferSize];

                while(hold){

                    for(int i = 0; i < Synth.bufferSize; i++){

                        buffer[i] = wave.getSample(sampleNo + i);
                    }

                    synth.passBuffer(buffer,outputModuleCode);
                    sampleNo += Synth.bufferSize;
                }

            }
        });

        oscillatorLoop.start();
    }

    public void stop(){
        hold = false;
    }

    public void sendBuffer(double[] buffer){

    }

    public void setOutput(int moduleCode){
        outputModuleCode = moduleCode;
    }

    public void setWave(WaveShape wave){
        this.wave = wave;
    }


}
