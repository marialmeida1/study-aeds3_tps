package tp01.src.models;
import java.time.LocalDate;

import tp01.src.storage.*;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Episodio implements Registro {

    public int id;
    public String nome;
    public int temporada;
    public LocalDate lancamento;
    public int duracao;

    public Episodio() {
        this(-1, "", -1, LocalDate.now(), -1);
    }
    public Episodio(String n, int t, LocalDate l, int d) {
        this(-1, n, t, l, d);
    }

    public Episodio(int i, String n, int t, LocalDate l, int d) {
        this.id = i;
        this.nome = n;
        this.temporada = t;
        this.lancamento = l;
        this.duracao = d;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "\nID:.................." + this.id +
               "\nNome:................" + this.nome +
               "\nTemporada:..........." + this.temporada +
               "\nData Lançamento:....." + this.lancamento +
               "\nDuração:............." + this.duracao;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt(this.temporada);
        dos.writeInt((int) this.lancamento.toEpochDay());
        dos.writeInt(this.duracao);
        return baos.toByteArray();
    }


    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.temporada = dis.readInt();
        this.lancamento = LocalDate.ofEpochDay(dis.readInt());
        this.duracao = dis.readInt();
    }
}
