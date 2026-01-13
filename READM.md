# Simulador de Pronto-Socorro

## Descrição do Projeto

Este projeto implementa um simulador concorrente de um pronto-socorro, desenvolvido em java, para a disciplina de Paradigmas de Programação.

O sistema modela o fluxo de um atendimento hospitalar, no qual pacientes chegam simultaneamente, passam por triagem de risco realizada por enfermeiros e são atendidos por médicos de acordo com a prioridade clínica, respeitando regras de concorrência e evitando problemas como race conditions e starvation.

## Objetivos

- Simular um ambiente de pronto-socorro com múltiplos atendimentos simultâneos
- Aplicar conceitos de threads, exclusão mútua e sincronização
- Garantir que pacientes mais graves sejam atendidos primeiro
- Evitar starvation de pacientes de menor prioridade

## Funcionalidades e Regras de Negócio

### Funcionalidades

- Simulação da chegada de múltiplos pacientes de forma concorrente
- Criação de pacientes como threads independentes
- Triagem de risco realizada por enfermeiros (recursos limitados)
- Classificação automática dos pacientes por nível de gravidade
- Organização dos pacientes em filas de prioridade
- Atendimento médico respeitando a ordem de prioridade clínica

### Regras de Negócio

- Todo paciente obrigatoriamente deve passar pela triagem antes do atendimento
- Cada paciente recebe apenas uma classificação de risco
- A triagem só ocorre se houver enfermeiro disponível
- Médicos sempre priorizam pacientes da fila:

🔴 Vermelho

🟡 Amarelo

🟢 Verde

- Pacientes de menor prioridade não podem ficar bloqueados indefinidamente (anti-starvation)

## Descrição

### Pacientes
- Cada paciente é representado por uma thread
- Ao chegar no sistema, o paciente:

1. Entra na fila de triagem
2. Passa pela triagem com um enfermeiro
3. Recebe uma classificação de risco
4. É direcionado para a fila correspondente

### Classificação de Risco

Os pacientes são classificados em três níveis:

🔴 Vermelho – atendimento imediato

🟡 Amarelo – atendimento prioritário

🟢 Verde – atendimento não urgente

As filas seguem a ordem de prioridade Vermelho > Amarelo > Verde.

### Enfermeiros

- Responsáveis pela triagem dos pacientes
- São recursos limitados e compartilhados
- Utilizam mecanismos de sincronização para evitar conflitos

### Médicos

- Atendem os pacientes conforme a prioridade
- Sempre verificam primeiro a fila vermelha
- O sistema garante que filas de menor prioridade não fiquem bloqueadas indefinidamente (anti-starvation)

## Concorrência e Sincronização

O projeto utiliza mecanismos como:

- Thread
- synchronized / Lock
- Filas sincronizadas
- Controle de acesso a recursos compartilhados

Esses mecanismos garantem:
- Ordem correta de atendimento
- Consistência dos dados
- Execução concorrente segura

## Estrutura do Projeto

```text
├── src
│   └── main
│       └── java
│           └── com.mycompany.pronto_socorro
│               ├── pronto_socorro
│               │   └── pronto_socorro.java     # Classe principal (main)
│               │
│               ├── concurrency
│               │   ├── ControleAtendimento.java   # Controle da concorrência no atendimento
│               │   ├── FilaPrioridade.java        # Filas de pacientes por prioridade
│               │   ├── Logger.java                # Registro de logs do sistema
│               │   └── RecursosHospitalares.java  # Controle de médicos e enfermeiros
│               │
│               ├── model
│               │   ├── Paciente.java              # Entidade paciente (thread)
│               │   └── Prioridade.java            # Enum de prioridades (Vermelho, Amarelo, Verde)
│               │
│               └── service
│                   ├── AtendimentoService.java    # Lógica de atendimento médico
│                   ├── ProntoSocorroService.java  # Orquestração do fluxo do sistema
│                   └── TriagemService.java        # Lógica de triagem de pacientes
│
├── pom.xml        # Configuração do projeto Maven
└── README.md      # Documentação do projeto
```

## Como executar o projeto

### Pré-requisitos
- Java JDK 8 ou superior
- IDE (IntelliJ IDEA, Eclipse ou VS Code) ou terminal

### Passos

1. Clone o repositório:
```text
git clone <url-do-repositorio>
```
2. Abra o projeto na IDE
3. Execute a classe Main
4. Acompanhe os logs no console

## Saídas do Sistema

Durante a execução, o sistema exibe logs informando:

- Chegada de pacientes
- Início e fim da triagem
- Classificação de risco atribuída
- Início e fim dos atendimentos médicos
- Estado das filas

## Conteúdos Aplicados

- Programação Concorrente
- Threads em Java
- Exclusão mútua
- Sincronização de recursos
- Escalonamento por prioridade

## Documento de Testes
https://docs.google.com/document/d/1uu592N0ifB_fMBM66F5gfddg7n5daob0A4nwBwgzPIM/edit?usp=sharing
