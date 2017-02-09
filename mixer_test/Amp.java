package mixer_test;

import synth.Envelope;
import synth.Module;
import synth.Synth;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.nio.ByteBuffer;


/**
 * Created by konra on 06.02.2017.
 */
public class Amp implements Module {

    SourceDataLine line;
    Envelope env;

    public Amp()  {

        AudioFormat format = new AudioFormat(synth.Synth.sampleRate, synth.Synth.bitDepth, 1, true, true);

        try {
            line = AudioSystem.getSourceDataLine(format);
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        line.start();
    }

    public void sendBuffer(double[] buffer){

        applyEnvelope(buffer);
        amplifyAndWrite(buffer);
    }

    private void applyEnvelope(double[] buffer){

    }

    private void amplifyAndWrite(double[] buffer){



        byte[] outputBuffer = new byte[2* synth.Synth.bufferSize];
        short shortValue;

        for(int i = 0; i < synth.Synth.bufferSize; i++){

            ByteBuffer sample = ByteBuffer.allocate(2);
            shortValue = (short) (buffer[i] * Short.MAX_VALUE);
            sample.putShort(shortValue);

            byte[] sampleBytes = sample.array();

           // line.write(sampleBytes,0,2);

            outputBuffer[2*i] = sampleBytes[0];
            outputBuffer[2*i+1] = sampleBytes[1];
        }

        line.write(outputBuffer, 0, 2* Synth.bufferSize);
    }

    public void setOutput(Module module){

    }
}
