package subslim.ui;

import javafx.scene.control.Label;
import subslim.AdjustableValue;
import subslim.synth.Note;
import subslim.synth.Sequencer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by konra on 22.02.2017.
 */
public abstract class SequencerField <E> {

    public static final ArrayList<SequencerField> sequencerFields = new ArrayList<>();

    protected Map<E, String> valueStringRepresentations;
    protected List<E> values;
    protected int currentIndex;
    protected double currentCursorScreenPosition;
    protected AdjustableValue<E> target;
    protected Label label;


    private SequencerField(Label label, AdjustableValue<E> target){

        values = new ArrayList<>();
        valueStringRepresentations = new HashMap<>();

        this.target = target;
        this.label = label;

        label.setOnMousePressed(event -> currentCursorScreenPosition = event.getScreenY());

        label.setOnMouseDragged(event -> {

            if (event.getScreenY() - currentCursorScreenPosition < -50 && currentIndex < values.size()-1) {
                currentIndex++;
                label.setText(valueStringRepresentations.get(values.get(currentIndex)));
                currentCursorScreenPosition = event.getScreenY();
                target.setValue(values.get(currentIndex));
            }

            if (event.getScreenY() - currentCursorScreenPosition > 50 && currentIndex > 0) {
                currentIndex--;
                label.setText(valueStringRepresentations.get(values.get(currentIndex)));
                currentCursorScreenPosition = event.getScreenY();
                target.setValue(values.get(currentIndex));
            }
        });
    }


    public static void createAndBindBpm(Label label, AdjustableValue<Integer> target) {

        SequencerField<Integer> sf = new SequencerField<Integer>(label, target) {
            @Override
            void initValues() {

                for(int i = 60; i <= 180; i++){
                    values.add(i);
                    valueStringRepresentations.put(i,Integer.toString(i));
                }

                currentIndex = values.indexOf(120);
            }
        };

        sf.initValues();

        sequencerFields.add(sf);
    }

    public static void createAndBindDuration(Label label, AdjustableValue<Integer> target) {

        SequencerField<Integer> sf = new SequencerField<Integer>(label, target) {
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

        sf.initValues();

        sequencerFields.add(sf);
    }

    public static void createAndBindBaseNote(Label label, AdjustableValue<Note> target) {

        SequencerField<Note> sf = new SequencerField<Note>(label, target) {
            @Override
            void initValues() {


                for(int octave = 2; octave <= 4; octave++){

                    for(int noteHeight = 0; noteHeight < 12; noteHeight++){

                        Note note = new Note(noteHeight,octave);
                        values.add(note);
                        valueStringRepresentations.put(note, Sequencer.noteRepresentations[noteHeight] + octave);
                    }
                }

                currentIndex = values.indexOf(new Note("C3"));
            }
        };

        sf.initValues();

        sequencerFields.add(sf);
    }

    public static void createAndBindStepsNo(Label label, AdjustableValue<Integer> target) {

        SequencerField<Integer> sf = new SequencerField<Integer>(label, target) {
            @Override
            void initValues() {


                for(int i = 1; i <= 16; i++){
                    values.add(i);
                    valueStringRepresentations.put(i,Integer.toString(i));
                }

                currentIndex = values.indexOf(16);
            }
        };

        sf.initValues();

        sequencerFields.add(sf);
    }

    public void setValue(int currentIndex){

        this.currentIndex = currentIndex;

        target.setValue(values.get(currentIndex));

        label.setText(valueStringRepresentations.get(values.get(currentIndex)));
    }

    public int currentIndex(){
        return currentIndex;
    }


    abstract void initValues();

}
