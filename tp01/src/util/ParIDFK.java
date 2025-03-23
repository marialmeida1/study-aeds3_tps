
/*
Esta classe representa um objeto para uma entidade
que será armazenado em uma árvore B+

Neste caso em particular, este objeto é representado
por dois números inteiros para que possa conter
relacionamentos entre dois IDs de entidades quaisquer
 
Implementado pelo Prof. Marcos Kutova
v1.0 - 2021
*/
package tp01.src.util;

import tp01.src.storeage.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIDFK implements RegistroArvoreBMais<ParIDFK> {

  private int id;
  private int fk;
  private short TAMANHO = 8;

  public ParIDFK() {
    this(-1, -1);
  }

  public ParIDFK(int n1) {
    this(n1, -1);
  }

  public ParIDFK(int n1, int n2) {
    try {
      this.id = n1; // ID da tabela principal (N)
      this.fk = n2; // ID da tablea secundária (1)
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  public int getId() {
    return id;
  }

  public int getFk() {
    return fk;
  }

  @Override
  public ParIDFK clone() {
    return new ParIDFK(this.id, this.fk);
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParIDFK a) {
    if (this.id != a.id)
      return this.id - a.id;
    else
      // Só compara os valores de Num2, se o Num2 da busca for diferente de -1
      // Isso é necessário para que seja possível a busca de lista
      return this.fk == -1 ? 0 : this.fk - a.fk;
  }

  public String toString() {
    return String.format("%3d", this.id) + ";" + String.format("%-3d", this.fk);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.id);
    dos.writeInt(this.fk);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.id = dis.readInt();
    this.fk = dis.readInt();
  }

}