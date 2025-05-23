[![CI](https://github.com/AndreFerrarez/devcalc-api/actions/workflows/ci.yml/badge.svg)](https://github.com/AndreFerrarez/devcalc-api/actions/workflows/ci.yml)



# DevCalc API

**Objetivo**  
API REST em Java (Javalin) para operações matemáticas simples, usada para validar pipelines de CI/CD.

**Build**  
Maven.

**Como executar localmente**
```bash
mvn clean package
java -jar target/devcalc-api-1.0-SNAPSHOT.jar


## Faznedo modificacao para testar as actions, porem com novos ajustes



## Debug de Pipeline

### Erro provocado
- **Pipeline** falhou no job `package` com a mensagem:  
  `The goal you specified requires a project…`  
- Descobrimos que havia um typo: `mvn packag` em vez de `mvn package`.

### Como identifiquei
- Usei a **aba Actions** do GitHub para abrir o log do job `package`.
- Expandi o passo “Package JAR” e li a mensagem de erro.

### Correção
- Reverti o typo no comando para `mvn package --batch-mode`.
- Pipeline voltou a rodar com sucesso: build → lint_and_test → package → deploy.


## Observações sobre Modo de Execução

- **Push automático**: sempre executa o pipeline completo (build, lint, test, package e deploy) ao detectar commits em `src/**`.  
- **Execução manual** (`workflow_dispatch`): só roda quando alguém inicia manualmente, e permite parametrizar (`run_lint` e `run_tests`).  
- Na aba **Actions**, os filtros de “Event” ajudam a separar esses dois tipos de run e monitorar facilmente quem e como acionou cada execução.