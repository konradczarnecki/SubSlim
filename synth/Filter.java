package synth;

/**
 * Created by konra on 09.02.2017.
 */
public class Filter implements Module {

    Module out;
    double reminder;
    double resonance;
    double a;
    double envelopeAmount;

    public Filter() {

        double cutoff = 2000;

        double rc = 1 / (2 * Math.PI * cutoff);

        a = (1 / (double) Synth.sampleRate) / ((1 / (double) Synth.sampleRate) + rc);

        reminder = 0;
        envelopeAmount = 0;
    }

    @Override
    public void sendBuffer(double[] buffer) {

        double[] outBuffer;

        outBuffer = processBuffer(buffer);

        out.sendBuffer(outBuffer);

    }

    @Override
    public void setOutput(Module module) {
        out = module;
    }

    private double[] processBuffer(double[] buffer){

        double[] outBuffer = new double[Synth.bufferSize];

        outBuffer[0] = a * buffer[0] + (1d-a) * reminder;

        for(int i = 1; i < Synth.bufferSize; i++){
            outBuffer[i] = a * buffer[i] + (1d-a) * outBuffer[i-1];
        }

        reminder = outBuffer[Synth.bufferSize-1];

        return outBuffer;
    }
}
