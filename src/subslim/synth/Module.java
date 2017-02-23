package subslim.synth;

/**
 * Created by konra on 06.02.2017.
 */
public interface Module {

    public void sendBuffer(double[] buffer);
    public void setOutput(Module module);
}
