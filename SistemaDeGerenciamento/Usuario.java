package SistemaDeGerenciamento;

import javax.swing.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;

public class Usuario {
    private String nome, email, cpf;
    Pattern patternEmail = Pattern.compile("^[^\\s][a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{1,}$");

    public void cadastro() {
        boolean cadastro = true;
        while (cadastro) {

            boolean nomeValido = true;
            while (nomeValido) {
                nome = JOptionPane.showInputDialog(null, "Digite seu nome:", "Cadastro", JOptionPane.YES_NO_CANCEL_OPTION);
                if (nome == null) {
                    break;
                }
                if (!nome.matches("^[^\\s][a-zA-ZÀ-ÿ\\s]{2,}+$")) {
                    JOptionPane.showMessageDialog(null, "O nome deve conter apenas letras.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else {
                    break;
                }
            }
            if (nome == null){
                break;
            }

            boolean emailValido = true;
            while (emailValido) {
                email = JOptionPane.showInputDialog(null,
                        "Digite seu email:", "Cadastro", JOptionPane.YES_NO_CANCEL_OPTION);
                if (email == null) {
                    break;
                }
                if (!patternEmail.matcher(email).matches()) {
                    showMessageDialog(null, "Email inválido.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else {
                    break;
                }
            }
            if (email == null){
                break;
            }

            boolean cpfValido = true;
            while (cpfValido) {
                cpf = JOptionPane.showInputDialog(null,
                        "Digite o cpf do usuário", "Cadastro", JOptionPane.YES_NO_CANCEL_OPTION);
                if (cpf == null) {
                    break;
                }
                if (!cpf.matches("\\d{11}")) {
                    showMessageDialog(null, "O CPF precisa conter 11 dígitos e apenas números.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else {
                    break;
                }
            }
            cadastro = false;
        }
    }

    public void salvarCadastroEmArquivo() {
        try {
                FileWriter writer = new FileWriter("usuarios.txt", true);
                writer.write("CPF: " + getCpf() + "\n");
                writer.write("Nome: " + getNome() + "\n");
                writer.write("Email: " + getEmail() + "\n");
                writer.write("---------------------------------\n");
                writer.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o cadastro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
