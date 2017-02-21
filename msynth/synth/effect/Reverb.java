package msynth.synth.effect;

import msynth.AdjustableValue;
import msynth.synth.Synth;

import java.util.Random;

/**
 * Created by konra on 17.02.2017.
 */
public class Reverb {

    private AllPassFilter f1, f2, f3, f4, f5;
    private AdjustableValue<Double> feedbackCoeff;
    private AdjustableValue<Double> wet;

    public Reverb(double[] coefficients, AdjustableValue<Double> wet){

        if(coefficients.length != 5) throw new IllegalArgumentException();

        feedbackCoeff = new AdjustableValue<>(0.5);
        this.wet = wet;

        f1 = new AllPassFilter(coefficients[0]);
        f2 = new AllPassFilter(coefficients[1]);
        f3 = new AllPassFilter(coefficients[2]);
        f4 = new AllPassFilter(coefficients[3]);
        f5 = new AllPassFilter(coefficients[4]);
    }

    public double[] processBuffer(double[] buffer){

        double[] outBuffer;

        outBuffer = f1.processBuffer(buffer, feedbackCoeff.getValue());
        outBuffer = f2.processBuffer(outBuffer, feedbackCoeff.getValue()*0.91);
        outBuffer = f3.processBuffer(outBuffer, feedbackCoeff.getValue()*1.01);
        outBuffer = f4.processBuffer(outBuffer, feedbackCoeff.getValue()*0.87);
        outBuffer = f5.processBuffer(outBuffer, feedbackCoeff.getValue());


        for(int i = 0; i < Synth.BUFFER_SIZE; i++){
            outBuffer[i] = (1-wet.getValue())*buffer[i] + wet.getValue()*outBuffer[i];
        }

        return outBuffer;
    }

    public AdjustableValue<Double> feedbackCoeff(){
        return feedbackCoeff;
    }

}
