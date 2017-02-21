package msynth.synth;

import msynth.AdjustableValue;
import msynth.synth.effect.Echo;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;


/**
 * Created by konra on 06.02.2017.
 */
public class Amp implements Module {

    SourceDataLine line;
    Envelope env;
    Echo echo;

    AdjustableValue<Double> attack, decay;

    public Amp()  {

        AudioFormat format = new AudioFormat(Synth.SAMPLE_RATE, Synth.BIT_DEPTH, 1, true, true);

        try {

            line = AudioSystem.getSourceDataLine(format);
            line.open(format, Synth.BUFFER_SIZE *4);

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        line.start();

        attack = new AdjustableValue<>(20d);
        decay = new AdjustableValue<>(150d);

        echo = new Echo();

    }

    public void sendBuffer(double[] buffer){

        applyEnvelope(buffer);
        buffer = echo.processBuffer(buffer);
        amplifyAndWrite(buffer);
    }

    public void startEnvelope() {

        env = new Envelope(attack.getValue(),decay.getValue());
    }


    private void applyEnvelope(double[] buffer){


            for(int i = 0; i < Synth.BUFFER_SIZE; i++){

                    buffer[i] *= env.nextFactor();
            }
    }


    private void amplifyAndWrite(double[] buffer){


        byte[] outputBuffer = new byte[2*Synth.BUFFER_SIZE];
        short shortValue;

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

            ByteBuffer sample = ByteBuffer.allocate(2);
            shortValue = (short) (buffer[i] * Short.MAX_VALUE);
            sample.putShort(shortValue);

            byte[] sampleBytes = sample.array();



            outputBuffer[2*i] = sampleBytes[0];
            outputBuffer[2*i+1] = sampleBytes[1];
        }

        line.write(outputBuffer, 0, 2*Synth.BUFFER_SIZE);
    }

    public void setOutput(Module module){

    }

    public AdjustableValue<Double> attack(){
        return attack;
    }

    public AdjustableValue<Double> decay(){
        return decay;
    }

    public Echo echo(){
        return echo;
    }



}
