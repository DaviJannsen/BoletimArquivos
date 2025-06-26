package provas;

import java.io.*;
import java.util.*;

import alunoss.Aluno;

public class Prova {
    private final Scanner scanner = new Scanner(System.in);
    private String disciplina;
    private List<Aluno> alunos = new ArrayList<>();

    public void criarArquivoRespostas() {
        System.out.print("Digite o nome da disciplina: ");
        disciplina = scanner.nextLine();

        try {
            FileWriter fw = new FileWriter(disciplina + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            String continuar;
            do {
                System.out.print("Digite o nome do aluno: ");
                String nome = scanner.nextLine().toUpperCase();

                String[] respostas = new String[10];

                for (int i = 0; i < 10; i++) {
                    while (true) {
                        System.out.print("Digite a resposta " + (i + 1) + " (V ou F): ");
                        String resposta = scanner.nextLine().trim().toUpperCase();
                        if (resposta.equals("V") || resposta.equals("F")) {
                            respostas[i] = resposta;
                            break;
                        } else {
                            System.out.println("Resposta inválida! Digite apenas V ou F.");
                        }
                    }
                }

                Aluno aluno = new Aluno(nome, respostas);
                alunos.add(aluno);

                System.out.print("Deseja adicionar outro aluno? (s/n): ");
                continuar = scanner.nextLine();

            } while (continuar.equalsIgnoreCase("s"));

            for (Aluno aluno : alunos) {
                bw.write(String.join("", aluno.getRespostas()) + "\t" + aluno.getNome());
                bw.newLine();
            }

            bw.close();
            fw.close();

            System.out.println("Informações salvas com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarGabarito() {
    System.out.print("Digite o nome da disciplina para o gabarito: ");
    disciplina = scanner.nextLine();

    String[] gabarito = new String[10];

    for (int i = 0; i < 10; i++) {
        while (true) {
            System.out.print("Digite a resposta correta da questão " + (i + 1) + " (V ou F): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (resposta.equals("V") || resposta.equals("F")) {
                gabarito[i] = resposta;
                break;
            } else {
                System.out.println("Entrada invalida! Digite apenas V ou F.");
            }
        }
    }

    try {
        FileWriter fw = new FileWriter(disciplina + "_gabarito.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(String.join("", gabarito));
        bw.newLine();

        bw.close();
        fw.close();

        System.out.println("Gabarito salvo com sucesso!");

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void compararRespostas(){
        System.out.print("Digite o nome da disciplina para corrigir: ");
        disciplina = scanner.nextLine();

        String Gabarito = disciplina + "_gabarito.txt";
        String Respostas = disciplina + ".txt";
            try {
       
            BufferedReader brGabarito = new BufferedReader(new FileReader(Gabarito));
            String linhaGabarito = brGabarito.readLine();
            String[] gabarito = linhaGabarito.trim().split(" ");
            brGabarito.close();

            BufferedReader brRespostas = new BufferedReader(new FileReader(Respostas));

            new File("resultados").mkdirs();
            BufferedWriter bwResultado = new BufferedWriter(new FileWriter(disciplina + "_resultado.txt"));

            String linha;
            while ((linha = brRespostas.readLine()) != null) {
                String[] partes = linha.split("\t");
                if (partes.length != 2) continue;
                
                String[] respostasAluno = partes[0].split(" ");
                String nome = partes[1];

                int pontuacao = 0;
                for (int i = 0; i < gabarito.length && i < respostasAluno.length; i++) {
                    if (gabarito[i].equalsIgnoreCase(respostasAluno[i])) {
                        pontuacao++;
                    }
                }

                bwResultado.write(nome + ": " + pontuacao + " pontos");
                bwResultado.newLine();
            }

        brRespostas.close();
        bwResultado.close();

        System.out.println("Correção concluída. Resultados salvos em resultados/" + disciplina + "_resultado.txt");

    } catch (IOException e) {
        System.out.println("Erro ao corrigir provas: " + e.getMessage());
    }
}

    



}
