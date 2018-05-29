package network;

import parse.ParseInputs;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by User on 18.06.2017.
 */
public class NeuralNetwork {

    NeuronLayer[] neuronLayers;
    WeightsLayer[] weightsLayers;
    double[][] networkError;
    List<NeuronLayer> neuronLayersList = new ArrayList<>();
    List<ParseInputs> inputValues = new ArrayList<>();

    int layersSize;
    int weightsLayersSize;
    int learningValuesSize;


    double learningRate;
    double minimumError;
    double errorNET;
    boolean stopLearn;


    public NeuralNetwork() {
        stopLearn = false;
        errorNET = 1000.0;
    }


    public void addLayer(int neurons) {
        neuronLayersList.add(new NeuronLayer(neurons));
    }

    public void connectWeightsToLayers() {
        this.weightsLayers = new WeightsLayer[this.neuronLayersList.size() - 1];
        this.neuronLayers = new NeuronLayer[neuronLayersList.size()];

        for (int i = 0; i < neuronLayersList.size(); i++) {
            neuronLayers[i] = neuronLayersList.get(i);
        }

        for (int i = 0; i < weightsLayers.length; i++) {
            this.weightsLayers[i] = new WeightsLayer(neuronLayers[i].size(), neuronLayers[i + 1].size());
            this.weightsLayers[i].init();
        }

        this.layersSize = this.neuronLayers.length - 1;
        this.weightsLayersSize = this.weightsLayers.length - 1;

    }

    public void test() {
        double max;
        int licznik;
        int count=0;
        for (int i = 0; i < learningValuesSize; i++) {
            neuronLayers[0].setInputs(inputValues.get(i));
            max = -1;
            licznik = 0;

            perceptron();

            double[] outputs = this.neuronLayers[layersSize].getOutputs();
            System.out.println();
            System.out.println(inputValues.get(i).getInput());
            for (int j = 0; j < outputs.length; j++) {
                System.out.print(String.valueOf("accuracy: " + (outputs[j])*100)+"%" + "  " + outputs[j]  + "  expected output: " + inputValues.get(i).getTargets().get(j));
                if (max <= outputs[j]) {
                    max = outputs[j];
                    licznik = j;
                }
                System.out.println();
            }

            if (inputValues.get(i).getTargets().get(licznik) == 1.0) {
                count++;
            }
        }
        System.out.println("PODSUMOWANIE SKUTECZNOSCI SIECI");
        System.out.println("ilosc przykladow " + learningValuesSize + " ilosc poprwanych wytypowan sieci = " + count + " skutecznosc sieci = " + (count*100)/learningValuesSize + "%");

    }

    public void train() {

        if ( errorNET <= minimumError) {
            stopLearn = true;
        } else {
            for (int i = 0; i < learningValuesSize; i++) {
                neuronLayers[0].setInputs(inputValues.get(i));

                perceptron();

                neuronLayers[layersSize].calculateLayerErrors(inputValues.get(i));
                networkError[i] = neuronLayers[layersSize].getLayerErrors();

                for (int j = weightsLayersSize; j >= 0; j--) {
                    this.weightsLayers[j].changeWeights(neuronLayers[j].getOutputs(), this.neuronLayers[j + 1].getLayerErrors(), learningRate);
                    if (j > 0) {
                        neuronLayers[j].calculateLayerErrors(this.neuronLayers[j + 1], this.weightsLayers[j]);
                    }
                }
            }

            double sum = 0.0;

            for (int i = 0; i < networkError.length; i++) {
                for (int j = 0; j < networkError[0].length; j++) {
                    sum += Math.pow(networkError[i][j], 2);
                }
            }
            errorNET = Math.abs(sum * 0.5);
        }
    }

    private void perceptron() {
        for (int i = 1; i <= layersSize; i++) {
            neuronLayers[i].init(neuronLayers[i - 1], weightsLayers[i - 1]);
        }
    }

    public double getError() {
        return errorNET;
    }

    public boolean finishedLearning() {
        return stopLearn;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMinimumError(double minimumError) {
        this.minimumError = minimumError;
    }

    public double getMinimumError(){
        return minimumError;
    }

    public void readFile(String path) {
        inputValues = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(path);
            String sCurrentLine;
            br = new BufferedReader(fr);
            while ((sCurrentLine = br.readLine()) != null) {
                ParseInputs p = new ParseInputs(sCurrentLine);
                inputValues.add(p);
            }
            learningValuesSize = inputValues.size();
            networkError = new double[learningValuesSize][neuronLayers[layersSize].size()];
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
