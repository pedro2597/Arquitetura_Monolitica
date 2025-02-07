package controller;

import model.Livro;
import model.Professor;
import model.Usuario;
import model.Biblioteca;

public class Observar {

    public String registrarObservador(String codigoUsuario, String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        if (!(usuario instanceof Professor)) {
            throw new IllegalArgumentException("Somente professores podem ser registrados como observadores.");
        }

        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }

        Professor professor = (Professor) usuario;
        livro.adicionarObservador(professor);
        return "Observador registrado com sucesso";
    }
}
