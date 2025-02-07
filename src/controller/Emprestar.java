package controller;

import model.Emprestimo;
import model.Exemplar;
import model.Livro;
import model.Usuario;
import model.Biblioteca;

public class Emprestar {


    public String emprestarLivro(String codigoUsuario, String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);
        if(usuario== null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }

        if (!livro.existeExemplarDisponivel()) {
            throw new IllegalArgumentException("Livro não disponível para emprestimo, pois já todos os exemplares estão emprestados.");
        }

        usuario.verificarPossibilidadeEmprestimo(livro);
        Emprestimo emprestimo = this.criarEmprestimo(livro, usuario);
        livro.adicionarEmprestimo(emprestimo);
        usuario.adicionarEmprestimo(emprestimo);

        if (livro.usuarioPossuiReserva(usuario)) {
            FinalizadorDeReserva.finalizar(livro, usuario);
        }
        return "Livro Emprestado com sucesso";
    }

    private Emprestimo criarEmprestimo(Livro livro, Usuario usuario) {

        int diasDeEmprestimo = usuario.getDiasEmprestimo();
        Exemplar exemplar = livro.getExemplarDisponivel();
        Emprestimo emprestimo = new Emprestimo(usuario, exemplar, diasDeEmprestimo);
        exemplar.emprestar(emprestimo);
        return emprestimo;
    }
}
