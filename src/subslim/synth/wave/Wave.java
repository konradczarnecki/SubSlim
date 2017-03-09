package subslim.synth.wave;

/**
 * Created by konra on 08.02.2017.
 */
public abstract class Wave {

    protected double frequency;

    public abstract double getSample(int sampleNo);

    public void setFrequency(double frequency){
        this.frequency = frequency;
    }

    public static Wave[] getWaves(){

        Wave[] waves = {new SineWave(), new SawtoothWave(), new SquareWave()};

        return waves;
    }

}
