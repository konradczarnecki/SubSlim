package subslim;

/**
 * Created by konra on 15.02.2017.
 */
public class AdjustableValue <E> {

    E value;

    public AdjustableValue(E value){
        this.value = value;
    }

    public void setValue(E value){
        this.value = value;
    }

    public E getValue(){ return value; }
    
}
