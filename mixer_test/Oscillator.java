package mixer_test;

public class Oscillator  {

    WaveShape wave;
    boolean hold;
    SynthMixer mixer;

    public Oscillator(WaveShape wave, SynthMixer mixer){

        this.wave = wave;
        this.mixer = mixer;
        hold = true;

    }

    public void start(int frequency){


        wave.setFrequency(frequency);

        Thread oscillatorLoop = new Thread(new Runnable(){

            public void run(){

                int sampleNo = 0;
                double[] buffer = new double[Synth.bufferSize];

                while(hold){

                    for(int i = 0; i < Synth.bufferSize; i++){

                        buffer[i] = wave.getSample(sampleNo + i);
                    }

                    mixer.sendBuffer(buffer);
                    sampleNo += Synth.bufferSize;
                }

            }
        });

        oscillatorLoop.start();
    }

    public void stop(){
        hold = false;
    }

}
