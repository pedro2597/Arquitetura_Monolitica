package model;

import java.util.ArrayList;

/*Armazenamento  e  de gerencia de regras de negocio*/
/* Cada metodo sem ser de armazenamento (sem ser buscarX) representa uma funcionalidade do sistema*/

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
