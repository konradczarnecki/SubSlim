package synth;

import synth.Module;

public class SynthMixer implements Module {

    Synth synth;
    double[] buffer1, buffer2;
    int outModuleCode;

    public SynthMixer(Synth synth){

        buffer1 = null;
        buffer2 = null;
    }

    @Override
    public synchronized void sendBuffer(double[] buffer) {


            if (buffer1 == null) {

                buffer1 = new double[Synth.bufferSize];

                for (int i = 0; i < Synth.bufferSize; i++) {
                    buffer1[i] = buffer[i];
                }

                try {
                    wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                buffer2 = buffer;

                mixAndPass();

                buffer1 = null;
                buffer2 = null;

                notifyAll();
            }

    }

    @Override
    public void setOutput(int moduleCode) {
        outModuleCode = moduleCode;
    }

    private void mixAndPass(){

        double[] outBuffer = new double[Synth.bufferSize];

        for(int i = 0; i < Synth.bufferSize; i++){

            outBuffer[i] = (buffer1[i] + buffer2[i])/2;
        }

        synth.passBuffer(outBuffer, outModuleCode);
    }
}