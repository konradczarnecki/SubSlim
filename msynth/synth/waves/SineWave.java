package msynth.synth.waves;

import msynth.synth.Synth;

/**
 * Created by konra on 08.02.2017.
 */
public class SineWave extends Wave {

    public double getSample(int sampleNo){
        return Math.sin(2* Math.PI * sampleNo * (frequency / Synth.SAMPLE_RATE));
    }
}
