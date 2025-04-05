package tp01.src.models;

import java.time.LocalDate;

import tp01.src.storeage.records.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Episodio implements Registro {

    /* Estrutura básica de um episódio, no qual possuí um id estrangeiro da série correspondente */

    public int id;
    public int fkSerie; // Salva o valor para busca futuras
    public String name;
    public int season;
    public LocalDate release;
    public int duration;

    public Episodio() {
        this(-1, -1, "", -1, LocalDate.now(), -1);
    }

    public Episodio(int f, String n, int t, LocalDate l, int d) {
        this(-1, f, n, t, l, d);
    }

    public Episodio(int i, int f, String n, int t, LocalDate l, int d) {
        this.id = i;
        this.fkSerie = f;
        this.name = n;
        this.season = t;
        this.release = l;
        this.duration = d;
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
                "\nNome:................" + this.name +
                "\nTemporada:..........." + this.season +
                "\nData Lançamento:....." + this.release +
                "\nDuração:............." + this.duration;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt(this.fkSerie);
        dos.writeUTF(this.name);
        dos.writeInt(this.season);
        dos.writeInt((int) this.release.toEpochDay());
        dos.writeInt(this.duration);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.fkSerie = dis.readInt();
        this.name = dis.readUTF();
        this.season = dis.readInt();
        this.release = LocalDate.ofEpochDay(dis.readInt());
        this.duration = dis.readInt();
    }
}
