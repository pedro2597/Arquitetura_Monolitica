import domain.entity,*;
import java.util.ArrayList;
package domain.entity;
import java.lang.reflect.Array;
import java.util.List;
import java.time.LocalDate;

public class Biblioteca {
    private static Biblioteca instance;

    private final ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    private final ArrayList<Livro> livros = new ArrayList<Livro>();

    private Biblioteca() {
        Livro livro1 = new Livro("Engenharia de Software", "100", "Ian Sommervile", "AddisonWesley", 2000, 6);
        Livro livro2 = new Livro("UML – Guia do Usuário", "101", "Grady Booch, James Rumbaugh, Ivar Jacobson", "Campus", 2000, 7);
        Livro livro3 = new Livro("Code Complete", "200", "Steve McConnell", "Microsoft Press", 2014, 2);
        Livro livro4 = new Livro("Agile Software Development", "201", "Robert Martin", "Prentice Hall", 2002, 1);
        Livro livro5 = new Livro("Refactoring: Improving the Design of Existing Code", "300", "Martin Fowler", "Addison-Wesley Professional", 1999, 1);
        Livro livro6 = new Livro("Software Metrics: A Rigorous and Practical Approach", "301", "Norman Fenton, James Bieman", "CRC Press", 2014, 3);
        Livro livro7 = new Livro("Design Patterns: Elements of Reusable Object-Oriented Software", "400", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Addison-Wesley Professional", 1994, 1);
        Livro livro8 = new Livro("UML Distilled: A Brief Guide to the Standard Object Modeling Language", "401", "Martin Fowler", "Addison-Wesley Professional", 2003, 3);

        livro1.adicionarExemplar(new Exemplar(livro1, "01"));
        livro1.adicionarExemplar(new Exemplar(livro1, "02"));
        livro2.adicionarExemplar(new Exemplar(livro2, "03"));
        livro3.adicionarExemplar(new Exemplar(livro3, "04"));
        livro4.adicionarExemplar(new Exemplar(livro4, "05"));
        livro5.adicionarExemplar(new Exemplar(livro5, "06"));
        livro5.adicionarExemplar(new Exemplar(livro5, "07"));
        livro7.adicionarExemplar(new Exemplar(livro7, "08"));
        livro7.adicionarExemplar(new Exemplar(livro7, "09"));

        this.livros.add(livro1);
        this.livros.add(livro2);
        this.livros.add(livro3);
        this.livros.add(livro4);
        this.livros.add(livro5);
        this.livros.add(livro6);
        this.livros.add(livro7);
        this.livros.add(livro8);

        this.usuarios.add(new AlunoGraduacao("João da Silva", "123"));
        this.usuarios.add(new AlunoPosGraduacao("Luiz Fernando Rodrigues", "456"));
        this.usuarios.add(new AlunoGraduacao("Aluno de Graduação", "789"));
        this.usuarios.add(new Professor("Carlos Lucena", "100"));

    }

    public static Biblioteca getInstance() {
        if (instance == null) {
            instance = new Biblioteca();
        }
        return instance;
    }

    public Usuario buscarUsuario(String codigoUsuario) {
        return this.usuarios.stream().filter(usuario -> usuario.getCodigoUsuario().equals(codigoUsuario)).findFirst().orElse(null);
    }

    public Livro buscarLivro(String codigoLivro) {
        return this.livros.stream().filter(livro -> livro.getCodigoLivro().equals(codigoLivro)).findFirst().orElse(null);
    }

}

public abstract class Usuario {

    protected String nome;
    protected String codigoUsuario;
    protected ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    protected ArrayList<Reserva> reservas = new ArrayList<>();
    protected ArrayList<Emprestimo> emprestPerma = new ArrayList<>();
    protected ArrayList<Reserva> reserPerma = new ArrayList<>();


    public abstract void verificarPossibilidadeEmprestimo(Livro livro);


    public void verificarPossibilidadeReserva(Livro livro) {
        if (livro.usuarioPossuiReserva(this)) {
            throw new IllegalArgumentException("Usuário não pode fazer reserva de um livro que já reservou");
        }
    }

    public abstract int getDiasEmprestimo();

    public int quantidadeDeEmprestimosAbertos() {
        return this.emprestimos.size();
    }

    public boolean possuiLivroAtrasado() {
        return this.emprestimos.stream().anyMatch(Emprestimo::isAtrasado);
    }

