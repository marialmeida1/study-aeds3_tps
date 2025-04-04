package tp01.src.models;
import java.io.IOException;

import tp01.src.storeage.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Serie implements Registro {

    public int id;
    public String nome;
    public String sinopse;
    public int episodes;      // This variable serves merely as a counter to how many episodes each series got, as a reference, so it should be an int
    public short releaseYear; // This variable only contains the year, so it should be a short, since it goes from 0 to 32767 and it only needs 16 bits, not 32 like an int
    public String streaming;
    public int idCategoria;   // This variable is the id of the category the series belongs to

    public Serie() {
        this(-1, "", "", 0, (short) 0, "", -1);
    }

    public Serie(String n, String s, int e, short d, String st, int idCat) {
        this(-1, n, s, e, d, st, idCat);
    }

    public Serie(int i, String n, String s, int e, short d, String st, int idCat) {
        this.id = i;
        this.nome = n;
        this.sinopse = s;
        this.episodes = e;
        this.releaseYear = d;
        this.streaming = st;
        this.idCategoria = idCat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getStreaming() {
        return streaming;
    }

    public int getEpisodes() {
        return episodes;
    }

    public short getReleaseYear() {
        return releaseYear;
    }

    public String toString() {
        return "\nID...........................: " + this.id +
               "\nNome.........................: " + this.nome +
               "\nAno de Lançamento............: " + this.releaseYear +
               "\nSinopse......................: " + this.sinopse +
               "\nStreaming....................: " + this.streaming + 
               "\nQuantidade de Episódios......: " + this.episodes ;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.sinopse);
        dos.writeInt(this.episodes);
        dos.writeShort(this.releaseYear); // Changed to writeShort
        dos.writeUTF(this.streaming);
        dos.writeInt(this.idCategoria);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.sinopse = dis.readUTF();
        this.episodes = dis.readInt();
        this.releaseYear = dis.readShort(); // Changed to readShort
        this.streaming = dis.readUTF();
        this.idCategoria = dis.readInt();
    }
}
