package tp01.src.models;

import java.time.LocalDate;

import tp01.src.storage.records.Register;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Representa um episódio de uma série, contendo informações como nome, temporada,
 * data de lançamento, duração e chave estrangeira para a série associada.
 * 
 * Implementa a interface {@link Register} para permitir serialização binária.
 */
public class Episode implements Register {

    private int id;
    private int fkSerie; // Chave estrangeira que relaciona o episódio a uma série
    private String name;
    private int season;
    private LocalDate release;
    private int duration;

    /**
     * Construtor padrão. Inicializa os campos com valores padrão.
     */
    public Episode() {
        this(-1, -1, "", -1, LocalDate.now(), -1);
    }

    /**
     * Construtor utilizado para criar um episódio sem ID.
     * 
     * @param f ID da série (chave estrangeira).
     * @param n Nome do episódio.
     * @param t Temporada do episódio.
     * @param l Data de lançamento.
     * @param d Duração em minutos.
     */
    public Episode(int f, String n, int t, LocalDate l, int d) {
        this(-1, f, n, t, l, d);
    }

    /**
     * Construtor completo utilizado para instanciar todos os campos.
     * 
     * @param i ID do episódio.
     * @param f ID da série (chave estrangeira).
     * @param n Nome do episódio.
     * @param t Temporada do episódio.
     * @param l Data de lançamento.
     * @param d Duração em minutos.
     */
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

    /**
     * Retorna uma representação textual do episódio.
     * 
     * @return String formatada com os dados do episódio.
     */
    @Override
    public String toString() {
        return "\nID:.................." + this.id +
                "\nFk Serie:............" + this.fkSerie +
                "\nNome:................" + this.name +
                "\nTemporada:..........." + this.season +
                "\nData Lançamento:....." + this.release +
                "\nDuração:............." + this.duration;
    }

    /**
     * Serializa o episódio em um vetor de bytes.
     * 
     * @return Array de bytes representando o episódio.
     * @throws IOException Se ocorrer erro durante a escrita.
     */
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

    /**
     * Desserializa os dados de um vetor de bytes e os atribui ao episódio atual.
     * 
     * @param b Array de bytes contendo os dados do episódio.
     * @throws IOException Se ocorrer erro durante a leitura.
     */
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
