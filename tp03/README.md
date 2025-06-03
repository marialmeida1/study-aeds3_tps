# TP 02 - Projeto: PUCFlix - Gerenciamento de Séries, Episódios e Atores - PUCFlix

## Informações Gerais
**Disciplina:** Algoritmos e Estrutura de Dados  
**Integrantes do Grupo:**  
- Mariana Almeida Mendonça    <br>                    [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/marialmeida1)
- Felipe Barros  <br>          [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/nkdwon)
- Bruna Furtado da Fonseca    <br>                    [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/cestpassion)
- Gustavo Henrique Rodrigues de Castro  <br>          [<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/GhrCastro) 

## Sobre a Tarefa

Este trabalho prático tem como objetivo aprofundar o uso de estruturas de dados para representar relacionamentos entre entidades no contexto de um sistema de gerenciamento de séries, chamado PUCFlix 1.0. Após a implementação de um relacionamento 1:N no primeiro trabalho (TP1), o foco agora está na implementação de um relacionamento N:N (muitos-para-muitos) entre as entidades Série e Ator, utilizando duas Árvores B+ para garantir buscas eficientes e consistência dos dados.

No sistema, cada série pode ter vários atores e cada ator pode participar de várias séries. Para isso, foram implementadas estruturas que permitem associar os IDs das séries aos IDs dos atores e vice-versa. Além disso, foi desenvolvido o CRUD completo para atores, com validações que impedem a exclusão de um ator caso ele esteja vinculado a alguma série.

O menu principal do programa foi ampliado para incluir a manutenção de atores, e as visões de séries e atores foram adaptadas para exibir os vínculos entre eles. A manipulação desses vínculos também pode ser feita durante o cadastro ou edição de séries, garantindo assim uma interface intuitiva e a integridade dos dados.

Este trabalho reforça a importância de organizar bem as estruturas de dados, garantir consistência nas operações e manter uma interface clara para o usuário final.

## Organização do Grupo

A equipe está organizada por meio das seguintes ferramentas e metodologias:
- **Metodologia Ágil:** As tarefas foram distribuídas em **sprints**.
- **Comunicação:** O grupo utiliza **Discord** e **WhatsApp** para alinhamento das atividades.
- **Gerenciamento de Tarefas:** O acompanhamento das atividades ocorre no **GitHub Projects**.

## Organização de Pastas do Projeto

- **`tp03/bin`**: Contém os arquivos `.class` compilados do código-fonte, organizados conforme a estrutura de pacotes de `src`. São usados para rodar a aplicação.

- **`tp03/src`**: Contém o código-fonte da aplicação, organizado em subpastas:
  - **data**: Manipulação e leitura de dados (ex: `ArquivoCliente`).
  - **models**: Representação das entidades do sistema (ex: `Cliente`, `Serie`).
  - **storeage**: Lógica de armazenamento e persistência de dados (ex: `HashExtensivel`).
  - **util**: Funções auxiliares e utilitárias (ex: `ParCPFID`).
  - **view**: Interfaces e interação com o usuário (ex: `MenuEpisodios`, `MenuSeries`).

- **`tp03/files`**: Armazena os dados persistentes do sistema, como arquivos de clientes e índices.

Essa estrutura facilita a organização modular e futura expansão do projeto.


## Rodando o Código

**Compilando o código:**

```bash
javac -d tp03/bin tp03/src/**/*.java 
```

**Rodando o código:**

```bash
java -cp tp03/bin tp03.src.Main                                    
```

---

## Descrição do Funcionamento

A estrutura de lista invertida implementada aqui permite indexar registros de forma eficiente, especialmente para facilitar buscas por palavras associadas a objetos (como nomes de séries). Basicamente, cada termo (palavra) está associado a uma lista de registros (identificados por ID) que o contém. Essa estrutura é muito usada em mecanismos de busca e sistemas de recuperação de informação.

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
  - `ControllerActor`

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

### `ControllerActor.java`

