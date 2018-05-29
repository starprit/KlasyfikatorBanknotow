package network;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 18.06.2017.
 */
public class WeightsLayer {

    double[][] matrix;
    double[] biasMatrix;

    int size;
    double bias = 1.0;

    public WeightsLayer(int current, int next) {
        matrix = new double[current][next];
        this.size = current * next + 1;
        this.biasMatrix = new double[next];
    }

    public void init() {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = ThreadLocalRandom.current().nextDouble(-1, 1);
                //System.out.print(matrix[i][j] + "  ");
            }
            //System.out.println();
        }

        for (int i = 0; i < biasMatrix.length; i++) {
            biasMatrix[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
            //System.out.print(biasMatrix[i] + "  ");
        }
    }


    double[] getRowWeights(int targetNeuron) {
        double[] matrixRow = new double[this.matrix.length + 1];

        for (int i = 0; i < this.matrix.length; ++i) {
            matrixRow[i] = this.matrix[i][targetNeuron];
        }


        matrixRow[this.matrix.length] = this.biasMatrix[targetNeuron];
        return matrixRow;
    }

    double[] getColWeights(int sourceNeuron) {
        double[] matrixRow = new double[this.matrix[0].length];

        for (int i = 0; i < this.matrix[0].length; ++i) {
            matrixRow[i] = this.matrix[sourceNeuron][i];
        }

        return matrixRow;
    }

    public void changeWeights(double[] outputs, double[] layerErrors, double learningRate) {

        double previous;
        for (int i = 0; i < matrix.length; i++) {
            if (outputs[i] != 0.0) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (layerErrors[j] != 1.0 && layerErrors[j] != 0.0) {
                        previous = matrix[i][j];
                        matrix[i][j] = 0.0;
                        matrix[i][j] = previous + outputs[i] * layerErrors[j] * (1.0 - layerErrors[j]) * learningRate;
                    }
                }
            }
        }

        for (int i = 0; i < biasMatrix.length; i++) {
            if (layerErrors[i] != 1.0 && layerErrors[i] != 0.0) {
                previous = biasMatrix[i];
                biasMatrix[i] = 0.0F;
                biasMatrix[i] = previous + layerErrors[i] * (1.0 - layerErrors[i]) * learningRate;
            }
        }


    }
}
