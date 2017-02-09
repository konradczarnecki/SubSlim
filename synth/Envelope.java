package synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Envelope {

    private double attack; // ms
    private double decay; // ms
    private double sustain; // 0-1
    private double release; // ms

    private double[] preSustainFactors;
    private double[] releaseFactors;

    int adsCounter = 0;
    int rCounter = 0;

    public Envelope(double attack, double decay, double sustain, double release){

        this.attack = attack;
        this.decay = decay;
        this.sustain = sustain;
        this.release = release;

        int attackInSamples = (int)  ((attack/1000)* (double) Synth.sampleRate);
        int decayInSamples = (int) ((decay/1000)* (double) Synth.sampleRate);
        int releaseInSamples = (int) ((release/1000)* (double) Synth.sampleRate);

        preSustainFactors = new double[attackInSamples + decayInSamples];
        releaseFactors = new double[releaseInSamples];

        double attackIncrement = 1d / attackInSamples;
        double decayIncrement = (1d-sustain) / decayInSamples;
        double releaseIncrement = sustain / releaseInSamples;


        for(int i = 0; i < attackInSamples; i++){
            preSustainFactors[i] = i * attackIncrement;
        }

        for(int i = attackInSamples; i < attackInSamples + decayInSamples; i++){
            preSustainFactors[i] = 1 - (i - attackInSamples) * decayIncrement;
        }

        for(int i = 0; i < releaseInSamples; i++){
            releaseFactors[i] = sustain - i * releaseIncrement;
        }
    }

    public Envelope(){

    }

    public double nextADSFactor(){

        if(adsCounter < preSustainFactors.length) return preSustainFactors[adsCounter++];
        else return sustain;
    }

    public double nextRFactor() throws ArrayIndexOutOfBoundsException{

        return releaseFactors[rCounter++];

    }

    public int numberOfReleaseSamples(){
        return releaseFactors.length;
    }

}
