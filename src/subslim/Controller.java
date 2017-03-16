package subslim;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import subslim.synth.*;
import subslim.synth.effect.Echo;
import subslim.synth.filter.Filter;
import subslim.synth.wave.*;
import subslim.ui.*;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


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
    @FXML ImageView mix;

    @FXML ImageView cutoff;
    @FXML ImageView resonance;
    @FXML ImageView filterType;
    @FXML ImageView filterEnv;

    @FXML ImageView attack;
    @FXML ImageView decay;
    @FXML ImageView filterAttack;
    @FXML ImageView filterDecay;

    @FXML ImageView lfo1Rate;
    @FXML ImageView lfo1Depth;


    @FXML ImageView echoTime1;
    @FXML ImageView echoTime2;
    @FXML ImageView echoDryWet;
    @FXML ImageView echoVerb;

    @FXML Label step1;
    @FXML Label step2;
    @FXML Label step3;
    @FXML Label step4;
    @FXML Label step5;
    @FXML Label step6;
    @FXML Label step7;
    @FXML Label step8;
    @FXML Label step9;
    @FXML Label step10;
    @FXML Label step11;
    @FXML Label step12;
    @FXML Label step13;
    @FXML Label step14;
    @FXML Label step15;
    @FXML Label step16;

    @FXML ImageView led1;
    @FXML ImageView led2;
    @FXML ImageView led3;
    @FXML ImageView led4;
    @FXML ImageView led5;
    @FXML ImageView led6;
    @FXML ImageView led7;
    @FXML ImageView led8;
    @FXML ImageView led9;
    @FXML ImageView led10;
    @FXML ImageView led11;
    @FXML ImageView led12;
    @FXML ImageView led13;
    @FXML ImageView led14;
    @FXML ImageView led15;
    @FXML ImageView led16;

    @FXML Label bpm;
    @FXML Label duration;
    @FXML Label baseNote;
    @FXML Label numberOfSteps;

    @FXML Rectangle playButton;

    @FXML ImageView out;

    @FXML ImageView savePreset;
    @FXML ImageView openPreset;
    @FXML ImageView reset;

    @FXML Rectangle closeButton;
    @FXML Rectangle minimizeButton;

    @FXML Label noFilterType;

    private Label[] stepLabels;
    private ImageView[] ledImageViews;

    private Synth synth;

    private Stage primaryStage;


    public void init(){

        initOscillators();

        initFilter();

        initEnvelopes();

        initEcho();

        initSequencer();

        stepsAndLedsToArray();

        initStepsAndLeds();

        initIconsAndPresetManager();

    }

    private void initOscillators() {


        KnobSwitch.createAndBind(Wave.getWaves(),osc1Wave,synth.osc1Wave());

        KnobSwitch.createAndBind(Oscillator.availableOctaves,osc1Octave, synth.osc1Octave());

        KnobSwitch.createAndBind(Oscillator.availableChords,osc1Chord, synth.chordTranspose());

        KnobSwitch.createAndBind(Wave.getWaves(),osc2Wave,synth.osc2Wave());

        KnobSwitch.createAndBind(Oscillator.availableOctaves,osc2Octave, synth.osc2Octave());

        Knob.createAndBind(osc2Detune,0.98,1.02,1,synth.detune());

        Knob.createAndBind(mix,0,2,1,synth.mix());
    }

    private void initFilter() {

        Knob.createAndBind(cutoff,100,14000,Filter.CUTOFF_DEFAULT,synth.filter().cutoff());

        Knob.createAndBind(resonance,0,1,Filter.RESONANCE_DEFAULT,synth.filter().resonance());

        Knob.createAndBind(filterEnv,0,1,Filter.ENV_AMOUNT_DEFAULT,synth.filter().envAmount());

        Knob.createAndBind(lfo1Rate,0.05,4, Lfo.RATE_DEFAULT,synth.filter().lfoFrequency());

        Knob.createAndBind(lfo1Depth, 0,1,Lfo.DEPTH_DEFAULT,synth.filter().lfoDepth());

//        FilterType[] types = {new MoogFilter(), new MoogFilter(), new MoogFilter()};
//        KnobSwitch.createAndBind(types, filterType, synth.filter().type());

        initNoFilterLabel();
    }

    private void initEnvelopes() {

        Knob decayKnob = Knob.createAndBind(decay,10,230, Amp.DECAY_DEFAULT,  synth.amp().decay());
        Knob.createAndBindSmartKnob(attack,30,180, Amp.ATTACK_DEFAULT,synth.amp().attack(),decayKnob);

        Knob filterDecayKnob = Knob.createAndBind(filterDecay, 10,230, Filter.DECAY_DEFAULT,synth.filter().decay());
        Knob.createAndBindSmartKnob(filterAttack, 30, 180, Filter.ATTACK_DEFAULT, synth.filter().attack(), filterDecayKnob);

        Knob.createAndBind(out, 0,1,1,synth.amp().outputLevel());
    }

    private void initEcho() {

        Knob.createAndBind(echoVerb,0.2,1, Echo.VERB_DEFAULT, synth.amp().echo().verbAmount());

        Knob.createAndBindDelay1(echoTime1,20,250,Echo.DELAY1_TIME_DEFAULT,synth);

        Knob.createAndBindDelay2(echoTime2,20,250,Echo.DELAY2_TIME_DEFAULT,synth);

        Knob.createAndBind(echoDryWet,0,0.8,Echo.WET_DEFAULT,synth.amp().echo().wet());
    }

    private void initSequencer(){

        bpm.setText("120");
        SequencerField.createAndBindBpm(bpm, synth.sequencer().bpm());

        duration.setText("1/8");
        SequencerField.createAndBindDuration(duration, synth.sequencer().noteLengthReciprocal());

        baseNote.setText("C3");
        SequencerField.createAndBindBaseNote(baseNote, synth.sequencer().baseNote());

        numberOfSteps.setText("16");
        SequencerField.createAndBindStepsNo(numberOfSteps, synth.sequencer().numberOfSteps());

        playButton.setOnMouseClicked(event ->{

            if(synth.sequencer().isPlaying().getValue()) synth.sequencer().stop();
            else synth.sequencer().play();
        });
    }


    private void stepsAndLedsToArray(){

        stepLabels = new Label[16];

        stepLabels[0] = step1;
        stepLabels[1] = step2;
        stepLabels[2] = step3;
        stepLabels[3] = step4;
        stepLabels[4] = step5;
        stepLabels[5] = step6;
        stepLabels[6] = step7;
        stepLabels[7] = step8;
        stepLabels[8] = step9;
        stepLabels[9] = step10;
        stepLabels[10] = step11;
        stepLabels[11] = step12;
        stepLabels[12] = step13;
        stepLabels[13] = step14;
        stepLabels[14] = step15;
        stepLabels[15] = step16;

        synth.sequencer().setStepLabels(stepLabels);

        ledImageViews = new ImageView[16];

        ledImageViews[0] = led1;
        ledImageViews[1] = led2;
        ledImageViews[2] = led3;
        ledImageViews[3] = led4;
        ledImageViews[4] = led5;
        ledImageViews[5] = led6;
        ledImageViews[6] = led7;
        ledImageViews[7] = led8;
        ledImageViews[8] = led9;
        ledImageViews[9] = led10;
        ledImageViews[10] = led11;
        ledImageViews[11] = led12;
        ledImageViews[12] = led13;
        ledImageViews[13] = led14;
        ledImageViews[14] = led15;
        ledImageViews[15] = led16;
    }

    private void initStepsAndLeds(){

        for(int i = 0; i < 16; i++){

            SequencerStepLabel.createAndBind(stepLabels[i], i, synth.sequencer().steps());
        }

        for(int i = 0; i < 16; i++){

            Led.createAndBind(ledImageViews[i], i, synth.sequencer().activeSteps());
        }

    }

    private void initIconsAndPresetManager(){


        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("SubSlim preset file", "*.sbs"));

        savePreset.setOnMouseClicked(event->{

            File presetToSave = fc.showSaveDialog(primaryStage);
            PresetManager.savePreset(presetToSave);
            if(synth.sequencer().isPlaying().getValue()) synth.sequencer().stop();
        });

        openPreset.setOnMouseClicked(event->{

            File presetToLoad = fc.showOpenDialog(primaryStage);
            PresetManager.loadPreset(presetToLoad);
            if(synth.sequencer().isPlaying().getValue()) synth.sequencer().stop();
        });

        reset.setOnMouseClicked(event->{

            PresetManager.loadPreset(getClass().getClassLoader().getResourceAsStream("presets/default.sbs"));
            if(synth.sequencer().isPlaying().getValue()) synth.sequencer().stop();
        });

        closeButton.setOnMouseClicked(event->{
            System.exit(0);
        });

        minimizeButton.setOnMouseClicked(event->{
            primaryStage.setIconified(true);
        });

        Tooltip saveTip = new Tooltip("Save preset");
        Tooltip openTip = new Tooltip("Load preset");
        Tooltip resetTip = new Tooltip("Reset to defaults");

        Tooltip.install(savePreset,saveTip);
        Tooltip.install(openPreset, openTip);
        Tooltip.install(reset, resetTip);


        int presetNo = (int) Math.ceil(Math.random()*7);
        String presetName = "p" + presetNo + ".sbs";

        PresetManager.loadPreset(getClass().getClassLoader().getResourceAsStream("presets/" + presetName));
    }

    private void initNoFilterLabel(){

        noFilterType.setVisible(false);

        filterType.setOnMouseDragged(event->{

            noFilterType.setVisible(true);

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {

                    noFilterType.setVisible(false);

                }
            },3000);

        });
    }


    public ImageView getBackground(){
        return back;
    }

    public void setSynth(Synth synth){
        this.synth = synth;
    }

    public void setPrimaryStage(Stage primaryStage){this.primaryStage = primaryStage;}

}