    public void finalizarReserva(Livro livro) {
        Reserva reserva = livro.buscarReserva(this);
        reservas.remove(reserva);
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
        this.emprestPerma.add(emprestimo);
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        this.reserPerma.add(reserva);
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public List<Emprestimo> getTodosEmprestimosFeitos() {
        return emprestPerma;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public List<Reserva> getTodasReservas() {
        return reserPerma;
    }

    public void finalizarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
    }

    public int getQuantidadeDeReservas() {
        return this.reservas.size();
    }

    public String getNome() {
        return this.nome;
    }

    public String getCodigoUsuario() {
        return this.codigoUsuario;
    }


    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
}

public class AlunoGraduacao extends Usuario {

    private final int diasEmprestimo = 3;

    public AlunoGraduacao(String nome, String codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }


    @Override
    public void verificarPossibilidadeEmprestimo(Livro livro) {
        if (this.possuiLivroAtrasado()) {
            throw new IllegalArgumentException("O aluno não pode pegar emprestado livros tendo emprestimos atrasados.");
        }
        if (this.emprestimos.size() == 3) {
            throw new IllegalArgumentException("O aluno não pode pegar emprestado mais do que 3 livros.");
        }
        if (livro.usuarioPossuiEmprestimo(this)) {
            throw new IllegalArgumentException("O aluno não pode pegar emprestado livros que ele atualmente está em posse.");
        }

        if (livro.quantidadeReservas() >= livro.quantidadeExemplaresDisponiveis() && !livro.usuarioPossuiReserva(this)) {
            throw new IllegalArgumentException("O aluno de graduacao não pode fazer emprestimo de um livro cuja quantidade de reserva é maior que o numero de exemplares disponíveis não tendo feito reserva");
        }
        if (livro.quantidadeReservas() >= livro.quantidadeDeExemplares() && !livro.usuarioPossuiReserva(this)) {
            throw new IllegalArgumentException("O aluno de graduacao não fez reserva para o livro em questão se a quantide de reservas é maior ou igual que a quantidade de exemplares.");
        }
    }

    @Override
    public int getDiasEmprestimo() {
        return this.diasEmprestimo;
    }

    public class AlunoPosGraduacao extends Usuario {
        private int diasEmprestimo = 5;

        public AlunoPosGraduacao(String nome, String codigoUsuario) {
            this.nome = nome;
            this.codigoUsuario = codigoUsuario;
        }


        @Override
        public void verificarPossibilidadeEmprestimo(Livro livro) {
            if (this.possuiLivroAtrasado()) {
                throw new IllegalArgumentException("O aluno de pos graduação não pode pegar emprestado livros tendo emprestimos atrasados.");
            }
            if (this.quantidadeDeEmprestimosAbertos() == 4) {
                throw new IllegalArgumentException("O aluno de pos graduação não pode pegar emprestado mais do que 4 livros.");
            }
            if (livro.usuarioPossuiEmprestimo(this)) {
                throw new IllegalArgumentException("O aluno de pos graduação pegar emprestado livros que ele atualmente está em posse.");
            }
            if (livro.quantidadeReservas() >= livro.quantidadeExemplaresDisponiveis() && !livro.usuarioPossuiReserva(this)) {
                throw new IllegalArgumentException("O aluno de pos não pode fazer emprestimo de um livro cuja quantidade de reserva é maior que o numero de exemplares disponíveis não tendo feito reserva");
            }
            if (livro.quantidadeReservas() >= livro.quantidadeDeExemplares() && !livro.usuarioPossuiReserva(this)) {
                throw new IllegalArgumentException("O aluno de pos graduação não fez reserva para o livro em questão se a quantide de reservas é maior ou igual que a quantidade de exemplares.");
            }
        }


        @Override
        public int getDiasEmprestimo() {
            return this.diasEmprestimo;
        }

    }
}

public class AlunoPosGraduacao extends Usuario {
    private int diasEmprestimo = 5;

    public AlunoPosGraduacao(String nome, String codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }


