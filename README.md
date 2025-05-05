# Módulo de Chave PIX

## Descrição
Este projeto implementa um módulo para gerenciamento de chaves PIX, com consulta, criação, alteração e inativação de chave Pix de acordo com os requsiitos soclicitados, seguindo as melhores práticas de desenvolvimento de software e os princípios dos 12 Factors.

## Arquitetura

### Arquitetura Hexagonal com Influências da Clean Architecture
O projeto utiliza uma arquitetura baseada nos princípios da Arquitetura Hexagonal (Ports and Adapters), com algumas influências da Clean Architecture, focando na separação de responsabilidades e na independência do domínio.

#### Estrutura Principal
- **Domain Layer**: Contém as entidades e regras de negócio
  - Entidades 
  - Interfaces de portas 
  - Exceções de domínio

- **Application Layer**: Implementa os casos de uso
  - Validações  
  - Estratégias
  - Mapeadores 
  - DTOs 

- **Infrastructure Layer**: Implementações técnicas
  - Persistência
  - APIs REST
  - Adaptadores

#### Portas e Adaptadores
- **Portas de Entrada (Driving Ports)**
  - `InclusaoChavePixPort`
  - `ConsultaChavePixPort`
  - `AlteracaoChavePixPort`

- **Portas de Saída (Driven Ports)**
  - `JpaChavePixRepository`

- **Adaptadores**
  - `ChavePixAdapter`: Adapta a interface do repositório JPA
  - Controladores REST
  - Implementações de persistência

### Características da Arquitetura
1. **Independência do Domínio**
   - Regras de negócio isoladas em sua própria camada

2. **Separação de Responsabilidades**
   - Cada camada tem uma responsabilidade bem definida

3. **Manutenibilidade**
   - Mudanças em uma camada não afetam outras
   - Código organizado e coeso

### Fluxo de Dados
1. **Entrada**: 
   - Requisições HTTP → Controladores REST → DTOs → Casos de Uso

2. **Processamento**:
   - Casos de Uso → Regras de Negócio → Validações → Adaptadores

3. **Saída**:
   - Adaptadores → Persistência → Respostas HTTP

## Design Patterns

### Padrões Criacionais
- **Factory Method**: Implementado em várias classes para criação de estratégias de validação e consulta

### Padrões Estruturais
- **Adapter**: Implementado na classe `ChavePixAdapter` para adaptação entre interfaces

### Padrões Comportamentais
- **Strategy**: Implementado para diferentes estratégias de validação e consulta
- **Template Method**: Implementado nas classes de validação
- **Chain of Responsibility**: Implementado no tratamento de exceções

## Implementação dos 12 Factors

### I. Base de Código
- Código versionado com Git
- Estrutura de diretórios organizada
- Múltiplos deploys possíveis

### II. Dependências
- Gerenciamento via Maven
- Dependências declaradas explicitamente no `pom.xml`
- Isolamento de dependências

### III. Configurações
- Configurações externas via arquivos de propriedades
- Separação por ambiente

### IV. Serviços de Apoio
- Banco de dados como recurso anexado (h2)
- Serviços externos configurados como recursos
- Interfaces bem definidas via ports

### V. Construa, lance, execute 
- Separação clara entre build e execução
- Processo de deploy definido
- Build automatizado e separado via Maven

### VI. Processos
- Aplicação executada como processos stateless
- Estado mantido em serviços externos
- Sem armazenamento de estado nos processos

### VII. Vínculo de porta
- Serviços expostos via REST
- Não depende de injeções de tempo de execução de um servidor web 
- Comunicação entre serviços via portas

### X. Dev/prod semelhantes (futuramente)
- Ambientes similares
- Mesmas dependências
- Mesma configuração de build

### XI. Logs
- Logs como fluxo de eventos
- Uso de SLF4J
- Logs estruturados e padronizados

## Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Maven
- Lombok
- SLF4J
- JUnit
- Mockito

## Como Executar

### Pré-requisitos
- Java 11 ou superior
- Maven
- Banco de dados H2

### Configuração
1. Clone o repositório
2. Configure as variáveis de ambiente necessárias
3. Execute o build:
```bash
mvn clean install
```

### Execução
```bash
mvn spring-boot:run
```

## Testes
```bash
mvn test
mvn clean verify       
```