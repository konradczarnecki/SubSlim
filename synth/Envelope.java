package synth;

/**
 * Created by konra on 06.02.2017.
 */
public class Envelope {

    private int attack; // ms
    private int decay; // ms
    private double sustain; // 0-1
    private int release; // ms

    public Envelope(int attack, int decay, double sustain, int release){

        this.attack = attack;
        this.decay = decay;
        this.sustain = sustain;
        this.release = release;
    }

    public Envelope(){

    }

    public double getFactor(){
        return 5;
    }

}
