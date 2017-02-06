package synth;

import javax.sound.sampled.LineUnavailableException;

/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 512;

    Module[] modules;

    public Synth() throws LineUnavailableException{

       modules = new Module[12];

       modules[0] = new Oscillator(WaveShape.SINE, this);
       modules[1] = new Oscillator(WaveShape.SAWTOOTH, this);
       modules[2] = new Amp();

       modules[1].setOutput(2);
        ((Oscillator) modules[1]).start(440);

    }

    public void passBuffer (double[] buffer, int moduleCode){

        modules[moduleCode].sendBuffer(buffer);
    }
}
