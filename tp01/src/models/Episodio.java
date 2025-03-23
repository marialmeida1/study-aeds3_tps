package tp01.src.models;

import java.time.LocalDate;

import tp01.src.storeage.*;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Episodio implements Registro {

    /* Estrutura básica de um episódio, no qual possuí um id estrangeiro da série correspondente */

    public int id;
    public int fkSerie; // Salva o valor para busca futuras
    public String nome;
    public int temporada;
    public LocalDate lancamento;
    public int duracao;

    public Episodio() {
        this(-1, -1, "", -1, LocalDate.now(), -1);
    }

    public Episodio(String n, int t, LocalDate l, int d) {
        this(-1, -1, n, t, l, d);
    }

    public Episodio(int i, int f, String n, int t, LocalDate l, int d) {
        this.id = i;
        this.fkSerie = f;
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

    public void setFkSerie(int fkSerie) {
        this.fkSerie = fkSerie;
    }

    public int getFkSerie() {
        return fkSerie;
    }

    public String toString() {
        return "\nID:.................." + this.id +
                "\nFk Serie:................" + this.fkSerie +
                "\nNome:................" + this.nome +
                "\nTemporada:..........." + this.temporada +
                "\nData Lançamento:....." + this.lancamento +
                "\nDuração:............." + this.duracao;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt(this.fkSerie);
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
        this.fkSerie = dis.readInt();
        this.nome = dis.readUTF();
        this.temporada = dis.readInt();
        this.lancamento = LocalDate.ofEpochDay(dis.readInt());
        this.duracao = dis.readInt();
    }
}
