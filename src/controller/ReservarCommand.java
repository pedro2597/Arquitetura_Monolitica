package controller;

import java.util.List;

public class ReservarCommand implements  Command{

    Reservar reservar = new Reservar();
    @Override
    public String execute(List<String> parametros) {
        String codigoDoUsuario = parametros.get(0);
        String codigoDoLivro = parametros.get(1);
        return reservar.reservarLivro(codigoDoUsuario, codigoDoLivro);
    }
}
