package synth;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;


/**
 * Created by konra on 06.02.2017.
 */
public class Amp implements Module {

    SourceDataLine line;
    Envelope env;


    AdjustableValue<Double> attack, decay;

    public Amp()  {

        AudioFormat format = new AudioFormat(Synth.sampleRate, Synth.bitDepth, 1, true, true);

        try {

            line = AudioSystem.getSourceDataLine(format);
            line.open(format, Synth.bufferSize*4);

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        line.start();

        attack = new AdjustableValue<>(20d);
        decay = new AdjustableValue<>(150d);

    }

    public void sendBuffer(double[] buffer){

        applyEnvelope(buffer);
        amplifyAndWrite(buffer);
    }

    public void startEnvelope() {

        env = new Envelope(attack.getValue(),decay.getValue());
    }


    private void applyEnvelope(double[] buffer){


            for(int i = 0; i < Synth.bufferSize; i++){

                    buffer[i] *= env.nextFactor();
            }
    }


    private void amplifyAndWrite(double[] buffer){


        byte[] outputBuffer = new byte[2*Synth.bufferSize];
        short shortValue;

        for(int i = 0; i < Synth.bufferSize; i++){

            ByteBuffer sample = ByteBuffer.allocate(2);
            shortValue = (short) (buffer[i] * Short.MAX_VALUE);
            sample.putShort(shortValue);

            byte[] sampleBytes = sample.array();



            outputBuffer[2*i] = sampleBytes[0];
            outputBuffer[2*i+1] = sampleBytes[1];
        }

        line.write(outputBuffer, 0, 2*Synth.bufferSize);
    }

    public void setOutput(Module module){

    }

    public AdjustableValue<Double> attack(){
        return attack;
    }

    public AdjustableValue<Double> decay(){
        return decay;
    }

}
