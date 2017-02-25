package subslim.synth.wave;

import subslim.synth.Synth;

/**
 * Created by konra on 08.02.2017.
 */
public class SquareWave extends Wave {

    public double getSample(int sampleNo){

        double fourierExpansion = 0;

        for(int k = 1; k <= 50; k++){

            fourierExpansion += (4 / Math.PI) * (Math.sin(2 * Math.PI * (2*k - 1) * (frequency / Synth.SAMPLE_RATE) * sampleNo)) / (2*k -1);
        }

        return fourierExpansion;
    }
}
