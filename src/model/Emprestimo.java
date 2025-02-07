package model;

import java.time.LocalDate;

public class Emprestimo {
    private Usuario usuario;
    private Exemplar exemplar;
    private LocalDate dataEmprestimo;
    private LocalDate previsaoEntrega;
    private LocalDate dataDevolucaoReal;

    public Emprestimo(Usuario usuario, Exemplar exemplar, int diasEmprestimo) {
        this.usuario = usuario;
        this.exemplar = exemplar;
        this.dataEmprestimo = LocalDate.now();
        this.previsaoEntrega = dataEmprestimo.plusDays(diasEmprestimo);
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public String getTituloLivro() {
        return this.exemplar.getTituloDoLivro();
    }


    public boolean isFinalizado() {
        return this.dataDevolucaoReal != null;
    }

    public boolean isAtrasado() {
        LocalDate hoje = LocalDate.now();
        return this.previsaoEntrega.isBefore(hoje) && !this.isFinalizado();
    }

    public void finalizar() {
        this.dataDevolucaoReal = LocalDate.now();
        this.exemplar.finalizarEmprestimo();
    }

    public LocalDate getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public LocalDate getPrevisaoEntrega() {
        return this.previsaoEntrega;
    }

    public LocalDate getDataDevolucaoReal() {
        return this.dataDevolucaoReal;
    }

    public String getCodigoUsuario() {
        return this.usuario.getCodigoUsuario();
    }

    public String getCodigoLivro() {
        return this.exemplar.codigoLivro();
    }


}
