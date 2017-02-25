package subslim.synth;

import subslim.AdjustableValue;
import subslim.synth.effect.Echo;
import wavfile.WavFile;
import wavfile.WavFileException;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by konra on 06.02.2017.
 */
public class Amp implements Module {

    public static final double ATTACK_DEFAULT = 40;
    public static final double DECAY_DEFAULT = 180;

    private SourceDataLine line;
    private Envelope env;
    private Echo echo;

    private AdjustableValue<Double> attack, decay, outputLevel;

    public Amp()  {

        AudioFormat format = new AudioFormat(Synth.SAMPLE_RATE, Synth.BIT_DEPTH, 1, true, false);

        try {

            line = AudioSystem.getSourceDataLine(format);
            line.open(format, Synth.BUFFER_SIZE *4);

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        line.start();

        attack = new AdjustableValue<>(ATTACK_DEFAULT);
        decay = new AdjustableValue<>(DECAY_DEFAULT);
        outputLevel = new AdjustableValue<>(1d);

        echo = new Echo();

    }

    public void sendBuffer(double[] buffer){

        applyEnvelope(buffer);
        buffer = echo.processBuffer(buffer);

        byte[] outBuffer = toBytes(buffer);
        write(outBuffer);

    }

    public void startEnvelope() {

        env = new Envelope(attack.getValue(),decay.getValue());
    }


    private void applyEnvelope(double[] buffer){


            for(int i = 0; i < Synth.BUFFER_SIZE; i++){

                    buffer[i] *= env.nextFactor();
            }
    }


    private byte[] toBytes(double[] buffer){


        byte[] outputBuffer = new byte[2*Synth.BUFFER_SIZE];
        short shortValue;

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

            ByteBuffer sample = ByteBuffer.allocate(2);
            shortValue = (short) (buffer[i] * outputLevel.getValue() * Short.MAX_VALUE);
            sample.putShort(shortValue);

            byte[] sampleBytes = sample.array();

            outputBuffer[2*i] = sampleBytes[1];
            outputBuffer[2*i+1] = sampleBytes[0];
        }

        return outputBuffer;
    }

    private void write(byte[] buffer){

        line.write(buffer, 0, 2*Synth.BUFFER_SIZE);
    }


    public void setOutput(Module module){}

    public AdjustableValue<Double> attack(){
        return attack;
    }

    public AdjustableValue<Double> decay(){
        return decay;
    }

    public Echo echo(){
        return echo;
    }

    public AdjustableValue<Double> outputLevel(){ return outputLevel;}




}
