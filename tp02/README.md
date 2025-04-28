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
Controlador principal para manipulação de séries e episódios. Atua como intermediário entre a camada de visualização (ViewSeries) e a camada de dados (ArquivoSeries e ArquivoEpisode).

**Métodos:**
- `menu()` : Exibe o menu principal com as opções de operações sobre séries e episódios.
- `listarEpisodiosPorSerie()` : Lista os episódios vinculados a uma série específica, buscada pelo nome.
- `listarEpisodiosPorTemporada()` : Lista episódios de uma temporada específica de uma série selecionada.
- `buscarSeriePorNome()` : Permite buscar uma série pelo nome e exibe suas informações detalhadas.
- `incluirSerie()` : Inclui uma nova série no sistema, coletando os dados por meio da interface de visualização.
- `alterarSerie()` : Permite alterar os dados de uma série previamente cadastrada. Realiza verificação para manter valores antigos caso campos não sejam alterados.
- `excluirSerie()` : Exclui uma série do sistema, desde que não haja episódios vinculados a ela.

---

### `ControllerEpisode.java`
Controlador responsável por gerenciar operações relacionadas aos episódios. Inclui funcionalidades de criação, busca, alteração e exclusão de episódios.

**Métodos:**
- `menu()`: Exibe o menu principal de opções e executa a operação escolhida pelo usuário.
- `buscarEpisodioPorNome()` : Realiza a busca de um episódio por nome, dentro de uma série específica.
- `buscarEpisodio()` : Busca e exibe um episódio selecionado pelo usuário.
- `incluirEpisodio()` : Coleta os dados do usuário e realiza a inclusão de um novo episódio.
- `alterarEpisodio()` : Altera os dados de um episódio já existente.
- `excluirEpisodio()` : Exclui um episódio selecionado pelo usuário.
- `buscarSeriePorNome()` : Realiza a busca de uma série pelo nome.

---

### `ArquivoSeries`
Classe responsável pela manipulação dos dados de séries, incluindo operações CRUD e indexação por nome.

**Estrutura interna:**
- `indiceIndiretoNome`: Índice que associa o nome da série ao seu ID.

**Principais métodos:**
- `create(Series s)` : Cria uma nova série, armazenando-a no arquivo e no índice de nomes.
- `readNome(String nome)` : Lê todas as séries com o nome especificado.
- `delete(int id)` : Exclui uma série do arquivo e remove sua entrada do índice de nomes.
- `update(Series novaSerie)` : Atualiza os dados de uma série, ajustando o índice de nomes se o nome tiver mudado.

---

### `ArquivoEpisode`
Classe responsável pela manipulação dos episódios, incluindo persistência, leitura e gerenciamento de índices.

**Índices utilizados:**
- `indiceIndiretoNome`: associa nome do episódio ao ID.
- `relacaoNN`: associa ID do episódio ao ID da série.

**Métodos:**
- `create(Episode e)` : Cria um novo episódio, armazenando-o e atualizando os índices.
- `readFkSerie(int fkSeries)` : Retorna todos os episódios associados a uma determinada série.
- `readNome(String nome)` : Retorna os episódios com o nome especificado.
- `readEpisodiosPorSerieENome(int fkSerie, String nome)` : Retorna os episódios que pertencem a uma série e possuem determinado nome.
- `delete(int id)` : Exclui um episódio e atualiza os índices relacionados.
- `update(Episode novaEpisodio)` : Atualiza um episódio existente, ajustando os índices caso o nome tenha sido alterado.

---

### `Series`
Representa uma série de TV ou streaming com informações como nome, sinopse, ano de lançamento e plataforma de streaming. Implementa a interface `{@link Register}` para permitir a serialização e desserialização em formato binário.

**Atributos principais:**
- `id`, `name`, `synopsis`, `releaseYear`, `streaming`

**Métodos:**
- `getters/setters`
- `toString()`
- `toByteArray()` / `fromByteArray(byte[])`

---

### `Episode`
Representa um episódio de uma série, contendo informações como nome, temporada, data de lançamento, duração e chave estrangeira para a série associada. Implementa a interface ```{@link Register}``` para permitir serialização binária.

**Atributos principais:**
- `id`, `fkSerie`, `name`, `season`, `release`, `duration`

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
Classe responsável pela interação com o usuário para ações relacionadas a episódios. Fornece métodos para exibir menus, coletar dados e confirmar ações sobre episódios.

**Métodos principais:**
- `exibirMenu()` : Exibe o menu principal de ações disponíveis para episódios.
- `mostraEpisodio(Episode episodio)` : Exibe os dados de um episódio.
- `obterNome()` : Solicita ao usuário o nome do episódio.
- `obterNomeSerie()` : Solicita ao usuário o nome da série associada ao episódio.
- `obterTemporada()` : Solicita ao usuário a temporada do episódio.
- `obterDataLancamento()` : Solicita ao usuário a data de lançamento do episódio.
- `obterDuracao()` : Solicita ao usuário a duração do episódio.
- `confirmAction(int actionNum)` : Confirma com o usuário se ele deseja prosseguir com determinada ação.

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
