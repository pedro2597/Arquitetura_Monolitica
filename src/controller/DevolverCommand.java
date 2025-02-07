package controller;

import java.util.List;

public class DevolverCommand implements  Command{

    Devolver devolver = new Devolver();
    @Override
    public String execute(List<String> parametros) {
        String codigoDoUsuario = parametros.get(0);
        String codigoDoLivro = parametros.get(1);
        return devolver.devolverLivro(codigoDoUsuario, codigoDoLivro);
    }
}