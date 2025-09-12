# Tasks: Algoritmo de Recomendação de Oportunidades

**Input**: Design documents from `/specs/001-desenvolva-uma-algoritmo/`
**Prerequisites**: plan.md (required), research.md, data-model.md, contracts/

## Execution Flow (main)
```
1. Load plan.md from feature directory
   → If not found: ERROR "No implementation plan found"
   → Extract: tech stack (Java 21, Lombok, JUnit 5), libraries, structure (single project)
2. Load optional design documents:
   → data-model.md: Extract entities (4 nodes) → model tasks
   → contracts/: Recommender.java, Opportunity.java → contract test tasks
   → research.md: Extract decisions → setup tasks
3. Generate tasks by category:
   → Setup: project structure, Maven config, linting
   → Tests: contract tests, integration tests
   → Core: models, services, API implementation
   → Integration: logging, error handling
   → Polish: unit tests, docs
4. Apply task rules:
   → Different files = mark [P] for parallel
   → Same file = sequential (no [P])
   → Tests before implementation (TDD)
5. Number tasks sequentially (T001, T002...)
6. Generate dependency graph
7. Create parallel execution examples
8. Validate task completeness:
   → All contracts have tests? Yes
   → All entities have models? Yes
   → All endpoints implemented? Yes
   → All tests before implementation? Yes
9. Return: SUCCESS (tasks ready for execution)
```

## Format: `[ID] [P?] Description`
- **[P]**: Can run in parallel (different files, no dependencies)
- Include exact file paths in descriptions

## Path Conventions
- **Single project**: `src/main/java/com/metis/opportunity_recommendation_algorithm/`, `src/test/java/`
- Paths based on plan.md structure (Option 1)

## Phase 3.1: Setup
- [ ] T001 Create Maven project structure per implementation plan
- [ ] T002 Configure pom.xml with Java 21, Lombok, JUnit 5 dependencies
- [ ] T003 [P] Configure Maven compiler plugin for Java 21
- [ ] T004 [P] Configure Spotless for code formatting

## Phase 3.2: Tests First (TDD) ⚠️ MUST COMPLETE BEFORE 3.3
**CRITICAL: These tests MUST be written and MUST FAIL before ANY implementation**
- [ ] T005 [P] Contract test for Recommender interface in src/test/java/com/metis/opportunity_recommendation_algorithm/api/RecommenderContractTest.java
- [ ] T006 [P] Contract test for Opportunity DTO in src/test/java/com/metis/opportunity_recommendation_algorithm/api/OpportunityContractTest.java
- [ ] T007 [P] Integration test for recommendation with tags in src/test/java/com/metis/opportunity_recommendation_algorithm/integration/RecommendationIntegrationTest.java
- [ ] T008 [P] Integration test for topN limit in src/test/java/com/metis/opportunity_recommendation_algorithm/integration/TopNLimitIntegrationTest.java
- [ ] T009 [P] Integration test for empty tags scenario in src/test/java/com/metis/opportunity_recommendation_algorithm/integration/EmptyTagsIntegrationTest.java

## Phase 3.3: Core Implementation (ONLY after tests are failing)
- [ ] T010 [P] Create Node model in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/Node.java
- [ ] T011 [P] Create Edge model in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/Edge.java
- [ ] T012 [P] Create NodeType enum in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/NodeType.java
- [ ] T013 [P] Create RelationType enum in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/RelationType.java
- [ ] T014 Create Graph class in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/Graph.java
- [ ] T015 Create RecommendationService in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/RecommendationService.java
- [ ] T016 Create Opportunity DTO in src/main/java/com/metis/opportunity_recommendation_algorithm/api/Opportunity.java
- [ ] T017 Create Recommender interface in src/main/java/com/metis/opportunity_recommendation_algorithm/api/Recommender.java
- [ ] T018 Create RecommenderImpl in src/main/java/com/metis/opportunity_recommendation_algorithm/internal/RecommenderImpl.java
- [ ] T019 Create Recommenders factory in src/main/java/com/metis/opportunity_recommendation_algorithm/Recommenders.java

## Phase 3.4: Integration
- [ ] T020 Add SLF4J logging to RecommendationService
- [ ] T021 Add error handling with RecommendationException
- [ ] T022 Implement input validation for studentId and topN

## Phase 3.5: Polish
- [ ] T023 [P] Unit tests for Graph operations in src/test/java/com/metis/opportunity_recommendation_algorithm/internal/GraphTest.java
- [ ] T024 [P] Unit tests for RecommendationService in src/test/java/com/metis/opportunity_recommendation_algorithm/internal/RecommendationServiceTest.java
- [ ] T025 Performance tests for graph with 1000 nodes
- [ ] T026 [P] Update Javadoc for public APIs
- [ ] T027 [P] Create README.md with usage examples
- [ ] T028 Run all tests and verify coverage >80%

## Dependencies
- Setup (T001-T004) before everything
- Tests (T005-T009) before implementation (T010-T022)
- Models (T010-T013) before Graph (T014)
- Graph (T014) before RecommendationService (T015)
- DTOs/APIs (T016-T017) before implementation (T018-T019)
- Implementation (T010-T022) before polish (T023-T028)

## Parallel Example
```
# Launch T005-T009 together (all test files are different):
Task: "Contract test for Recommender interface in src/test/java/com/metis/opportunity_recommendation_algorithm/api/RecommenderContractTest.java"
Task: "Contract test for Opportunity DTO in src/test/java/com/metis/opportunity_recommendation_algorithm/api/OpportunityContractTest.java"
Task: "Integration test for recommendation with tags in src/test/java/com/metis/opportunity_recommendation_algorithm/integration/RecommendationIntegrationTest.java"
Task: "Integration test for topN limit in src/test/java/com/metis/opportunity_recommendation_algorithm/integration/TopNLimitIntegrationTest.java"
Task: "Integration test for empty tags scenario in src/test/java/com/metis/opportunity_recommendation_algorithm/integration/EmptyTagsIntegrationTest.java"
```

## Notes
- [P] tasks = different files, no dependencies
- Verify tests fail before implementing (RED phase of TDD)
- Commit after each task with descriptive message
- Avoid: vague tasks, same file conflicts
- All paths are absolute from repository root

## Task Generation Rules
*Applied during main() execution*

1. **From Contracts**:
   - Recommender.java → T005 contract test [P]
   - Opportunity.java → T006 contract test [P]
   
2. **From Data Model**:
   - Nó de Aluno → T010 Node model [P]
   - Nó de Oportunidade → T011 Edge model [P] (wait, adjust: Edge is separate)
   - Nó de Habilidade → T012 NodeType enum [P]
   - Nó de Tema → T013 RelationType enum [P]
   
3. **From User Stories**:
   - Acceptance scenario 1 → T007 integration test [P]
   - Acceptance scenario 2 → T008 integration test [P]
   - Edge case 1 → T009 integration test [P]

4. **Ordering**:
   - Setup → Tests → Models → Services → APIs → Implementation → Polish
   - Dependencies block parallel execution

## Validation Checklist
*GATE: Checked by main() before returning*

- [x] All contracts have corresponding tests (2 contracts → 2 tests)
- [x] All entities have model tasks (4 entities → 4 model tasks)
- [x] All tests come before implementation (T005-T009 before T010-T022)
- [x] Parallel tasks truly independent (different files)
- [x] Each task specifies exact file path
- [x] No task modifies same file as another [P] task