    @Override
    public void verificarPossibilidadeEmprestimo(Livro livro) {
        if (this.possuiLivroAtrasado()) {
            throw new IllegalArgumentException("O aluno de pos graduação não pode pegar emprestado livros tendo emprestimos atrasados.");
        }
        if (this.quantidadeDeEmprestimosAbertos() == 4) {
            throw new IllegalArgumentException("O aluno de pos graduação não pode pegar emprestado mais do que 4 livros.");
        }
        if (livro.usuarioPossuiEmprestimo(this)) {
            throw new IllegalArgumentException("O aluno de pos graduação pegar emprestado livros que ele atualmente está em posse.");
        }
        if(livro.quantidadeReservas() >=livro.quantidadeExemplaresDisponiveis() && !livro.usuarioPossuiReserva(this)){
            throw new IllegalArgumentException("O aluno de pos não pode fazer emprestimo de um livro cuja quantidade de reserva é maior que o numero de exemplares disponíveis não tendo feito reserva");
        }
        if (livro.quantidadeReservas() >= livro.quantidadeDeExemplares() && !livro.usuarioPossuiReserva(this)) {
            throw new IllegalArgumentException("O aluno de pos graduação não fez reserva para o livro em questão se a quantide de reservas é maior ou igual que a quantidade de exemplares.");
        }
    }


    @Override
    public int getDiasEmprestimo() {
        return this.diasEmprestimo;
    }

}

public class Professor extends Usuario implements Observador {
    private int dias = 7;
    private int notificacoes = 0;

    public Professor(String nome, String codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }

    @Override
    public void verificarPossibilidadeEmprestimo(Livro livro) {
        if (this.possuiLivroAtrasado()) {
            throw new IllegalArgumentException("O professor não pode pegar emprestado livros tendo emprestimos atrasados.");
        }

        if (livro.usuarioPossuiEmprestimo(this)) {
            throw new IllegalArgumentException("O professor não pode pegar emprestado livros que ele atualmente está em posse.");
        }

    }

    @Override
    public int getDiasEmprestimo() {
        return dias;
    }


    @Override
    public void notificarReserva() {
        this.notificacoes = notificacoes + 1;
    }

    public int getQuantidadeDeNotificacoes() {
        return this.notificacoes;
    }
}

public class Livro {
    private String titulo;
    private String codigoLivro;
    private String autor;
    private String editora;
    private int ano;
    private int edicao;

    private final List<Exemplar> exemplares = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();
    private final List<Observador> observadores = new ArrayList<>();

    public Livro(String titulo, String codigoLivro, String autor, String editora, int ano, int edicao) {
        this.titulo = titulo;
        this.codigoLivro = codigoLivro;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
        this.edicao = edicao;
    }

    public Exemplar getExemplarDisponivel() {
        return this.exemplares.stream().filter(Exemplar::isDisponivel).findFirst().orElse(null);
    }

    public boolean existeExemplarDisponivel() {
        return exemplares.stream().anyMatch(Exemplar::isDisponivel);
    }

    public boolean usuarioPossuiReserva(Usuario usuario) {
        return this.reservas.stream().anyMatch(reserva -> reserva.getCodigoUsuario().equals(usuario.getCodigoUsuario()));
    }

    public Reserva obterReservaUsuario(Usuario usuario) {
        return this.reservas.stream().filter(reserva -> reserva.getCodigoUsuario().equals(usuario.getCodigoUsuario())).findFirst().orElse(null);
    }

    public void finalizarReserva(Usuario usuario) {
        Reserva reserva = this.obterReservaUsuario(usuario);
        this.reservas.remove(reserva);
    }

    public void finalizarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public boolean usuarioPossuiEmprestimo(Usuario usuario) {
        return this.emprestimos.stream().anyMatch(emprestimo -> emprestimo.getCodigoUsuario().equals(usuario.getCodigoUsuario()));
    }

    public List<Reserva> getReservas() {
        return this.reservas;
    }

    public Emprestimo buscarEmprestimoUsuario(Usuario usuario) {
        return this.emprestimos.stream().filter(emprestimo -> emprestimo.getCodigoUsuario().equals(usuario.codigoUsuario)).findFirst().orElse(null);
    }

    public Reserva buscarReserva(Usuario usuario) {
        for (Reserva reserva : this.reservas) {
            if (reserva.getUsuario().equals(usuario)) {
                return reserva;
            }
        }
        return null;
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        if (this.quantidadeReservas() > 2) {
            this.notificarObservador();
        }
    }

    public void adicionarExemplar(Exemplar exemplar) {
        this.exemplares.add(exemplar);
    }

    public int quantidadeDeExemplares() {
        return this.exemplares.size();
    }

    public void adicionarObservador(Observador observador) {
        this.observadores.add(observador);
    }


    public int quantidadeExemplaresDisponiveis() {
        return (int) exemplares.stream().filter(Exemplar::isDisponivel).count();
    }

