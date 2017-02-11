package synth;

/**
 * Created by konra on 09.02.2017.
 */
public class Filter implements Module {

    Module out;
    double reminder;
    double resonance;
    double cutoff;
    double envelopeAmount;
    Envelope env;
    boolean stopped;

    double attack, decay, sustain, release;

    double y1, y2,y3, y4, oldx, oldy1, oldy2, oldy3;

    public Filter() {

        y1 = y2 = y3 = y4 = oldx = oldy1 = oldy2 = oldy3 = 0;
        cutoff = 1000;
        resonance = 0.6;
        envelopeAmount = 0;
        reminder = 0;
        attack = 50;
        decay = 200;
        sustain = 0.2;
        release = 200;
    }

    @Override
    public void sendBuffer(double[] buffer) {

        processBuffer(buffer);
    }

    public void startEnvelope(){
        env = new Envelope(attack,decay);
    }


    @Override
    public void setOutput(Module module) {
        out = module;
    }


    private void processBuffer(double[] buffer){

        double[] outBuffer = new double[Synth.bufferSize];



        y4 = reminder;
        double x;

        for(int i = 0; i < Synth.bufferSize; i++){

            double cutoff = this.cutoff;


            cutoff = cutoff * (1 - envelopeAmount) + envelopeAmount * cutoff * env.nextFactor();


            double f = 2 * cutoff / Synth.sampleRate;
            double k = 3.6*f - 1.6*f*f -1;
            double p = (k+1) * 0.5;
            double scale = Math.pow(Math.E, (1-p) * 1.386249);
            double r = resonance * scale;

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

        reminder = outBuffer[Synth.bufferSize-1];

        out.sendBuffer(outBuffer);
    }

    public void setAttack(double attack){
        this.attack = attack;
    }

    public void setDecay(double decay){
        this.decay = decay;
    }


    public void setCutoff(double cutoff){
        this.cutoff = cutoff;
    }

    public void setResonance(double resonance){
        this.resonance = resonance;
    }

    public void setEnvelopeAmount(double env){
        this.envelopeAmount = env;
    }
}
