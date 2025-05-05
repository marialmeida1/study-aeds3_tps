# Documentação das Alterações Realizadas

## Objetivo
Corrigir problemas relacionados à manipulação de relações entre atores e séries no sistema PUCFlix, garantindo que as relações sejam criadas, armazenadas e recuperadas corretamente.

---

## Alterações Realizadas

### 1. **Correção na Criação de Relações**
- **Arquivo:** `src\data\ArchiveRelationNN.java`
- **Descrição:** 
  - Adicionada validação para evitar a criação de relações duplicadas entre atores e séries.
  - Adicionados logs de depuração para verificar os dados armazenados nos índices `actorSerie` e `serieActor`.
  - Garantido que apenas relações válidas (com IDs positivos) sejam criadas.
- **Motivo:** Resolver o problema de relações duplicadas ou incorretas sendo criadas.

---

### 2. **Correção na Inclusão de Séries**
- **Arquivo:** `src\controller\ControllerSeries.java`
- **Descrição:**
  - Removida lógica não intencional que criava automaticamente uma relação entre a série recém-criada e o ator com ID `1`.
  - Garantido que nenhuma relação seja criada durante a inclusão de uma nova série.
- **Motivo:** Evitar que séries sejam automaticamente vinculadas a atores sem a ação explícita do usuário.

---

### 3. **Correção na Listagem de Relações**
- **Arquivo:** `src\data\ArchiveRelationNN.java`
- **Descrição:**
  - Adicionados logs de depuração para verificar as relações recuperadas pelos métodos `readActorsBySerie` e `readSeriesByActor`.
  - Garantido que apenas as relações corretas sejam retornadas.
- **Motivo:** Identificar e corrigir problemas relacionados à recuperação de relações incorretas.

---

### 4. **Correção na Vinculação de Atores a Séries**
- **Arquivo:** `src\data\ArchiveRelationNN.java`
- **Descrição:**
  - Adicionada verificação para evitar a criação de relações duplicadas ao vincular um ator a uma série.
- **Motivo:** Garantir que cada ator esteja vinculado a uma série apenas uma vez.

---

## Testes Realizados
1. **Criação de Séries:**
   - Verificado que nenhuma relação é criada automaticamente ao incluir uma nova série.
2. **Vinculação de Atores a Séries:**
   - Testado o vínculo de atores a séries e confirmado que apenas as relações corretas são criadas.
3. **Listagem de Relações:**
   - Confirmado que as relações exibidas correspondem às ações realizadas pelo usuário.
4. **Depuração:**
   - Logs de depuração revisados para garantir que os dados armazenados e recuperados estão corretos.

---

## Próximos Passos
- Revisar a implementação da classe `ArchiveTreeB` para garantir que os métodos `create` e `read` estão manipulando os dados corretamente.
- Adicionar mais testes automatizados para validar cenários complexos de manipulação de relações.

---

## Conclusão
As alterações realizadas corrigiram os problemas identificados relacionados à criação e recuperação de relações entre atores e séries. O sistema agora está mais robusto e confiável.

