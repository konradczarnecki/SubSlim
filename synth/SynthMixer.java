package synth;


import java.util.ArrayList;


public class SynthMixer implements Module {

    Module out;
    ArrayList<double[]> buffers;
    int numberOfChannels;

    public SynthMixer(int numberOfChannels){

        buffers = new ArrayList<>();
        this.numberOfChannels = numberOfChannels;

    }

    @Override
    public synchronized void sendBuffer(double[] buffer) {

        buffers.add(buffer);

        if(buffers.size() == numberOfChannels){

            mixAndPass();

            buffers.clear();

            notifyAll();

        } else {

            try {

                wait();

            } catch (InterruptedException e) { }
        }

    }

    @Override
    public void setOutput(Module module) {
        out = module;
    }

    private void mixAndPass(){

        double[] outBuffer = new double[Synth.bufferSize];

        for(int i = 0; i < Synth.bufferSize; i++){

            double sum = 0;

             for(double[] b: buffers) {

                 sum += b[i];
             }

             sum /= buffers.size();


             outBuffer[i] = sum;
        }

        out.sendBuffer(outBuffer);
    }
}