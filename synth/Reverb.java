package synth;

/**
 * Created by konra on 17.02.2017.
 */
public class Reverb {

    AllPassFilter f1, f2, f3, f4, f5;
    AdjustableValue<Double> feedbackCoeff;
    AdjustableValue<Double> wet;

    public Reverb(double[] coefficients){

        if(coefficients.length != 5) throw new IllegalArgumentException();

        feedbackCoeff = new AdjustableValue<>(0.5);
        wet = new AdjustableValue<>(0d);

        f1 = new AllPassFilter(coefficients[0],feedbackCoeff.getValue()*1.1);
        f2 = new AllPassFilter(coefficients[1], feedbackCoeff.getValue()*0.97);
        f3 = new AllPassFilter(coefficients[2], feedbackCoeff.getValue()*1.17);
        f4 = new AllPassFilter(coefficients[3], feedbackCoeff.getValue()*0.8);
        f5 = new AllPassFilter(coefficients[4], feedbackCoeff.getValue()*1.03);
    }

    public double[] processBuffer(double[] buffer){

        double[] outBuffer;

        outBuffer = f1.processBuffer(buffer, feedbackCoeff.getValue()*1.03);
        outBuffer = f2.processBuffer(outBuffer, feedbackCoeff.getValue()*0.97);
        outBuffer = f3.processBuffer(outBuffer, feedbackCoeff.getValue()*1.14);
        outBuffer = f4.processBuffer(outBuffer, feedbackCoeff.getValue()*0.87);
        outBuffer = f5.processBuffer(outBuffer, feedbackCoeff.getValue()*0.95);


        for(int i = 0; i < Synth.bufferSize; i++){
            outBuffer[i] = (1-wet.getValue())*buffer[i] + wet.getValue()*outBuffer[i];
        }

        return outBuffer;
    }

    public AdjustableValue<Double> decay(){
        return feedbackCoeff;
    }

    public AdjustableValue<Double> wet(){
        return wet;
    }
}
