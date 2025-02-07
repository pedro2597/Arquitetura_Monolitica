package controller;

import java.util.List;

public class ListarInformacoesLivroCommand implements Command {

    private final ListarInformacoesLivro listarInformacoesLivro = new ListarInformacoesLivro();

    @Override
    public String execute(List<String> parametros) {
        String codigoLivro = parametros.get(0);
        return listarInformacoesLivro.listar(codigoLivro);
    }
}
