package view;

import java.util.List;

public class EntradaObj {
    private String comando;
    private List<String> parametros;

    public EntradaObj(String comando, List<String> parametros) {
        this.comando = comando;
        this.parametros = parametros;
    }

    public String getComando() {
        return comando;
    }

    public List<String> getParametros() {
        return parametros;
    }
}
