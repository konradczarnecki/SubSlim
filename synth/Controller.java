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

    @FXML ChoiceBox baseNoteLetter;
    @FXML ChoiceBox baseNoteNumber;
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

            if(synth.sequencer.isPlaying()){
                synth.sequencer.stop();
                synth.osc1.stop();
                synth.osc2.stop();
            }
            else synth.sequencer.play();
        });

        initSequencerSteps();

        cutoff.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setCutoff((double)newValue);
        });

        resonance.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setResonance((double) newValue);
        });

        filterEnvelope.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setEnvelopeAmount((double) newValue);
        });

        filterAttack.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setAttack((double) newValue);
        });

        filterDecay.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setDecay((double) newValue);
        });

        filterSustain.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setSustain((double) newValue);
        });

        filterRelease.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.filter.setRelease((double) newValue);
        });

        attack.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.amp.setAttack((double) newValue);
        });

        decay.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.amp.setDecay((double) newValue);
        });

        sustain.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.amp.setSustain((double) newValue);
        });

        release.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.amp.setRelease((double) newValue);
        });


    }

    private void initSequencerSteps(){

        step1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(0,Integer.parseInt(newValue));
        });
        step2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(1,Integer.parseInt(newValue));
        });
        step3.textProperty().addListener((observable, oldValue, newValue) -> {
            synth.sequencer.setStep(2,Integer.parseInt(newValue));
        });
        step4.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(3,Integer.parseInt(newValue));
        });
        step5.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(4,Integer.parseInt(newValue));
        });
        step6.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(5,Integer.parseInt(newValue));
        });
        step7.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(6,Integer.parseInt(newValue));
        });
        step8.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(7,Integer.parseInt(newValue));
        });
        step9.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(8,Integer.parseInt(newValue));
        });
        step10.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(9,Integer.parseInt(newValue));
        });
        step11.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(10,Integer.parseInt(newValue));
        });
        step12.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(11,Integer.parseInt(newValue));
        });
        step13.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(12,Integer.parseInt(newValue));
        });
        step14.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(13,Integer.parseInt(newValue));
        });
        step15.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(14,Integer.parseInt(newValue));
        });
        step16.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+"))
                synth.sequencer.setStep(15,Integer.parseInt(newValue));
        });
    }

}
