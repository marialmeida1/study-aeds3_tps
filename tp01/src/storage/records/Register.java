package tp01.src.storage.records;
import java.io.IOException;

/**
 * Interface que define os métodos básicos para serialização e identificação de registros.
 * 
 * Esta interface é utilizada por classes que representam registros que podem ser salvos em arquivos,
 * permitindo a conversão de objetos para arrays de bytes e vice-versa, além de manipulação de seus identificadores.
 * 
 */
public interface Register {
    public void setId(int i);
    public int getId();
    public byte[] toByteArray() throws IOException;
    public void fromByteArray(byte[] b) throws IOException;
}
