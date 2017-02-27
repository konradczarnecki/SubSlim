package subslim;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import subslim.ui.*;

import java.io.*;

/**
 * Created by konra on 23.02.2017.
 */
public class PresetManager {

    private Stage parent;
    private FileChooser fc = new FileChooser();

    public PresetManager(Stage parentWindowForFileChooser)
    {
        parent = parentWindowForFileChooser;
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("SubSlim preset file", "*.sbs"));

    }

    public void savePreset(){

        File savedPreset = fc.showSaveDialog(parent);


        if(savedPreset != null) {
            try {

                DataOutputStream outStream = new DataOutputStream(new FileOutputStream(savedPreset));

                for(Knob k: Knob.knobs){

                    outStream.writeDouble(k.getRotation());
                }

                for(KnobSwitch ks: KnobSwitch.switches){

                    outStream.writeDouble(ks.getRotation());
                    outStream.writeInt(ks.getPosition());
                }

                for(SequencerStepLabel ssl: SequencerStepLabel.steps){

                    outStream.writeInt(ssl.currentTranspose());
                }

                for(Led led: Led.leds){

                    outStream.writeBoolean(led.getActive());
                }

                for(SequencerField sf: SequencerField.sequencerFields){

                    outStream.writeInt(sf.currentIndex());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void loadPreset(){

        File openedPreset = fc.showOpenDialog(parent);

        if(openedPreset != null) {
            try {

                DataInputStream inStream = new DataInputStream(new FileInputStream(openedPreset));

                for(Knob k: Knob.knobs){

                    double value = inStream.readDouble();
                    k.setRotation(value);
                }

                for(KnobSwitch ks: KnobSwitch.switches){

                    double rotation = inStream.readDouble();
                    int position = inStream.readInt();
                    ks.setState(rotation,position);
                }

                for(SequencerStepLabel ssl: SequencerStepLabel.steps){

                    ssl.setTranspose(inStream.readInt());
                }

                for(Led led: Led.leds){

                    led.setActive(inStream.readBoolean());
                }

                for(SequencerField sf: SequencerField.sequencerFields){

                    sf.setValue(inStream.readInt());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
