# Projeto: Gerenciamento de Séries e Episódios

## Informações Gerais
**Disciplina:** Algoritmos e Estrutura de Dados  
**Integrantes do Grupo:**  
- Mariana Almeida  
- Felipe Barros  
- Bruna Furtado  
- Gustavo Henrique  

## Sobre a Tarefa
O objetivo deste projeto é desenvolver um sistema para gerenciar séries e episódios, implementando funcionalidades essenciais de CRUD (Create, Read, Update, Delete) e garantindo a integridade dos relacionamentos entre as entidades. A principal estrutura de dados utilizada será a Árvore B+ para otimização das buscas e indexação, além da Tabela Hash Extensível.

## Organização do Grupo
A equipe está organizada por meio das seguintes ferramentas e metodologias:
- **Metodologia Ágil:** As tarefas foram distribuídas em **sprints**.
- **Comunicação:** O grupo utiliza **Discord** e **WhatsApp** para alinhamento das atividades.
- **Gerenciamento de Tarefas:** O acompanhamento das atividades ocorre no **GitHub Projects**.

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


