package controller;

import java.util.List;

public class ObservarCommand implements Command {

    private final Observar observar = new Observar();

    @Override
    public String execute(List<String> parametros) {
        if (parametros.size() < 2) {
            throw new IllegalArgumentException("Parâmetros insuficientes para o comando Observar.");
        }
        String codigoUsuario = parametros.get(0);
        String codigoLivro = parametros.get(1);
        return observar.registrarObservador(codigoUsuario, codigoLivro);
    }
}
