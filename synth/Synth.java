package synth;

import synth.waves.SawtoothWave;
import synth.waves.SineWave;
import synth.waves.SquareWave;

import javax.sound.sampled.LineUnavailableException;

/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 512;

    Module[] modules;

    Oscillator osc0, osc2, osc3;
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

        mixer = new SynthMixer(this, 3);


        osc0 = new Oscillator(new SawtoothWave(), this, mixer);
        amp1 = new Amp();
        osc2 = new Oscillator(new SawtoothWave(), this, mixer);
        osc3 = new Oscillator(new SawtoothWave(), this, mixer);



        modules[0] = osc0;
        modules[1] = amp1;
        modules[2] = osc2;
        modules[3] = mixer;

        osc0.setOutput(3);
        osc2.setOutput(3);
        osc3.setOutput(3);
        mixer.setOutput(1);
    }


    public void play(){

        osc2.start(440);
        osc0.start(587.33);
        osc3.start(800);


    }
}
