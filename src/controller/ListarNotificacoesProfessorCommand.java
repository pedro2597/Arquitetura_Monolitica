package controller;

import java.util.List;

public class ListarNotificacoesProfessorCommand implements Command {

    private final ListarNotificacoesProfessor listarNotificacoesProfessor = new ListarNotificacoesProfessor();

    @Override
    public String execute(List<String> parametros) {
        String codigoUsuario = parametros.get(0);
        return listarNotificacoesProfessor.listar(codigoUsuario);
    }
}
