package tp01.src.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tp01.src.storeage.*;

public class ParIDFK implements RegistroHashExtensivel<ParIDFK> {

        
    private int id;     // valor FK
    private int fk;     // valor FK
    private final short TAMANHO = 8;  // tamanho em bytes

    public ParIDFK() {
        this.id = -1;
        this.fk = -1;
    }

    public ParIDFK(int id, int fk) throws Exception {
        this.id = id;
        this.fk = fk;
    }

    public int getId() {
        return id;
    }

    public int getFk() {
        return fk;
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.id + ";" + this.fk+")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt(this.fk);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] b = new byte[11];
        dis.read(b);
        this.id = dis.readInt();
        this.fk = dis.readInt();
    }
}
