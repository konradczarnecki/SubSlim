package msynth.synth.effect;

import msynth.synth.Synth;

import java.util.LinkedList;

/**
 * Created by konra on 17.02.2017.
 */
public class AllPassFilter {

    private LinkedList<Double> delayBuffer;


    public AllPassFilter(double delay){

        delayBuffer = new LinkedList();

        for(int i = 0; i < (delay/1000)* Synth.SAMPLE_RATE; i++){
            delayBuffer.addFirst(0d);
        }

    }

    public double[] processBuffer(double[] buffer,double coefficient){

        double[] outBuffer = new double[Synth.BUFFER_SIZE];

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

            double fromDelay = delayBuffer.removeLast();
            double toDelay = (buffer[i] + coefficient*fromDelay);

            delayBuffer.addFirst(toDelay);

            double out = fromDelay - coefficient*toDelay;

            outBuffer[i] = out;
        }

        return outBuffer;

    }
}
