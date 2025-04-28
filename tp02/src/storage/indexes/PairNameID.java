package tp01.src.storage.indexes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;

import tp01.src.storage.records.RegisterTreeB;

/**
 * Classe que representa um par (nome, ID), usada em estruturas como Árvores B+.
 * 
 * O nome é armazenado com tamanho fixo de 30 bytes, truncado se necessário,
 * garantindo compatibilidade com arquivos binários. O ID é um inteiro que serve como identificador único.
 * 
 */
public class PairNameID implements RegisterTreeB<PairNameID> {

  private String nome; /** Nome (chave de busca). Limitado a 30 bytes. */
  private int id; /** ID associado ao nome. */
  private short TAMANHO = 34; /** Tamanho total da estrutura serializada (30 bytes do nome + 4 do ID). */
  private short TAMANHO_NOME = 30; /** Tamanho máximo para o nome em bytes. */

  /**
   * Construtor padrão. Inicializa com nome vazio e ID -1.
   * 
   * @throws Exception Em caso de erro no processamento do nome.
   */
  public PairNameID() throws Exception {
    this("", -1);
  }

  /**
   * Construtor com nome. O ID será -1.
   * 
   * @param n Nome a ser armazenado.
   * @throws Exception Em caso de erro no processamento do nome.
   */
  public PairNameID(String n) throws Exception {
    this(n, -1);
  }

  /**
   * Construtor com nome e ID. Trunca o nome caso exceda o tamanho máximo.
   * 
   * @param t Nome (chave de busca).
   * @param i ID associado.
   * @throws Exception Em caso de erro no processamento do nome.
   */
  public PairNameID(String t, int i) throws Exception {

    if(!t.isEmpty()) {

      // Converte o título para um vetor de bytes
      byte[] vb = t.getBytes(StandardCharsets.UTF_8);

      // Garante que o vetor de bytes tenha no máximo TAMANHO_NOME bytes
      if(vb.length > TAMANHO_NOME) {

        // Cria um vetor do tamanho máximo
        byte[] vb2 = new byte[TAMANHO_NOME];
        System.arraycopy(vb, 0, vb2, 0, vb2.length);

        // Verifica se os últimos bytes estão fora do intervalo de 0 a 127 (o que indicaria que o último caractere é um caractere acentuado)
        int n = TAMANHO_NOME-1;
        while(n>0 && (vb2[n]<0 || vb2[n]>127))
          n--;

        // Cria um novo array de bytes removendo o último byte
        byte[] vb3 = new byte[n+1];
        System.arraycopy(vb2, 0, vb3, 0, vb3.length);
        vb2 = vb3;

        // Cria uma nova string para o título a partir desse vetor de no máximo TAMANHO_NOME bytes
        t = new String(vb2);
      }
    }
    this.nome = t; // ID do Usuário
    this.id = i; // ID da Pergunta
  }
  
  /**
   * Retorna o nome armazenado.
   * 
   * @return Nome.
   */
  public String getNome() {
      return nome;
  }

  /**
   * Retorna o ID armazenado.
   * 
   * @return ID.
   */
  public int getId() {
      return id;
  }

  /**
   * Cria uma cópia do objeto.
   * 
   * @return Novo objeto {@code PairNameID} com os mesmos dados.
   */
  @Override
  public PairNameID clone() {
    try {
      return new PairNameID(this.nome, this.id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retorna o tamanho da estrutura em bytes (nome + id).
   * 
   * @return Tamanho fixo (34 bytes).
   */
  public short size() {
    return this.TAMANHO;
  }

  /**
   * Compara dois objetos {@code PairNameID}, ignorando acentuação e case.
   * Se os nomes forem iguais, compara pelo ID.
   * 
   * @param a Objeto a ser comparado.
   * @return Resultado da comparação (semelhante a {@code compareTo}).
   */
  public int compareTo(PairNameID a) {
    String str1 = transforma(this.nome);
    String str2 = transforma(a.nome);

    // reduz o tamanho da segunda string, se necessário (para facilitar as buscas)
    if(str2.length() > str1.length())
      str2 = str2.substring(0, str1.length());    
    if(str1.compareTo(str2)==0)
      if(this.id == -1)
        return 0;
      else
        return this.id - a.id;
    else
      return str1.compareTo(str2);
  }

  /**
   * Retorna representação textual do objeto.
   * 
   * @return String no formato "nome;id".
   */
  public String toString() {
    return this.nome + ";" + String.format("%-3d", this.id);
  }

  /**
   * Serializa o objeto em um array de bytes.
   * 
   * @return Array de bytes contendo os dados do objeto.
   * @throws IOException Em caso de erro na escrita.
   */
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    byte[] vb = new byte[TAMANHO_NOME];
    byte[] vbNome = this.nome.getBytes();
    int i=0;
    while(i<vbNome.length) {
      vb[i] = vbNome[i];
      i++;
    }
    while(i<TAMANHO_NOME) {
      vb[i] = ' ';
      i++;
    }
    dos.write(vb);
    dos.writeInt(this.id);
    return baos.toByteArray();
  }

  /**
   * Desserializa um array de bytes preenchendo os campos do objeto.
   * 
   * @param ba Array de bytes.
   * @throws IOException Em caso de erro na leitura.
   */
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    byte[] vb = new byte[TAMANHO_NOME];
    dis.read(vb);
    this.nome = (new String(vb)).trim();
    this.id = dis.readInt();
  }

  /**
   * Remove acentos e converte a string para minúsculas para facilitar comparações.
   * 
   * @param str String a ser transformada.
   * @return String normalizada (sem acentos e em minúsculas).
   */
  public static String transforma(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
  }

}