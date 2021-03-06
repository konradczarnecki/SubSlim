package subslim.synth.filter;

import subslim.AdjustableValue;
import subslim.synth.Envelope;
import subslim.synth.Lfo;

/**
 * Created by konra on 25.02.2017.
 */
public abstract class FilterType {

    protected AdjustableValue<Double> resonance;
    protected AdjustableValue<Double> cutoff;
    protected AdjustableValue<Double> envelopeAmount;
    protected Envelope env;
    protected Lfo lfo;

    public abstract double[] processBuffer(double[] buffer);

    public void setFields(AdjustableValue<Double> resonance, AdjustableValue<Double> cutoff,
                          AdjustableValue<Double> envelopeAmount, Envelope env, Lfo lfo){

        this.resonance = resonance;
        this.cutoff = cutoff;
        this.envelopeAmount = envelopeAmount;
        this.env = env;
        this.lfo = lfo;
    }
}
