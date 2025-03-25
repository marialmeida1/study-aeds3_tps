package tp01.src.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParStringID implements RegistroHashExtensivel<ParStringID> {

    private String chave; // String key (e.g., 'nome')
    private int id;       // Integer value (e.g., 'id')
    private final short TAMANHO = 64; // Fixed size in bytes (e.g., 60 bytes for the string + 4 bytes for the int)

    public ParStringID() {
        this.chave = ""; // Default empty string
        this.id = -1;    // Default invalid ID
    }

    public ParStringID(String chave, int id) throws Exception {
        if (chave.length() > 60) {
            throw new IllegalArgumentException("A chave deve ter no máximo 60 caracteres.");
        }
        this.chave = String.format("%-60s", chave); // Pad or truncate to 60 characters
        this.id = id;
    }

    public String getChave() {
        return chave.trim(); // Return trimmed string
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return hash(this.chave.trim());
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.chave.trim() + ";" + this.id + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeBytes(this.chave); // Write the fixed-size string
        dos.writeInt(this.id);      // Write the integer ID
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] chaveBytes = new byte[60]; // Fixed size for the string
        dis.read(chaveBytes);
        this.chave = new String(chaveBytes).trim(); // Convert bytes to string and trim
        this.id = dis.readInt(); // Read the integer ID
    }

    public static int hash(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash = 31 * hash + chave.charAt(i); // Simple hash function
        }
        return Math.abs(hash); // Ensure positive hash value
    }
}
