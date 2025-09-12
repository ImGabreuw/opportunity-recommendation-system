# Research Findings

## Performance Goals
**Decision**: Usar processamento in-memory com Java Streams para grafos de médio porte (até 10k nós).  
**Rationale**: Simplicidade e performance adequada para casos de uso típicos de recomendação; evita overhead de bibliotecas externas.  
**Alternatives considered**: JGraphT rejeitado por adicionar dependência desnecessária; Neo4j rejeitado por ser overkill para in-memory.

## Constraints (Dependências Mínimas)
**Decision**: Limitar a Lombok e JUnit 5 apenas.  
**Rationale**: Lombok reduz boilerplate sem complexidade; JUnit 5 suficiente para testes.  
**Alternatives considered**: Mockito considerado mas rejeitado inicialmente; pode ser adicionado se testes de mocking forem necessários.

## Graph-Based Algorithm Best Practices
**Decision**: Usar representação de grafo simples com Map<Node, List<Edge>>.  
**Rationale**: Adequado para algoritmos content-based; fácil de implementar e testar.  
**Alternatives considered**: Estruturas mais complexas rejeitadas por YAGNI.

## Lombok Usage in Libraries
**Decision**: Usar @Value para DTOs, @Builder para fábricas, evitar @Data.  
**Rationale**: Mantém API limpa e imutável; evita problemas de equals/hashCode.  
**Alternatives considered**: Manual getters/setters rejeitados por verbosidade.