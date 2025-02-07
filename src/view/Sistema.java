package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Command;
import controller.EmprestarCommand;
import controller.ReservarCommand;
import controller.ListarInformacoesLivroCommand;
import controller.ObservarCommand;
import controller.DevolverCommand;
import controller.ListarEmprestimosReservasUsuarioCommand;
import controller.ListarNotificacoesProfessorCommand;
import controller.SaiCommand;

public class Sistema {
    private static Sistema sistemaInstance;

    private Sistema() {
        inicializar();
    }

    public static Sistema getInstance() {
        if (sistemaInstance == null) {
            sistemaInstance = new Sistema();
        }
        return sistemaInstance;
    }

    private final Map<String, Command> commands = new HashMap<>();

    private void inicializar() {
        commands.put("emp", new EmprestarCommand());
        commands.put("res", new ReservarCommand());
        commands.put("liv", new ListarInformacoesLivroCommand());
        commands.put("obs",new ObservarCommand());
        commands.put("dev",new DevolverCommand());
        commands.put("usu", new ListarEmprestimosReservasUsuarioCommand());
        commands.put("ntf", new ListarNotificacoesProfessorCommand());
        commands.put("sai",new SaiCommand());
    }

    public String executar(String comando, List<String> parametros) {
        if (!commands.containsKey(comando)) {
            throw new IllegalArgumentException("Comando Inválido");
        }
        Command command = commands.get(comando);
        return command.execute(parametros);
    }
}

