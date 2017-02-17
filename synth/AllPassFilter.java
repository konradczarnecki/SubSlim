package synth;

import java.util.LinkedList;

/**
 * Created by konra on 17.02.2017.
 */
public class AllPassFilter {

    private LinkedList<Double> delayBuffer;
    double coefficient;


    public AllPassFilter(double delay, double coefficient){

        delayBuffer = new LinkedList();

        for(int i = 0; i < (delay/1000)*Synth.sampleRate; i++){
            delayBuffer.addFirst(0d);
        }

        this.coefficient = coefficient;
    }


    public double[] processBuffer(double[] buffer,double coefficient){

        double[] outBuffer = new double[Synth.bufferSize];

        for(int i = 0; i < Synth.bufferSize; i++){

            double fromDelay = delayBuffer.removeLast();
            double toDelay = (buffer[i] + coefficient*fromDelay);

            delayBuffer.addFirst(toDelay);

            double out = fromDelay - coefficient*toDelay;

            outBuffer[i] = out;
        }

        return outBuffer;

    }
}
