package controller;

import java.util.List;

public class EmprestarCommand implements  Command{

    Emprestar emprestar = new Emprestar();
    @Override
    public String execute(List<String> parametros) {
        String codigoDoUsuario = parametros.get(0);
        String codigoDoLivro = parametros.get(1);
        return emprestar.emprestarLivro(codigoDoUsuario, codigoDoLivro);
    }
}
