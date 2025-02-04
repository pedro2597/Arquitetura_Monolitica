import java.util.List;
import java.util.ArrayList;
import domain.entity.Livro;
import domain.entity.Professor;
import domain.entity.Usuario;
import domain.entity.Emprestimo;
import domain.entity.Exemplar;
import domain.entity.Reserva;

public interface Command {
    String execute(List<String> parametros);
}

public class SaiCommand implements  Command{

    @Override
    public String execute(List<String> parametros) {
        return null;
    }
}

public class Observar {

    public String registrarObservador(String codigoUsuario, String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        if (!(usuario instanceof Professor)) {
            throw new IllegalArgumentException("Somente professores podem ser registrados como observadores.");
        }

        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }

        Professor professor = (Professor) usuario;
        livro.adicionarObservador(professor);
        return "Observador registrado com sucesso";
    }
}

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

public class Reservar {


    public String reservarLivro(String codigoUsuario, String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        usuario.verificarPossibilidadeReserva(livro);
        Reserva reserva = this.criarReserva(livro, usuario);
        livro.adicionarReserva(reserva);
        usuario.adicionarReserva(reserva);
        return "Livro Reservado com sucesso";
    }

    private Reserva criarReserva(Livro livro, Usuario usuario) {
        if (usuario.getQuantidadeDeReservas() >= 3) {
            throw new IllegalArgumentException("Não pode reservar mais que 3 livros.");
        }
        Reserva reserva = new Reserva(usuario, livro);
        return reserva;
    }
}

public class ReservarCommand implements  Command{

    Reservar reservar = new Reservar();
    @Override
    public String execute(List<String> parametros) {
        String codigoDoUsuario = parametros.get(0);
        String codigoDoLivro = parametros.get(1);
        return reservar.reservarLivro(codigoDoUsuario, codigoDoLivro);
    }
}

public class Emprestar {


    public String emprestarLivro(String codigoUsuario, String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);
        if(usuario== null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }

        if (!livro.existeExemplarDisponivel()) {
            throw new IllegalArgumentException("Livro não disponível para emprestimo, pois já todos os exemplares estão emprestados.");
        }

        usuario.verificarPossibilidadeEmprestimo(livro);
        Emprestimo emprestimo = this.criarEmprestimo(livro, usuario);
        livro.adicionarEmprestimo(emprestimo);
        usuario.adicionarEmprestimo(emprestimo);

        if (livro.usuarioPossuiReserva(usuario)) {
            FinalizadorDeReserva.finalizar(livro, usuario);
        }
        return "Livro Emprestado com sucesso";
    }

    private Emprestimo criarEmprestimo(Livro livro, Usuario usuario) {

        int diasDeEmprestimo = usuario.getDiasEmprestimo();
        Exemplar exemplar = livro.getExemplarDisponivel();
        Emprestimo emprestimo = new Emprestimo(usuario, exemplar, diasDeEmprestimo);
        exemplar.emprestar(emprestimo);
        return emprestimo;
    }
}

public class EmprestarCommand implements  Command{

    Emprestar emprestar = new Emprestar();
    @Override
    public String execute(List<String> parametros) {
        String codigoDoUsuario = parametros.get(0);
        String codigoDoLivro = parametros.get(1);
        return emprestar.emprestarLivro(codigoDoUsuario, codigoDoLivro);
    }
}

public class Devolver{

    Biblioteca biblioteca = Biblioteca.getInstance();

    public String devolverLivro(String codigoUsuario, String codigoLivro){
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);
        Livro livro = biblioteca.buscarLivro(codigoLivro);
        Emprestimo emprestimo = livro.buscarEmprestimoUsuario(usuario);
        FinalizadorDeEmprestimo.finalizar(emprestimo,livro,usuario);
        return "Devolução realizada com sucesso";
    }
}

public class DevolverCommand implements  Command{

    Devolver devolver = new Devolver();
    @Override
    public String execute(List<String> parametros) {
        String codigoDoUsuario = parametros.get(0);
        String codigoDoLivro = parametros.get(1);
        return devolver.devolverLivro(codigoDoUsuario, codigoDoLivro);
    }
}

public class FinalizadorDeReserva {
    public static void finalizar(Livro livro, Usuario usuario){
        usuario.finalizarReserva(livro);
        livro.finalizarReserva(usuario);
    }
}

public class FinalizadorDeEmprestimo {

    public static void finalizar(Emprestimo emprestimo, Livro livro, Usuario usuario){
        livro.finalizarEmprestimo(emprestimo);
        usuario.finalizarEmprestimo(emprestimo);
        emprestimo.finalizar();
    }
}

