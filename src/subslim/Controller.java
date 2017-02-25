package subslim;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import subslim.synth.*;
import subslim.synth.wave.*;



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


    private Label[] stepLabels;
    private ImageView[] ledImageViews;



    private Synth synth;

    private PresetManager presetManager;


    public void init(){

        initOscillators();

        initFilter();

        initEnvelopes();

        initEcho();

        initSequencer();

        stepsAndLedsToArray();

        initStepsAndLeds();

        initPresetManager();

    }

    private void initEcho() {

        Knob.createAndBind(echoVerb,0.3,1,0.5, synth.amp().echo().verbAmount());

        Knob.createAndBindDelay1(echoTime1,20,250,150,synth);

        Knob.createAndBindDelay2(echoTime2,20,250,70,synth);

        Knob.createAndBind(echoDryWet,0,0.8,0,synth.amp().echo().wet());
    }

    private void initEnvelopes() {

        Knob decayKnob = Knob.createAndBind(decay,10,230, Amp.DECAY_DEFAULT,  synth.amp().decay());
        Knob.createAndBindSmartKnob(attack,30,180, Amp.ATTACK_DEFAULT,synth.amp().attack(),decayKnob);

        Knob filterDecayKnob = Knob.createAndBind(filterDecay, 10,230,Filter.DECAY_DEFAULT,synth.filter().decay());

        Knob.createAndBindSmartKnob(filterAttack, 30, 180, Filter.ATTACK_DEFAULT, synth.filter().attack(), filterDecayKnob);

        Knob.createAndBind(out, 0,1,1,synth.amp().outputLevel());
    }

    private void initFilter() {

        Knob.createAndBind(cutoff,100,14000,3000,synth.filter().cutoff());

        Knob.createAndBind(resonance,0,1,0,synth.filter().resonance());

        Knob.createAndBind(filterEnv,0,1,0,synth.filter().envAmount());

        Knob.createAndBind(lfo1Rate,0.05,4,1,synth.filter().lfoFrequency());

        Knob.createAndBind(lfo1Depth, 0,1,0,synth.filter().lfoDepth());

        FilterType[] types = {new MoogFilter(),new MoogFilter(), new MoogFilter()};
        KnobSwitch.createAndBindFilter(types, filterType, synth.filter().type(), synth);
    }

    private void initOscillators() {

        Wave[] osc1WaveValues = {new SineWave(),new SawtoothWave(), new SquareWave()};
        KnobSwitch.createAndBind(osc1WaveValues,osc1Wave,synth.osc1Wave());

        Integer[] osc1OctaveValues = {-2,-1,0,1,2};
        KnobSwitch.createAndBind(osc1OctaveValues,osc1Octave, synth.osc1Octave());

        Double[] osc1ChordValues = {5d,0d,7d};
        KnobSwitch.createAndBind(osc1ChordValues,osc1Chord, synth.chordTranspose());

        Wave[] osc2WaveValues = {new SineWave(),new SawtoothWave(), new SquareWave()};
        KnobSwitch.createAndBind(osc2WaveValues,osc2Wave,synth.osc2Wave());

        Integer[] osc2OctaveValues = {-2,-1,0,1,2};
        KnobSwitch.createAndBind(osc2OctaveValues,osc2Octave, synth.osc2Octave());

        Knob.createAndBind(osc2Detune,0.95,1.05,1,synth.detune());

        Knob.createAndBind(mix,0,2,1,synth.mix());
    }

    private void initStepsAndLeds(){

        for(int i = 0; i < 16; i++){

            SequencerStepLabel.createAndBind(stepLabels[i],i,synth.sequencer().steps());
        }

        for(int i = 0; i < 16; i++){

            Led.createAndBind(ledImageViews[i], i, synth.sequencer().activeSteps());
        }

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
            else synth.sequencer().play(null);
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

    private void initPresetManager(){

        savePreset.setOnMouseClicked(event-> presetManager.savePreset());

        openPreset.setOnMouseClicked(event-> presetManager.loadPreset());

    }


    public ImageView getBackground(){
        return back;
    }

    public void setSynth(Synth synth){
        this.synth = synth;
    }

    public void setPresetManager(PresetManager manager){presetManager = manager;}

}
