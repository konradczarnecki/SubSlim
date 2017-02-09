package synth;

import synth.Module;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SynthMixer implements Module {

    Synth synth;
    int outModuleCode;
    ArrayList<double[]> buffers;
    int numberOfChannels;
    CyclicBarrier barrier;
    Object lock;

    public SynthMixer(Synth synth, int numberOfChannels){

        buffers = new ArrayList<>();
        this.synth = synth;
        this.numberOfChannels = numberOfChannels;
        lock = new Object();
        barrier = new CyclicBarrier(numberOfChannels, new Runnable(){
            @Override
            public void run() {
                mixAndPass();
                buffers.clear();
            }
        });
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setOutput(int moduleCode) {
        outModuleCode = moduleCode;
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

        synth.passBuffer(outBuffer, outModuleCode);
    }
}