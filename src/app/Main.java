package app;
import provas.Prova;

public class Main {
    public static void main(String[] args) {
        Prova prova = new Prova();
        prova.criarGabarito();
        prova.criarArquivoRespostas();
        prova.compararRespostas();
    }
}
