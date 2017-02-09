package synth;

import synth.waves.*;

/**
 * Created by konra on 06.02.2017.
 */
public class Oscillator implements Module {

    Wave wave;
    Synth synth;
    boolean hold;
    int outputModuleCode;
    SynthMixer mixer;



    public Oscillator(Wave wave, Synth synth, SynthMixer mixer){

        this.wave = wave;
        this.synth = synth;
        this.mixer = mixer;
        hold = true;

    }

    public void start(double frequency){


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
                   // mixer.sendBuffer(buffer);
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

    public void setWave(Wave wave){
        this.wave = wave;
    }


}
