package tp01.src.models;

import java.io.*;

import tp01.src.storage.records.Register;

public class Series implements Register {
    private int id;
    private String name;
    private String synopsis;
    private short releaseYear; // Apenas o ano de lançamento
    private String streaming;

    public Series() {
        this(-1, "", "", (short) 0, "");
    }

    public Series(String name, String synopsis, short releaseYear, String streaming) {
        this(-1, name, synopsis, releaseYear, streaming);
    }

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

    @Override
    public String toString() {
        return "\nID...........................: " + this.id +
               "\nname.........................: " + this.name +
               "\nAno de Lançamento............: " + this.releaseYear +
               "\nsynopsis......................: " + this.synopsis +
               "\nStreaming....................: " + this.streaming;
    }

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
