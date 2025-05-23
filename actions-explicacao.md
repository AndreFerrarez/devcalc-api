# Diferença entre Workflows e Actions no GitHub Actions

## O que é um Workflow

Um **workflow** é um arquivo YAML que define **quando** e **como** executar uma série de jobs (tarefas) no GitHub Actions. No DevCalc usamos o `ci.yml` para:

* **Gatilhos** (`on:`): eventos que disparam o pipeline, como push em `main`, pull request em `src/**` e execução manual.
* **Jobs**: fases como `build`, `test`, `lint`, `package` e `deploy`.
* **Dependências** (`needs:`): garantem a ordem de execução entre os jobs.

Exemplo de gatilho e job no `ci.yml`:

```yaml
on:
  push:
    branches: [ main ]
    paths: ['src/**']
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Build with Maven
        run: mvn clean install
```

## O que é uma Action

Uma **action** é um componente reutilizável que realiza uma única tarefa dentro de um workflow. Pode ser:

* **Marketplace**: ações públicas usadas com `uses: owner/repo@tag`.
* **Local**: definidas no repositório em `.github/actions/<nome>/action.yml`.

A action é invocada em um `step`:

```yaml
- name: Run Checkstyle
  uses: dbelyaev/action-checkstyle@v1.19.1
  with:
    github_token: ${{ secrets.GITHUB_TOKEN }}
    checkstyle_config: 'google_checks.xml'
    reporter: 'github-pr-check'
```

Aqui:

* `uses:` especifica o repositório e a versão.
* `with:` passa parâmetros definidos pela action.

## Estrutura interna de uma Action

Dentro de uma action local ou remota existe o arquivo `action.yml`, que define:

```yaml
name: 'Minha Action'
description: 'Descrição do que a action faz'

inputs:
  example_input:
    description: 'Parâmetro de entrada'
    required: true
    default: 'valor'

outputs:
  example_output:
    description: 'Valor de saída'

runs:
  using: 'docker'
  image: 'Dockerfile'
```

* **`inputs`**: parâmetros que o usuário fornece.
* **`outputs`**: valores que a action retorna.
* **`runs`**: como a action executa (Docker, JavaScript, composite).

## Exemplo real no DevCalc

No pipeline adicionei o job **lint** que roda o Checkstyle para Java:

```yaml
lint:
  needs: test
  runs-on: ubuntu-latest
  steps:
    - uses: actions/checkout@v4
    - name: Run Checkstyle
      uses: dbelyaev/action-checkstyle@v1.19.1
      with:
        github_token: ${{ secrets.GITHUB_TOKEN }}
        checkstyle_config: 'google_checks.xml'
        reporter: 'github-pr-check'
        fail_level: 'warning'
```

Essa action inspeciona o código em `src/main/java`, aplica as regras do **Google Checks** e reporta violações direto no PR.

---

Com isso entende-se que:

* **Workflows** orquestram vários jobs e definem gatilhos e dependências.
* **Actions** executam tarefas específicas e podem ser públicas (Marketplace) ou locais.
* O `action.yml` é o coração de cada action, declarando suas entradas, saídas e modo de execução.

Essa separação torna os pipelines mais modulares e fáceis de manter.
