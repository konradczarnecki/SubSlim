package subslim.synth.effect;

import subslim.synth.Synth;

import java.util.LinkedList;

/**
 * Created by konra on 17.02.2017.
 */
public class AllPassFilter {

    private LinkedList<Double> delayBuffer;


    public AllPassFilter(double delay){

        delayBuffer = new LinkedList<>();

        for(int i = 0; i < (delay/1000d) * (double) Synth.SAMPLE_RATE; i++){
            delayBuffer.addFirst(0d);
        }

    }

    public double[] processBuffer(double[] buffer,double feedbackCoefficient){

        double[] outBuffer = new double[Synth.BUFFER_SIZE];

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

            double fromDelay = delayBuffer.removeLast();
            double toDelay = (buffer[i] + feedbackCoefficient*fromDelay);

            delayBuffer.addFirst(toDelay);

            double out = fromDelay - feedbackCoefficient*toDelay;

            outBuffer[i] = out;
        }

        return outBuffer;

    }
}
