package modelo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import aeds3.*;

public class ParISBNID implements RegistroHashExtensivel {
    
    private String isbn;           // chave
    private int    id;             // valor
    private final short TAMANHO = 17;  // tamanho em bytes

    public ParISBNID() throws Exception {
        this.isbn = "";
        this.id = -1;
    }

    public ParISBNID(String isbn, int id) throws Exception {
        if(isbn.length()==0 || isbn.length()==13) {
            this.isbn = isbn;
            this.id = id;
        } else
            throw new Exception("ISBN inválido");
    }

    public String getIsbn() {
        return isbn;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Math.abs(this.isbn.hashCode());
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.isbn + ";" + this.id+")";
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        if(isbn.length()!=13)
            throw new Exception("ISBN inválido");
        byte[] vb = isbn.getBytes();
        dos.write(vb);
        dos.writeInt(id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] vb = new byte[13];
        dis.read(vb);
        isbn = new String(vb);
        id = dis.readInt();
    }

    public static int hash(String isbn) {
        return Math.abs(isbn.hashCode());
    }

}
