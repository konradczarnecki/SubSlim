package synth;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by konra on 16.02.2017.
 */
public class Delay {

    AdjustableValue<Double> wet;
    private LinkedList<Double> delayBuffer;

    public Delay(double delay, AdjustableValue<Double> wet){


        int delayBufferSize = (int) (((delay/1000d)*(double)Synth.sampleRate));

        delayBuffer = new LinkedList<>();

        for(int i = 0; i < delayBufferSize; i++){
            delayBuffer.add(0d);
        }

        this.wet = wet;

    }


    public double[] processBuffer(double[] buffer){


        double[] outBuffer = new double[Synth.bufferSize];

        for(int i = 0; i < Synth.bufferSize; i++){
            outBuffer[i] = ((1-wet.getValue())*buffer[i] + wet.getValue()*delayBuffer.removeLast());


            delayBuffer.addFirst(buffer[i]);
        }

        return outBuffer;
    }
}
