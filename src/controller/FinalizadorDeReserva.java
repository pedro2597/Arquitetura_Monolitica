package controller;

import model.Livro;
import model.Usuario;

public class FinalizadorDeReserva {
    public static void finalizar(Livro livro, Usuario usuario){
        usuario.finalizarReserva(livro);
        livro.finalizarReserva(usuario);
    }
}
