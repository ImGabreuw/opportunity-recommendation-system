# Quickstart Guide

## Instalação
Adicione ao seu `pom.xml`:
```xml
<dependency>
    <groupId>com.metis</groupId>
    <artifactId>opportunity-recommendation-algorithm</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Uso Básico
```java
import com.metis.opportunity_recommendation_algorithm.api.Recommender;
import com.metis.opportunity_recommendation_algorithm.Recommenders;

// Criar instância
Recommender recommender = Recommenders.defaultRecommender();

// Recomendar oportunidades para um estudante
List<Opportunity> recommendations = recommender.recommend("student123", 5);

// Processar resultados
for (Opportunity opp : recommendations) {
    System.out.println(opp.getId() + ": " + opp.getRelevanceScore());
}
```

## Configuração
- O recomendador padrão usa dados de exemplo.
- Para dados customizados, implementar `DataLoader` e passar via builder.

## Validação
Execute os testes de exemplo para verificar funcionamento:
```bash
mvn test -Dtest=RecommenderContractTest
```