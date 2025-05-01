package tp02.src.models;

import java.io.*;
import tp02.src.storage.records.Register;

/**
 * Representa uma série de TV ou streaming com informações como nome,
 * sinopse, ano de lançamento e plataforma de streaming.
 * 
 * Implementa a interface {@link Register} para permitir a serialização e
 * desserialização em formato binário.
 */
public class Series implements Register {
    private int id;
    private String name;
    private String synopsis;
    private short releaseYear; // Apenas o ano de lançamento
    private String streaming;

    /**
     * Construtor padrão. Inicializa os campos com valores padrão.
     */
    public Series() {
        this(-1, "", "", (short) 0, "");
    }

    /**
     * Construtor sem ID, utilizado para inserções iniciais.
     *
     * @param name Nome da série.
     * @param synopsis Sinopse da série.
     * @param releaseYear Ano de lançamento.
     * @param streaming Plataforma de streaming onde a série está disponível.
     */
    public Series(String name, String synopsis, short releaseYear, String streaming) {
        this(-1, name, synopsis, releaseYear, streaming);
    }

    /**
     * Construtor completo da classe.
     *
     * @param id Identificador único da série.
     * @param name Nome da série.
     * @param synopsis Sinopse da série.
     * @param releaseYear Ano de lançamento.
     * @param streaming Plataforma de streaming.
     */
    public Series(int id, String name, String synopsis, short releaseYear, String streaming) {
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
        this.releaseYear = releaseYear;
        this.streaming = streaming;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public short getReleaseYear() { return releaseYear; }
    public void setReleaseYear(short releaseYear) { this.releaseYear = releaseYear; }

    public String getStreaming() { return streaming; }
    public void setStreaming(String streaming) { this.streaming = streaming; }

    /**
     * Retorna uma representação textual da série.
     *
     * @return String contendo os dados da série.
     */
    @Override
    public String toString() {
        return "\nID...........................: " + this.id +
               "\nname.........................: " + this.name +
               "\nAno de Lançamento............: " + this.releaseYear +
               "\nsynopsis......................: " + this.synopsis +
               "\nStreaming....................: " + this.streaming;
    }

    /**
     * Serializa a série para um array de bytes.
     *
     * @return Array de bytes representando a série.
     * @throws IOException Se ocorrer erro na escrita.
     */
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.name);
        dos.writeUTF(this.synopsis);
        dos.writeShort(this.releaseYear);
        dos.writeUTF(this.streaming);
        return baos.toByteArray();
    }

    /**
     * Desserializa os dados da série a partir de um array de bytes.
     *
     * @param b Array de bytes com os dados serializados.
     * @throws IOException Se ocorrer erro na leitura.
     */
    @Override
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        
        this.id = dis.readInt();
        this.name = dis.readUTF();
        this.synopsis = dis.readUTF();
        this.releaseYear = dis.readShort();
        this.streaming = dis.readUTF();
    }
}
