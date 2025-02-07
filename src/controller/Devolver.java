package controller;

import model.Emprestimo;
import model.Livro;
import model.Usuario;
import model.Biblioteca;

public class Devolver{

    Biblioteca biblioteca = Biblioteca.getInstance();

    public String devolverLivro(String codigoUsuario, String codigoLivro){
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);
        Emprestimo emprestimo = livro.buscarEmprestimoUsuario(usuario);
        FinalizadorDeEmprestimo.finalizar(emprestimo,livro,usuario);
        return "Devolução realizada com sucesso";
    }
}