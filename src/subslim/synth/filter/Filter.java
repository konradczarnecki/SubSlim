package subslim.synth.filter;

import subslim.AdjustableValue;
import subslim.synth.Envelope;
import subslim.synth.Lfo;
import subslim.synth.Module;

/**
 * Created by konra on 09.02.2017.
 */
public class Filter implements Module {

    public static final double ATTACK_DEFAULT = 40;
    public static final double DECAY_DEFAULT = 180;
    public static final double CUTOFF_DEFAULT = 3000;
    public static final double RESONANCE_DEFAULT = 0;
    public static final double ENV_AMOUNT_DEFAULT = 0;


    private Module out;

    private AdjustableValue<Double> resonance;
    private AdjustableValue<Double> cutoff;
    private AdjustableValue<Double> envelopeAmount;
    private AdjustableValue<FilterType> type;
    private Envelope env;
    private Lfo lfo;

    private AdjustableValue<Double> attack, decay;


    public Filter() {

        cutoff = new AdjustableValue<>(CUTOFF_DEFAULT);
        resonance = new AdjustableValue<>(RESONANCE_DEFAULT);
        envelopeAmount = new AdjustableValue<>(ENV_AMOUNT_DEFAULT);
        attack = new AdjustableValue<>(ATTACK_DEFAULT);
        decay = new AdjustableValue<>(DECAY_DEFAULT);
        lfo = new Lfo();
        type = new AdjustableValue<>(new MoogFilter());
    }


    @Override
    public void sendBuffer(double[] buffer) {

        buffer = type.getValue().processBuffer(buffer);
        bufferOut(buffer);
    }

    @Override
    public void setOutput(Module module) {
        out = module;
    }



    public void startEnvelope(){

        type.getValue().setFields(resonance,cutoff,envelopeAmount,env,lfo);
        env = new Envelope(attack.getValue(),decay.getValue());
        env.setShape(0.8,0.8);
        type.getValue().setEnvelope(env);
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

    public AdjustableValue<FilterType> type(){ return type;}

}
