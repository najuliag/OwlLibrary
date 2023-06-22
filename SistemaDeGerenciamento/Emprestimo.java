package SistemaDeGerenciamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo {
    private Livro livroEmpres;
    private Usuario usuarioEmpres;
    private boolean disponivel;

    public void prazoEntrega(){
        LocalDate dataEmpres = LocalDate.now();
        LocalDate dataDevol = dataEmpres.plusDays(10);

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataDevol.format(formatoData);

        String mensagem = "Você deve devolver esse livro no dia " + dataFormatada;
        System.out.println(mensagem);
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Livro getLivroEmpres() {
        return livroEmpres;
    }

    public void setLivroEmpres(Livro livroEmpres) {
        this.livroEmpres = livroEmpres;
    }

    public Usuario getUsuarioEmpres() {
        return usuarioEmpres;
    }

    public void setUsuarioEmpres(Usuario usuarioEmpres) {
        this.usuarioEmpres = usuarioEmpres;
    }

    public Emprestimo(Livro livroEmpres, Usuario usuarioEmpres) {
        this.livroEmpres = livroEmpres;
        this.usuarioEmpres = usuarioEmpres;
    }
}
