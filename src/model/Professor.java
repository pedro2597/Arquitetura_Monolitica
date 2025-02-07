package model;

public class Professor extends Usuario implements Observador {
    private int dias = 7;
    private int notificacoes = 0;

    public Professor(String nome, String codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }

    @Override
    public void verificarPossibilidadeEmprestimo(Livro livro) {
        if (this.possuiLivroAtrasado()) {
            throw new IllegalArgumentException("O professor não pode pegar emprestado livros tendo emprestimos atrasados.");
        }

        if (livro.usuarioPossuiEmprestimo(this)) {
            throw new IllegalArgumentException("O professor não pode pegar emprestado livros que ele atualmente está em posse.");
        }

    }

    @Override
    public int getDiasEmprestimo() {
        return dias;
    }


    @Override
    public void notificarReserva() {
        this.notificacoes = notificacoes + 1;
    }

    public int getQuantidadeDeNotificacoes() {
        return this.notificacoes;
    }
}
