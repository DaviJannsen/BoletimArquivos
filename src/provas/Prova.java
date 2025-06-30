package provas;

import java.io.*;
import java.util.*;

import alunoss.Aluno;

public class Prova {
    private final Scanner scanner = new Scanner(System.in);
    private String disciplina;
    private List<Aluno> alunos = new ArrayList<>();

    public void criarRespostas() {
        System.out.print("Digite o nome da disciplina: ");
        disciplina = scanner.nextLine();

        try {
            FileWriter fw = new FileWriter(disciplina + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            while (true) {
                System.out.print("Digite o nome do aluno: ");
                String nome = scanner.nextLine().toUpperCase();

                String[] respostas = new String[10];

                for (int i = 0; i < 10; i++) {
                    respostas[i] = lerResposta("Questão " + (i + 1));
                }

                Aluno aluno = new Aluno(nome, respostas);
                alunos.add(aluno);
                bw.write(String.join("", respostas) + "\t" + nome);
                bw.newLine();

                String continuar;
                do {
                    System.out.print("Deseja adicionar outro aluno? (S/N): ");
                    continuar = scanner.nextLine().trim().toUpperCase();
                    if (!continuar.equals("S") && !continuar.equals("N")) {
                        System.out.println("Opção inválida. Digite apenas 'S' ou 'N'.");
                    }
                } while (!continuar.equals("S") && !continuar.equals("N"));

                if (!continuar.equals("S")) {
                    break;
                }
            }

            bw.close();
            fw.close();

            System.out.println("Respostas salvas com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarGabarito() {
        System.out.print("Digite o nome da disciplina para o gabarito: ");
        disciplina = scanner.nextLine();

        String[] gabarito = new String[10];

        for (int i = 0; i < 10; i++) {
            gabarito[i] = lerResposta("Resposta correta da questão " + (i + 1));
        }
        if (respostasIguais(gabarito)) {
            System.out.println("Gabarito inválido: todas as respostas são iguais.");
            return;
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

    public void compararRespostas() {
        System.out.print("Digite o nome da disciplina para corrigir: ");
        disciplina = scanner.nextLine().trim();

        String arquivoGabarito = disciplina + "_gabarito.txt";
        String arquivoRespostas = disciplina + ".txt";
        String arquivoResultado = disciplina + "_resultado.txt";
        try {
            BufferedReader brGabarito = new BufferedReader(new FileReader(arquivoGabarito));
            BufferedReader brRespostas = new BufferedReader(new FileReader(arquivoRespostas));
            BufferedWriter bwResultado = new BufferedWriter(new FileWriter(arquivoResultado));

            String linhaGabarito = brGabarito.readLine();
            String[] gabarito = linhaGabarito.trim().split("");
            brGabarito.close();

            String linha;
            while ((linha = brRespostas.readLine()) != null) {
                String[] partes = linha.split("\t");
                if (partes.length != 2) {
                    continue;
                }
                String[] respostasAluno = partes[0].split("");
                String nome = partes[1];

                Aluno aluno = new Aluno(nome, respostasAluno);

                aluno.calcularPontuacao(gabarito);
                int pontuacao = aluno.getPontuacao();

                if (aluno.respostasIguais()) {
                    bwResultado.write(aluno.getNome() + ": " + pontuacao + (" (todas respostas iguais)"));
                    bwResultado.newLine();
                } else {
                    bwResultado.write(aluno.getNome() + ": " + pontuacao + " pontos");
                    bwResultado.newLine();
                }
            }

            brRespostas.close();
            bwResultado.close();

            System.out.println("Correção concluída. Resultados salvos em " + disciplina + "_resultado.txt");
            arquivoOrdemNome();
            arquivoNotaEMedia();

        } catch (IOException e) {
            System.out.println("Erro ao corrigir provas: " + e.getMessage());
        }
    }

    private void arquivoOrdemNome() {
        String arquivoResultado = disciplina + "_resultado.txt";
        String arquivoPorNome = disciplina + "_alfabetica.txt";

        List<Aluno> alunos = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivoResultado));
            String linha;

            while ((linha = br.readLine()) != null) {
                if (!linha.contains(":")) {
                    continue;
                }
                String[] partes = linha.split(":");
                String nome = partes[0].trim();
                String pontosStr = partes[1].replaceAll("\\D", "").trim(); // equivalente ao [^0-9]
                int pontos;
                if (pontosStr.isEmpty()) {
                    pontos = 0;
                } else {
                    pontos = Integer.parseInt(pontosStr);
                }

                alunos.add(new Aluno(nome, null, pontos));
            }
            br.close();

            alunos.sort(Comparator.comparing(Aluno::getNome));
            BufferedWriter bwNome = new BufferedWriter(new FileWriter(arquivoPorNome));
            for (Aluno aluno : alunos) {
                bwNome.write(aluno.getNome() + ": " + aluno.getPontuacao() + " pontos");
                bwNome.newLine();
            }
            bwNome.close();

        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo por nome: " + e.getMessage());
        }
    }

    private void arquivoNotaEMedia() {
        String arquivoResultado = disciplina + "_resultado.txt";
        String arquivoPorNota = disciplina + "_nota.txt";

        List<Aluno> alunos = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivoResultado));
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(":");
                String nome = partes[0].trim();
                String pontosStr = partes[1].replaceAll("\\D", "").trim();
                int pontos;
                if (pontosStr.isEmpty()) {
                    pontos = 0;
                } else {
                    pontos = Integer.parseInt(pontosStr);
                }

                alunos.add(new Aluno(nome, null, pontos));
            }
            br.close();

