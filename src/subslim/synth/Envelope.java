package subslim.synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Envelope {

    private double[] outputFactors;
    private double attackShape;
    private double decayShape;

    private int counter;

    public Envelope(double attack, double decay){

        counter = 0;
        attackShape = 0.6;
        decayShape = 1.5;

        int attackInSamples = (int)  ((attack/1000)* Synth.SAMPLE_RATE);
        int decayInSamples = (int) ((decay/1000)* Synth.SAMPLE_RATE);

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

        if(counter < outputFactors.length) return outputFactors[counter++];
        else return 0;
    }

    public void setShape(double attackFactor, double decayFactor){

        attackShape = attackFactor;
        decayShape = decayFactor;
    }

}
