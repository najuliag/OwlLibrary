package SistemaDeGerenciamento;

import javax.swing.*;

public class Biblioteca {
    public static void main(String[] args) {

        Administrador adm = new Administrador();

        boolean executando = true;


            JOptionPane.showMessageDialog(null, "Bem-vindo à Owl Library!\n\n" +
                    "Faça seu login como administrador para acessar o sistema.\n\n", "Início", JOptionPane.YES_NO_CANCEL_OPTION);
            adm.loginAdm();

        while (executando) {
            String opcao = JOptionPane.showInputDialog(null, "O que você deseja fazer agrora?\n\n"
                    + "[1] Cadastrar usuários\n" + "[2] Remover usuário\n" + "[3] Listar usuários\n"
                    + "[4] Cadastrar livro\n" + "[5] Listar livros\n" + "[6] Gerar relatório\n"
                    + "[7] Procurar usuário\n" + "[8] Entrar como usuário\n" + "[9] Sair", "Página do adm", JOptionPane.YES_NO_CANCEL_OPTION);

            if (opcao != null) {
                switch (opcao) {
                    case "1" -> {
                        adm.adicionarUsuario();
                    }
                    case "2" -> {
                        adm.removerUsuario();
                    }
                    case "3" -> {
                        adm.listarUsuarios();
                    }
                    case "4" -> {
                        adm.cadastrarLivro();
                    }
                    case "5" -> {
                        adm.listarLivros();
                    }
                    case "6" -> {
                        String arquivo = "relatorio.txt";

                        adm.qntdDeLivros(arquivo);
                        adm.lerArquivo(arquivo);
                        adm.livrosEmprestados(arquivo);

                    }
                    case "7" ->{
                        adm.procurarUsuario();
                    }
                    case "8" -> {
                        boolean loginUsuario = adm.loginUsuario();

                        if (loginUsuario) {
                            boolean opcoesUsuario = true;
                            while (opcoesUsuario) {
                                String acessar = JOptionPane.showInputDialog(null, "Bem-vindo (a)!\n\n"
                                        + "O que você deseja fazer?\n"
                                        + "[1] Ver livros disponíveis\n"
                                        + "[2] Procurar um livro\n"
                                        + "[3] Realizar empréstimo\n"
                                        + "[4] Devolver um livro\n"
                                        + "[5] Sair", "Página do Usuário", JOptionPane.YES_NO_CANCEL_OPTION);
                                if (acessar != null) {
                                    switch (acessar) {
                                        case "1" -> {
                                            adm.listarLivros();
                                        }
                                        case "2" -> {
                                            adm.procurarLivro();
                                        }
                                        case "3" -> {
                                            adm.realizarEmprestimo();
                                        }
                                        case "4" -> {
                                            adm.realizarDevolucao();
                                        }
                                        case "5" -> {
                                            opcoesUsuario = false;
                                            JOptionPane.showMessageDialog(null, "Saindo da página do usuário.");
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Saindo da página do usuário.", "Encerramento", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                            }
                        }
                    }
                    case "9" ->{
                        executando = false;
                        JOptionPane.showMessageDialog(null, "Encerrando programa.");
                    }
                    case "10"->{
                        adm.procurarLivro();
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, "Programa encerrado.", "Encerramento", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
    }
}






