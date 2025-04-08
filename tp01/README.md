# TP 01 - Projeto: PUCFlix - Gerenciamento de Séries e EpisódiosPUCFlix

## Informações Gerais
**Disciplina:** Algoritmos e Estrutura de Dados  
**Integrantes do Grupo:**  
- Mariana Almeida Mendonça    <br>                    [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/marialmeida1)
- Felipe Barros  <br>          [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/nkdwon)
- Bruna Furtado da Fonseca    <br>                    [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/cestpassion)
- Gustavo Henrique Rodrigues de Castro  <br>          [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/GhrCastro) 

## Sobre a Tarefa
O objetivo deste projeto é desenvolver um sistema para gerenciar séries e episódios, implementando funcionalidades essenciais de CRUD (Create, Read, Update, Delete) e garantindo a integridade dos relacionamentos entre as entidades. A principal estrutura de dados utilizada será a Árvore B+ para otimização das buscas e indexação, além da Tabela Hash Extensível.

## Organização do Grupo
A equipe está organizada por meio das seguintes ferramentas e metodologias:
- **Metodologia Ágil:** As tarefas foram distribuídas em **sprints**.
- **Comunicação:** O grupo utiliza **Discord** e **WhatsApp** para alinhamento das atividades.
- **Gerenciamento de Tarefas:** O acompanhamento das atividades ocorre no **GitHub Projects**.

## Organização de Pastas do Projeto

- **`tp01/base_code`**: Armazena implementações de código base, como árvores B+, tabelas hash extensíveis e operações CRUD, ainda não utilizadas no projeto, mas fundamentais para futuras expansões.

- **`tp01/bin`**: Contém os arquivos `.class` compilados do código-fonte, organizados conforme a estrutura de pacotes de `src`. São usados para rodar a aplicação.

- **`tp01/src`**: Contém o código-fonte da aplicação, organizado em subpastas:
  - **data**: Manipulação e leitura de dados (ex: `ArquivoCliente`).
  - **models**: Representação das entidades do sistema (ex: `Cliente`, `Serie`).
  - **storeage**: Lógica de armazenamento e persistência de dados (ex: `HashExtensivel`).
  - **util**: Funções auxiliares e utilitárias (ex: `ParCPFID`).
  - **view**: Interfaces e interação com o usuário (ex: `MenuEpisodios`, `MenuSeries`).

- **`tp01/files`**: Armazena os dados persistentes do sistema, como arquivos de clientes e índices.

- **`tp01/appendix`**: Armazena os arquivos correspondentes ao relatos de cada aluno.


Essa estrutura facilita a organização modular e futura expansão do projeto.


## Sprints e Tarefas

### 🟢 Sprint 1: Estrutura e Funcionalidade Principal (Foco no CRUD e no Relacionamento 1:N)
**Prazo recomendado:** Até 24

1️⃣ **Implementar CRUD de Séries** (🗂️)  
   - Criar a estrutura da entidade Série  
   - Implementar inclusão, alteração, busca e exclusão  

2️⃣ **Implementar CRUD de Episódios** (🎞️)  
   - Criar a estrutura da entidade Episódio  
   - Implementar inclusão, alteração, busca e exclusão  

3️⃣ **Criar a relação 1:N entre séries e episódios usando Árvore B+** (🔗)  
   - Definir o par (idSerie; idEpisódio)  
   - Criar e gerenciar os índices na Árvore B+  

4️⃣ **Criar a visão e controle de séries (Interface + Lógica)** (📺)  
   - Criar a classe VisaoSeries para entrada/saída de dados  
   - Criar a classe ControleSeries para gerenciar operações  

5️⃣ **Criar a visão e controle de episódios (Interface + Lógica)** (🎭)  
   - Criar a classe VisaoEpisodios para entrada/saída de dados  
   - Criar a classe ControleEpisodios para gerenciar operações  

