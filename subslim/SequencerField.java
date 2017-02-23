package subslim;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by konra on 22.02.2017.
 */
public abstract class SequencerField <E> {

    protected Map<E, String> valueStringRepresentations;
    protected List<E> values;
    protected int currentIndex;
    protected double currentCursorScreenPosition;


    public SequencerField(Label label, AdjustableValue<E> target){

        values = new ArrayList<>();
        valueStringRepresentations = new HashMap<>();

        initValues();

        label.setOnMousePressed(event -> {
            currentCursorScreenPosition = event.getScreenY();
        });

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

    abstract void initValues();
}
