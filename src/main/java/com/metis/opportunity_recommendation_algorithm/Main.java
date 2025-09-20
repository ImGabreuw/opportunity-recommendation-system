package com.metis.opportunity_recommendation_algorithm;

import com.metis.opportunity_recommendation_algorithm.io.Console;
import com.metis.opportunity_recommendation_algorithm.io.GraphReader;
import com.metis.opportunity_recommendation_algorithm.io.GraphWriter;

public class Main {

    public static void main(String[] args) {
        GraphReader reader = new GraphReader("grafo.txt");
        GraphWriter writer = new GraphWriter("output.txt");
        Console console = new Console(reader, writer);

        int option;
        while ((option = console.readOption()) != Console.EXIT_OPTION) {
            switch (option) {
                case 1 -> console.readCommand();
                case 2 -> console.writeCommand();
                case 3 -> console.insertNodeCommand();
                case 4 -> console.insertEdgeCommand();
                case 5 -> console.removeNodeCommand();
                case 6 -> console.removeEdgeCommand();
                case 7 -> console.showFileContentCommand();
                case 8 -> console.showGraphCommand();
                case 9 -> console.showGraphConnectivityCommand();
                case 10 -> console.showRecommendationCommand();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        System.out.println("Encerrando a aplicação. Até logo!");
    }

}
