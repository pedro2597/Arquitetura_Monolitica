package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {

    protected String nome;
    protected String codigoUsuario;
    protected ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    protected ArrayList<Reserva> reservas = new ArrayList<>();
    protected ArrayList<Emprestimo> emprestPerma = new ArrayList<>();
    protected ArrayList<Reserva> reserPerma = new ArrayList<>();


    public abstract void verificarPossibilidadeEmprestimo(Livro livro);


    public void verificarPossibilidadeReserva(Livro livro) {
        if (livro.usuarioPossuiReserva(this)) {
            throw new IllegalArgumentException("Usuário não pode fazer reserva de um livro que já reservou");
        }
    }

    public abstract int getDiasEmprestimo();

    public int quantidadeDeEmprestimosAbertos() {
        return this.emprestimos.size();
    }

    public boolean possuiLivroAtrasado() {
        return this.emprestimos.stream().anyMatch(Emprestimo::isAtrasado);
    }

    public void finalizarReserva(Livro livro) {
        Reserva reserva = livro.buscarReserva(this);
        reservas.remove(reserva);
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
        this.emprestPerma.add(emprestimo);
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        this.reserPerma.add(reserva);
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public List<Emprestimo> getTodosEmprestimosFeitos() {
        return emprestPerma;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public List<Reserva> getTodasReservas() {
        return reserPerma;
    }

    public void finalizarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
    }

    public int getQuantidadeDeReservas() {
        return this.reservas.size();
    }

    public String getNome() {
        return this.nome;
    }

    public String getCodigoUsuario() {
        return this.codigoUsuario;
    }


    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
}
