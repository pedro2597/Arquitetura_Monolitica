import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Digite um comando");
                EntradaObj entradaObj = Input.ler();
                String res = Sistema.getInstance().executar(entradaObj.getComando(), entradaObj.getParametros());
                if(res == null){
                    System.out.println("Saindo");
                    break;
                }
                System.out.println(res);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

public class Input {

    public static EntradaObj ler() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return decodificador(input);
    }

    private static EntradaObj decodificador(String input) {
        String[] partes = input.split(" ");
        String comando = partes[0];
        List<String> parametros = Arrays.stream(partes)
                .skip(1)
                .collect(Collectors.toList());
        return new EntradaObj(comando, parametros);
    }

}

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