Controlador responsável por gerenciar operações relacionadas aos atores e suas relações com séries. Atua como intermediário entre a interface de visualização e os arquivos de dados, permitindo ações como criação, busca, alteração e exclusão de atores, além de listar séries associadas a eles.

**Métodos:**

* `menu()`: Exibe o menu principal de opções e executa a operação escolhida pelo usuário.
* `incluirAtor()`: Coleta os dados do usuário e realiza a inclusão de um novo ator/atriz no sistema.
* `buscarAtor()`: Busca e exibe informações detalhadas de um ator/atriz a partir do nome informado.
* `alterarAtor()`: Permite atualizar os dados de um ator/atriz existente, mantendo campos inalterados caso o usuário opte por não modificá-los.
* `excluirAtor()`: Exclui um ator/atriz, desde que não esteja vinculado(a) a nenhuma série.
* `listSeriesByActor()`: Lista todas as séries em que o ator/atriz selecionado(a) está relacionado(a).

---

### `ArchiveSeries`
Classe responsável pela manipulação dos dados de séries, incluindo operações CRUD e indexação por nome.

**Estrutura interna:**
- `indiceIndiretoNome`: Índice que associa o nome da série ao seu ID.

**Principais métodos:**
- `create(Series s)` : Cria uma nova série, armazenando-a no arquivo e no índice de nomes.
- `readNome(String nome)` : Lê todas as séries com o nome especificado.
- `delete(int id)` : Exclui uma série do arquivo e remove sua entrada do índice de nomes.
- `update(Series novaSerie)` : Atualiza os dados de uma série, ajustando o índice de nomes se o nome tiver mudado.

---

### `ArchiveEpisode`
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

### `ArchiveSeries`

Classe responsável pela manipulação das séries, incluindo persistência, leitura e gerenciamento de índice por nome.

**Índice utilizado:**

* `indiceIndiretoNome`: associa nome da série ao seu ID.

**Métodos:**

* `create(Series s)` : Cria uma nova série, garantindo que o nome seja único, armazenando-a e atualizando o índice de nomes.
* `readNome(String nome)` : Retorna todas as séries com o nome especificado, consultando o índice.
* `delete(int id)` : Exclui uma série e remove sua entrada do índice de nomes.
* `update(Series novaSerie)` : Atualiza uma série existente, ajustando o índice caso o nome tenha sido alterado.

---

### `ArchiveRelationNN`

Responsável por gerenciar as relações **n\:n (muitos-para-muitos)** entre entidades do sistema, como **atores e séries**. Utiliza duas estruturas de índice B-tree para manter a integridade e a eficiência das buscas em ambas as direções: ator → série e série → ator.

#### Principais Responsabilidades:

* Criar, ler e excluir relações entre atores e séries.
* Garantir que não haja duplicidade nas relações.
* Fornecer métodos para leitura de todos os atores de uma série e todas as séries de um ator.
* Excluir todas as relações associadas a uma determinada série.

#### Atributos:

* `actorSerie`: índice B-tree que armazena relações do tipo ator → série.
* `serieActor`: índice B-tree que armazena relações do tipo série → ator.

#### Métodos principais:

* `createRelation(int idActor, int idSerie)`: cria uma nova relação entre ator e série, se ela ainda não existir.
* `readActorsBySerie(int idSerie)`: retorna todos os atores associados a uma série.
* `readSeriesByActor(int idActor)`: retorna todas as séries associadas a um ator.
* `deleteRelation(int idActor, int idSerie)`: exclui uma relação específica entre um ator e uma série.
* `deleteAllRelations(int idSerie)`: remove todas as relações entre uma série e seus atores associados.

#### Observações:

* As relações são armazenadas em dois arquivos distintos, permitindo consultas bidirecionais com alta performance.
* Utiliza a classe `PairIDFK` para representar pares de IDs relacionados.
* Verificações de existência são realizadas antes da criação de relações para evitar redundância.
* Debugs e logs são impressos no console durante a criação e leitura, facilitando o acompanhamento da lógica durante testes.

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

