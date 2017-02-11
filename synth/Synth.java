package synth;

import synth.waves.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 512;

    Oscillator osc1, osc2;
    Wave osc1Wave, osc2Wave;
    Amp amp;
    SynthMixer mixer;
    Filter filter;
    Sequencer sequencer;

    double chordTranspose;
    int osc1Octave, osc2Octave;
    double detune;

    public Synth(){

        osc1Wave = new SawtoothWave();
        osc2Wave = new SawtoothWave();

        osc1 = new Oscillator(osc1Wave);
        osc2 = new Oscillator(osc2Wave);
        mixer = new SynthMixer(2);
        amp = new Amp();
        filter = new Filter();
        sequencer = new Sequencer(this);
        chordTranspose = 0;
        detune = 1;

        setWiring();
    }

    private void setWiring(){

        osc1.setOutput(mixer);
        osc2.setOutput(mixer);
        mixer.setOutput(filter);
        filter.setOutput(amp);

    }


    public void testPlay(){

        osc1.start(440);
        osc2.start(587.33);

    }

    public void playNote(String noteCode){



        String notesOrder = "CcDdEFfGgAaH"; //Uppercase - whole tone, Lowercase - half tone eg. A - A, A# - a, D# - d, Gb - f
        double f0 = 261.63; // C4 261.63 Hz - base frequency

        int l = notesOrder.indexOf(noteCode.substring(0,1));
        int k = Integer.parseInt(noteCode.substring(1,2)) - 4;
        double n = 12 * k + l;

        double frequency = f0 * Math.pow( (Math.pow(2d,1/12d)), n);



        double chord = Math.pow( Math.pow(2d, 1/12d), chordTranspose);

        osc1.stop();
        osc2.stop();

        amp.startEnvelope();
        filter.startEnvelope();

        osc1 = new Oscillator(Wave.getWave(osc1Wave));
        osc2 = new Oscillator(Wave.getWave(osc2Wave));

        osc1.setOctave(osc1Octave);
        osc2.setOctave(osc2Octave);

        osc1.setOutput(mixer);
        osc2.setOutput(mixer);

        osc1.start(frequency*chord);
        osc2.start(frequency*detune);

    }

}
