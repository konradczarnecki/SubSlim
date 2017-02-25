package subslim.synth.wave;

import subslim.synth.Synth;

/**
 * Created by konra on 08.02.2017.
 */
public class SawtoothWave extends Wave {

    public double getSample(int sampleNo){

        double fourierExpansion = 1 / 2;

        for(int k = 1; k <= 50; k++) {

            fourierExpansion -= (1 / Math.PI) * Math.pow(-1, k) *
                    (Math.sin(2 * Math.PI * k * (frequency/ Synth.SAMPLE_RATE) * sampleNo)) / k;
        }

        return fourierExpansion;
    }
}
