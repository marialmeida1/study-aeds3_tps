package tp02.src.storage.indexes;

import java.io.*;

import tp02.src.storage.records.RegisterTreeB;

public class PairIDFK implements RegisterTreeB<PairIDFK> {

  private int fk; // Chave estrangeira — identificador da entidade relacionada
  private int id; // Chave primária — identificador da entidade principal
  private short TAMANHO = 8;

  public PairIDFK() {
    this(-1, -1);
  }

  public PairIDFK(int fk) {
    this(fk, -1);
  }

  public PairIDFK(int fk, int id) {
    this.fk = fk;
    this.id = id;
  }

  public int getFk() {
    return fk;
  }

  public int getId() {
    return id;
  }

  @Override
  public PairIDFK clone() {
    return new PairIDFK(this.fk, this.id);
  }

  public short size() {
    return this.TAMANHO;
  }

  @Override
  public int compareTo(PairIDFK a) {
    System.out.println("FK (chave estrangeira): " + this.fk);
    System.out.println("ID (chave primária): " + this.id);

    if (this.id == -1) {
      return this.fk - a.fk;
    }

    if (this.fk != a.fk) {
      return this.fk - a.fk;
    }

    return this.id == -1 ? 0 : this.id - a.id;
  }

  public String toString() {
    return String.format("%3d", this.fk) + ";" + String.format("%-3d", this.id);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.fk);
    dos.writeInt(this.id);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.fk = dis.readInt();
    this.id = dis.readInt();
  }
}
