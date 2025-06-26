package alunoss;

public class Aluno {
    private String nome;
    private String[] respostas;
    private int pontuacao;

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

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
