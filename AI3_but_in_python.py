#Jacob Diaz Iglesias
#May 1, 2020
#Section 3540

import math
import random 

class Helper:
    #Probability function: Sigmoid
    def sigmoid(x):
        return 1/(1+math.exp(-x))

    #Calculus derivatives function: Sigmoid Prime
    def sigmoid_p(x):
        return ((1/(1 + math.exp(-x))) * (1-(1/(1 + math.exp(-x)))))

    #Random int within upper bound
    def randint(r):
        return int(random.random() * 10**15) % r

    #Used for final output
    def determine(invars, wh, inputs):
        z = 0
        for i in range(inputs):
            z += invars[i] * wh[i]
        z += wh[inputs] #add bias

        return Helper.sigmoid(z)

    #Used for calculating cost and derivatives
    def determineNoSig(invars, wh, inputs):
        z = 0
        for i in range(inputs):
            z += invars[i] * wh[i]
        z += wh[inputs] #add bias

        return z

    #basically the entre barin part of the AI
    def generate(dat, weights, insize):
        #network start seed
        #using random numbers tends to make the AI unstable
        #you probably should leave these alone
        for i in range(insize+1): #creates one extra (b)
        	weights[i] = 0.5
        
        if not dat:
            return 0

        #Do NOT mess with these two unless you wanna screw something up
        learning_rate = 0.3
        loop_iterations = len(dat) * 5

        #Especially dont touch anything here

        z = 0.0
        ri = 0
        pred = 0.0
        target = 0.0
        dcost_pred = 0.0
        dpred_dz = 0.0
        dz_dw = [0] * insize
        dz_db = 0.0
        dcost_dz = 0.0
        dcost_dw = [0] * insize
        dcost_db = 0.0

        #training loop
        for g in range(loop_iterations+1):
            #use random data point to train
            ri = Helper.randint(len(dat))

            #main function
            z = Helper.determineNoSig(dat[ri], weights, insize)
            pred = Helper.sigmoid(z)
            target = dat[ri][insize]

            #how far off is the prediction
            dcost_pred = 2 * (pred - target)
            dpred_dz = Helper.sigmoid_p(z)

            #how far off are the weights and bias

            for r in range(insize):
                dz_dw[r] = dat[ri][r]

            dz_db = 1

            dcost_dz = dcost_pred * dpred_dz

            for r in range(insize):
            	dcost_dw[r] = dcost_dz * dz_dw[r]
            
            dcost_db = dcost_dz * dz_db
            
            for r in range(insize):
            	weights[r] = weights[r] - learning_rate * dcost_dw[r]

            weights[insize] = weights[insize] - learning_rate * dcost_db;

        return 0

class Core:
    dataHolder = [[]]
    lastIn = 0.0
    weights = 0.0
    inputs = 0

    #constructer to define how many data points in array
    def __init__(self, p):
        self.inputs = p
        self.weights = [0] * (self.inputs+1)
        self.dataHolder = [[0]*(p+1)]

    #train
    def train(self):
        Helper.generate(self.dataHolder, self.weights, self.inputs)

    #run
    def run(self, data_in):
        self.lastIn = [0] * (self.inputs+1)
        for y in range(len(data_in)):
            self.lastIn[y] = data_in[y]

        return Helper.determine(data_in, self.weights, self.inputs)

    def addLast(self, p):
        self.lastIn[len(self.lastIn) - 1] = p

        self.dataHolder.append(self.lastIn);
        self.lastIn = [];
        return

# The main function basically just showcases the
# classes above. In a real world scenario, this 
# would be attached to some inputs and outputs
# where it could learn over time what to do.
def main():
    n1 = Core(16)
    temp = []
    trainingLoops = 100 #play with these if you'd like
    runLoops = 5 #play with these if you'd like
    percent = [[0, 0] for w in range(runLoops)]

    for i in range(runLoops):
        n1.train()

        for a in range(trainingLoops):

            #example inputs the ai is given 
            #these could be distance values for a car in real life
            temp = []
            temp = [Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5,
                    Helper.randint(15)-5]

            out = 0.0
            out = n1.run(temp) #do the calculation

            print("")
            print("________________________________________")
            print("Scenarios in memeory:", len(n1.dataHolder))
            print("Legend: Input:[weight] value")
            print("higher weight means higher importance to result")
            for l in range(len(temp)):
                print((l), "[" + str(n1.weights[l]) + "]:", temp[l])
            print("")
            print("should be between 0 and 1")
            print("if input 1 and 2 are both above value 3")
            print("then output should be 1.0")   
            print("----------------------------------------")

            #this part automates user correction (right or wrong)
            if (temp[0] >= 0 and temp[1] < 5): 
                n1.addLast(1); #tells AI the last output should have been 1
                print("Prediction:", out)
                print("Should be: 1.0")
                if (out > 0.5):
                    print("Correct? Yes")
                    percent[i][1] += 1
                else:
                    print("Correct? No")
                    percent[i][0] += 1
            else:
                n1.addLast(0) #tells AI the last output should have been 0
                print("Prediction:", out)
                print("Should be: 0.0")
                if (out < 0.5):
                    print("Correct? Yes")
                    percent[i][1] += 1
                else:
                    print("Correct? No")
                    percent[i][0] += 1

    print("\n\n\n")
    print("Final Stats:")
    for k in range(runLoops):
        print("Times Trained " + str(k) + ": " + 
                    str(percent[k][1] / (percent[k][0] + percent[k][1]) * 100) + 
                    "% correct")
            
if __name__=="__main__":
    main()