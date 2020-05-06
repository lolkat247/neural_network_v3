package neuron;

import java.util.Vector;

public class Helper {
	
	private static double sigmoid(double x) {
		return 1/(1 + Math.exp(-x));
	}
	
	private static double sigmoid_p(double x) {
		return ((1/(1 + Math.exp(-x))) * (1 - (1/(1 + Math.exp(-x)))));
	}
	
	private static int randint(int r) {
		return (int)(Math.random() * 50000 + 1) % r;
	}
	
	public double determine(double[] invars, double[] wh, int inputs){

		double z = 0;
		for (int i=0; i<inputs; i++) {
			z += invars[i] * wh[i];
		}
		z += wh[inputs];
        
        return sigmoid(z);
    }
	
	private double determineNoSig(double[] invars, double[] wh, int inputs){

		double z = 0;
		for (int i=0; i<inputs; i++) {
			z += invars[i] * wh[i];
		}
		z += wh[inputs];
        
        return z;
    }

	public double generate(Vector<double[]> dat, double[] weights, int insize) {

        // network start seed
        // using random numbers tends to make the AI unstable
        // you probably should leave these alone
        
        for (int i = 0; i <= insize; i++) { //creates one extra (b)
        	weights[i] = 0.5;
        }

        if (dat.isEmpty()){
            return 0;
        }

        // WARNING: Do NOT mess with these two unless you wanna screw something up
        double learning_rate = 0.3;//0.0003;
        int loop_iterations = dat.size() * 5;//50000;

        // Don't touch anything past this point

        double z;
        int ri;
        double pred;
        double target;
        double dcost_pred;
        double dpred_dz;
        double dz_dw[] = new double[insize];
        double dz_db;
        double dcost_dz;
        double dcost_dw[] = new double[insize];
        double dcost_db;
        
        // training loop
        for (int g = 0; g <= loop_iterations; g++) {
            // use random point to train AI
            ri = randint(dat.size());

            //main function
            z = determineNoSig(dat.get(ri), weights, insize);
            pred = sigmoid(z);
            target = dat.get(ri)[insize];

            // WARNING: the following code is incredibly complex and should not be altered

            // how far off is the prediction
            dcost_pred = 2 * (pred - target);
            dpred_dz = sigmoid_p(z);

            // how far off are the weights and bias
            // 
            // WARNING: You're not that smart... DON'T TOUCH!!!
            //
            
            for (int r = 0; r < insize; r++) {
            	dz_dw[r] = dat.get(ri)[r];
            }
            dz_db = 1;

            dcost_dz = dcost_pred * dpred_dz;
            
            for (int r = 0; r < insize; r++) {
            	dcost_dw[r] = dcost_dz * dz_dw[r];
            }
            
            dcost_db = dcost_dz * dz_db;
            
            for (int r = 0; r < insize; r++) {
            	weights[r] = weights[r] - learning_rate * dcost_dw[r];
            }
            weights[insize] = weights[insize] - learning_rate * dcost_db;

        }
        
        return 0;
        
    }

}
