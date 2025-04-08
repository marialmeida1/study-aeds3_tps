package tp01.src.storage.indexes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tp01.src.storage.records.RegisterExtensibleHash;

/**
 * Representa um par (chave, valor) para uso em hashing extensível,
 * onde a chave é um ID inteiro e o valor é um endereço (posição) do tipo `long`.
 * 
 * Implementa a interface {@link RegisterExtensibleHash} para permitir
 * serialização e suporte à estrutura de hashing extensível.
 */
public class PairIDAddress implements RegisterExtensibleHash<PairIDAddress> {
    
    private int id;              // Chave primária (ID)
    private long endereco;       // Endereço ou posição no arquivo
    private final short TAMANHO = 12;  // Tamanho fixo da estrutura em bytes (4 + 8)

    /**
     * Construtor padrão.
     * Inicializa os campos com valores inválidos (-1).
     */
    public PairIDAddress() {
        this.id = -1;
        this.endereco = -1;
    }

    /**
     * Construtor com valores de ID e endereço.
     *
     * @param id Chave primária.
     * @param end Endereço associado ao ID.
     */
    public PairIDAddress(int id, long end) {
        this.id = id;
        this.endereco = end;
    }

    /**
     * Retorna o ID (chave primária).
     *
     * @return ID do par.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o endereço (valor).
     *
     * @return Endereço associado ao ID.
     */
    public long getEndereco() {
        return endereco;
    }

    /**
     * Retorna o hash code baseado no ID.
     *
     * @return Código de hash.
     */
    @Override
    public int hashCode() {
        return this.id;
    }

    /**
     * Retorna o tamanho da estrutura em bytes.
     *
     * @return Tamanho fixo do registro (12 bytes).
     */
    public short size() {
        return this.TAMANHO;
    }

    /**
     * Retorna uma representação textual do par.
     *
     * @return String no formato "(id;endereco)".
     */
    public String toString() {
        return "("+this.id + ";" + this.endereco+")";
    }

    /**
     * Serializa o par (ID e endereço) para um array de bytes.
     *
     * @return Array de bytes com os dados serializados.
     * @throws IOException Se ocorrer erro na escrita.
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeLong(this.endereco);
        return baos.toByteArray();
    }

    /**
     * Desserializa um array de bytes para preencher os atributos da classe.
     *
     * @param ba Array de bytes contendo os dados serializados.
     * @throws IOException Se ocorrer erro na leitura.
     */
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.endereco = dis.readLong();
    }

}
