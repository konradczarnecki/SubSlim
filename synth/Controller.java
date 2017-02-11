package synth;

import com.sun.deploy.panel.RadioPropertyGroup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import synth.waves.*;


public class Controller {

    @FXML RadioButton osc1Sawtooth;
    @FXML RadioButton osc1Square;
    @FXML RadioButton osc1Sine;
    @FXML ChoiceBox osc1Octave;
    @FXML ChoiceBox osc1Chord;

    @FXML Slider mixer;

    @FXML RadioButton osc2Sawtooth;
    @FXML RadioButton osc2Square;
    @FXML RadioButton osc2Sine;
    @FXML ChoiceBox osc2Octave;
    @FXML Slider osc2Detune;

    @FXML Slider cutoff;
    @FXML Slider resonance;
    @FXML Slider filterEnvelope;
    @FXML Slider filterAttack;
    @FXML Slider filterDecay;
    @FXML Slider filterSustain;
    @FXML Slider filterRelease;

    @FXML Slider attack;
    @FXML Slider decay;
    @FXML Slider release;
    @FXML Slider sustain;

    @FXML ChoiceBox baseNote;
    @FXML TextField bpm;
    @FXML TextField steps;

    @FXML TextField step1;
    @FXML TextField step2;
    @FXML TextField step3;
    @FXML TextField step4;
    @FXML TextField step5;
    @FXML TextField step6;
    @FXML TextField step7;
    @FXML TextField step8;
    @FXML TextField step9;
    @FXML TextField step10;
    @FXML TextField step11;
    @FXML TextField step12;
    @FXML TextField step13;
    @FXML TextField step14;
    @FXML TextField step15;
    @FXML TextField step16;

    @FXML Button playButton;

    Synth synth;

    public void setSynth(Synth synth){
        this.synth = synth;
    }

    @FXML
    public void initializee(){

        ToggleGroup osc1Wave = new ToggleGroup();
        ToggleGroup osc2Wave = new ToggleGroup();


        osc1Sawtooth.setToggleGroup(osc1Wave);
        osc1Square.setToggleGroup(osc1Wave);
        osc1Sine.setToggleGroup(osc1Wave);

        osc2Sawtooth.setToggleGroup(osc2Wave);
        osc2Square.setToggleGroup(osc2Wave);
        osc2Sine.setToggleGroup(osc2Wave);

        osc1Sawtooth.setUserData(new SawtoothWave());
        osc1Square.setUserData(new SquareWave());
        osc1Sine.setUserData(new SineWave());

        osc2Sawtooth.setUserData(new SawtoothWave());
        osc2Square.setUserData(new SquareWave());
        osc2Sine.setUserData(new SineWave());

        osc1Sawtooth.setSelected(true);
        osc2Sawtooth.setSelected(true);

        osc1Wave.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                synth.osc1.setWave((Wave) osc2Wave.getSelectedToggle().getUserData());
            }
        });

        osc2Wave.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                synth.osc2.setWave((Wave) osc2Wave.getSelectedToggle().getUserData());
            }
        });

        playButton.setOnAction(event -> {
            synth.stopNote();
            synth.playNote("A3");
        });
    }

}
