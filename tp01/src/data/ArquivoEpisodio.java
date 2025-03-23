package tp01.src.data;

import tp01.src.models.*;
import tp01.src.util.*;
import tp01.src.storeage.*;

public class ArquivoEpisodio extends Arquivo<Episodio> {

    Arquivo<Episodio> arqEpisodio;
    HashExtensivel<ParCPFID> indiceIndiretoCPF;

    public ArquivoEpisodio() throws Exception {
        super("episodio", Episodio.class.getConstructor()); // Cria um episódio básico

        indiceIndiretoCPF = new HashExtensivel<>(
                ParCPFID.class.getConstructor(),
                4,
                "tp01/files/episodio/indiceCPF.d.db", 
                "tp01/files/episodio/indiceCPF.c.db"
        );
    }

    @Override
    public int create(Episodio c) throws Exception {
        int id = super.create(c);
        indiceIndiretoCPF.create(new ParCPFID(c.getCpf(), id));
        return id;
    }

    public Episodio read(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if (pci == null)
            return null;
        return read(pci.getId());
    }

    public boolean delete(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if (pci != null)
            if (delete(pci.getId()))
                return indiceIndiretoCPF.delete(ParCPFID.hash(cpf));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio c = super.read(id);
        if (c != null) {
            if (super.delete(id))
                return indiceIndiretoCPF.delete(ParCPFID.hash(c.getCpf()));
        }
        return false;
    }

    @Override
    public boolean update(Episodio novoEpisodio) throws Exception {
        Episodio clienteVelho = read(novoEpisodio.getCpf());
        if (super.update(novoEpisodio)) {
            if (novoEpisodio.getCpf().compareTo(clienteVelho.getCpf()) != 0) {
                indiceIndiretoCPF.delete(ParCPFID.hash(clienteVelho.getCpf()));
                indiceIndiretoCPF.create(new ParCPFID(novoEpisodio.getCpf(), novoEpisodio.getId()));
            }
            return true;
        }
        return false;
    }
}
