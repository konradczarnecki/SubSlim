package msynth.synth.effect;

import msynth.AdjustableValue;
import msynth.synth.Synth;

/**
 * Created by konra on 17.02.2017.
 */
public class Echo {

    private Reverb verb;
    private Delay delay1, delay2;
    private AdjustableValue<Double> wet;
    private AdjustableValue<Double> verbAmount;


    private double[] coeffs = {7,23,59,67,71};

    public Echo(){

        wet = new AdjustableValue<>(0d);
        verbAmount = new AdjustableValue<>(0d);

        verb = new Reverb(coeffs, verbAmount);
        delay1 = new Delay(150,new AdjustableValue<>(0.4));
        delay2 = new Delay(75,new AdjustableValue<>(0.4));

    }

    public double[] processBuffer(double[] buffer){

        double[] outBuffer;

        outBuffer = delay1.processBuffer(buffer);
        outBuffer = delay2.processBuffer(outBuffer);
        outBuffer = verb.processBuffer(outBuffer);

        for(int i = 0; i < Synth.BUFFER_SIZE; i++) {
            outBuffer[i] = (1 - wet.getValue()) * buffer[i] + wet.getValue() * outBuffer[i];
        }

        return outBuffer;

    }

    public void setDelay1(double time){
        delay1 = new Delay(time, wet);
    }

    public void setDelay2(double time){
        delay2 = new Delay(time, wet);
    }


    public AdjustableValue<Double> wet(){
        return wet;
    }

    public AdjustableValue<Double> verbAmount(){
        return verbAmount;
    }
}