            alunos.sort((a1, a2) -> Integer.compare(a2.getPontuacao(), a1.getPontuacao()));
            BufferedWriter bwNota = new BufferedWriter(new FileWriter(arquivoPorNota));
            double soma = 0;

            for (Aluno aluno : alunos) {
                bwNota.write(aluno.getNome() + ": " + aluno.getPontuacao() + " pontos");
                bwNota.newLine();
                soma += aluno.getPontuacao();
            }

            double media = soma / alunos.size();
            bwNota.write("MÉDIA DA TURMA: " + String.format("%.2f", media));
            bwNota.close();

        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo por nota: " + e.getMessage());
        }
    }

    private String lerResposta(String prompt) {
        String resposta;
        do {
            System.out.print(prompt + " (V/F): ");
            resposta = scanner.nextLine().trim().toUpperCase();
            if (!resposta.equals("V") && !resposta.equals("F")) {
                System.out.println("❗ Resposta inválida. Digite apenas V ou F.");
            }
        } while (!resposta.equals("V") && !resposta.equals("F"));
        return resposta;
    }

    private boolean respostasIguais(String[] respostas) {
        for (String resposta : respostas) {
            if (!resposta.equals(respostas[0])) {
                return false;
            }
        }
        return true;
    }

    public void exibirResultados() {
        System.out.print("Digite o nome da disciplina para exibir os resultados: ");
        disciplina = scanner.nextLine().trim();

        String arquivoResultado = disciplina + "_resultado.txt";

        File resultado = new File(arquivoResultado);
        if (!resultado.exists()) {
            System.out.println("Arquivo de resultado não encontrado para a disciplina \"" + disciplina + "\".");
            return;
        }

        System.out.println("\nRESULTADOS DA DISCIPLINA: " + disciplina.toUpperCase());
        System.out.println("----------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(resultado))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println("" + linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de resultados: " + e.getMessage());
        }

        System.out.println("----------------------------------\n");
    }

    public void exibirResultadosOrdenadosPorNome() {
        System.out.print("Digite o nome da disciplina para exibir os resultados por nome: ");
        disciplina = scanner.nextLine().trim();

        String arquivo = disciplina + "_alfabetica.txt";
        File resultado = new File(arquivo);

        if (!resultado.exists()) {
            System.out
                    .println("Arquivo de resultado por nome não encontrado para a disciplina \"" + disciplina + "\".");
            return;
        }

        System.out.println("\nRESULTADOS ORDENADOS POR NOME - " + disciplina.toUpperCase());
        System.out.println("----------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(resultado))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de resultados: " + e.getMessage());
        }

        System.out.println("----------------------------------\n");
    }

    public void exibirResultadosPorNota() {
        System.out.print("Digite o nome da disciplina para exibir os resultados por nota: ");
        disciplina = scanner.nextLine().trim();

        String arquivo = disciplina + "_nota.txt";
        File resultado = new File(arquivo);

        if (!resultado.exists()) {
            System.out
                    .println("Arquivo de resultado por nota não encontrado para a disciplina \"" + disciplina + "\".");
            return;
        }

        System.out.println("\nRESULTADOS POR NOTA - " + disciplina.toUpperCase());
        System.out.println("----------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(resultado))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de resultados: " + e.getMessage());
        }

        System.out.println("----------------------------------\n");
    }
}
