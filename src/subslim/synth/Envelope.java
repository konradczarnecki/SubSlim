package subslim.synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Envelope {

    private static final double DEFAULT_ATTACK_SHAPE = 0.6;
    private static final double DEFAULT_DECAY_SHAPE = 1.5;

    private double[] outputFactors;
    private double attackShape = 0.6;
    private double decayShape = 1.5;

    int counter;

    public Envelope(double attack, double decay){

        counter = 0;

        int attackInSamples = (int)  ((attack/1000)* (double) Synth.SAMPLE_RATE);
        int decayInSamples = (int) ((decay/1000)* (double) Synth.SAMPLE_RATE);

        outputFactors = new double[attackInSamples + decayInSamples];

        double attackIncrement = 1d / attackInSamples;
        double decayIncrement = 1d / decayInSamples;


        for(int i = 0; i < attackInSamples; i++){
            outputFactors[i] = Math.pow((i * attackIncrement), attackShape);
        }

        for(int i = attackInSamples; i < attackInSamples + decayInSamples; i++){
            outputFactors[i] = Math.pow((1 - (i - attackInSamples) * decayIncrement), decayShape);
        }

    }

    public double nextFactor(){

        if(counter < outputFactors.length)
            return outputFactors[counter++];
        else return 0;

    }

    public void setShape(double attackFactor, double decayFactor){
        attackShape = attackFactor;
        decayShape = decayFactor;
    }

}
