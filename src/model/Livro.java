package model;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    private String titulo;
    private String codigoLivro;
    private String autor;
    private String editora;
    private int ano;
    private int edicao;

    private final List<Exemplar> exemplares = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Observador> observadores = new ArrayList<>();

    public Livro(String titulo, String codigoLivro, String autor, String editora, int ano, int edicao) {
        this.titulo = titulo;
        this.codigoLivro = codigoLivro;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
        this.edicao = edicao;
    }

    public Exemplar getExemplarDisponivel() {
        return this.exemplares.stream().filter(Exemplar::isDisponivel).findFirst().orElse(null);
    }

    public boolean existeExemplarDisponivel() {
        return exemplares.stream().anyMatch(Exemplar::isDisponivel);
    }

    public boolean usuarioPossuiReserva(Usuario usuario) {
        return this.reservas.stream().anyMatch(reserva -> reserva.getCodigoUsuario().equals(usuario.getCodigoUsuario()));
    }

    public Reserva obterReservaUsuario(Usuario usuario) {
        return this.reservas.stream().filter(reserva -> reserva.getCodigoUsuario().equals(usuario.getCodigoUsuario())).findFirst().orElse(null);
    }

    public void finalizarReserva(Usuario usuario) {
        Reserva reserva = this.obterReservaUsuario(usuario);
        this.reservas.remove(reserva);
    }

    public void finalizarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public boolean usuarioPossuiEmprestimo(Usuario usuario) {
        return this.emprestimos.stream().anyMatch(emprestimo -> emprestimo.getCodigoUsuario().equals(usuario.getCodigoUsuario()));
    }

    public List<Reserva> getReservas() {
        return this.reservas;
    }

    public Emprestimo buscarEmprestimoUsuario(Usuario usuario) {
        return this.emprestimos.stream().filter(emprestimo -> emprestimo.getCodigoUsuario().equals(usuario.codigoUsuario)).findFirst().orElse(null);
    }

    public Reserva buscarReserva(Usuario usuario) {
        for (Reserva reserva : this.reservas) {
            if (reserva.getUsuario().equals(usuario)) {
                return reserva;
            }
        }
        return null;
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        if (this.quantidadeReservas() > 2) {
            this.notificarObservador();
        }
    }

    public void adicionarExemplar(Exemplar exemplar) {
        this.exemplares.add(exemplar);
    }

    public int quantidadeDeExemplares() {
        return this.exemplares.size();
    }

    public void adicionarObservador(Observador observador) {
        this.observadores.add(observador);
    }


    public int quantidadeExemplaresDisponiveis() {
        return (int) exemplares.stream().filter(Exemplar::isDisponivel).count();
    }

    public int quantidadeReservas() {
        return this.reservas.size();
    }

    public void notificarObservador() {
        observadores.forEach(Observador::notificarReserva);
    }


    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public String getCodigoLivro() {
        return this.codigoLivro;
    }

    public String getEditora() {
        return this.editora;
    }

    public int getAno() {
        return this.ano;
    }

    public int getEdicao() {
        return this.edicao;
    }

    public void setCodigoLivro(String codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

}
