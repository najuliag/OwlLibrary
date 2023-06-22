package SistemaDeGerenciamento;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class Livro {

    protected String titulo, autor;
    protected String ano;

    public void cadastrarLivro(){

        boolean cadastroLivro = true;
        boolean anoValido = true;

        while (cadastroLivro){
            this.titulo = JOptionPane.showInputDialog(null, "Digite o título do livro: ", "Cadastro de livros", JOptionPane.YES_NO_CANCEL_OPTION);
            if (titulo == null){
                break;
            }else {
                this.autor = JOptionPane.showInputDialog(null, "Digite o autor do livro: ", "Cadastro de livros", JOptionPane.YES_NO_CANCEL_OPTION);
            }
            if (autor == null){
                break;
            }else {
                while (anoValido){
                    this.ano = JOptionPane.showInputDialog(null, "Digite o ano de lançamento: ", "Cadastro de livros", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (ano == null){
                        break;
                    }
                    if (!ano.matches("^[0-9]{4}")){
                        JOptionPane.showMessageDialog(null, "Ano inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }else {
                        anoValido = false;
                    }
                }
            }
            cadastroLivro = false;
        }
    }

    public void salvarLivroEmArquivo() {
        try {
            FileWriter writer = new FileWriter("livros.txt", true);
            writer.write("Obra: " + getTitulo() + "\n");
            writer.write("Autor: " + getAutor() + "\n");
            writer.write("Ano de lançamento: " + getAno() + "\n");
            writer.write("---------------------------------\n");
            writer.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    public Livro(String autor, String ano) {
        this.autor = autor;
        this.ano = ano;
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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

}




