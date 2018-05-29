package main;

import network.NeuralNetwork;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        NeuralNetwork net = new NeuralNetwork();

        System.out.print( "Creating neuron layers ..." );
        net.addLayer( 4 );	// input layer 
        net.addLayer( 6 );	// hidden layer 
        net.addLayer( 6 );	// hidden layer 
        net.addLayer( 2 );	// output layer 
        System.out.println( "OK" );

        System.out.print( "Connecting neuron layers ..." );
        net.connectWeightsToLayers();
        System.out.println( "OK" );

        System.out.println( "\n\rNet structure:" );

        net.setLearningRate(0.05);
        net.setMinimumError(0.005);

        net.readFile("res/train.txt");

        int i =0;
        while ( !net.finishedLearning() ) {		// while the net learns
            net.train();						// perform one learning step

            StringBuffer sb = new StringBuffer();
            double neterror = net.getError();
            String acc = (neterror>1.0) ? "2 bad" : String.valueOf((1.0-neterror)*100)+"%";
            sb.append( "\n\rEpoch " + i + "  minerror: " + net.getMinimumError() + "\n\rneterror: " + neterror + "\n\raccuracy: " + acc + "\n\r");
            sb.append( "\n\r-----------------------------------------------------" );
            System.out.println( sb );
            i++;
        }

        net.readFile("res/test.txt");
        net.test();
    }
}
