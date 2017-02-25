package subslim.synth;

import java.util.ArrayList;


public class ChannelMixer implements Module {

    private Module out;
    private ArrayList<double[]> buffers;
    private int numberOfChannels;

    public ChannelMixer(int numberOfChannels){

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

        double[] outBuffer = new double[Synth.BUFFER_SIZE];

        for(int i = 0; i < Synth.BUFFER_SIZE; i++){

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