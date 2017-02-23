package subslim;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import subslim.synth.Note;
import subslim.synth.Synth;
import subslim.synth.waves.*;


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

    Label[] stepLabels;
    ImageView[] ledImageViews;

    SequencerStepLabel[] sequencerSteps;
    Led[] leds;

    Synth synth;

    PresetManager presetManager;


    public void init(){

        initOscillators();

        initFilter();

        initEnvelopes();

        initEcho();

        initSequencer();

        stepsAndLedsToArray();

        initStepsAndLeds();

        initPresets();
    }

    private void initEcho() {

        Knob reverbDecayKnob = new Knob(echoVerb,0.3,1,0.5, synth.amp().echo().verbAmount());

        Knob delay1Knob = new Knob(echoTime1,20,700,150,null){

            protected void bindKnob(){

                knobImage.setOnMouseReleased(event->{

                    angle = knobImage.getRotate();

                    currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);

                    synth.amp().echo().setDelay1(currentValue);
                });
            }
        };


        Knob delay2Knob = new Knob(echoTime2,20,700,70,null){

            protected void bindKnob(){

                knobImage.setOnMouseReleased(event->{

                    angle = knobImage.getRotate();

                    currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);

                    synth.amp().echo().setDelay2(currentValue);
                });
            }
        };


        Knob echoWetKnob = new Knob(echoDryWet,0,0.8,0,synth.amp().echo().wet());
    }

    private void initEnvelopes() {

        SmartKnob decayKnob = new SmartKnob(decay,10,230, 50,  synth.amp().decay(), null);
        SmartKnob attackKnob = new SmartKnob(attack,10,230, 20,synth.amp().attack(),decayKnob);

        SmartKnob filterDecayKnob = new SmartKnob(filterDecay, 10,230,50,synth.filter().decay(),null);
        SmartKnob filterAttackKnob = new SmartKnob(filterAttack, 10, 230, 20, synth.filter().attack(), filterDecayKnob);

        Knob outKnob = new Knob(out, 0,1,1,synth.amp().outputLevel());
    }

    private void initFilter() {

        Knob cutoffKnob = new Knob(cutoff,0,14000,3000,synth.filter().cutoff());

        Knob resonanceKnob = new Knob(resonance,0,1,0,synth.filter().resonance());

        Knob filterEnvKnob = new Knob(filterEnv,0,1,0,synth.filter().envAmount());

        Knob lfo1FrequencyKnob = new Knob(lfo1Rate,0.1,6,1,synth.filter().lfoFrequency());

        Knob lfo1DepthKnob = new Knob(lfo1Depth, 0,1,0,synth.filter().lfoDepth());
    }

    private void initOscillators() {

        Wave[] osc1WaveValues = {new SineWave(),new SawtoothWave(), new SquareWave()};
        KnobSwitch<Wave> osc1WaveKnob = new KnobSwitch<>(osc1WaveValues,osc1Wave,synth.osc1Wave());

        Integer[] osc1OctaveValues = {-2,-1,0,1,2};
        KnobSwitch<Integer> osc1OctaveKnob = new KnobSwitch<>(osc1OctaveValues,osc1Octave, synth.osc1Octave());

        Double[] osc1ChordValues = {5d,0d,7d};
        KnobSwitch<Double> osc1ChordKnob = new KnobSwitch<>(osc1ChordValues,osc1Chord, synth.chordTranspose());

        Wave[] osc2WaveValues = {new SineWave(),new SawtoothWave(), new SquareWave()};
        KnobSwitch<Wave> osc2WaveKnob = new KnobSwitch<>(osc2WaveValues,osc2Wave,synth.osc2Wave());

        Integer[] osc2OctaveValues = {-2,-1,0,1,2};
        KnobSwitch<Integer> osc2OctaveKnob = new KnobSwitch<>(osc2OctaveValues,osc2Octave, synth.osc2Octave());

        Knob detuneKnob = new Knob(osc2Detune,0.95,1.05,1,synth.detune());

        Knob mixKnob = new Knob(mix,0,2,1,synth.mix());
    }

    private void initStepsAndLeds(){

        sequencerSteps = new SequencerStepLabel[16];
        leds = new Led[16];

        for(int i = 0; i < 16; i++){

            sequencerSteps[i] = new SequencerStepLabel(stepLabels[i],i,synth.sequencer().steps());
        }

        for(int i = 0; i < 16; i++){

            leds[i] = new Led(ledImageViews[i], i, synth.sequencer().activeSteps());
        }

    }

    private void initSequencer(){

        bpm.setText("120");

        SequencerField<Integer> bpmField = new SequencerField<Integer>(bpm, synth.sequencer().bpm()) {
            @Override
            void initValues() {

                for(int i = 60; i <= 180; i++){
                    values.add(i);
                    valueStringRepresentations.put(i,Integer.toString(i));
                }

                currentIndex = values.indexOf(120);
            }
        };


        duration.setText("1/8");

        SequencerField<Integer> durationField = new SequencerField<Integer>(duration, synth.sequencer().noteLengthReciprocal()) {
            @Override
            void initValues() {

                for(int i = 0; i < 4; i++){

                    int durationReciprocal = (int) Math.pow(2d,i);

                    values.add(durationReciprocal);
                    valueStringRepresentations.put(durationReciprocal, 1 + "/" + durationReciprocal*4);
                }

                currentIndex = values.indexOf(2);
            }
        };


        baseNote.setText("C3");

        SequencerField<Note> baseNoteField = new SequencerField<Note>(baseNote, synth.sequencer().baseNote()) {
            @Override
            void initValues() {


                for(int octave = 2; octave <= 4; octave++){

                    for(int noteHeight = 0; noteHeight < 12; noteHeight++){

                        Note note = new Note(noteHeight,octave);
                        values.add(note);
                        valueStringRepresentations.put(note, synth.sequencer().noteRepresentations[noteHeight] + octave);
                    }
                }

                currentIndex = values.indexOf(new Note("C3"));
            }
        };

        numberOfSteps.setText("16");

        SequencerField<Integer> numberOfStepsField = new SequencerField<Integer>(numberOfSteps, synth.sequencer().numberOfSteps()) {
            @Override
            void initValues() {

                for(int i = 1; i <= 16; i++){
                    values.add(i);
                    valueStringRepresentations.put(i,Integer.toString(i));
                }

                currentIndex = values.indexOf(16);
            }
        };


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

    private void initPresets(){

        savePreset.setOnMouseClicked(event->{

            presetManager.savePreset();
        });

        openPreset.setOnMouseClicked(event->{

            presetManager.loadPreset();
        });

    }

    public ImageView getBackground(){
        return back;
    }

    public void setSynth(Synth synth){
        this.synth = synth;
    }

    public void setPresetManager(PresetManager manager){presetManager = manager;}

}
