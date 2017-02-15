package synth;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import synth.waves.*;


public class FxController {

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
    @FXML ChoiceBox filterAttackType;
    @FXML ChoiceBox filterDecayType;


    @FXML Slider attack;
    @FXML Slider decay;
    @FXML ChoiceBox attackType;
    @FXML ChoiceBox decayType;

    @FXML Slider lfoRate;
    @FXML Slider lfoDepth;
    @FXML ChoiceBox lfoTarget;

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
    public void init(){

        initOscillators();

        initSequencer();

        initFilterAndEnvelopes();
    }

    private void initFilterAndEnvelopes(){

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
            filterDecay.setMax((60000/(2*synth.sequencer.getBPM())) - filterAttack.valueProperty().doubleValue());
        });

        attack.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.amp.setAttack((double) newValue);
            decay.setMax((60000/(2*synth.sequencer.getBPM())) - attack.valueProperty().doubleValue());
        });

        decay.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.amp.setDecay((double) newValue);
        });


        cutoff.setValue(1000);
        resonance.setValue(0.6);
        filterEnvelope.setValue(0.5);
        attack.setValue(100);
        decay.setValue(100);


        tuneEnvelopes();

    }
    private void initOscillators(){

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

                synth.osc1Wave = (Wave) osc1Wave.getSelectedToggle().getUserData();
            }
        });

        osc2Wave.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                synth.osc2Wave = (Wave) osc2Wave.getSelectedToggle().getUserData();
            }
        });

        osc1Octave.setItems(FXCollections.observableArrayList("-2", "-1", "0", "1", "2"));
        osc1Octave.getSelectionModel().select(2);

        osc1Octave.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            synth.osc1Octave = newValue.intValue()-2;
        });

        osc2Octave.setItems(FXCollections.observableArrayList("-2", "-1", "0", "1", "2"));
        osc2Octave.getSelectionModel().select(2);

        osc2Octave.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            synth.osc2Octave = newValue.intValue()-2;
        });

        osc1Chord.setItems(FXCollections.observableArrayList("0", "2", "5", "7"));
        int[] values = {0 ,2 ,5 ,7};

        osc1Chord.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            synth.chordTranspose = values[newValue.intValue()];
        });

        osc2Detune.setMax(1.05);
        osc2Detune.setMin(0.95);
        osc2Detune.setValue(1);

        osc2Detune.valueProperty().addListener((observable, oldValue, newValue) -> {
            synth.detune = osc2Detune.getValue();
        });

    }

    private void initSequencer(){

        step1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(0,Integer.parseInt(newValue));
        });
        step2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(1,Integer.parseInt(newValue));
        });
        step3.textProperty().addListener((observable, oldValue, newValue) -> {
            synth.sequencer.setStep(2,Integer.parseInt(newValue));
        });
        step4.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(3,Integer.parseInt(newValue));
        });
        step5.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(4,Integer.parseInt(newValue));
        });
        step6.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(5,Integer.parseInt(newValue));
        });
        step7.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(6,Integer.parseInt(newValue));
        });
        step8.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(7,Integer.parseInt(newValue));
        });
        step9.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(8,Integer.parseInt(newValue));
        });
        step10.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(9,Integer.parseInt(newValue));
        });
        step11.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(10,Integer.parseInt(newValue));
        });
        step12.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(11,Integer.parseInt(newValue));
        });
        step13.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(12,Integer.parseInt(newValue));
        });
        step14.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(13,Integer.parseInt(newValue));
        });
        step15.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(14,Integer.parseInt(newValue));
        });
        step16.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("-?\\d+"))
                synth.sequencer.setStep(15,Integer.parseInt(newValue));
        });

        bpm.textProperty().addListener((observable, oldValue, newValue) -> {
            synth.sequencer.setBPM(Integer.parseInt(newValue));
            tuneEnvelopes();
        });

        playButton.setOnAction(event -> {

            if(synth.sequencer.isPlaying()){
                synth.sequencer.stop();
                synth.osc1.stop();
                synth.osc2.stop();
            }
            else synth.sequencer.play();
        });
    }

    private void tuneEnvelopes(){

        attack.setMax(60000/(2*synth.sequencer.getBPM()));
        decay.setMax((60000/(2*synth.sequencer.getBPM())) - attack.valueProperty().doubleValue());
        filterAttack.setMax(60000/(2*synth.sequencer.getBPM()));
        filterDecay.setMax((60000/(2*synth.sequencer.getBPM())) - filterAttack.valueProperty().doubleValue());
        decay.setMin(30);
        attack.setMin(30);
    }

}