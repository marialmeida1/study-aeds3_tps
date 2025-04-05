package tp01.src.storeage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ParStringID implements RegistroArvoreBMais<ParStringID> {

    private String chave;
    private int id;
    private static final short TAMANHO = 64;
    private static final short TAMANHO_CHAVE = 60;

    public ParStringID() {
        this.chave = "";
        this.id = -1;
    }

    public ParStringID(String chave) {
        this(chave, -1);
    }

    public ParStringID(String t, int i) {
        this.chave = ajustarTamanhoChave(t);
        this.id = i;
    }

    private String ajustarTamanhoChave(String t) {
        if (t == null || t.isEmpty()) return "";
        byte[] vb = t.getBytes(StandardCharsets.UTF_8);
        if (vb.length > TAMANHO_CHAVE) {
            byte[] vb2 = new byte[TAMANHO_CHAVE];
            System.arraycopy(vb, 0, vb2, 0, TAMANHO_CHAVE);
            t = new String(vb2, StandardCharsets.UTF_8).trim();
        }
        return t;
    }

    public String getChave() {
        return chave;
    }

    public int getId() {
        return id;
    }

    @Override
    public ParStringID clone() {
        return new ParStringID(this.chave, this.id);
    }

    public short size() {
        return TAMANHO;
    }

    public int compareTo(ParStringID a) {
        String str1 = transforma(this.chave);
        String str2 = transforma(a.chave);
        if (str2.length() > str1.length()) {
            str2 = str2.substring(0, str1.length());
        }
        int cmp = str1.compareTo(str2);
        return (cmp == 0) ? Integer.compare(this.id, a.id) : cmp;
    }

    @Override
    public String toString() {
        return String.format("%-60s", this.chave) + ";" + String.format("%-3d", this.id);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        byte[] chaveBytes = new byte[TAMANHO_CHAVE];
        byte[] originalBytes = this.chave.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(originalBytes, 0, chaveBytes, 0, Math.min(originalBytes.length, TAMANHO_CHAVE));
        dos.write(chaveBytes);
        dos.writeInt(this.id);
        dos.flush();
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] chaveBytes = new byte[TAMANHO_CHAVE];
        dis.readFully(chaveBytes);
        this.chave = new String(chaveBytes, StandardCharsets.UTF_8).trim();
        this.id = dis.readInt();
    }

    public static String transforma(String str) {
        if (str == null) return "";
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("" ).toLowerCase();
    }
}
