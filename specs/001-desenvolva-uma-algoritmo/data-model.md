# Data Model

## Entities

### Nó de Aluno (Student Node)
- **ID**: String (único)
- **Type**: STUDENT
- **Relationships**:
  - POSSUI_HABILIDADE → Nó de Habilidade
  - TEM_INTERESSE_EM → Nó de Tema
- **Validation**: ID não nulo, type STUDENT

### Nó de Oportunidade (Opportunity Node)
- **ID**: String (único)
- **Type**: OPPORTUNITY
- **Relationships**:
  - REQUER_HABILIDADE → Nó de Habilidade
  - RELACIONADA_A_TEMA → Nó de Tema
- **Validation**: ID não nulo, type OPPORTUNITY

### Nó de Habilidade (Skill Node)
- **ID**: String (único)
- **Type**: SKILL
- **Relationships**:
  - (inverso) POSSUI_HABILIDADE ← Nó de Aluno
  - (inverso) REQUER_HABILIDADE ← Nó de Oportunidade
- **Validation**: ID não nulo, type SKILL

### Nó de Tema (Theme Node)
- **ID**: String (único)
- **Type**: THEME
- **Relationships**:
  - (inverso) TEM_INTERESSE_EM ← Nó de Aluno
  - (inverso) RELACIONADA_A_TEMA ← Nó de Oportunidade
- **Validation**: ID não nulo, type THEME

## Relationships
- **POSSUI_HABILIDADE**: Aluno → Habilidade (muitos-para-muitos)
- **TEM_INTERESSE_EM**: Aluno → Tema (muitos-para-muitos)
- **REQUER_HABILIDADE**: Oportunidade → Habilidade (muitos-para-muitos)
- **RELACIONADA_A_TEMA**: Oportunidade → Tema (muitos-para-muitos)

## State Transitions
- N/A (modelo estático baseado em conexões de grafo)