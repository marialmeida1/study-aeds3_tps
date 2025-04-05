package entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

import aeds3.EntidadeArquivo;

public class Livro implements EntidadeArquivo {

    private int id;
    private String isbn;
    private String titulo;
    private String autor;
    private byte edicao;
    private LocalDate dataLancamento;
    private float preco;

    public Livro() throws Exception  {
        this(-1, "", "", "", 0, LocalDate.now(),0F);
    }

    public Livro(String isbn, String titulo, String autor, int edicao, LocalDate dataLancamento, float preco) throws Exception {
        this(-1, isbn, titulo, autor, edicao, dataLancamento, preco);
    }

    public Livro(int id, String isbn, String titulo, String autor, int edicao, LocalDate dataLancamento, float preco) throws Exception {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.edicao = (byte)edicao;
        this.dataLancamento = dataLancamento;
        this.preco = preco;

        if(!this.isbn.equals("") && !isValidISBN13(this.isbn))
            throw new Exception("ISBN inválido");
    } 

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getISBN() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public byte getEdicao() {
        return edicao;
    }

    public void setEdicao(byte edicao) {
        this.edicao = edicao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.write(isbn.getBytes());
        dos.writeUTF(titulo);
        dos.writeUTF(autor);
        dos.writeByte(edicao);
        dos.writeInt((int)dataLancamento.toEpochDay());
        dos.writeFloat(preco);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
        id = dis.readInt();
        byte[] aux = new byte[13];
        dis.read(aux);
        isbn = new String(aux);
        titulo = dis.readUTF();
        autor = dis.readUTF();
        edicao = dis.readByte();
        dataLancamento = LocalDate.ofEpochDay(dis.readInt());
        preco = dis.readFloat();
    }




public static boolean isValidISBN13(String isbn) {
        // Remove qualquer caractere que não seja número
        isbn = isbn.replaceAll("[^0-9]", "");

        // Verifica se tem 13 dígitos
        if (isbn.length() != 13) {
            return false;
        }

        // Cálculo do dígito verificador (algoritmo ISBN-13)
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        int lastDigit = Character.getNumericValue(isbn.charAt(12));

        return checkDigit == lastDigit;
    }    
}