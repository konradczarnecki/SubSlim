package subslim.synth;

import subslim.AdjustableValue;

/**
 * Created by konra on 11.02.2017.
 */
public class Lfo {

    public static final double RATE_DEFAULT = 1;
    public static final double DEPTH_DEFAULT = 0;

    private AdjustableValue<Double> frequency;
    private AdjustableValue<Double> depth;
    private int counter;

    public Lfo(){

        this.frequency = new AdjustableValue<>(RATE_DEFAULT);
        this.depth = new AdjustableValue<>(DEPTH_DEFAULT);

        counter = 0;

    }
    public double nextFactor(){

        double factor = 0.1 + 0.9*((Math.sin(2*Math.PI*(frequency.getValue()/ Synth.SAMPLE_RATE)*counter)+1)/2);
        factor = Math.pow(factor,depth.getValue());
        counter++;

        return factor;

    }

    public AdjustableValue<Double> frequency(){
        return frequency;
    }

    public AdjustableValue<Double> depth(){
        return depth;
    }

}
