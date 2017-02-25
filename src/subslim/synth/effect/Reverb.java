package subslim.synth.effect;

import subslim.AdjustableValue;
import subslim.synth.Synth;

/**
 * Created by konra on 17.02.2017.
 */
public class Reverb {

    private static final double FEEDBACK_COEFF = 0.3;
    private static final double[] FILTERS_DELAY_TIMES = {11,21,58,79,85,111,157};

    private AllPassFilter[] filters;

    private AdjustableValue<Double> wet;

    public Reverb(AdjustableValue<Double> wet){

        filters = new AllPassFilter[7];

        for(int i = 0; i < 7; i++){
            filters[i] = new AllPassFilter(FILTERS_DELAY_TIMES[i]);
        }

        this.wet = wet;

    }

    public double[] processBuffer(double[] buffer){

        double[] outBuffer;

        outBuffer = filters[0].processBuffer(buffer,FEEDBACK_COEFF);

        for(int i = 1; i < 7; i++){
            outBuffer = filters[i].processBuffer(outBuffer,FEEDBACK_COEFF);
        }
        
        for(int i = 0; i < Synth.BUFFER_SIZE; i++){
            outBuffer[i] = (1-wet.getValue())*buffer[i] + wet.getValue()*outBuffer[i];
        }

        return outBuffer;
    }

}
