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

## 🧾 Descrição das Classes Principais

### 🎬 `ControllerEpisode`

**Pacote:** `tp01.src.controller`  
Controlador responsável pelas operações com episódios. Faz a ponte entre o modelo (`Episode`), a visualização (`ViewEpisode`) e a persistência (`ArquivoEpisode`, `ArquivoSeries`).

#### 🧩 Atributos:
- `ArquivoEpisode arqEpisodios`
- `ArquivoSeries arqSeries`
- `ViewEpisode visaoEpisodios`
- `Scanner console`

#### 🛠 Principais Métodos:

| Método                        | Descrição                                                                 |
|------------------------------|---------------------------------------------------------------------------|
| `menu()`                     | Exibe menu de opções e direciona para as ações disponíveis.               |
| `incluirEpisodio()`          | Coleta dados e insere um novo episódio vinculado a uma série existente.  |
| `buscarEpisodio()`           | Busca um episódio pelo nome e exibe suas informações.                    |
| `buscarEpisodioPorNome()`    | Permite selecionar um episódio a partir de uma lista de resultados.      |
| `alterarEpisodio()`          | Permite a alteração dos dados de um episódio.                            |
| `excluirEpisodio()`          | Remove um episódio após confirmação do usuário.                          |
| `buscarSeriePorNome()`       | Retorna uma série com base em uma busca textual.                         |

#### ✔️ Regras de Negócio:
- Um episódio só pode ser criado se estiver associado a uma série.
- Validações: nome não pode ser vazio, temporada e duração devem ser válidos.
- Operações críticas requerem confirmação.
- Sem séries cadastradas, não é possível criar episódios.

---

### 📺 `ControllerSeries`

**Pacote:** `tp01.src.controller`  
Responsável pelo fluxo geral da aplicação, incluindo CRUD de séries e interação com episódios.

#### 🧩 Responsabilidades:
- Controlar o menu principal.
- Executar operações de CRUD de séries.
- Listar episódios vinculados a uma série ou temporada.
- Proteger exclusão de séries com episódios associados.

#### 🛠 Principais Métodos:
- `menu()`
- `incluirSerie()`
- `buscarSeriePorNome()`
- `alterarSerie()`
- `excluirSerie()`
- `listarEpisodiosPorSerie()`
- `listarEpisodiosPorTemporada()`

#### 🔁 Fluxo do menu:
```java
switch (opcao) {
    case 1: incluirSerie(); break;
    case 2: buscarSeriePorNome(); break;
    case 3: alterarSerie(); break;
    case 4: excluirSerie(); break;
    case 5: listarEpisodiosPorSerie(); break;
    case 6: listarEpisodiosPorTemporada(); break;
    case 0: break;
    default: System.out.println("Opção inválida!");
}
```

---

### 📁 `ArquivoEpisode`

**Pacote:** `tp01.src.data`  
Gerencia a persistência dos episódios e suas relações com séries.

#### 🛠 Funcionalidades:
- Armazenamento binário.
- Índice por nome (B-Tree).
- Relacionamento com séries via chave estrangeira.

#### 🔧 Atributos:
- `ArchiveTreeB<PairNameID> indiceIndiretoNome`
- `ArchiveTreeB<PairIDFK> relacaoNN`

#### 🔧 Métodos:
- `create(Episode e)`
- `readFkSerie(int fkSeries)`
- `readNome(String nome)`
- `readEpisodiosPorSerieENome(int fkSerie, String nome)`
- `update(Episode novaEpisodio)`
- `delete(int id)`

#### 💡 Exemplo:
```java
ArquivoEpisode arquivo = new ArquivoEpisode();
Episode novo = new Episode("Piloto", 1, 45);
int id = arquivo.create(novo);
arquivo.update(novo);
arquivo.delete(id);
```

---

### 📁 `ArquivoSeries`

**Pacote:** `tp01.src.data`  
Responsável pela persistência de séries com indexação por nome.

#### 🔧 Atributo:
- `ArchiveTreeB<PairNameID> indiceIndiretoNome`

#### 🔧 Métodos:
- `create(Series s)`
- `readNome(String nome)`
- `update(Series novaSerie)`
- `delete(int id)`

#### 💡 Exemplo:
```java
ArquivoSeries arquivo = new ArquivoSeries();
Series s = new Series("Dark", "Drama", (short) 2017, "Netflix");
int id = arquivo.create(s);
arquivo.update(s);
arquivo.delete(id);
```

---

### 🧩 `Episode`

**Pacote:** `tp01.src.models`  
Modela um episódio e permite serialização binária.

#### 🔧 Atributos:
- `int id`, `int fkSerie`, `String name`, `int season`, `LocalDate release`, `int duration`

#### 🛠 Construtores:
- Padrão, sem ID e completo.

#### 🔧 Métodos principais:
- `toByteArray()`
- `fromByteArray(byte[] b)`
- `toString()`

#### 💡 Exemplo:
```java
Episode ep = new Episode(1, "Começo", 1, LocalDate.of(2024, 10, 1), 45);
byte[] dados = ep.toByteArray();
ep.fromByteArray(dados);
```

---

### 📺 `Series`

**Pacote:** `tp01.src.models`  
Modela uma série com campos básicos e suporte a serialização.

#### 🔧 Atributos:
- `int id`, `String name`, `String synopsis`, `short releaseYear`, `String streaming`

#### 🔧 Métodos principais:
- `toByteArray()`
- `fromByteArray(byte[] b)`
- `toString()`

#### 💡 Exemplo:
```java
Series s = new Series("Dark", "Viagem no tempo", (short) 2017, "Netflix");
byte[] dados = s.toByteArray();
s.fromByteArray(dados);
```

---

### 🖥️ `ViewEpisode`

**Pacote:** `tp01.src.view`  
Interface textual para operações de episódio.

#### 🧩 Responsabilidades:
- Apresentar menus.
- Coletar dados com validação.
- Confirmar ações críticas.

#### 🔧 Métodos:
- `exibirMenu()`
- `mostraEpisodio(Episode episodio)`
- `obterNome()`, `obterDuracao()`, `obterDataLancamento()`, etc.
- `confirmAction(int actionNum)`

---

### 🖥️ `ViewSeries`

**Pacote:** `tp01.src.view`  
Interface textual para operações de séries.

#### 🧩 Responsabilidades:
- Coletar e exibir dados de séries.
- Confirmar ações críticas.

#### 🔧 Métodos:
- `exibirMenu()`
- Coleta: `obterNome()`, `obterSinopse()`, `obterAno()`, `obterStreaming()`
- Exibição e confirmação

Claro! Aqui está como você pode adicionar uma seção ao final (ou onde preferir) do seu `README.md` principal, incluindo o caminho para as experiências dos integrantes do trabalho, de forma organizada:

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
