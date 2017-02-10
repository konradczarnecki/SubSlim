package synth;

import synth.waves.*;

/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 512;


    Oscillator osc1, osc2;
    Amp amp;
    SynthMixer mixer;
    Filter filter;

    public Synth(){

        setWiring();
    }

    private void setWiring(){

        osc1 = new Oscillator(new SawtoothWave());
        osc2 = new Oscillator(new SawtoothWave());
        mixer = new SynthMixer(2);
        amp = new Amp();
        filter = new Filter();

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


        osc1.stop();
        osc2.stop();

        double fifth = Math.pow( Math.pow(2d, 1/12d), 5d);

        amp.startEnvelope();
        filter.startEnvelope();
        osc1.start(frequency);
        osc2.start(frequency * fifth);

    }

    public void stopNote(){

        filter.stopEnvelope();
        osc1.stopIn(amp.stopEnvelope());

    }
}
