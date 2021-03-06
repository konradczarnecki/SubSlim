package subslim.synth;

import subslim.synth.wave.*;

/**
 * Created by konra on 06.02.2017.
 */
public class Oscillator implements Module {

    public static final Integer[] availableOctaves = {-2,-1,0,1,2};
    public static final Double[] availableChords = {5d,0d,7d};

    private Wave wave;
    private boolean hold;
    private Module out;
    private int octave;
    private double mix;

    public Oscillator(Wave wave){

        this.wave = wave;
        octave = 0;
        mix = 1;
    }

    public void start(double frequency){

        wave.setFrequency(frequency*Math.pow(2d,octave));

        hold = true;

        Thread oscillatorLoop = new Thread(new Runnable(){

            public void run(){

                int sampleNo = 0;
                double[] buffer = new double[Synth.BUFFER_SIZE];

                while(hold){

                    for(int i = 0; i < Synth.BUFFER_SIZE; i++){

                        buffer[i] = wave.getSample(sampleNo + i)*mix;

                    }

                    out.sendBuffer(buffer);
                    sampleNo += Synth.BUFFER_SIZE;
                }

            }
        });

        oscillatorLoop.start();
    }

    public void stop(){
        hold = false;
    }

    public void sendBuffer(double[] buffer){}

    public void setOutput(Module module){
        out = module;
    }

    public void setOctave(int octave){
        this.octave = octave;
    }

    public void setMix(double mix){ this.mix = mix;}

}
