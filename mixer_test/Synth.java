package mixer_test;

public class Synth {

    public static final int sampleRate = 44100;
    public static final int bitDepth = 16;
    public static final int bufferSize = 512;


    Oscillator osc1, osc2;
    SynthMixer mixer;
    Amp amp1; // converts double[] to byte[] and writes to SourceDataLine instance

    public Synth(){

        mixer = new SynthMixer(this);
        osc1 = new Oscillator(WaveShape.SINE, mixer );
        osc2 = new Oscillator(WaveShape.SQUARE, mixer);
        amp1 = new Amp();
    }

    public void passBuffer (double[] buffer){

        amp1.sendBuffer(buffer);
    }


    public void play(){

        osc2.start(400);
        osc1.start(450);

    }

    public static void main (String[] args){

        Synth synth = new Synth();
        synth.play();

    }
}
