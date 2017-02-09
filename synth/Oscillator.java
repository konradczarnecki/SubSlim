package synth;

import synth.waves.*;

/**
 * Created by konra on 06.02.2017.
 */
public class Oscillator implements Module {

    Wave wave;
    boolean hold;
    Module out;

    long sampleCoutner;

    public Oscillator(Wave wave){

        this.wave = wave;

    }

    public void start(double frequency){

        wave.setFrequency(frequency);

        hold = true;

        sampleCoutner = 0;

        Thread oscillatorLoop = new Thread(new Runnable(){

            public void run(){

                int sampleNo = 0;
                double[] buffer = new double[Synth.bufferSize];

                while(hold){

                    for(int i = 0; i < Synth.bufferSize; i++){

                        buffer[i] = wave.getSample(sampleNo + i);

                        sampleCoutner++;
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

    public void stopIn(int samplesBeforeStop){

        while(sampleCoutner < sampleCoutner+samplesBeforeStop){

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(sampleCoutner > samplesBeforeStop+sampleCoutner) notify();
        }

        hold = false;

    }

    public void sendBuffer(double[] buffer){

    }

    public void setOutput(Module module){
        out = module;
    }

    public void setWave(Wave wave){
        this.wave = wave;
    }


}
