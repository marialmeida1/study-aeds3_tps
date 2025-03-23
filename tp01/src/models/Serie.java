package tp01.src.models;
import java.time.LocalDate;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import tp01.src.storeage.*;

public class Serie implements Registro {

    public int id;
    public String nome;
    public String cpf;
    public float salario;
    public LocalDate releaseYear;
    public int idCategoria;

    public Serie() {
        this(-1, "", "", 0F, LocalDate.now());
    }
    public Serie(String n, String c, float s, LocalDate d) {
        this(-1, n, c, s, d);
    }

    public Serie(int i, String n, String c, float s, LocalDate d) {
        this.id = i;
        this.nome = n;
        this.cpf = c;
        this.salario = s;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String toString() {
        return "\nID........: " + this.id +
               "\nNome......: " + this.nome +
               "\nCPF.......: " + this.cpf +
               "\nSalário...: " + this.salario;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.write(this.cpf.getBytes());
        dos.writeFloat(this.salario);
        return baos.toByteArray();
    }


    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        byte[] cpf = new byte[11];
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        dis.read(cpf);
        this.cpf = new String(cpf);
        this.salario = dis.readFloat();
    }
}
