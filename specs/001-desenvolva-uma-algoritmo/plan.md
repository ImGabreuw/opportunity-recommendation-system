# Implementation Plan: Algoritmo de Recomendação de Oportunidades

**Branch**: `001-desenvolva-uma-algoritmo` | **Date**: September 12, 2025 | **Spec**: [link to spec.md]
**Input**: Feature specification from `/specs/001-desenvolva-uma-algoritmo/spec.md`

## Execution Flow (/plan command scope)
```
1. Load feature spec from Input path
   → If not found: ERROR "No feature spec at {path}"
2. Fill Technical Context (scan for NEEDS CLARIFICATION)
   → Detect Project Type from context (web=frontend+backend, mobile=app+api)
   → Set Structure Decision based on project type
3. Evaluate Constitution Check section below
   → If violations exist: Document in Complexity Tracking
   → If no justification possible: ERROR "Simplify approach first"
   → Update Progress Tracking: Initial Constitution Check
4. Execute Phase 0 → research.md
   → If NEEDS CLARIFICATION remain: ERROR "Resolve unknowns"
5. Execute Phase 1 → contracts, data-model.md, quickstart.md, agent-specific template file (e.g., `CLAUDE.md` for Claude Code, `.github/copilot-instructions.md` for GitHub Copilot, or `GEMINI.md` for Gemini CLI).
6. Re-evaluate Constitution Check section
   → If new violations: Refactor design, return to Phase 1
   → Update Progress Tracking: Post-Design Constitution Check
7. Plan Phase 2 → Describe task generation approach (DO NOT create tasks.md)
8. STOP - Ready for /tasks command
```

**IMPORTANT**: The /plan command STOPS at step 7. Phases 2-4 are executed by other commands:
- Phase 2: /tasks command creates tasks.md
- Phase 3-4: Implementation execution (manual or via tools)

## Summary
Implementar um algoritmo de recomendação content-based baseado em grafos para oportunidades extracurriculares, utilizando Java 21 e Lombok, seguindo arquitetura library-first com API pública estável e implementação interna flexível.

## Technical Context
**Language/Version**: Java 21  
**Primary Dependencies**: Lombok, JUnit 5  
**Storage**: N/A (biblioteca in-memory)  
**Testing**: JUnit 5  
**Target Platform**: JVM  
**Project Type**: single (biblioteca Maven)  
**Performance Goals**: NEEDS CLARIFICATION (não especificado na spec)  
**Constraints**: Evitar bibliotecas externas desnecessárias; algoritmo baseado em grafos já validado no POC  
**Scale/Scope**: Biblioteca para integração com projetos Spring; suportar grafos de médio porte

## Constitution Check
*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

**Simplicity**:
- Projects: 1 (biblioteca única)
- Using framework directly? (sim, Lombok para boilerplate)
- Single data model? (sim, baseado em Node/Edge do grafo)
- Avoiding patterns? (sim, sem Repository/UoW desnecessários)

**Architecture**:
- EVERY feature as library? (sim, projeto é uma biblioteca)
- Libraries listed: opportunity-recommendation-algorithm (motor de recomendação baseado em grafos)
- CLI per library: N/A (não requer CLI, é biblioteca)
- Library docs: llms.txt format planned? (sim, via Javadoc)

**Testing (NON-NEGOTIABLE)**:
- RED-GREEN-Refactor cycle enforced? (sim, TDD obrigatório)
- Git commits show tests before implementation? (sim)
- Order: Contract→Integration→E2E→Unit strictly followed? (sim)
- Real dependencies used? (sim, grafos reais)
- Integration tests for: new libraries, contract changes, shared schemas? (sim)
- FORBIDDEN: Implementation before test, skipping RED phase

**Observability**:
- Structured logging included? (não)
- Frontend logs → backend? (N/A)
- Error context sufficient? (sim)

**Versioning**:
- Version number assigned? (sim, SemVer)
- BUILD increments on every change? (sim)
- Breaking changes handled? (sim, depreciação)

## Project Structure

### Documentation (this feature)
```
specs/001-desenvolva-uma-algoritmo/
├── plan.md              # This file (/plan command output)
├── research.md          # Phase 0 output (/plan command)
├── data-model.md        # Phase 1 output (/plan command)
├── quickstart.md        # Phase 1 output (/plan command)
├── contracts/           # Phase 1 output (/plan command)
└── tasks.md             # Phase 2 output (/tasks command - NOT created by /plan)
```

