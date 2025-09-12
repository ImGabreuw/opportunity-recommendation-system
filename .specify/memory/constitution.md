# Opportunity Recommendation System Constitution

## Core Principles

### I. Library-First & Modularidade
Cada componente da solução (ingestor, pré-processador, engenheiro de features, ranker, avaliador) deve ser projetado como um módulo/coletor de responsabilidades bem definido, com interface clara e testes unitários isolados.

### II. Test-First (NON-NEGOTIABLE)
Desenvolvimento orientado a testes. Para mudanças significativas no algoritmo, comece com testes que descrevam o comportamento esperado (unit + integração). PRs sem testes cobrindo a nova lógica não serão aceitos.

#### III. Simplicidade e Observabilidade
riorize implementação simples de logging em etapas críticas (ingestão, transformação, ranking). Evite complexidade desnecessária; siga YAGNI.

## Project Constraints

- **Linguagem**: Java 21 somente.
- **Bibliotecas permitidas**: Lombok (redução de boilerplate), JUnit 5, Mockito, SLF4J (Logback runtime). Evitar dependências pesadas sem justificativa.

## Development Workflow

- **Branching**: `master` protegido; use `feature/*`, `fix/*`, `chore/*`.
- **Commits**: mensagens curtas e semantic prefixes (`feat:`, `fix:`, `docs:`, `test:`, `refactor:`).
- **PR Requirements**: build green, tests passing, format check, descrição explicando a motivação e impacto, pelo menos 1 aprovador.

## Package Layout (convenção)

Base package: `com.metis.opportunity_recommendation_algorithm`

Uma biblioteca bem projetada separa claramente a API pública da implementação interna. O objetivo é oferecer um contrato estável (o que o usuário usa) e permitir mudanças livres na implementação (onde a complexidade vive).

1) Pacotes `api` vs `internal`/`impl`

- `com.metis.opportunity_recommendation_algorithm.api` — TODO O PÚBLICO: interfaces, DTOs e exceções que consumidores podem importar. Este pacote representa o contrato da biblioteca e deve ser documentado com Javadoc e mantido estável.
- `com.metis.opportunity_recommendation_algorithm.internal` (ou `impl`) — IMPLEMENTAÇÃO: grafo, motores, utilitários internos, otimizações. Código aqui pode mudar sem aviso desde que a `api` permaneça compatível.

Exemplo de árvore de pacotes recomendada:

```
com.metis.opportunity_recommendation_algorithm
├── api/
│   ├── Recommender.java           // interface pública (Facade)
│   ├── Opportunity.java           // DTO estável
│   ├── RecommenderBuilder.java    // factory/builder pública
│   └── exception/
│       └── RecommendationException.java
│
├── internal/
│   ├── engine/
│   │   ├── GraphEngine.java
│   │   └── SimilarityEngine.java
│   ├── model/
│   │   ├── Node.java
│   │   └── Edge.java
│   └── infra/
│       └── CsvLoader.java
│
└── Recommenders.java // Ponto de entrada público (factory convenience)
```

2) Facade e Programação por Interfaces

- Exponha uma única *Facade* pública (por exemplo, `Recommender`) que contenha os métodos que consumidores usarão. A implementação concreta (`RecommenderImpl`) fica em `internal`.
- Tipos de dados retornados/aceitos pela API devem ser DTOs simples e preferencialmente imutáveis (ex.: `Opportunity` com `@Value`).

3) Factory/Builder como Ponto de Entrada

- Forneça uma fábrica pública (`RecommenderBuilder` ou `Recommenders`) que crie e inicialize as implementações internas. Assim, o usuário nunca precisa instanciar classes de `internal`.

Exemplo de uso público:

```java
Recommender rec = Recommenders.defaultRecommender();
List<Opportunity> list = rec.recommend("studentId", 10);
```

4) Camadas dentro de `internal`

- **core/domain**: estruturas centrais (grafo, regras do algoritmo).
- **engine/service**: orquestração e execução do pipeline (ingestão → features → rank).
- **infra**: I/O, loaders, persistência, adaptadores externos.

5) Contrato da API e Versionamento

- Tudo em `api` é contrato público. Mudanças aqui exigem cuidado e versão apropriada.
- Use SemVer: `MAJOR.MINOR.PATCH`.
	- PATCH: correções internas sem mudança na API.
	- MINOR: adição retrocompatível (novos métodos padrão, overloads default).
	- MAJOR: breaking changes (remover/alterar assinaturas públicas).

6) Depreciação

- Quando precisar substituir um método público, adicione o novo método e marque o antigo com `@Deprecated` no Javadoc explicando a migração. Remoção só em versão MAJOR.

7) Regras Práticas

- Código fora do repositório NÃO deve importar `com.metis...internal`.
- Javadoc, exemplos e testes de contrato para todas as classes em `api`.
- Mantenha `api` pequeno e estável; mova complexidade para `internal`.

### Resumo prático para este projeto

1. Criar `com.metis.opportunity_recommendation_algorithm.api` com: `Recommender` (interface), DTOs (ex.: `Opportunity`), `Recommenders` (factory).
2. Mover/organizar implementações complexas para `com.metis.opportunity_recommendation_algorithm.internal` (engine, model, infra).
3. Garantir que `src/main/java` exporte apenas o pacote `api` como o contrato público.
4. Documentar mudança de API seguindo SemVer e política de depreciação.

Seguindo isso, o projeto evolui como uma biblioteca robusta: usuários programam contra a API estável, enquanto você pode refatorar e otimizar livremente dentro de `internal`.

## Coding Conventions

- **Nomenclatura**: PascalCase para classes, camelCase para métodos/variáveis, constantes em UPPER_SNAKE.
- **Imutabilidade**: preferir objetos imutáveis (`@Value`) para dados de entrada e resultados intermediários; use builders (`@Builder`) para objetos complexos.
- **Lombok**:
	- `@Value` para value-objects imutáveis.
	- `@Builder` para criação legível de objetos quando necessário.
	- `@Getter`/`@Setter` com cautela; evitar `@Data` em entidades mutáveis ou persistentes.
	- Adicionar `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` quando equals/hashCode precisam de controle fino.
- **Null-safety**: evitar `null` quando possível; prefira `Optional<T>` para retornos que podem estar ausentes (não para campos de entidades). Documentar escolhas.
- **Exceptions**: checked para erros de IO/infra; runtime para erros de lógica. Defina `RecommendationException` como base para runtime errors do domínio.
- **Streams & Collections**: preferir operações funcionais (streams) para transformações; não abuse a ponto de reduzir legibilidade.

## Testing

- **Unit tests**: JUnit 5, focar em regras do algoritmo e transformações.
- **Integration tests**: cenários do pipeline completo com dados de exemplo (fixtures), cobertura para casos de borda.
- **Performance tests**: benchs simples para avaliar comportamento em grafos grandes (medir tempo e consumo de memória).
- **Coverage**: meta >= 80% para o pacote `algo`.

**Version**: 1.0.0 | **Ratified**: 2025-09-12 | **Last Amended**: 2025-09-12