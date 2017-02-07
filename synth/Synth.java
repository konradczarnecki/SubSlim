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

    Oscillator osc0, osc2;
    Amp amp1;
    SynthMixer mixer;

    public Synth(){

        modules = new Module[12];

        setWiring();
    }

    public void passBuffer (double[] buffer, int moduleCode){

        modules[moduleCode].sendBuffer(buffer);
    }

    private void setWiring(){



        osc0 = new Oscillator(WaveShape.SINE, this);
        amp1 = new Amp();
        osc2 = new Oscillator(WaveShape.SINE, this);
        mixer = new SynthMixer(this);



        modules[0] = osc0;
        modules[1] = amp1;
        modules[2] = osc2;
        modules[3] = mixer;

        osc0.setOutput(3);
        osc2.setOutput(3);
        mixer.setOutput(1);
    }

    public void play(){


        osc0.start(400);
        osc2.start(1000);

    }
}
