package model;

public class AlunoGraduacao extends Usuario {

    private final int diasEmprestimo = 3;

    public AlunoGraduacao(String nome, String codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }


    @Override
    public void verificarPossibilidadeEmprestimo(Livro livro) {
        if (this.possuiLivroAtrasado()) {
            throw new IllegalArgumentException("O aluno não pode pegar emprestado livros tendo emprestimos atrasados.");
        }
        if (this.emprestimos.size() == 3) {
            throw new IllegalArgumentException("O aluno não pode pegar emprestado mais do que 3 livros.");
        }
        if (livro.usuarioPossuiEmprestimo(this)) {
            throw new IllegalArgumentException("O aluno não pode pegar emprestado livros que ele atualmente está em posse.");
        }

        if(livro.quantidadeReservas() >=livro.quantidadeExemplaresDisponiveis() && !livro.usuarioPossuiReserva(this)){
            throw new IllegalArgumentException("O aluno de graduacao não pode fazer emprestimo de um livro cuja quantidade de reserva é maior que o numero de exemplares disponíveis não tendo feito reserva");
        }
        if (livro.quantidadeReservas() >= livro.quantidadeDeExemplares() && !livro.usuarioPossuiReserva(this)) {
            throw new IllegalArgumentException("O aluno de graduacao não fez reserva para o livro em questão se a quantide de reservas é maior ou igual que a quantidade de exemplares.");
        }
    }

    @Override
    public int getDiasEmprestimo() {
        return this.diasEmprestimo;
    }

}
