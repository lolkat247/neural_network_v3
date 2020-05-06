package neuron;

import java.util.Vector;

public class Core {
    
    public Vector<double[]> dataHolder = new Vector<double[]>(); // TODO fix this
    Helper helper = new Helper();
    double[] lastIn;
    public double[] weights; // TODO you should probably stop doing this
    int inputs;
    
    public Core(int p) {
    	inputs = p;
    	weights = new double[inputs + 1];
    }
    
    public void train() {
    	helper.generate(dataHolder, weights, inputs);
    }
	    
    public double run(double[] data_in) {
    	lastIn = new double[inputs + 1];
    	for (int y = 0; y < data_in.length; y++) {
    		lastIn[y] = data_in[y];
    	}
    	
    	return helper.determine(data_in, weights, inputs);
    	
    }
    
    public void addLast(int p) {
    	lastIn[lastIn.length - 1] = p;
    	dataHolder.add(lastIn);
    	lastIn = null;
    }


}
