package network;

import parse.ParseInputs;

/**
 * Created by User on 18.06.2017.
 */
public class NeuronLayer {

    Neuron[] neurons;

    public NeuronLayer(int neurons) {
        this.neurons = new Neuron[neurons];

        for(int i = 0; i < neurons; ++i) {
            this.neurons[i] = new Neuron();
        }

    }

    public void init(NeuronLayer neuronLayer, WeightsLayer weightsLayer) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].init(neuronLayer.getOutputs(), weightsLayer.getRowWeights(i));
            neurons[i].activateSigmoid();

        }

    }

    public void calculateLayerErrors(ParseInputs targets) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].calculateNeuronError(targets.getTargets().get(i));
        }
    }

    public void calculateLayerErrors(NeuronLayer neuronLayer, WeightsLayer weightsLayer) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].computeOutputError(neuronLayer.getLayerErrors(), weightsLayer.getColWeights(i));
        }
    }


    public double[] getOutputs() {
        double[] temp = new double[neurons.length];

        for (int i = 0; i < neurons.length; i++) {
            temp[i] = this.neurons[i].getOutput();
        }
        return temp;
    }

    public double[] getLayerErrors() {
        double[] temp = new double[neurons.length];

        for (int i = 0; i < neurons.length; i++) {
            temp[i] = neurons[i].getNeuronError();
        }
        return temp;
    }


    public void setInputs(ParseInputs parseInputs) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setInput(parseInputs.getValues().get(i));
        }
    }


    int size() {
        return this.neurons.length;
    }
}
