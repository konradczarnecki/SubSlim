package msynth.synth.effect;

import msynth.AdjustableValue;
import msynth.synth.Synth;

import java.util.LinkedList;

/**
 * Created by konra on 16.02.2017.
 */
public class Delay {

    AdjustableValue<Double> wet;
    private LinkedList<Double> delayBuffer;

    public Delay(double delay, AdjustableValue<Double> wet){


        int delayBufferSize = (int) (((delay/1000d)*(double) Synth.SAMPLE_RATE));

        delayBuffer = new LinkedList<>();

        for(int i = 0; i < delayBufferSize; i++){
            delayBuffer.add(0d);
        }

        this.wet = wet;

    }


    public double[] processBuffer(double[] buffer){


        double[] outBuffer = new double[Synth.BUFFER_SIZE];

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){
            outBuffer[i] = ((1-wet.getValue())*buffer[i] + wet.getValue()*delayBuffer.removeLast());


            delayBuffer.addFirst(buffer[i]);
        }

        return outBuffer;
    }
}
