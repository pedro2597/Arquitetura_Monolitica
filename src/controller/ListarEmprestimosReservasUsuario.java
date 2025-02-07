package controller;

import model.Emprestimo;
import model.Reserva;
import model.Usuario;
import model.Biblioteca;

import java.util.ArrayList;
import java.util.List;

public class ListarEmprestimosReservasUsuario {

    public String listar(String codigoUsuario) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        List<String> filaMensagens = new ArrayList<>();

        filaMensagens.add("Empréstimos:");
        for (Emprestimo emprestimo : usuario.getTodosEmprestimosFeitos()) {
            filaMensagens.add("Título: " + emprestimo.getTituloLivro());
            filaMensagens.add("Data de Empréstimo: " + emprestimo.getDataEmprestimo());
            filaMensagens.add("Status: " + (emprestimo.isFinalizado() ? "Finalizado" : "Em curso"));
            if (emprestimo.isFinalizado()) {
                filaMensagens.add("Data de Devolução: " + emprestimo.getDataDevolucaoReal());
            } else {
                filaMensagens.add("Data de Devolução Prevista: " + emprestimo.getPrevisaoEntrega());
            }
            filaMensagens.add("-----");
        }

        filaMensagens.add("Reservas:");
        for (Reserva reserva : usuario.getTodasReservas()) {
            filaMensagens.add("Título: " + reserva.getLivro().getTitulo());
            filaMensagens.add("Data da Reserva: " + reserva.getDataReservada());
            filaMensagens.add("-----");
        }

        return String.join("\n", filaMensagens);
    }
}
