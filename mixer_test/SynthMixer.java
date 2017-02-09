package mixer_test;


public class SynthMixer {

    Synth synth;
    double[] buffer1, buffer2;
    boolean buffersProcessed;


    public SynthMixer(Synth synth){

        buffer1 = null;
        buffer2 = null;
        this.synth = synth;
    }

    public synchronized void sendBuffer(double[] buffer) {



            if (buffer1 == null) {

                buffersProcessed = false;

                buffer1 = new double[Synth.bufferSize];

                for (int i = 0; i < Synth.bufferSize; i++) {
                    buffer1[i] = buffer[i];
                }

                try {

//                    while(!buffersProcessed) {
                        wait();
//                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {

                buffer2 = buffer;

                mixAndPass();

                buffer1 = null;
                buffer2 = null;

                buffersProcessed = true;
                notify();
            }
    }

    private void mixAndPass(){

        double[] outBuffer = new double[Synth.bufferSize];

        for(int i = 0; i < Synth.bufferSize; i++){

            outBuffer[i] = (buffer1[i] + buffer2[i])/2;
        }

        synth.passBuffer(outBuffer);
    }
}