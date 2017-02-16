package synth;

/**
 * Created by konra on 11.02.2017.
 */
public class Lfo {

    AdjustableValue<Double> frequency;
    AdjustableValue<Double> depth;
    private int counter;

    public Lfo(double frequency, double depth){

        this.frequency = new AdjustableValue<>(frequency);
        this.depth = new AdjustableValue<>(depth);

        counter = 0;

    }
    public double nextFactor(){

        double factor = (Math.sin(2*Math.PI*(frequency.getValue()/(double) Synth.sampleRate)*counter)+1)/2;
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
