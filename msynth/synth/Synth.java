package msynth.synth;

import msynth.AdjustableValue;
import msynth.synth.waves.*;


/**
 * Created by konra on 06.02.2017.
 */
public class Synth {

    public static final int SAMPLE_RATE = 44100;
    public static final int BIT_DEPTH = 16;
    public static final int BUFFER_SIZE = 512;

    private Oscillator osc1, osc2;
    private AdjustableValue<Wave> osc1Wave, osc2Wave;
    private Amp amp;
    private ChannelMixer mixer;
    private Filter filter;
    private Sequencer sequencer;

    private AdjustableValue<Double> chordTranspose;
    private AdjustableValue<Integer> osc1Octave, osc2Octave;
    private AdjustableValue<Double> detune;
    private AdjustableValue<Double> mix;

    public Synth(){

        osc1Wave = new AdjustableValue<>(new SawtoothWave());
        osc2Wave = new AdjustableValue<>(new SawtoothWave());

        osc1 = new Oscillator(osc1Wave.getValue());
        osc2 = new Oscillator(osc2Wave.getValue());
        mixer = new ChannelMixer(2);
        amp = new Amp();
        filter = new Filter();
        sequencer = new Sequencer(this);

        chordTranspose = new AdjustableValue<>(0d);
        detune = new AdjustableValue<>(1d);
        osc1Octave = new AdjustableValue<>(0);
        osc2Octave = new AdjustableValue<>(0);
        mix = new AdjustableValue<>(1d);

        setWiring();
        sequencer.play();
    }

    private void setWiring(){

        osc1.setOutput(mixer);
        osc2.setOutput(mixer);
        mixer.setOutput(filter);
        filter.setOutput(amp);

    }


    public void playNote(String noteCode){

        String notesOrder = "CcDdEFfGgAaH"; //Uppercase - whole tone, Lowercase - half tone eg. A - A, A# - a, D# - d, Gb - f
        double f0 = 261.63; // C4 261.63 Hz - base frequency

        int l = notesOrder.indexOf(noteCode.substring(0,1));
        int k = Integer.parseInt(noteCode.substring(1,2)) - 4;
        double n = 12 * k + l;

        double frequency = f0 * Math.pow( (Math.pow(2d,1/12d)), n);

        double chord = Math.pow( Math.pow(2d, 1/12d), chordTranspose.getValue());

        osc1.stop();
        osc2.stop();

        amp.startEnvelope();
        filter.startEnvelope();

        osc1 = new Oscillator(Wave.getWave(osc1Wave.getValue()));
        osc2 = new Oscillator(Wave.getWave(osc2Wave.getValue()));

        osc1.setOctave(osc1Octave.getValue());
        osc2.setOctave(osc2Octave.getValue());

        osc1.setMix(2-mix.getValue());
        osc2.setMix(mix.getValue());

        osc1.setOutput(mixer);
        osc2.setOutput(mixer);

        osc1.start(frequency*chord);
        osc2.start(frequency*detune.getValue());
    }

    public AdjustableValue<Wave> osc1Wave(){
        return osc1Wave;
    }

    public AdjustableValue<Wave> osc2Wave(){
        return osc2Wave;
    }

    public AdjustableValue<Integer> osc1Octave(){
        return osc1Octave;
    }

    public AdjustableValue<Integer> osc2Octave(){
        return osc2Octave;
    }

    public AdjustableValue<Double> chordTranspose(){
        return chordTranspose;
    }

    public AdjustableValue<Double> mix(){
        return mix;
    }

    public AdjustableValue<Double> detune(){
        return detune;
    }

    public Filter filter(){
        return filter;
    }

    public Amp amp(){
        return amp;
    }

    public Sequencer sequencer(){ return sequencer; }



}
