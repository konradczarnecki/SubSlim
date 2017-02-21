package msynth.synth;

import msynth.AdjustableValue;

/**
 * Created by konra on 09.02.2017.
 */
public class Filter implements Module {

    private Module out;
    private double reminder;
    private AdjustableValue<Double> resonance;
    private AdjustableValue<Double> cutoff;
    private AdjustableValue<Double> envelopeAmount;
    private Envelope env;
    private Lfo lfo;


    private AdjustableValue<Double> attack, decay;

    private double y1, y2,y3, y4, oldx, oldy1, oldy2, oldy3;

    public Filter() {


        y1 = y2 = y3 = y4 = oldx = oldy1 = oldy2 = oldy3 = 0;
        cutoff = new AdjustableValue<>(5000d);
        resonance = new AdjustableValue<>(0d);
        envelopeAmount = new AdjustableValue<>(0d);
        reminder = 0;
        attack = new AdjustableValue<>(20d);
        decay = new AdjustableValue<>(50d);

        lfo = new Lfo(1,0);
    }


    @Override
    public void sendBuffer(double[] buffer) {

        processBuffer(buffer);
    }

    @Override
    public void setOutput(Module module) {
        out = module;
    }



    public void startEnvelope(){
        env = new Envelope(attack.getValue(),decay.getValue());
    }


    public double[] processBuffer(double[] buffer){

        double[] outBuffer = new double[Synth.BUFFER_SIZE];


        y4 = reminder;
        double x;

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

            double cutoff = this.cutoff.getValue();


            cutoff = cutoff * (1 - envelopeAmount.getValue()) + envelopeAmount.getValue() * cutoff * env.nextFactor();
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

        bufferOut(outBuffer);

        return outBuffer;

    }

    private void bufferOut(double[] buffer){

        if(out != null)
        out.sendBuffer(buffer);
    }


    public AdjustableValue<Double> attack(){
        return attack;
    }

    public AdjustableValue<Double> decay(){
        return decay;
    }

    public AdjustableValue<Double> cutoff(){
        return cutoff;
    }

    public AdjustableValue<Double> resonance(){
        return resonance;
    }

    public AdjustableValue<Double> envAmount(){
        return envelopeAmount;
    }

    public AdjustableValue<Double> lfoFrequency(){ return lfo.frequency();}

    public AdjustableValue<Double> lfoDepth(){ return lfo.depth();}

}
