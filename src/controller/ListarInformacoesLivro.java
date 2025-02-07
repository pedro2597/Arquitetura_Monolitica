package controller;

import model.Emprestimo;
import model.Livro;
import model.Reserva;
import model.Exemplar;
import model.Biblioteca;

import java.util.ArrayList;
import java.util.List;

public class ListarInformacoesLivro {

    public String listar(String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Livro livro = biblioteca.buscarLivro(codigoLivro);

        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }

        List<String> filaMensagens = new ArrayList<>();

        filaMensagens.add("Título: " + livro.getTitulo());
        filaMensagens.add("Quantidade de Reservas: " + livro.quantidadeReservas());

        if (livro.quantidadeReservas() > 0) {
            filaMensagens.add("Reservas:");
            for (Reserva reserva : livro.getReservas()) {
                filaMensagens.add("Usuário: " + reserva.getUsuario().getNome());
            }
        }

        filaMensagens.add("Exemplares:");
        for (Exemplar exemplar : livro.getExemplares()) {
            filaMensagens.add("Código Exemplar: " + exemplar.getCodigoExemplar());
            filaMensagens.add("Status: " + (exemplar.isDisponivel() ? "Disponível" : "Emprestado"));

            if (!exemplar.isDisponivel()) {
                Emprestimo emprestimo = exemplar.getEmprestimo();
                if (emprestimo != null) {
                    filaMensagens.add("Usuário: " + emprestimo.getUsuario().getNome());
                    filaMensagens.add("Data de Empréstimo: " + emprestimo.getDataEmprestimo());
                    filaMensagens.add("Data de Devolução Prevista: " + emprestimo.getPrevisaoEntrega());
                }
            }
            filaMensagens.add("-----");
        }

        return String.join("\n", filaMensagens);
    }
}
