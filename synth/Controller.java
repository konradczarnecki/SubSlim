package synth;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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

        Knob mixKnob = new Knob(mix,-1,1,0);


    }

    public ImageView getBack(){
        return back;
    }


}
