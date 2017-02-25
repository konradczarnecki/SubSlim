package subslim.synth;

import subslim.AdjustableValue;

/**
 * Created by konra on 25.02.2017.
 */
public class MoogFilter extends FilterType {

    private double y1 = 0,  y2 = 0, y3 = 0, y4 = 0, oldx = 0, oldy1 = 0, oldy2 = 0, oldy3 = 0;
    private double reminder = 0;

    public MoogFilter(AdjustableValue<Double> resonance, AdjustableValue<Double> cutoff,
                      AdjustableValue<Double> envelopeAmount, Envelope env, Lfo lfo){

        super(resonance, cutoff, envelopeAmount, env, lfo);
    }

    public double[] processBuffer(double[] buffer){

        double[] outBuffer = new double[Synth.BUFFER_SIZE];

        y4 = reminder;
        double x;

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

            double cutoff = this.cutoff.getValue();


            cutoff = cutoff * (1 - envelopeAmount.getValue()) + Math.pow(envelopeAmount.getValue(),0.5d) * cutoff * env.nextFactor();
            cutoff = cutoff * lfo.nextFactor();

            double f = 2 * cutoff / Synth.SAMPLE_RATE;
            double k = 3.6*f - 1.6*f*f -1;
            double p = (k+1) * 0.5;
            double scale = Math.pow(Math.E, (1-p) * 1.386249);
            double r = resonance.getValue() * scale;

            x = buffer[i] - r * y4;

            y1 = x*p +oldx*p -k*y1;
            y2 = y1*p + oldy1*p - k*y2;
            y3 = y2*p + oldy2*p - k*y3;
            y4 = y3*p + oldy3*p - k*y4;

            y4 = y4 - (y4*y4*y4)/6;

            outBuffer[i] = y4;

            oldx = x;
            oldy1 = y1;
            oldy2 = y2;
            oldy3 = y3;
        }

        reminder = outBuffer[Synth.BUFFER_SIZE -1];

        return outBuffer;
    }
}
