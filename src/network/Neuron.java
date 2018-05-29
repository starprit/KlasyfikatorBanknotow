package network;

/**
 * Created by User on 18.06.2017.
 */
public class Neuron {

    double input;
    double output;
    double sigmoid;
    double neuronError;

    public Neuron() {
    }

    void setInput(double input) {
        this.input = input;
        this.output = input;
        this.sigmoid = input;
    }

    public void activateSigmoid() {
        output = 1.0 / (1.0 + Math.exp(-this.input));
    }


    public double getOutput() {
        return output;
    }

    public void init(double[] layerOutputs, double[] weightsValues) {
        this.input = 0.0;

        for (int i = 0; i < layerOutputs.length; i++) {
            if (layerOutputs[i] != 0.0F && weightsValues[i] != 0.0F) {
                this.input += layerOutputs[i] * weightsValues[i];
            }
        }
        this.input += weightsValues[weightsValues.length - 1];
    }


    public void calculateNeuronError(double target) {
        if (output != 0.0 && output != 1.0) {
            neuronError = (target - this.output) * this.output * (1.0F - this.output);
        }
    }

    public void computeOutputError(double[] layerErrors, double[] weights) {
        neuronError = 0.0;

        for(int i = 0; i < layerErrors.length; ++i) {
            if(layerErrors[i] != 0.0F && output != 0.0F && output != 1.0F) {
                neuronError += layerErrors[i] * weights[i] * output * (1.0F - output);
            }
        }

    }


    public double getNeuronError() {
        return neuronError;
    }

}
