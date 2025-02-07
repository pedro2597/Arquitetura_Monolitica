package model;

import java.time.LocalDate;

public class Reserva{
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataReservada;

    public Reserva(Usuario usuario, Livro livro){
        this.usuario = usuario;
        this.livro = livro;
        this.dataReservada = LocalDate.now();
    }

    public Usuario getUsuario(){
        return this.usuario;
    }
    public String getCodigoUsuario(){
        return this.usuario.getCodigoUsuario();
    }
    public String getCodigoLivro(){
        return this.livro.getCodigoLivro();
    }

    public Livro getLivro(){
        return this.livro;
    }

    public LocalDate getDataReservada(){
        return this.dataReservada;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setLivro(Livro livro){
        this.livro = livro;
    }


}
