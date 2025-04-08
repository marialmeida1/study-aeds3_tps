# Experiência de Desenvolvimento

## Considerações

Ao iniciar o trabalho, Gustavo criou um servidor no Discord com canais organizados para facilitar a comunicação da equipe, incluindo áreas específicas para postagem do enunciado, links do repositório Git e troca de código entre os integrantes.

#### Canal de enunciados  
> ![Captura de tela 2025-04-07 194159](https://github.com/user-attachments/assets/b11e5db5-9bb3-44b4-bbdb-c3d38b1f8831)

<br>

#### Divisão das tarefas  
> O trabalho foi dividido em 12 tarefas menores, de modo a distribuir de forma justa a carga entre os quatro membros. Cada integrante ficou responsável por concluir, ao menos, três tarefas.  
> 
> ![Captura de tela 2025-04-07 194215](https://github.com/user-attachments/assets/af1841f4-d5e6-465f-b5f9-7a3e9ccee68f)  
> ![Captura de tela 2025-04-07 194231](https://github.com/user-attachments/assets/da631357-8599-4443-9fcf-5778f2a121ae)  
> ![image](https://github.com/user-attachments/assets/b0494323-e9c9-46d3-a940-73622f261b53)

<br>

#### Códigos relacionados ao trabalho  
> ![Captura de tela 2025-04-07 194249](https://github.com/user-attachments/assets/3fa77278-62af-4038-9ea7-b7899300d484)

<br>

#### Repositório Git  
> Mariana criou o repositório do trabalho e, em conjunto com o grupo, estabeleceu um fluxo de trabalho no Git que foi ajustado ao longo do projeto.  
> 
> - Cada membro trabalhou em uma branch fixa nomeada no padrão `<GitHub-userName>-dev`.  
> - Após concluir uma tarefa funcional, os commits eram enviados para a branch `Development`, garantindo a integração contínua entre os desenvolvimentos.  
> - Ao término de cada etapa, a branch `Master` recebia apenas versões 100% testadas e funcionais, mantendo-a sempre uma entrega atrás, como uma forma de segurança contra perdas irreversíveis.  
> 
> ![Captura de tela 2025-04-07 195515](https://github.com/user-attachments/assets/f14d898f-e67d-43a5-8271-a917f45fe40f)

As tarefas também foram organizadas por meio de *issues* no GitHub, onde os membros podiam se designar livremente às atividades, permitindo uma boa visualização do progresso e evitando retrabalho.

Gustavo foi responsável pelo desenvolvimento inicial dos CRUDs e views relacionados às séries, com os arquivos principais criados nos primeiros commits da sua branch. Mais adiante, Felipe e Mariana propuseram melhorias na estrutura de armazenamento e nas buscas de séries, o que levou a uma refatoração de parte do código original. Mariana, além disso, criou a relação 1:N entre séries e episódios utilizando uma Árvore B+, o que possibilitou indexações mais eficientes e maior organização da estrutura interna do sistema.

Já Bruna contribuiu com a visualização dos episódios por temporada, organizando a exibição das informações de maneira mais clara para o usuário. Ela também foi responsável por implementar algumas regras de negócio, como impedir a exclusão de séries que possuíam episódios cadastrados e evitar o cadastro de episódios em séries inexistentes, garantindo maior integridade aos dados do sistema. 

Na fase final do desenvolvimento, Gustavo assumiu a implementação da funcionalidade de **listar episódios por série**, onde foi necessário aprimorar o algoritmo de busca. Inicialmente, o sistema retornava apenas o primeiro item encontrado, o que causava confusão em casos de nomes semelhantes como “Friends” e “Friends of Tomorrow”. Mariana solucionou isso criando uma interface de menu para permitir que o usuário selecionasse a série correta:

```java
-------------------------------
  Escolha a série:
  1. Friends
  2. Friends of tomorrow
  3. Fri
-------------------------------
1
===============================
Série: Friends
```

Gustavo então aprimorou essa lógica ao adicionar uma verificação: se apenas uma série corresponder à busca, ela é automaticamente selecionada, sem exibir o menu de opções:

```java
===============================
Série: Friends
```

Além dessas melhorias, Mariana também foi responsável por implementar o CRUD completo dos episódios, desenvolvendo todas as funcionalidades de cadastro, leitura, atualização e exclusão. E também colaborou com a escrita do README, juntamente com a Bruna,.

Felipe, por sua vez, teve papel fundamental na fase de testes. Ele foi responsável por testar todas as operações implementadas, garantindo que o sistema estivesse funcionando corretamente em todas as frentes. Além disso, configurou os índices utilizando tanto **Tabela Hash Extensível** quanto **Árvore B+**, um desafio técnico importante que exigiu aprofundamento teórico (📌).

Bruna também introduziu uma pausa interativa (“pressione Enter para voltar”) após a exibição das informações no terminal, evitando que os dados sumissem rapidamente da tela e proporcionando uma experiência de uso mais fluida. Além disso, para melhorar a legibilidade e facilitar a manutenção, ela adicionou JavaDocs em todo o código do projeto, documentando classes, métodos e estruturas com clareza.

Ao final do desenvolvimento, Bruna complementou o arquivo README.md, explicando cada classe e seus principais métodos. Também organizou esta seção de relatos de experiência dos colegas de grupo juntamente com o Gustavo, garantindo que as contribuições individuais fossem devidamente registradas. Por fim, ficou responsável por responder ao checklist do relatório final do trabalho, revisando os critérios exigidos e certificando-se de que todos os pontos haviam sido contemplados.

As maiores dificuldades enfrentadas pela equipe envolveram o uso e entendimento corretos de estruturas como **Hash Extensível** e **Árvore B+**, mas a boa comunicação e o trabalho em grupo permitiram superar esses obstáculos e concluir o projeto com sucesso.

Acreditamos que implementamos com sucesso todas as funcionalidades propostas no trabalho. O maior desafio foi, sem dúvida, entender como trabalhar corretamente com os índices e implementá-los de forma eficaz. No geral, o CRUD em si não apresentou grandes dificuldades, especialmente após estudarmos e seguirmos os exemplos de código fornecidos. Com paciência e colaboração, os resultados esperados foram alcançados.

O trabalho foi uma experiência de grande aprendizado para mim. Aplicar, na prática, os conceitos ensinados teoricamente me permitiu enxergar sua aplicação real. Além disso, integrar esse conhecimento aos meus estudos concomitantes sobre **banco de dados** tornou o processo ainda mais enriquecedor. Apesar dos desafios, especialmente na **abstração de informações sobre árvores** e na **compreensão do impacto dos índices na aplicação**, a experiência foi extremamente valiosa. Saio deste projeto com uma visão mais clara sobre estrutura de dados, organização de sistemas e trabalho em equipe.
