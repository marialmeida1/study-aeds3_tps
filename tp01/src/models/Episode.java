package tp01.src.models;

import java.time.LocalDate;

import tp01.src.storage.records.Register;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Episode implements Register {

    /* Estrutura básica de um episódio, no qual possuí um id estrangeiro da série correspondente */

    private int id;
    private int fkSerie; // Salva o valor para buscas futuras
    private String name;
    private int season;
    private LocalDate release;
    private int duration;

    public Episode() {
        this(-1, -1, "", -1, LocalDate.now(), -1);
    }

    public Episode(int f, String n, int t, LocalDate l, int d) {
        this(-1, f, n, t, l, d);
    }

    public Episode(int i, int f, String n, int t, LocalDate l, int d) {
        this.id = i;
        this.fkSerie = f;
        this.name = n;
        this.season = t;
        this.release = l;
        this.duration = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkSerie() {
        return fkSerie;
    }

    public void setFkSerie(int fkSerie) {
        this.fkSerie = fkSerie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "\nID:.................." + this.id +
                "\nFk Serie:............" + this.fkSerie +
                "\nNome:................" + this.name +
                "\nTemporada:..........." + this.season +
                "\nData Lançamento:....." + this.release +
                "\nDuração:............." + this.duration;
    }

    @Override
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

    @Override
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
