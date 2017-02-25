package subslim.synth.effect;

import subslim.AdjustableValue;
import subslim.synth.Synth;

/**
 * Created by konra on 17.02.2017.
 */
public class Reverb {

    private static final double FEEDBACK_COEFF = 0.6;
    private static final double[] VARIATION_FACTORS = {0.99, 1.03, 0.89, 0,97, 1};

    private AllPassFilter[] filters;

    private AdjustableValue<Double> wet;

    public Reverb(double[] allPassFilterDelays, AdjustableValue<Double> wet){

        if(allPassFilterDelays.length != 5) throw new IllegalArgumentException();

        filters = new AllPassFilter[5];

        for(int i = 0; i < 5; i++){
            filters[i] = new AllPassFilter(allPassFilterDelays[i]);
        }

        this.wet = wet;

    }

    public double[] processBuffer(double[] buffer){

        double[] outBuffer;

        outBuffer = filters[0].processBuffer(buffer,FEEDBACK_COEFF*VARIATION_FACTORS[0]);

        for(int i = 1; i < 5; i++){
            outBuffer = filters[i].processBuffer(outBuffer,FEEDBACK_COEFF*VARIATION_FACTORS[i]);
        }
        
        for(int i = 0; i < Synth.BUFFER_SIZE; i++){
            outBuffer[i] = (1-wet.getValue())*buffer[i] + wet.getValue()*outBuffer[i];
        }

        return outBuffer;
    }

}
