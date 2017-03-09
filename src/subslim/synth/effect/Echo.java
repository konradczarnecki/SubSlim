package subslim.synth.effect;

import subslim.AdjustableValue;
import subslim.synth.Synth;

/**
 * Created by konra on 17.02.2017.
 */
public class Echo {

    public static final double WET_DEFAULT = 0;
    public static final double VERB_DEFAULT = 0.2;
    public static final double DELAY1_TIME_DEFAULT = 150;
    public static final double DELAY2_TIME_DEFAULT = 75;

    private Reverb verb;
    private Delay delay1, delay2;
    private AdjustableValue<Double> wet;
    private AdjustableValue<Double> verbAmount;

    public Echo(){

        wet = new AdjustableValue<>(WET_DEFAULT);
        verbAmount = new AdjustableValue<>(VERB_DEFAULT);

        verb = new Reverb(verbAmount);
        delay1 = new Delay(DELAY1_TIME_DEFAULT,wet);
        delay2 = new Delay(DELAY2_TIME_DEFAULT,wet);
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