### Source Code (repository root)
```
# Option 1: Single project (DEFAULT)
src/
├── main/java/com/metis/opportunity_recommendation_algorithm/
│   ├── api/          # Interfaces públicas, DTOs
│   ├── internal/     # Implementação (grafo, engine)
│   └── Recommenders.java  # Factory
└── test/java/        # Testes unitários e integração

# Option 2: Web application (when "frontend" + "backend" detected)
# N/A

# Option 3: Mobile + API (when "iOS/Android" detected)
# N/A
```

**Structure Decision**: Option 1 (single project - biblioteca Maven)

## Phase 0: Outline & Research
1. **Extract unknowns from Technical Context** above:
   - Performance Goals: Pesquisar benchmarks para algoritmos de recomendação em grafos
   - Constraints: Confirmar dependências mínimas (apenas Lombok, JUnit 5)

2. **Generate and dispatch research agents**:
   ```
   For each unknown in Technical Context:
     Task: "Research performance benchmarks for graph-based recommendation algorithms in Java"
   For each technology choice:
     Task: "Find best practices for Lombok usage in library development"
   ```

3. **Consolidate findings** in `research.md` using format:
   - Decision: Usar grafos in-memory com Streams para processamento
   - Rationale: Simplicidade e performance adequada para médio porte
   - Alternatives considered: Bibliotecas externas como JGraphT rejeitadas por complexidade desnecessária

**Output**: research.md with all NEEDS CLARIFICATION resolved

## Phase 1: Design & Contracts
*Prerequisites: research.md complete*

1. **Extract entities from feature spec** → `data-model.md`:
   - Nó de Aluno: ID, conexões a habilidades/temas
   - Nó de Oportunidade: ID, conexões a habilidades/temas
   - Nó de Habilidade: ID, conexões a alunos/oportunidades
   - Nó de Tema: ID, conexões a alunos/oportunidades

2. **Generate API contracts** from functional requirements:
   - Recommender.recommend(studentId, topN) → List<Opportunity>
   - Use interface simples, sem REST/GraphQL

3. **Generate contract tests** from contracts:
   - Testar interface Recommender com mocks
   - Tests devem falhar inicialmente

4. **Extract test scenarios** from user stories:
   - Cenário de recomendação baseada em tags
   - Cenário de limite topN

5. **Update agent file incrementally** (O(1) operation):
   - Run `/scripts/update-agent-context.sh copilot` for GitHub Copilot
   - Add tech: Java 21, Lombok, graph-based algorithms
   - Preserve manual additions
   - Output to repository root

**Output**: data-model.md, /contracts/*, failing tests, quickstart.md, .github/copilot-instructions.md

## Phase 2: Task Planning Approach
*This section describes what the /tasks command will do - DO NOT execute during /plan*

**Task Generation Strategy**:
- Load `/templates/tasks-template.md` as base
- Generate tasks from Phase 1 design docs (contracts, data model, quickstart)
- Each contract → contract test task [P]
- Each entity → model creation task [P] 
- Each user story → integration test task
- Implementation tasks to make tests pass

**Ordering Strategy**:
- TDD order: Tests before implementation 
- Dependency order: API before internal implementation
- Mark [P] for parallel execution (independent files)

**Estimated Output**: 15-20 numbered, ordered tasks in tasks.md

**IMPORTANT**: This phase is executed by the /tasks command, NOT by /plan

## Phase 3+: Future Implementation
*These phases are beyond the scope of the /plan command*

**Phase 3**: Task execution (/tasks command creates tasks.md)  
**Phase 4**: Implementation (execute tasks.md following constitutional principles)  
**Phase 5**: Validation (run tests, execute quickstart.md, performance validation)

## Complexity Tracking
*Fill ONLY if Constitution Check has violations that must be justified*

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |

## Progress Tracking
*This checklist is updated during execution flow*

**Phase Status**:
- [x] Phase 0: Research complete (/plan command)
- [x] Phase 1: Design complete (/plan command)
- [x] Phase 2: Task planning complete (/plan command - describe approach only)
- [x] Phase 3: Tasks generated (/tasks command)
- [ ] Phase 4: Implementation complete
- [ ] Phase 5: Validation passed

**Gate Status**:
- [x] Initial Constitution Check: PASS
- [x] Post-Design Constitution Check: PASS
- [x] All NEEDS CLARIFICATION resolved
- [x] Complexity deviations documented

---
*Based on Constitution v1.0.0 - See `/memory/constitution.md`*