6️⃣ **Garantir que séries não possam ser excluídas se tiverem episódios vinculados** (🚫)  
   - Implementar regra de negócio para evitar exclusão incorreta  

### 🟡 Sprint 2: Regras Extras, Testes e Documentação (Foco em otimização, regras e finalização)
**Prazo recomendado:** Até 30 (com tolerância até 31)

7️⃣ **Permitir visualização dos episódios organizados por temporada** (📆)  
   - Criar método para exibir episódios de uma série separados por temporada  

8️⃣ **Criar e configurar os índices usando Tabela Hash Extensível e Árvore B+** (📌)  
   - Implementar os índices para otimizar as buscas  

9️⃣ **Garantir que episódios só possam ser criados para séries existentes** (✔️)  
   - Criar validação para impedir episódios sem série válida  

🔟 **Testar todas as operações e validar o funcionamento** (🔍)  
   - Testar CRUD, relacionamento 1:N e regras de exclusão  

1️⃣1️⃣ **Criar documentação e relatório (README) para o GitHub** (📝)  
   - Explicar como o sistema funciona, estrutura de classes e métodos principais  

1️⃣2️⃣ **Responder ao checklist do professor e relatar desafios** ✅ (📑)  
   - Verificar cada requisito do trabalho e documentar dificuldades enfrentadas  


## Rodando o Código

**Compilando o código:**

```bash
javac -d tp01/bin tp01/src/**/*.java 
```

**Rodando o código:**

```bash
java -cp tp01/bin tp01.src.Main                                    
```

---

## 📦 Descrição das Classes

### `Main`
A classe `Main` é o **ponto de entrada do sistema PUCFlix**, responsável por inicializar a aplicação e exibir o **menu principal de navegação** para o usuário.

**Responsabilidades principais:**
- Exibir o menu inicial com as opções:
  - Gerenciamento de séries.
  - Gerenciamento de episódios.
  - Encerramento da aplicação.
- Capturar e tratar a escolha do usuário.
- Redirecionar o fluxo para os controladores:
  - `ControllerSeries`
  - `ControllerEpisode`

**Estrutura de execução:**
- Utiliza um loop `do...while` para manter a aplicação ativa até a opção de saída (`0`).
- Possui tratamento de exceções para entradas inválidas.
- Instancia os controladores diretamente a cada acesso.

---

### `ControllerSeries.java`
Responsável por orquestrar todas as operações relacionadas às **séries** no sistema.

**Principais responsabilidades:**
- Exibição do menu principal (`menu()`).
- Inclusão, alteração, busca e exclusão de séries.
- Listagem de episódios por série ou temporada.
- Validações de entrada e confirmações com o usuário.
- Verificação de integridade antes da exclusão de séries.

**Métodos principais:**
- `menu()`
- `incluirSerie()`
- `alterarSerie()`
- `excluirSerie()`
- `buscarSeriePorNome()`
- `listarEpisodiosPorSerie()`
- `listarEpisodiosPorTemporada()`

---

### `ControllerEpisode.java`
Gerencia todas as operações relacionadas aos **episódios**, conectando os dados e a interface com o usuário.

**Principais métodos:**
- `menu()`: Exibe o menu principal de episódios.
- `incluirEpisodio()`: Coleta dados e cadastra um novo episódio.
- `buscarEpisodio()` / `buscarEpisodioPorNome()`: Localizam episódios por nome.
- `alterarEpisodio()`: Permite editar dados de um episódio.
- `excluirEpisodio()`: Exclui logicamente um episódio.
- `buscarSeriePorNome()`: Localiza a série à qual o episódio pertence.

**Observação:** Conta com validações e confirmações interativas para garantir a integridade dos dados e melhorar a experiência de uso.

---

### `ArquivoSeries`
Responsável pela persistência e manipulação de dados das séries.

**Estrutura interna:**
- `indiceIndiretoNome`: Índice que associa o nome da série ao seu ID.

