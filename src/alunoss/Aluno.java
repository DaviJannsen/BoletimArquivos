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

    public boolean respostasIguais() {
        if (respostas == null || respostas.length == 0) return false;
        for (String r : respostas) {
            if (!r.equalsIgnoreCase(respostas[0])) {
                return false;
            }
        }
        return true;
    }

    public void calcularPontuacao(String[] gabarito) {
        if (respostasIguais()) {
            pontuacao = 0;
            return;
        }
        int pontos = 0;
        for (int i = 0; i < gabarito.length && i < respostas.length; i++) {
            if (gabarito[i].equalsIgnoreCase(respostas[i])) {
                pontos++;
            }
        }
        this.pontuacao = pontos;
    }

    // Sobrecarga pra uso nos métodos de leitura de arquivos
    public Aluno(String nome, String[] respostas, int pontuacao) {
        this.nome = nome;
        this.respostas = respostas;
        this.pontuacao = pontuacao;
    }
}
