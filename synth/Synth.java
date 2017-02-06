package synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 512;

    Module[] modules;

    public Synth(){

       modules = new Module[12];

       modules[0] = new Oscillator(WaveShape.SINE, this);
       modules[1] = new Oscillator(WaveShape.SAWTOOTH, this);

    }

    public void passBuffer (double[] buffer){


    }
}
