package app;
import provas.Prova;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Prova prova = new Prova();
        Scanner sc = new Scanner(System.in);
        int opc;

        do{
            System.out.println("\n---- SISTEMA DE PROVAS ----");
            System.out.println("1 - Criar Gabarito");
            System.out.println("2 - Registrar Respostas de Alunos");
            System.out.println("3 - Corrigir Provas");
            System.out.println("4 - Exibir Resultado");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            while (!sc.hasNextInt()) {
                System.out.print("Opção inválida. Tente novamente: ");
                sc.next();
            }

            opc = sc.nextInt();
            sc.nextLine(); 

            switch (opc) {
                case 1:
                    prova.criarGabarito();
                    break;
                case 2:
                    prova.criarRespostas();
                    break;
                case 3:
                    prova.compararRespostas();
                    break;
                case 4:
                    int opcSec;
                    do{
                        System.out.println("---- EXIBIR RESULTADOS ----");
                        System.out.println("1- Exibir resultados normais");
                        System.out.println("2- Exibir resultados por ordem alfabética");
                        System.out.println("3- Exibir resultados por nota média");
                        System.out.println("4- Voltar ao Menu");
                        while (!sc.hasNextInt()) {
                            System.out.print("Opção inválida. Tente novamente: ");
                            sc.next();
                        }
                    opcSec = sc.nextInt();
                    sc.nextLine(); // limpar buffer

                switch (opcSec) {
                    case 1:
                        prova.exibirResultados();
                        break;
                    case 2:
                        prova.exibirResultadosOrdenadosPorNome();
                        break;
                    case 3:
                        prova.exibirResultadosPorNota();
                        break;
                    case 4:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha entre 0 e 3.");
                    }
                    } while (opcSec != 4);
                        break;
          
                case 0:
                    System.out.println("Encerrando . . . ");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha entre 0 e 3.");
            }

        } while (opc != 0);

        sc.close();
    }
}
