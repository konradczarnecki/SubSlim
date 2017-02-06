package synth;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;


/**
 * Created by konra on 06.02.2017.
 */
public class Amp implements Module {

    SourceDataLine line;

    public Amp() throws LineUnavailableException {

        AudioFormat format = new AudioFormat(Synth.sampleRate, Synth.bitDepth, 1, true, true);


        line = AudioSystem.getSourceDataLine(format);
        line.open(format, 2*Synth.bufferSize);
        line.start();
    }

    public void sendBuffer(double[] buffer){

        applyEnvelope(buffer);
        amplifyAndWrite(buffer);
    }

    private void applyEnvelope(double[] buffer){

    }

    private void amplifyAndWrite(double[] buffer){



        byte[] outputBuffer = new byte[2*Synth.bufferSize];
        short shortValue;

        for(int i = 1; i < Synth.bufferSize; i++){

            ByteBuffer sample = ByteBuffer.allocate(2);
            shortValue = (short) (buffer[i] * Short.MAX_VALUE);
            sample.putShort(shortValue);

            byte[] sampleBytes = sample.array();

            outputBuffer[2*i-1] = sampleBytes[0];
            outputBuffer[2*i] = sampleBytes[1];
        }

        line.write(outputBuffer, 0, 2*Synth.bufferSize);
    }

    public void setOutput(int moduleCode){

    }
}
