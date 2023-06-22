package SistemaDeGerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.Map;


public class Administrador {
    private Usuario usuario = new Usuario("", "");
    private String login = "adm";
    private String senha = "123";


    public void loginAdm() {
        boolean loginValido = true;

        String acessoLogin = JOptionPane.showInputDialog(null, "Digite o login do administrador: ", "Login Adm", JOptionPane.YES_NO_CANCEL_OPTION);
        String acessoSenha = mascararSenha();

        while (loginValido) {
            if (acessoLogin != null && acessoSenha != null) {
                if (!acessoSenha.equals(this.getSenha()) || !acessoLogin.equals(this.getLogin())) {
                    JOptionPane.showMessageDialog(null, "Login ou senha inválidos. Tente novamente.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    acessoLogin = JOptionPane.showInputDialog(null, "Digite o login do administrador: ", "Login Adm", JOptionPane.YES_NO_CANCEL_OPTION);
                    acessoSenha = mascararSenha();
                    continue;
                } else {
                    break;
                }
            }

        }
    }

    public String mascararSenha() {

        JPasswordField senhaMascarada = new JPasswordField();
        senhaMascarada.setEchoChar('*');

        JLabel jl = new JLabel("Digite a senha do administrador: ");

        Box box = Box.createVerticalBox();
        jl.setBorder(new EmptyBorder(0, 0, 5, 0));
        box.add(jl);
        box.add(senhaMascarada);

        Icon icon = UIManager.getIcon("OptionPane.informationIcon");

        int x = JOptionPane.showConfirmDialog(null, box, "Login Adm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

        if (x == JOptionPane.OK_OPTION) {
            return senhaMascarada.getText();
        }
        return null;

    }


    //MÉTODOS DO USUÁRIO

    Map<String, Usuario> usuarios = new HashMap<>();

    public void adicionarUsuario() {
        new Usuario(usuario.getNome(), usuario.getEmail());
        usuario.cadastro();
        usuario.salvarCadastroEmArquivo();
        if (usuario.getNome() != null && usuario.getEmail() != null && usuario.getCpf() != null) {
            usuarios.put(usuario.getCpf(), new Usuario(usuario.getNome(), usuario.getEmail()));
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Cadastro", JOptionPane.YES_NO_CANCEL_OPTION);
        }
        usuario = new Usuario("", "");

    }

    public void removerUsuario() {
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário foi cadastrado", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String cpf = JOptionPane.showInputDialog(null, "Qual o cpf que você deseja remover?", "Remover", JOptionPane.YES_NO_CANCEL_OPTION);

            if (cpf != null) {
                if (usuarios.containsKey(cpf)) {
                    usuarios.remove(cpf);
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso.", "Remover", JOptionPane.OK_OPTION);
                } else {
                    JOptionPane.showMessageDialog(null, "O usuário não consta no sistema.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void listarUsuarios() {
        try {
            FileReader arq = new FileReader("usuarios.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null) {
                System.out.printf("%s\n", linha);
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        if ("usuarios.txt" == null) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void procurarUsuario() {
        String cpfProcurado = JOptionPane.showInputDialog(null, "Digite o cpf do usuário que você deseja encontrar: ",
                "Procurar usuário", JOptionPane.YES_NO_CANCEL_OPTION);

        if (cpfProcurado != null) {
            try {
                FileReader arquivo = new FileReader("usuarios.txt");
                BufferedReader leitor = new BufferedReader(arquivo);

                String dados;
                while ((dados = leitor.readLine()) != null) {

                    if (dados.contains(cpfProcurado)) {
                        String cpf = dados.substring(5).trim();

                        if (cpf.equals(cpfProcurado)) {
                            String nome = leitor.readLine().substring(5).trim();
                            String email = leitor.readLine().substring(7).trim();

                            System.out.println("CPF: " + cpf);
                            System.out.println("Nome: " + nome);
                            System.out.println("Email: " + email);
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Usuário não encontrado.");
                        }
                    }
                }
                leitor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loginUsuario() {
        String loginNome;
        String loginCPF;
        boolean loginValido = false;

        while (!loginValido) {
            loginNome = JOptionPane.showInputDialog(null, "Digite o seu nome: ", "Login Usuário", JOptionPane.YES_NO_CANCEL_OPTION);
            loginCPF = JOptionPane.showInputDialog(null, "Digite o seu CPF: ", "Login Usuário", JOptionPane.YES_NO_CANCEL_OPTION);

            if (loginNome == null || loginCPF == null) {
                return false;
            }

            if (verificarLogin(loginCPF, loginNome)) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Login Usuário", JOptionPane.INFORMATION_MESSAGE);
                loginValido = true;
            } else {
                JOptionPane.showMessageDialog(null, "Nome ou CPF incorretos.", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }

        return loginValido;
    }

    private boolean verificarLogin(String cpf, String nome) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"));
            String linha;
            String cpfArmazenado = null;
            String nomeArmazenado = null;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("CPF:")) {
                    cpfArmazenado = linha.substring(5).trim();
                } else if (linha.startsWith("Nome:")) {
                    nomeArmazenado = linha.substring(6).trim();
                }

                if (cpfArmazenado != null && nomeArmazenado != null) {
                    if (cpfArmazenado.equals(cpf) && nomeArmazenado.equals(nome)) {
                        reader.close();
                        return true;
                    }

                    cpfArmazenado = null;
                    nomeArmazenado = null;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //MÉTODOS DO LIVRO

    private Map<String, Livro> livros = new HashMap<>();
    private Livro livro = new Livro("", "");

    public void cadastrarLivro() {
        new Livro(livro.getAutor(), livro.getAno());
        livro.cadastrarLivro();
        livro.salvarLivroEmArquivo();
        if (livro.getTitulo() != null && livro.getAutor() != null && livro.getAno() != null) {
            livros.put(livro.getTitulo(), new Livro(livro.getAutor(), livro.getAno()));

            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso.", "Cadastro de Livros", JOptionPane.YES_NO_CANCEL_OPTION);
        }
    }


    public void listarLivros() {
        try {
            FileReader arq = new FileReader("livros.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null) {
                System.out.printf("%s\n", linha);
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ("livros.txt" == null) {
            JOptionPane.showMessageDialog(null, "Nenhum livro cadastrado", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void procurarLivro() {
        String livroProcurado = JOptionPane.showInputDialog(null, "Digite o título do livro que você deseja procurar: ",
                "Procurar livro", JOptionPane.YES_NO_CANCEL_OPTION);
        String procurarPorAutor = JOptionPane.showInputDialog(null, "Digite o autor do livro: ",
                "Procurar livro", JOptionPane.YES_NO_CANCEL_OPTION);

        if (livroProcurado != null && procurarPorAutor != null){
            try {
                BufferedReader leitor = new BufferedReader(new FileReader("livros.txt"));

                String dados;
                String livroArmazenado = null;
                String autorArmazenado = null;
                while ((dados = leitor.readLine()) != null) {
                    if (dados.contains(livroProcurado)) {
                        livroArmazenado = dados.substring(5).trim();
                    } else if (dados.contains(procurarPorAutor)) {
                        autorArmazenado = dados.substring(6).trim();
                    }
                    if (livroArmazenado != null && autorArmazenado != null) {
                        if (livroArmazenado.equalsIgnoreCase(livroProcurado) && autorArmazenado.equalsIgnoreCase(procurarPorAutor)) {
                            String ano = leitor.readLine().substring(3).trim();

                            System.out.println("Livro encontrado!");

                            System.out.println("Obra: " + livroArmazenado);
                            System.out.println("Autor: " + autorArmazenado);
                            System.out.println("Ano de lançamento: " + ano);
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Livro não encontrado.");
                        }
                    }
                }
                leitor.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    //MÉTODOS DO EMPRÉSTIMO
    private Map<String, Emprestimo> emprestimos = new HashMap<>();
    private Emprestimo emprestimo = new Emprestimo(livro, usuario);

    public void realizarEmprestimo() {
        String livroEmpres = JOptionPane.showInputDialog(null, "Digite o título do livro que você deseja pegar emprestado:", "Empréstimo", JOptionPane.YES_NO_CANCEL_OPTION);
        String cpfEmpres = JOptionPane.showInputDialog(null, "Digite o seu CPF para confirmar o empréstimo:", "Empréstimo", JOptionPane.YES_NO_CANCEL_OPTION);

        if (livroEmpres != null && cpfEmpres != null) {
            if (livros.containsKey(livroEmpres)) {
                Emprestimo jaEmprestado = emprestimos.get(livroEmpres);

                if (jaEmprestado == null || jaEmprestado.isDisponivel() && usuarios.containsKey(cpfEmpres)){
                    Livro livro = livros.get(livroEmpres);
                    emprestimo.setLivroEmpres(livro);
                    emprestimo.setUsuarioEmpres(usuarios.get(cpfEmpres));

                    livros.remove(livroEmpres);
                    emprestimos.put(livroEmpres, emprestimo);
                    System.out.println("Livro emprestado.");

                    emprestimo.prazoEntrega();
                }

            } else {
                System.out.println("Livro não disponível para empréstimo.");
            }
        }
    }


    public void realizarDevolucao() {
        String tituloLivro = JOptionPane.showInputDialog(null, "Digite o título do livro que está sendo devolvido:", "Devolução", JOptionPane.YES_NO_CANCEL_OPTION);
        //String cpf = JOptionPane.showInputDialog(null, "Digite o seu CPF para confirmar devolução:", "Devolução", JOptionPane.YES_NO_CANCEL_OPTION);

        if (tituloLivro != null){
            if (emprestimos.containsKey(tituloLivro)) {
                    Emprestimo emprestimo = emprestimos.get(tituloLivro);

                    if (!emprestimo.isDisponivel()){
                        Livro livro = emprestimo.getLivroEmpres();
                        emprestimos.remove(tituloLivro);
                        livros.put(tituloLivro, livro);
                        System.out.println("Livro devolvido com sucesso.");
                        emprestimo.setDisponivel(true);
                    }else {
                        System.out.println("O livro já está disponível.");
                    }


            } else {
                System.out.println("Empréstimo não encontrado ou informações inválidas.");
            }
        }
    }

    //RELATÓRIOS

    public void qntdDeLivros(String qntdLivros){
        try {
            FileWriter gravarInfo = new FileWriter(qntdLivros);
            BufferedWriter qntd = new BufferedWriter(gravarInfo);

                qntd.write("Quantidade de livros disponíveis no acervo: " + livros.size());
                qntd.newLine();

            qntd.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void livrosEmprestados(String livrosEmprestados){
        try {
            FileWriter arq = new FileWriter(livrosEmprestados, true);
            BufferedWriter livro = new BufferedWriter(arq);

            if (!emprestimos.isEmpty()) {
                livro.write("Livros que estão sendo emprestados: ");
                livro.newLine();

                for (Map.Entry<String, Emprestimo> emprestimoRealizados : emprestimos.entrySet()) {
                    String tituloLivro = emprestimoRealizados.getKey();
                    Emprestimo emprestimo = emprestimoRealizados.getValue();

                    String usuario = emprestimo.getUsuarioEmpres().getNome();

                    livro.write("Título: " + tituloLivro);
                    livro.newLine();
                    livro.write("Nome do usuário: " + usuario);
                    livro.newLine();
                }
            }else {
                livro.write("Nenhum livro emprestado no momento.");
                livro.newLine();
            }

            livro.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void lerArquivo(String arquivo){
        try {
            FileReader arq = new FileReader(arquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null) {
                System.out.printf("%s\n", linha);
                linha = lerArq.readLine();
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }


    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
