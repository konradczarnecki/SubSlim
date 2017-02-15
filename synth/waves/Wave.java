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

    public static Wave getWave(Wave type){
        if(type instanceof SawtoothWave) return new SawtoothWave();
        else if (type instanceof SineWave) return new SineWave();
        else if (type instanceof  SquareWave) return new SquareWave();
        else throw new IllegalArgumentException();
    }

}
