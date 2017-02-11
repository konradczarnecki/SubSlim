package synth;

import synth.waves.*;

/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 1024;


    Oscillator osc1, osc2;
    Wave osc1Wave, osc2Wave;
    Amp amp;
    SynthMixer mixer, multiMixer;
    Filter filter;

    public Synth(){

        osc1Wave = new SawtoothWave();
        osc2Wave = new SawtoothWave();

        osc1 = new Oscillator(osc1Wave);
        osc2 = new Oscillator(osc2Wave);
        mixer = new SynthMixer(2);
        multiMixer = new SynthMixer(1);
        amp = new Amp();
        filter = new Filter();

        setWiring();
    }

    private void setWiring(){

        osc1.setOutput(mixer);
        osc2.setOutput(mixer);
        multiMixer.setOutput(filter);
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



        double fifth = Math.pow( Math.pow(2d, 1/12d), 5d);


        amp.startEnvelope();
        filter.startEnvelope();

        osc1 = new Oscillator(new SawtoothWave());
        osc2 = new Oscillator(new SawtoothWave());

        osc1.setOutput(mixer);
        osc2.setOutput(mixer);

        osc1.start(frequency);
        osc2.start(frequency * fifth);


    }

    public void stopNote(){

        filter.stopEnvelope();

        int delay = amp.stopEnvelope();
        if(delay != -1) {
            osc1.stopIn(delay);
            osc2.stopIn(delay);
        } else {
            osc1.stop();
            osc2.stop();
        }




    }
}
