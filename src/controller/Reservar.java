package controller;

import model.Livro;
import model.Reserva;
import model.Usuario;
import model.Biblioteca;

public class Reservar {


    public String reservarLivro(String codigoUsuario, String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        usuario.verificarPossibilidadeReserva(livro);
        Reserva reserva = this.criarReserva(livro, usuario);
        livro.adicionarReserva(reserva);
        usuario.adicionarReserva(reserva);
        return "Livro Reservado com sucesso";
    }

    private Reserva criarReserva(Livro livro, Usuario usuario) {
        if (usuario.getQuantidadeDeReservas() >= 3) {
            throw new IllegalArgumentException("Não pode reservar mais que 3 livros.");
        }
        Reserva reserva = new Reserva(usuario, livro);
        return reserva;
    }
}