public class ListarEmprestimosReservasUsuario {

    public String listar(String codigoUsuario) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        List<String> filaMensagens = new ArrayList<>();

        filaMensagens.add("Empréstimos:");
        for (Emprestimo emprestimo : usuario.getTodosEmprestimosFeitos()) {
            filaMensagens.add("Título: " + emprestimo.getTituloLivro());
            filaMensagens.add("Data de Empréstimo: " + emprestimo.getDataEmprestimo());
            filaMensagens.add("Status: " + (emprestimo.isFinalizado() ? "Finalizado" : "Em curso"));
            if (emprestimo.isFinalizado()) {
                filaMensagens.add("Data de Devolução: " + emprestimo.getDataDevolucaoReal());
            } else {
                filaMensagens.add("Data de Devolução Prevista: " + emprestimo.getPrevisaoEntrega());
            }
            filaMensagens.add("-----");
        }

        filaMensagens.add("Reservas:");
        for (Reserva reserva : usuario.getTodasReservas()) {
            filaMensagens.add("Título: " + reserva.getLivro().getTitulo());
            filaMensagens.add("Data da Reserva: " + reserva.getDataReservada());
            filaMensagens.add("-----");
        }

        return String.join("\n", filaMensagens);
    }
}

public class ListarEmprestimosReservasUsuarioCommand implements Command {

    private final ListarEmprestimosReservasUsuario listarEmprestimosReservasUsuario = new ListarEmprestimosReservasUsuario();

    @Override
    public String execute(List<String> parametros) {
        String codigoUsuario = parametros.get(0);
        return listarEmprestimosReservasUsuario.listar(codigoUsuario);
    }
}

public class ListarInformacoesLivro {

    public String listar(String codigoLivro) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Livro livro = biblioteca.buscarLivro(codigoLivro);

        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }

        List<String> filaMensagens = new ArrayList<>();

        filaMensagens.add("Título: " + livro.getTitulo());
        filaMensagens.add("Quantidade de Reservas: " + livro.quantidadeReservas());

        if (livro.quantidadeReservas() > 0) {
            filaMensagens.add("Reservas:");
            for (Reserva reserva : livro.getReservas()) {
                filaMensagens.add("Usuário: " + reserva.getUsuario().getNome());
            }
        }

        filaMensagens.add("Exemplares:");
        for (Exemplar exemplar : livro.getExemplares()) {
            filaMensagens.add("Código Exemplar: " + exemplar.getCodigoExemplar());
            filaMensagens.add("Status: " + (exemplar.isDisponivel() ? "Disponível" : "Emprestado"));

            if (!exemplar.isDisponivel()) {
                Emprestimo emprestimo = exemplar.getEmprestimo();
                if (emprestimo != null) {
                    filaMensagens.add("Usuário: " + emprestimo.getUsuario().getNome());
                    filaMensagens.add("Data de Empréstimo: " + emprestimo.getDataEmprestimo());
                    filaMensagens.add("Data de Devolução Prevista: " + emprestimo.getPrevisaoEntrega());
                }
            }
            filaMensagens.add("-----");
        }

        return String.join("\n", filaMensagens);
    }
}

public class ListarInformacoesLivroCommand implements Command {

    private final ListarInformacoesLivro listarInformacoesLivro = new ListarInformacoesLivro();

    @Override
    public String execute(List<String> parametros) {
        String codigoLivro = parametros.get(0);
        return listarInformacoesLivro.listar(codigoLivro);
    }
}

public class ListarNotificacoesProfessor {

    public String listar(String codigoUsuario) {
        Biblioteca biblioteca = Biblioteca.getInstance();
        Usuario usuario = biblioteca.buscarUsuario(codigoUsuario);

        if (!(usuario instanceof Professor professor)) {
            throw new IllegalArgumentException("Professor não encontrado ou código de usuário inválido.");
        }

        List<String> filaMensagens = new ArrayList<>();

        filaMensagens.add("O professor foi notificado " + professor.getQuantidadeDeNotificacoes() + " vezes sobre reservas simultâneas.");

        return String.join("\n", filaMensagens);
    }
}

public class ListarNotificacoesProfessorCommand implements Command {

    private final ListarNotificacoesProfessor listarNotificacoesProfessor = new ListarNotificacoesProfessor();

    @Override
    public String execute(List<String> parametros) {
        String codigoUsuario = parametros.get(0);
        return listarNotificacoesProfessor.listar(codigoUsuario);
    }
}