package synth.waves;

/**
 * Created by konra on 08.02.2017.
 */
public abstract class Wave {

    double frequency;


    public abstract double getSample(int sampleNo);

    public void setFrequency(double frequency){
        this.frequency = frequency;
    }

}
