package tp01.src.models;

import java.io.*;
import tp01.src.storeage.*;

public class Serie implements Registro {
    private int id;
    private String name;
    private String synopsis;
    private int episodes;      // Contador de episódios
    private short releaseYear; // Apenas o ano de lançamento
    private String streaming;

    public Serie() {
        this(-1, "", "", 0, (short) 0, "");
    }

    public Serie(String name, String synopsis, int episodes, short releaseYear, String streaming) {
        this(-1, name, synopsis, episodes, releaseYear, streaming);
    }

    public Serie(int id, String name, String synopsis, int episodes, short releaseYear, String streaming) {
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
        this.episodes = episodes;
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

    public int getEpisodes() { return episodes; }
    public void setEpisodes(int episodes) { this.episodes = episodes; }

    public short getReleaseYear() { return releaseYear; }
    public void setReleaseYear(short releaseYear) { this.releaseYear = releaseYear; }

    public String getStreaming() { return streaming; }
    public void setStreaming(String streaming) { this.streaming = streaming; }

    @Override
    public String toString() {
        return "\nID...........................: " + this.id +
               "\nname.........................: " + this.name +
               "\nAno de Lançamento............: " + this.releaseYear +
               "\nsynopsis......................: " + this.synopsis +
               "\nStreaming....................: " + this.streaming + 
               "\nQuantidade de Episódios......: " + this.episodes;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.name);
        dos.writeUTF(this.synopsis);
        dos.writeInt(this.episodes);
        dos.writeShort(this.releaseYear);
        dos.writeUTF(this.streaming);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        
        this.id = dis.readInt();
        this.name = dis.readUTF();
        this.synopsis = dis.readUTF();
        this.episodes = dis.readInt();
        this.releaseYear = dis.readShort();
        this.streaming = dis.readUTF();
    }
}
