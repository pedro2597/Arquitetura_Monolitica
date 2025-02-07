package controller;

import java.util.List;

public class ListarEmprestimosReservasUsuarioCommand implements Command {

    private final ListarEmprestimosReservasUsuario listarEmprestimosReservasUsuario = new ListarEmprestimosReservasUsuario();

    @Override
    public String execute(List<String> parametros) {
        String codigoUsuario = parametros.get(0);
        return listarEmprestimosReservasUsuario.listar(codigoUsuario);
    }
}
