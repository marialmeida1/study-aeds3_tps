/*
 * Esta classe representa um objeto para uma entidade
 * que será armazenado em uma árvore B+.
 * 
 * Neste caso em particular, este objeto é representado
 * por dois números inteiros para que possa conter
 * relacionamentos entre dois IDs de entidades quaisquer.
 *
 * Implementado pelo Prof. Marcos Kutova
 * v1.0 - 2021
 */
package tp02.src.storage.indexes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tp02.src.storage.records.RegisterTreeB;

/**
 * Classe que representa um par de IDs para ser armazenado em uma estrutura de
 * árvore B+.
 * Usado para representar relacionamentos entre registros de duas entidades.
 */
public class PairIDFK implements RegisterTreeB<PairIDFK> {

  private int id; /** ID da entidade principal (chave primária). */
  private int fk; /** ID da entidade relacionada (chave estrangeira). */
  private short TAMANHO = 8; /** Tamanho fixo da estrutura serializada (em bytes). */

  /**
   * Construtor padrão. Inicializa ambos os campos com -1.
   */
  public PairIDFK() {
    this(-1, -1);
  }

  /**
   * Construtor que inicializa apenas a chave estrangeira (FK), com ID -1.
   *
   * @param n1 Valor da chave estrangeira.
   */
  public PairIDFK(int n1) {
    this(-1, n1);
  }

  /**
   * Construtor que inicializa os dois campos: ID e FK.
   *
   * @param n1 ID da entidade principal.
   * @param n2 ID da entidade relacionada (FK).
   */
  public PairIDFK(int n1, int n2) {
    try {
      this.id = n1; // ID da tabela principal (N)
      this.fk = n2; // ID da tabela secundária (1)
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  /**
   * Retorna o ID da entidade principal.
   *
   * @return ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Retorna a chave estrangeira (FK).
   *
   * @return FK.
   */
  public int getFk() {
    return fk;
  }

  /**
   * Cria e retorna uma cópia deste objeto.
   *
   * @return Clone de PairIDFK.
   */
  @Override
  public PairIDFK clone() {
    return new PairIDFK(this.id, this.fk);
  }

  /**
   * Retorna o tamanho da estrutura em bytes.
   *
   * @return Tamanho em bytes (8).
   */
  public short size() {
    return this.TAMANHO;
  }

  /**
   * Compara dois objetos {@code PairIDFK} com base em seus IDs e FKs.
   * A comparação dá prioridade ao ID, e em caso de empate, à FK.
   *
   * @param a Outro objeto PairIDFK.
   * @return Valor negativo, zero ou positivo conforme a ordenação.
   */
  public int compareTo(PairIDFK a) {
    if (this.id == -1) {
        return this.fk - a.fk;
    }
    if (this.id != a.id) {
        return this.id - a.id;
    }
    return this.fk == -1 ? 0 : this.fk - a.fk;
  }

  /**
   * Retorna uma representação em string do par ID;FK.
   *
   * @return String formatada com os dois valores.
   */
  public String toString() {
    return String.format("%3d", this.id) + ";" + String.format("%-3d", this.fk);
  }

  /**
   * Serializa o objeto em um array de bytes.
   *
   * @return Array de bytes com os dados serializados.
   * @throws IOException Se ocorrer erro na escrita.
   */
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.id);
    dos.writeInt(this.fk);
    return baos.toByteArray();
  }

  /**
   * Desserializa os dados de um array de bytes para preencher o objeto.
   *
   * @param ba Array de bytes com os dados serializados.
   * @throws IOException Se ocorrer erro na leitura.
   */
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.id = dis.readInt();
    this.fk = dis.readInt();
  }

}