package synth;

/**
 * Created by konra on 11.02.2017.
 */
public class LFO {

    private double frequency;
    private double depth;
    private boolean stopped;

    public void start(double frequency){

        stopped = false;

        Thread lfoThread = new Thread(new Runnable(){
            @Override
            public void run() {

                while(!stopped){


                }
            }
        });

    }
}
