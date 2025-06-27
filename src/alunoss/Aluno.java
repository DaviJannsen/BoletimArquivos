package alunoss;

public class Aluno {
    private String nome;
    private String[] respostas;

    public Aluno(String nome, String[] respostas) {
        this.nome = nome;
        this.respostas = respostas; 
    }

    public String getNome() {
        return nome;
    }

    public String[] getRespostas() {
        return respostas;
    }
}
