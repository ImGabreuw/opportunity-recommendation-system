import internal.*;

public class Main {

    public static Graph graph() {
        Graph graph = new Graph();

        // Adicionar Nós
        graph.addNode(new Node("aluno1", NodeType.STUDENT)); // Maria
        graph.addNode(new Node("aluno2", NodeType.STUDENT)); // João

        graph.addNode(new Node("hab_java", NodeType.SKILL));
        graph.addNode(new Node("hab_python", NodeType.SKILL));
        graph.addNode(new Node("hab_sql", NodeType.SKILL));

        graph.addNode(new Node("tema_ia", NodeType.THEME));
        graph.addNode(new Node("tema_dados", NodeType.THEME));

        graph.addNode(new Node("op1", NodeType.OPPORTUNITY)); // Vaga de IA
        graph.addNode(new Node("op2", NodeType.OPPORTUNITY)); // Vaga de BD

        // Adicionar Arestas (construir o perfil e descrever oportunidades)
        // Perfil da Maria: Java, IA
        graph.addEdge("aluno1", RelationType.POSSUI_HABILIDADE, "hab_java");
        graph.addEdge("aluno1", RelationType.TEM_INTERESSE_EM, "tema_ia");

        // Perfil do João: Python, SQL, Dados
        graph.addEdge("aluno2", RelationType.POSSUI_HABILIDADE, "hab_python");
        graph.addEdge("aluno2", RelationType.POSSUI_HABILIDADE, "hab_sql");
        graph.addEdge("aluno2", RelationType.TEM_INTERESSE_EM, "tema_dados");

        // Oportunidade 1: IA com Java e Python
        graph.addEdge("op1", RelationType.REQUER_HABILIDADE, "hab_java");
        graph.addEdge("op1", RelationType.REQUER_HABILIDADE, "hab_python");
        graph.addEdge("op1", RelationType.RELACIONADA_A_TEMA, "tema_ia");

        // Oportunidade 2: Banco de Dados com SQL
        graph.addEdge("op2", RelationType.REQUER_HABILIDADE, "hab_sql");
        graph.addEdge("op2", RelationType.RELACIONADA_A_TEMA, "tema_dados");

        return graph;
    }

    public static void main(String[] args) {
        Graph graph = graph();
        RecommendationService service = new RecommendationService(graph);
        System.out.println("Recomendações para Maria:");
        for (Node opportunity : service.recommend("aluno1", 1)) {
            System.out.println("- " + opportunity.getId());
        }
    }

}
