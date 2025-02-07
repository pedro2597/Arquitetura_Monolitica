package controller;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

public class FinalizadorDeEmprestimo {

    public static void finalizar(Emprestimo emprestimo, Livro livro, Usuario usuario){
        livro.finalizarEmprestimo(emprestimo);
        usuario.finalizarEmprestimo(emprestimo);
        emprestimo.finalizar();
    }
}
