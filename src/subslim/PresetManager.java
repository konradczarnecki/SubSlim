package subslim;

import subslim.ui.*;

import java.io.*;

/**
 * Created by konra on 23.02.2017.
 */
public class PresetManager {

    public  static void savePreset(File savedPreset){

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

                for(SequencerStepLabel sslab: SequencerStepLabel.steps){

                    outStream.writeInt(sslab.currentTranspose());
                }

                for(Led led: Led.leds){

                    outStream.writeBoolean(led.getActive());
                }

                for(SequencerField sf: SequencerField.sequencerFields){

                    outStream.writeInt(sf.currentIndex());
                }

                outStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void loadPreset(InputStream stream){

        if(stream != null) {
            try {

                DataInputStream inStream = new DataInputStream(stream);

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

                inStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadPreset(File savedPreset){

        if(savedPreset != null) {

            try {
                FileInputStream stream = new FileInputStream(savedPreset);
                loadPreset(stream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
