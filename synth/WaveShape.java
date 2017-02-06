package synth;

/**
 * Created by konra on 06.02.2017.
 */
public enum WaveShape {

    SINE {

        int frequency;

        @Override
        public double getSample(int sampleNumber){
            return Math.sin(2* Math.PI * sampleNumber * ((double) frequency / Synth.sampleRate));
        }

        @Override
        public void setFrequency(int frequency){
            this.frequency = frequency;
        }

    },

    SAWTOOTH {

        int frequency;

        @Override
        public double getSample(int sampleNumber){
            return - (2/Math.PI) * Math.atan( 1 / (Math.tan( sampleNumber*Math.PI / (Synth.sampleRate / frequency ))));
        }

        @Override
        public void setFrequency(int frequency){
            this.frequency = frequency;
        }

    },

    SQUARE {

        int frequency;

        @Override
        public double getSample(int sampleNumber){

            if(Math.sin(2* Math.PI * sampleNumber * ((double) frequency / Synth.sampleRate)) <= 0) return -1;
            else return 1;
        }

        @Override
        public void setFrequency(int frequency){
            this.frequency = frequency;
        }

    };


    public abstract double getSample(int sampleNumber);
    public abstract void setFrequency(int frequency);
}
