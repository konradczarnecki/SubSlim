package synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Envelope {

    private double[] factors;

    int adsCounter;
    int rCounter;

    public Envelope(double attack, double decay){

        adsCounter = 0;
        rCounter = 0;

        int attackInSamples = (int)  ((attack/1000)* (double) Synth.sampleRate);
        int decayInSamples = (int) ((decay/1000)* (double) Synth.sampleRate);


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

        if(adsCounter < factors.length)
            return factors[adsCounter++];
        else return 0;

    }

}
