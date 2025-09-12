# GitHub Copilot Instructions

## Project Context
- **Language**: Java 21
- **Framework**: Maven library
- **Purpose**: Algorithm for recommending extracurricular opportunities based on student profiles
- **Architecture**: Library-first with public API and internal implementation

## Key Technologies
- Lombok for boilerplate reduction
- JUnit 5 for testing
- Graph-based data model (Node, Edge, Relations)
- Content-based recommendation algorithm

## Coding Standards
- Use @Value for immutable DTOs
- Prefer Streams for functional operations
- Keep API package clean and stable
- Internal package for implementation details

## Recent Changes
- Added spec and plan for recommendation algorithm
- Defined graph-based entities (Student, Opportunity, Skill, Theme nodes)
- Established API contracts with Recommender interface

## Best Practices
- Write tests before implementation (TDD)
- Use meaningful variable names in English
- Document public APIs with Javadoc
- Avoid external dependencies unless necessary