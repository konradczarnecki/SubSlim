package subslim;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by konra on 23.02.2017.
 */
public class PresetManager {

    Stage parent;

    public PresetManager(Stage parentWindowForFileChooser){
        parent = parentWindowForFileChooser;
    }

    public void savePreset(){

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("SubSlim preset file", "*.sbs"));
        File savedPreset = fc.showSaveDialog(parent);
        DataOutputStream outStream = null;

        if(savedPreset != null) {
            try {

                outStream = new DataOutputStream(new FileOutputStream(savedPreset));

                for(Knob k: Knob.knobs){

                    outStream.writeDouble(k.getRotation());
                }

                for(KnobSwitch s: KnobSwitch.switches){

                    outStream.writeDouble(s.getRotation());
                    outStream.writeInt(s.getPosition());
                }

                for(SequencerStepLabel e: SequencerStepLabel.steps){

                    outStream.writeInt(e.currentTranspose());
                }

                for(Led d: Led.leds){

                    outStream.writeBoolean(d.getActive());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void loadPreset(){

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("SubSlim preset file", "*.sbs"));
        File openedPreset = fc.showOpenDialog(parent);
        DataInputStream inStream = null;

        if(openedPreset != null) {
            try {

                inStream = new DataInputStream(new FileInputStream(openedPreset));

                for(Knob k: Knob.knobs){

                    double value = inStream.readDouble();
                    k.setRotation(value);
                }

                for(KnobSwitch s: KnobSwitch.switches){

                    double rotation = inStream.readDouble();
                    int position = inStream.readInt();
                    s.setState(rotation,position);
                }

                for(SequencerStepLabel e: SequencerStepLabel.steps){

                    e.setTranspose(inStream.readInt());
                }

                for(Led d: Led.leds){

                    d.setActive(inStream.readBoolean());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Stage getParentWindow(){
        return parent;
    }
}