### `Actor`

Representa um ator cadastrado no sistema, contendo um identificador único e um nome. Implementa a interface `{@link Register}` para permitir serialização binária, possibilitando seu armazenamento em arquivos.

**Atributos principais:**

* `id`, `name`

**Métodos:**

* `getters/setters`
* `toString()`
* `toByteArray()` / `fromByteArray(byte[])`

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

### `ViewActor`

Responsável pela interface com o usuário nas ações relacionadas a **atores e atrizes**.

**Funções principais:**

* Exibir menus e opções.
* Coletar dados como nome do ator/atriz.
* Confirmar ações como inclusão, alteração e exclusão.
* Exibir detalhes de um ator/atriz.

**Métodos principais:**

* `exibirMenu()`
* `mostraAtor(Actor ator)`
* `obterNome()`
* `confirmAction(int actionNum)`

---

### `ListaInvertida`

Responsável pela estrutura de índice invertido, armazenando termos associados a IDs de registros (como nomes de séries e seus respectivos IDs).

**Funções principais:**

* Criar e gerenciar os arquivos.
* Associar termos a registros.
* Ler os registros associados a um termo.
* Remover um registro de um termo.

**Métodos principais:**

* create(ElementoLista e)
* read(int id) 
* update(ElementoLista e) 
* delete(int id)

### ElementoLista

Representa um elemento associado a um termo da lista invertida, armazenando a identidade do registro e quantas vezes o termo aparece.

**Funções principais: **

* Guardar o ID do item indexado.
* Armazenar a frequência de ocorrência do termo.
* Permitir ordenação e clonagem de elementos.

**Métodos principais: **

* ElementoLista(int id, int frequencia)
* getID() / setID(int id) 
* getFrequencia() / setFrequencia(int freq)
* compareTo(ElementoLista e)
* clone()

### ListaInvertidaUtils

Classe utilitária para pré-processamento de termos usados na indexação.

**Funções principais: **

* Padronizar palavras antes da inserção na lista invertida.
* Eliminar palavras irrelevantes (stop words).
* Melhorar a qualidade da indexação textual.

**Métodos principais: **

* normalizar(String termo) 
* removerStopWords(String[] termos)
* extractTerms(String texto) 
* calcFrequencia(String texto, String termo)
* calcularIDF(ListaInvertida lista, String termo)


## Experiência dos Integrantes do Trabalho

Trabalhar com a estrutura de lista invertida nos permitiu compreender de forma mais clara como funcionam sistemas de indexação e recuperação de dados, algo fundamental em áreas como bancos de dados e mecanismos de busca. A implementação das funcionalidades de criação, leitura e exclusão de termos foi um desafio inicial, principalmente para garantir a integridade das informações nos arquivos binários. No entanto, à medida que integramos essa estrutura ao CRUD da entidade Séries, conseguimos visualizar na prática sua utilidade e como ela contribui significativamente para a eficiência nas buscas por texto, otimizando o acesso às informações de forma rápida e estruturada.

- [Experiência de Desenvolvimento](XP.md)

---

## Checklist Final do Relatório

Para concluir, seguem abaixo as respostas ao checklist solicitado pelo professor. Todas as funcionalidades foram implementadas e testadas com sucesso durante o desenvolvimento do projeto.

- O índice invertido com os termos dos títulos das séries foi criado usando a classe ListaInvertida? **- SIM**
- O índice invertido com os termos dos títulos dos episódios foi criado usando a classe ListaInvertida? **- SIM**
- O índice invertido com os termos dos nomes dos atores foi criado usando a classe ListaInvertida? **- SIM**
- É possível buscar séries por palavras usando o índice invertido? **- SIM**
- É possível buscar episódios por palavras usando o índice invertido? **- SIM**
- É possível buscar atores por palavras usando o índice invertido? **- SIM**
- O trabalho está completo? **- SIM**
- O trabalho é original e não a cópia de um trabalho de um colega? **- SIM**