**Principais métodos:**
- `create(Series s)`
- `readNome(String nome)`
- `delete(int id)`
- `update(Series novaSerie)`

Garante consistência entre os dados armazenados e os índices de busca, proporcionando eficiência nas operações.

---

### `ArquivoEpisode`
Gerencia a persistência dos episódios e mantém índices auxiliares para facilitar as buscas.

**Índices utilizados:**
- `indiceIndiretoNome`: associa nome do episódio ao ID.
- `relacaoNN`: associa ID do episódio ao ID da série.

**Principais métodos:**
- `create(Episode e)`
- `readFkSerie(int fkSeries)`
- `readNome(String nome)`
- `readEpisodiosPorSerieENome(int fkSerie, String nome)`
- `delete(int id)`
- `update(Episode novaEpisodio)`

Centraliza a lógica de leitura/escrita dos episódios e mantém integridade entre os dados e os índices.

---

### `Series`
Modelo de dados para representar uma **série**.

**Atributos principais:**
- `id`, `name`, `synopsis`, `releaseYear`, `streaming`

**Construtores:**
- Padrão, sem ID (criação), e completo (reconstrução)

**Métodos:**
- `getters/setters`
- `toString()`
- `toByteArray()` / `fromByteArray(byte[])`

---

### `Episode`
Modelo de dados que representa um **episódio** de uma série.

**Atributos principais:**
- `id`, `fkSerie`, `name`, `season`, `release`, `duration`

**Construtores:**
- Padrão, com e sem ID

**Métodos:**
- `getters/setters`
- `toString()`
- `toByteArray()` / `fromByteArray(byte[])`

---

### `ViewSeries`
Responsável pela interface com o usuário nas ações relacionadas a **séries**.

**Funções principais:**
- Mostrar menus e opções.
- Coletar dados como nome, sinopse, ano e plataforma de streaming.
- Confirmar ações.
- Exibir detalhes de uma série.

**Métodos principais:**
- `exibirMenu()`
- `mostraSerie(Series serie)`
- `obterNome()`, `obterSinopse()`, `obterAnoLancamento()`, `obterStreaming()`
- `confirmAction(int actionNum)`

---

### `ViewEpisode`
Interface textual para as funcionalidades de **episódios**.

**Responsabilidades principais:**
- Mostrar menus e opções.
- Coletar dados como nome, temporada, data de lançamento e duração.
- Confirmar ações.
- Exibir os detalhes de episódios.

**Métodos principais:**
- `exibirMenu()`
- `mostraEpisodio(Episode episodio)`
- `obterNome()`, `obterNomeSerie()`, `obterTemporada()`, `obterDataLancamento()`, `obterDuracao()`
- `confirmAction(int actionNum)`

---

## Experiência dos Integrantes do Trabalho

Cada integrante do grupo compartilhou, em primeira pessoa, um breve relato sobre sua experiência ao longo do desenvolvimento deste projeto. Esses relatos refletem os aprendizados, desafios enfrentados e contribuições individuais. A leitura desses textos complementa a visão técnica do projeto com uma perspectiva mais pessoal e colaborativa.

- [Experiência de Desenvolvimento](XP.md)

---

## Checklist Final do Relatório

Para concluir, seguem abaixo as respostas ao checklist solicitado pelo professor. Todas as funcionalidades foram implementadas e testadas com sucesso durante o desenvolvimento do projeto.

- As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente? **Sim**
- As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente? **Sim**
- Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? **Sim**
- O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios? **Sim**
- Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries? **Sim**
- Há uma visualização das séries que mostre os episódios por temporada? **Sim**
- A remoção de séries checa se há algum episódio vinculado a ela? **Sim**
- A inclusão da série em um episódio se limita às séries existentes? **Sim**
- O trabalho está funcionando corretamente? **Sim**
- O trabalho está completo? **Sim**
- O trabalho é original e não a cópia de um trabalho de outro grupo? **Sim**
