package modelo;

/*
Esta classe representa um objeto para uma entidade
que será armazenado em uma árvore B+

Neste caso em particular, este objeto é representado
por uma string e um inteiro para que possa ser usado
como índice indireto de titulos para uma entidade qualquer.

Implementado pelo Prof. Marcos Kutova
v1.0 - 2024
*/

import aeds3.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ParTituloId implements RegistroArvoreBMais<ParTituloId> {

  private String titulo;
  private int id;
  private short TAMANHO = 34;
  private short TAMANHO_TITULO = 30;

  public ParTituloId() throws Exception {
    this("", -1);
  }

  public ParTituloId(String n) throws Exception {
    this(n, -1);
  }

  public ParTituloId(String t, int i) throws Exception {

    if(!t.isEmpty()) {

      // Converte o título para um vetor de bytes
      byte[] vb = t.getBytes(StandardCharsets.UTF_8);

      // Garante que o vetor de bytes tenha no máximo TAMANHO_TITULO bytes
      if(vb.length > TAMANHO_TITULO) {

        // Cria um vetor do tamanho máximo
        byte[] vb2 = new byte[TAMANHO_TITULO];
        System.arraycopy(vb, 0, vb2, 0, vb2.length);

        // Verifica se os últimos bytes estão fora do intervalo de 0 a 127 (o que indicaria que o último caractere é um caractere acentuado)
        int n = TAMANHO_TITULO-1;
        while(n>0 && (vb2[n]<0 || vb2[n]>127))
          n--;

        // Cria um novo array de bytes removendo o último byte
        byte[] vb3 = new byte[n+1];
        System.arraycopy(vb2, 0, vb3, 0, vb3.length);
        vb2 = vb3;

        // Cria uma nova string para o título a partir desse vetor de no máximo TAMANHO_TITULO bytes
        t = new String(vb2);
      }
    }
    this.titulo = t; // ID do Usuário
    this.id = i; // ID da Pergunta
  }
  
  public String getTitulo() {
      return titulo;
  }

  public int getId() {
      return id;
  }

  @Override
  public ParTituloId clone() {
    try {
      return new ParTituloId(this.titulo, this.id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParTituloId a) {
    String str1 = transforma(this.titulo);
    String str2 = transforma(a.titulo);

    // reduz o tamanho da segunda string, se necessário (para facilitar as buscas)
    if(str2.length() > str1.length())
      str2 = str2.substring(0, str1.length());    
    if(str1.compareTo(str2)==0)
      if(this.id == -1)
        return 0;
      else
        return this.id - a.id;
    else
      return str1.compareTo(str2);
  }

  public String toString() {
    return this.titulo + ";" + String.format("%-3d", this.id);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    byte[] vb = new byte[TAMANHO_TITULO];
    byte[] vbTitulo = this.titulo.getBytes();
    int i=0;
    while(i<vbTitulo.length) {
      vb[i] = vbTitulo[i];
      i++;
    }
    while(i<TAMANHO_TITULO) {
      vb[i] = ' ';
      i++;
    }
    dos.write(vb);
    dos.writeInt(this.id);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    byte[] vb = new byte[TAMANHO_TITULO];
    dis.read(vb);
    this.titulo = (new String(vb)).trim();
    this.id = dis.readInt();
  }

  public static String transforma(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
  }

}