package com.metis.opportunity_recommendation_algorithm.io;

import com.metis.opportunity_recommendation_algorithm.api.response.OpportunityResponse;
import com.metis.opportunity_recommendation_algorithm.api.Recommender;
import com.metis.opportunity_recommendation_algorithm.api.RecommenderFactory;
import com.metis.opportunity_recommendation_algorithm.internal.engine.DirectedGraphCategorizationAlgorithm;
import com.metis.opportunity_recommendation_algorithm.internal.engine.KosarajuAlgorithm;
import com.metis.opportunity_recommendation_algorithm.internal.models.*;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.DirectedGraphCategory;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.RelationType;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class Console {

    public static final int EXIT_OPTION = 11;

    private final GraphReader reader;
    private final GraphWriter writer;

    private final Scanner scanner = new Scanner(System.in);

    private KnowledgeGraph graph;


    /*
    COMANDOS:

    1. Ler dados do arquivo grafo.txt;
    2. Gravar dados no arquivo grafo.txt;
    3. Inserir vértice;
    4. Inserir aresta;
    5. Remove vértice;
    6. Remove aresta;
    7. Mostrar conteúdo do arquivo;
    8. Mostrar grafo;
    9. Apresentar a conexidade do grafo e o reduzido;
    10. Apresentar recomendações para um aluno;
    11. Encerrar a aplicação.
     */

    public void printMenuCommands() {
        System.out.println();
        System.out.println("=== Menu de Comandos ===");
        System.out.println("1. Ler dados do arquivo grafo.txt");
        System.out.println("2. Gravar dados no arquivo grafo.txt");
        System.out.println("3. Inserir vértice");
        System.out.println("4. Inserir aresta");
        System.out.println("5. Remover vértice");
        System.out.println("6. Remover aresta");
        System.out.println("7. Mostrar conteúdo do arquivo");
        System.out.println("8. Mostrar grafo");
        System.out.println("9. Apresentar a conexidade do grafo e o reduzido");
        System.out.println("10. Apresentar recomendações para um aluno");
        System.out.println("11. Encerrar a aplicação");
        System.out.print("Escolha uma opção: ");
    }

    public int readOption() {
        printMenuCommands();

        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            option = -1; // Opção inválida
        }

        System.out.println(); // Linha em branco para melhor visualização

        return option;
    }

    public void readCommand() {
        System.out.printf(">>> Lendo dados do arquivo %s...\n", reader.getFilePath());
        graph = reader.read();
        System.out.println(">>> Dados lidos com sucesso!");
    }

    public void writeCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo.");
            return;
        }

        System.out.printf(">>> Gravando dados no arquivo %s...\n", writer.getFilePath());
        writer.write(graph);
        System.out.println(">>> Dados gravados com sucesso!");
    }

    public void insertNodeCommand() {
        if (graph == null) {
            graph = new KnowledgeGraph();
        }

        System.out.printf(">>> Informe o vértice no formato: <ID> <%s>\n", NodeType.listTypes());
        String line = scanner.nextLine();

        String[] parts = line.split(" ");

        if (parts.length != 2) {
            System.out.printf(">>> Formato inválido. Use: <ID> <%s>\n", NodeType.listTypes());
            return;
        }

        String nodeId = parts[0];
        NodeType nodeType = NodeType.fromName(parts[1]);

        if (nodeType == null) {
            System.out.printf(">>> Tipo de vértice inválido. Opções válidas: <%s>\n", NodeType.listTypes());
            return;
        }

        graph.addNode(new Node(nodeId, nodeType));
    }

    public void removeNodeCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo.");
            return;
        }

        System.out.print(">>> Informe o ID do vértice a ser removido: ");
        String nodeId = scanner.nextLine();

        graph.removeNode(nodeId);
    }

    public void insertEdgeCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo ou insira um vértice com a opção 3.");
            return;
        }

        System.out.printf(">>> Informe a aresta no formato: <ID_Vértice_Origem> <%s> <ID_Vértice_Destino>\n", RelationType.listRelationTypes());
        String line = scanner.nextLine();

        String[] parts = line.split(" ");

        if (parts.length != 3) {
            System.out.printf(">>> Formato inválido. Use: <ID_Vértice_Origem> <%s> <ID_Vértice_Destino>\n", RelationType.listRelationTypes());
            return;
        }

        String sourceId = parts[0];
        RelationType relationType = RelationType.fromName(parts[1]);
        String targetId = parts[2];

        if (relationType == null) {
            System.out.printf(">>> Tipo de relacionamento inválido. Opções válidas: <%s>\n", RelationType.listRelationTypes());
            return;
        }

        graph.addEdge(sourceId, relationType, targetId);
    }

    public void removeEdgeCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo.");
            return;
        }

        System.out.printf(">>> Informe a aresta a ser removida no formato: <ID_Vértice_Origem> <%s> <ID_Vértice_Destino>\n", RelationType.listRelationTypes());
        String line = scanner.nextLine();

        String[] parts = line.split(" ");

        if (parts.length != 3) {
            System.out.printf(">>> Formato inválido. Use: <ID_Vértice_Origem> <%s> <ID_Vértice_Destino>\n", RelationType.listRelationTypes());
            return;
        }

        String sourceId = parts[0];
        RelationType relationType = RelationType.fromName(parts[1]);
        String targetId = parts[2];

        if (relationType == null) {
            System.out.printf(">>> Tipo de relacionamento inválido. Opções válidas: <%s>\n", RelationType.listRelationTypes());
            return;
        }

        graph.removeEdge(sourceId, relationType, targetId);
    }

    public void showFileContentCommand() {
        System.out.printf(">>> Conteúdo do arquivo %s:\n", reader.getFilePath());

        Path path = Path.of(reader.getFilePath());
        try {
            for (String line : Files.readAllLines(path)) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(">>> Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void showGraphCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo.");
            return;
        }

        System.out.println(">>> Conteúdo do grafo:");
        System.out.println(graph);
    }

    public void showGraphConnectivityCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo.");
            return;
        }

        DirectedGraphCategorizationAlgorithm categorizationAlgorithm = DirectedGraphCategorizationAlgorithm.getInstance();
        DirectedGraphCategory category = categorizationAlgorithm.categorize(graph);

        System.out.println(">>> Categoria do grafo: " + category);

        KosarajuAlgorithm kosarajuAlgorithm = KosarajuAlgorithm.getInstance();
        KnowledgeGraph condensedGraph = kosarajuAlgorithm.getCondensedGraph(graph);

        System.out.println(">>> Grafo condensado (reduzido):");
        System.out.println(condensedGraph);
    }

    public void showRecommendationCommand() {
        if (graph == null) {
            System.out.println(">>> Nenhum grafo carregado. Use o comando 1 para ler os dados do arquivo.");
            return;
        }

        System.out.print(">>> Informe o ID do aluno para recomendações: ");
        String studentId = scanner.nextLine();

        Node studentNode = graph.getNode(studentId);

        if (studentNode == null || !studentNode.isStudentNode()) {
            System.out.println(">>> Vértice de aluno não encontrado.");
            return;
        }

        System.out.println(">>> Informe o número de recomendações desejadas: ");
        int topN;
        try {
            topN = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(">>> Número inválido. Usando 5 como padrão.");
            topN = 5;
        }

        Recommender recommender = RecommenderFactory.create(graph);
        List<OpportunityResponse> opportunities = recommender.recommend(studentId, topN);

        if (opportunities.isEmpty()) {
            System.out.println(">>> Nenhuma oportunidade recomendada encontrada.");
        } else {
            printOpportunitiesTable(studentId, opportunities);
        }
    }

    private static void printOpportunitiesTable(String studentId, List<OpportunityResponse> opportunities) {
        System.out.printf("=== Recomendações de Oportunidades para o Aluno '%s' ===\n", studentId);

        // Elementos alinhados a esquerda com largura fixa
        System.out.printf("%-5s %-15s %-50s %-10s\n", "No.", "ID", "Descrição", "Relevância");
        System.out.println("-------------------------------------------------------------------------------------");

        for (int i = 0; i < opportunities.size(); i++) {
            OpportunityResponse opp = opportunities.get(i);
            System.out.printf("%-5d %-15s %-50s %-10.2f\n", i + 1, opp.id(), opp.description(), opp.relevanceScore());
        }

        System.out.println("-------------------------------------------------------------------------------------");
    }

}