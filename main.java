package main;

public class main {
	
	public static int randint(int r) {
		return (int)(Math.random() * 50000 + 1) % r;
	}

	public static void main(String[] args) {
		
		neuron.Core n1 = new neuron.Core(16);
		double[] temp;
		final int trLoops = 10;
		final int rnLoops = 5000;
		double[][] percent = new double[trLoops][rnLoops]; // TODO
	
		for (int i = 0; i < trLoops; i++) {
			n1.train();
			
			for (int a = 0; a < rnLoops; a++) {
				
				temp = null;
				temp = new double[]{(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5,
						  			(double)randint(15)-5
						  			};
				
				double out;
				out = n1.run(temp);
				
				System.out.println();
				for (int l = 0; l < temp.length; l++) {
					System.out.println((l) + " [" + n1.weights[l] + "]: " + temp[l]);
				}
				System.out.println(n1.dataHolder.size());
				System.out.println(out);
				
				if (Math.pow(temp[0], 2) + temp[0] - 5 <= 0) {
					n1.addLast(1);
					System.out.println(1.0);
					if (out > 0.5) {
						percent[i][1] += 1; // TODO
					} else {
						percent[i][0] += 1; // TODO
					}
				}
				else {
					n1.addLast(0);
					System.out.println(0.0);
					if (out < 0.5) {
						percent[i][1] += 1; // TODO
					} else {
						percent[i][0] += 1; // TODO
					}
				}

			}

		}
		
		for (int k = 0; k < trLoops; k++) {
			System.out.println(Integer.toString(k) + ": " + (percent[k][1] / (percent[k][0] + percent[k][1]) * 100) + "% correct"); // TODO
		}
		
	}

}
