# Apêndice

## Gustavo Henrique Rodrigues de Castro

[<img src = "https://img.shields.io/badge/github-black.svg?&style=for-the-badge&logo=github&logoColor=white">](https://github.com/GhrCastro)

Ao começar o trabalho, a primeira coisa que foi criada por mim foi um servidor do discord com canais destinados à divisão de tarefas e postagem do enunciado, links do git e códigos a serem compartilhados entre nós:

<br>

 #### Canal de enunciados
> ![Captura de tela 2025-04-07 194159](https://github.com/user-attachments/assets/b11e5db5-9bb3-44b4-bbdb-c3d38b1f8831)

<br>

 #### Divisão das tarefas
> O tp foi quebrado em 12 'tasks' menores, a fim de garantir uma carga de trabalho justa e igual para os membros, tendo em conta a presença de 4 membros, todos deveriam ter feito, ao final do tp, ao menos 3 tasks.
> 
>![Captura de tela 2025-04-07 194215](https://github.com/user-attachments/assets/af1841f4-d5e6-465f-b5f9-7a3e9ccee68f)
>![Captura de tela 2025-04-07 194231](https://github.com/user-attachments/assets/da631357-8599-4443-9fcf-5778f2a121ae)
> ![image](https://github.com/user-attachments/assets/b0494323-e9c9-46d3-a940-73622f261b53)

<br>

#### Códigos relacionados ao tp
> ![Captura de tela 2025-04-07 194249](https://github.com/user-attachments/assets/3fa77278-62af-4038-9ea7-b7899300d484)

<br>

#### Repositório git
>Por último, a mariana criou o reositório do tp, e juntos como um grupo, definimos um gitFlow a ser seguido, que acabou por sofrer alterações no caminho e terminar com o seguinte fluxo:
> - Cada um tem sua branch fixa, com a seguinte estrutura de nome <GitHub-userName>-dev, onde deveríamos trabalhar em separado, cada um em sua respectiva task, após isso, era determinante que o código fosse postado desta branch para a Development(branch destinada ao código funcional e completo até o momento, mas não finalizado 100%) todos os dias após as modificações (caso estivesse funcionando). Assim, Todos poderíamos puxar as mudanças do colega de equipe para a nossa própria branch no dia seguinte sem problemas, e ao final de cada tp, todo o código, estando 100% funcional e testado, seria postado na branch Master, garantindo assim, que a master esteja sempre "1 tp atrasado" e garantindo que nunca tenhamos perdas de código irreversíveis.
>   
>![Captura de tela 2025-04-07 195515](https://github.com/user-attachments/assets/f14d898f-e67d-43a5-8271-a917f45fe40f)

Após as organizações iniciais no discord, e a criação de tasks e um gitFlow adequado às tarefas do tp, decidimos postar as tasks aqui no github através de 'issues' onde cada membro do grupo poderia pegá-las e se designar tarefas, assim garantindo que pudéssemos ver o que precisava ser feito, e o que já estava em desenvolvimento.

Eu fiquei inicialmente responsável pelo desenvolvimento pelos cruds e views relacionados à séries, e todas os arquivos relacionados a séries foram inicialmente desenvolvidos nos commits iniciais da minha branch.

Posteriormente ao desenvolverem suas respectivas tasks, o Felipe e a Mari descobriram uma forma melhor de fazer o armazenamento e buscas de séries e fizeram refatorações em alguns destes arquivos no código. Quase na conclusão do tp, eu também implementei a task 'listar episódios por série', onde eu consegui melhorar um pouco o algoritmo de busca de série que havíamos trabalhado. 

> Havia originalmente um problema na primeira versão do algoritmo que buscava as séries: se houvessem mais de uma série com nomes que começavam igual, ou um nome era o complemento do outro como em 'friends' 'friends of tomorrow' ou 'fri' onde  ambos "friends" e "friends2" contém "fri", e "friends of tomorrow" começa igual à "friends", o código inicial retornava apenas o primeiro índice encontrado 'series[0]'. Este problema foi consertado posteriormente pela mari, onde criamos um algoritmo em estilo de menu, onde ao buscar uma série para qualquer operação que fosse, desde busca ou deleção, até listagem de episódios ou alteração, o código agora exibe um menu com todas as séries com o nome semelhante ou contido, e te da a  opção de escolher a correta, antes de prosseguir, mais ou menos assim:
>
> ```java
> -------------------------------
>   Escolha a série:
>   1. Friends
>   2. Friends of tomorrow
>   3. Fri
> -------------------------------
> 1
> ===============================
> Série: Friends
> ```
>
> Ao começar o códgio de listagem de episódios por série, eu utilizei a mesma estrutura, mas notei que não precisávamos exibir o menu de opções, caso houvesse a correspondência da String em apenas um elemento, portanto agora, ao encontrarmos uma única série de nome "friends" ela seria imediatamente identificada como 'series[0]' e utilizada, pois o elemento 0 seria o único em seu nó.
> ```java
>  ===============================
> Série: Friends
> ```


 Para falar a verdade, as maiores dificuldades enfrentadas ao criarmos os códigos do tp, foram entender corretamente quando e para que utilizar HashExtensível, e quando e para que utilizar ÁrvoreB+, mas graças à nossa organização e trabalho em grupo, tudo decorreu bem, e conseguimos solucionar as propostas do tp01 sem maiores problemas.


