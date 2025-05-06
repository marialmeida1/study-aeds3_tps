package tp02.src.models;

import tp02.src.storage.records.Register;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Actor implements Register {

    private int id;
    private String name;

    public Actor() {
        this(-1, "");
    }

    public Actor(String n) {
        this.id = -1;     // usa o valor recebido
        this.name = n;   // usa o valor recebido
    } 

    public Actor(int i, String n) {
        this.id = i;     // usa o valor recebido
        this.name = n;   // usa o valor recebido
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna uma representação textual do episódio.
     * 
     * @return String formatada com os dados do episódio.
     */
    @Override
    public String toString() {
        return "\nNome...... " + this.name +
                "\nID......... " + this.id;
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
        dos.writeUTF(this.name);
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
        this.name = dis.readUTF();
    }
}