    public int quantidadeReservas() {
        return this.reservas.size();
    }

    public void notificarObservador() {
        observadores.forEach(Observador::notificarReserva);
    }


    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public String getCodigoLivro() {
        return this.codigoLivro;
    }

    public String getEditora() {
        return this.editora;
    }

    public int getAno() {
        return this.ano;
    }

    public int getEdicao() {
        return this.edicao;
    }

    public void setCodigoLivro(String codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

}

public interface Observador{
    public void notificarReserva();
}

public class Exemplar{
    private Livro livro;
    private String codigoExemplar;
    private Emprestimo emprestimo;
    public Exemplar(Livro livro, String codigoExemplar){
        this.livro = livro;
        this.codigoExemplar = codigoExemplar;
        this.emprestimo = null;
    }

    public String getCodigoExemplar(){
        return this.codigoExemplar;
    }

    public Livro getLivro(){
        return this.livro;
    }
    public String codigoLivro(){
        return this.livro.getCodigoLivro();
    }
    public String getTituloDoLivro(){
        return this.livro.getTitulo();
    }

    public void setLivro(Livro livro){
        this.livro = livro;
    }

    public boolean isDisponivel(){
        return this.emprestimo == null;
    }

    public void emprestar(Emprestimo emprestimo){
        this.emprestimo = emprestimo;
    }
    public void finalizarEmprestimo(){
        this.emprestimo = null;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exemplar: ").append(this.codigoExemplar).append("\n");
        sb.append("Título: ").append(this.livro.getTitulo()).append("\n");
        sb.append("Autor: ").append(this.livro.getAutor()).append("\n");
        sb.append("Editora: ").append(this.livro.getEditora()).append("\n");
        sb.append("Edição: ").append(this.livro.getEdicao()).append("\n");
        sb.append("Ano de Publicação: ").append(this.livro.getAno()).append("\n");

        if (isDisponivel()) {
            sb.append("Status: Disponível").append("\n");
        } else {
            sb.append("Status: Emprestado").append("\n");
            sb.append("Usuário: ").append(this.emprestimo.getUsuario().getNome()).append("\n");
            sb.append("Data do Empréstimo: ").append(this.emprestimo.getDataEmprestimo()).append("\n");
            sb.append("Data de Devolução Prevista: ").append(this.emprestimo.getPrevisaoEntrega()).append("\n");
        }
        return sb.toString();
    }
}

public class Reserva{
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataReservada;

    public Reserva(Usuario usuario, Livro livro){
        this.usuario = usuario;
        this.livro = livro;
        this.dataReservada = LocalDate.now();
    }

    public Usuario getUsuario(){
        return this.usuario;
    }
    public String getCodigoUsuario(){
        return this.usuario.getCodigoUsuario();
    }
    public String getCodigoLivro(){
        return this.livro.getCodigoLivro();
    }

    public Livro getLivro(){
        return this.livro;
    }

    public LocalDate getDataReservada(){
        return this.dataReservada;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setLivro(Livro livro){
        this.livro = livro;
    }


}

public class Emprestimo {
    private Usuario usuario;
    private Exemplar exemplar;
    private LocalDate dataEmprestimo;
    private LocalDate previsaoEntrega;
    private LocalDate dataDevolucaoReal;

    public Emprestimo(Usuario usuario, Exemplar exemplar, int diasEmprestimo) {
        this.usuario = usuario;
        this.exemplar = exemplar;
        this.dataEmprestimo = LocalDate.now();
        this.previsaoEntrega = dataEmprestimo.plusDays(diasEmprestimo);
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public String getTituloLivro() {
        return this.exemplar.getTituloDoLivro();
    }


    public boolean isFinalizado() {
        return this.dataDevolucaoReal != null;
    }

    public boolean isAtrasado() {
        LocalDate hoje = LocalDate.now();
        return this.previsaoEntrega.isBefore(hoje) && !this.isFinalizado();
    }

    public void finalizar() {
        this.dataDevolucaoReal = LocalDate.now();
        this.exemplar.finalizarEmprestimo();
    }

    public LocalDate getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public LocalDate getPrevisaoEntrega() {
        return this.previsaoEntrega;
    }

    public LocalDate getDataDevolucaoReal() {
        return this.dataDevolucaoReal;
    }

    public String getCodigoUsuario() {
        return this.usuario.getCodigoUsuario();
    }

    public String getCodigoLivro() {
        return this.exemplar.codigoLivro();
    }


}