package model;

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
