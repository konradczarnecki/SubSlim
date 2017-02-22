package msynth.synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Envelope {

    private double[] factors;

    int counter;

    public Envelope(double attack, double decay){

        counter = 0;

        int attackInSamples = (int)  ((attack/1000)* (double) Synth.SAMPLE_RATE);
        int decayInSamples = (int) ((decay/1000)* (double) Synth.SAMPLE_RATE);


        factors = new double[attackInSamples + decayInSamples];


        double attackIncrement = 1d / attackInSamples;
        double decayIncrement = (1d) / decayInSamples;


        for(int i = 0; i < attackInSamples; i++){
            factors[i] = i * attackIncrement;
        }

        for(int i = attackInSamples; i < attackInSamples + decayInSamples; i++){
            factors[i] = 1 - (i - attackInSamples) * decayIncrement;
        }

    }

    public double nextFactor(){

        if(counter < factors.length)
            return factors[counter++];
        else return 0;

    }

}
