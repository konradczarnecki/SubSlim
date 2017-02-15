package synth;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import synth.waves.*;


/**
 * Created by konra on 14.02.2017.
 */
public class Controller {


    @FXML ImageView back;

    @FXML ImageView osc1Wave;
    @FXML ImageView osc1Octave;
    @FXML ImageView osc1Chord;
    @FXML ImageView osc2Wave;
    @FXML ImageView osc2Octave;
    @FXML ImageView osc2Detune;
    @FXML ImageView osc2Lfo;
    @FXML ImageView mix;

    @FXML ImageView cutoff;
    @FXML ImageView resonance;
    @FXML ImageView filterLfo;
    @FXML ImageView filterEnv;

    @FXML ImageView attack;
    @FXML ImageView decay;
    @FXML ImageView filterAttack;
    @FXML ImageView filterDecay;

    @FXML ImageView lfo1Rate;
    @FXML ImageView lfo1Depth;
    @FXML ImageView lfo2Rate;
    @FXML ImageView lfo2Depth;


    Synth synth;


    public void setSynth(Synth synth){
        this.synth = synth;
    }

    public void init(){

        Wave[] osc1WaveValues = {new SineWave(),new SawtoothWave(), new SquareWave()};
        KnobSwitch<Wave> osc1WaveKnob = new KnobSwitch<>(osc1WaveValues,osc1Wave,synth.osc1Wave);

        Integer[] osc1OctaveValues = {-2,-1,0,1,2};
        KnobSwitch<Integer> osc1OctaveKnob = new KnobSwitch<>(osc1OctaveValues,osc1Octave, synth.osc1Octave);

        Double[] osc1ChordValues = {5d,0d,7d};
        KnobSwitch<Double> osc1ChordKnob = new KnobSwitch<>(osc1ChordValues,osc1Chord, synth.chordTranspose);

        Wave[] osc2WaveValues = {new SineWave(),new SawtoothWave(), new SquareWave()};
        KnobSwitch<Wave> osc2WaveKnob = new KnobSwitch<>(osc2WaveValues,osc2Wave,synth.osc2Wave);

        Integer[] osc2OctaveValues = {-2,-1,0,1,2};
        KnobSwitch<Integer> osc2OctaveKnob = new KnobSwitch<>(osc2OctaveValues,osc2Octave, synth.osc2Octave);

        Knob detuneKnob = new Knob(osc2Detune,0.95,1.05,1,synth.detune);

//        Knob osc2LfoKnob

        Knob mixKnob = new Knob(mix,-1,1,0,synth.mix);



        Knob cutoffKnob = new Knob(cutoff,0,12000,0,synth.filter.cutoff());

        Knob resonanceKnob = new Knob(resonance,0,1,0,synth.filter.resonance());

        Knob filterEnvKnob = new Knob(filterEnv,0,1,0,synth.filter.envAmount());

//        Knob filterLfo


        SmartKnob decayKnob = new SmartKnob(decay,10,230, 50,  synth.amp.decay(), null);
        SmartKnob attackKnob = new SmartKnob(attack,10,230, 20,synth.amp.attack(),decayKnob);

        SmartKnob filterDecayKnob = new SmartKnob(filterDecay, 10,230,50,synth.filter.decay(),null);
        SmartKnob filterAttackKnob = new SmartKnob(filterAttack, 10, 230, 20, synth.filter.attack(), filterDecayKnob);

    }

    public ImageView getBack(){
        return back;
    }


}
