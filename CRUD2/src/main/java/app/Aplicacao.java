public class Aplicacao {
    
}
package app;

import java.util.List;
import java.util.Scanner;

import dao.DAO;
import dao.JogoDAO;
import model.Jogo;

public class Aplicacao {
    
    private static JogoDAO jogoDAO = new JogoDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int opcao;
        
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner
            
            switch (opcao) {
                case 1:
                    inserirJogo();
                    break;
                case 2:
                    listarJogos();
                    break;
                case 3:
                    atualizarJogo();
                    break;
                case 4:
                    excluirJogo();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("\n==== Menu ====");
        System.out.println("1) Inserir");
        System.out.println("2) Listar");
        System.out.println("3) Atualizar");
        System.out.println("4) Excluir");
        System.out.println("5) Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void inserirJogo() {
        System.out.println("\n==== Inserir Jogo ====");
        System.out.print("Informe o ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        
        System.out.print("Informe o nome do jogo: ");
        String nome = scanner.nextLine();
        
        System.out.print("Informe o gênero do jogo: ");
        String genero = scanner.nextLine();
        
        System.out.print("Informe a plataforma do jogo: ");
        String plataforma = scanner.nextLine();
        
        Jogo jogo = new Jogo(id, nome, genero, plataforma);
        
        if (jogoDAO.insert(jogo)) {
            System.out.println("Jogo inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir jogo.");
        }
    }
    
    private static void listarJogos() {
        System.out.println("\n==== Listar Jogos ====");
        List<Jogo> jogos = jogoDAO.get();
        
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
        } else {
            for (Jogo jogo : jogos) {
                System.out.println(jogo.toString());
            }
        }
    }
    
    private static void atualizarJogo() {
        System.out.println("\n==== Atualizar Jogo ====");
        System.out.print("Informe o ID do jogo a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        
        Jogo jogo = jogoDAO.get(id);
        
        if (jogo == null) {
            System.out.println("Jogo não encontrado.");
        } else {
            System.out.print("Informe o novo nome do jogo: ");
            String nome = scanner.nextLine();
            
            System.out.print("Informe o novo gênero do jogo: ");
            String genero = scanner.nextLine();
            
            System.out.print("Informe a nova plataforma do jogo: ");
            String plataforma = scanner.nextLine();
            
            jogo.setNome(nome);
            jogo.setGenero(genero);
            jogo.setPlataforma(plataforma);
            
            if (jogoDAO.update(jogo)) {
                System.out.println("Jogo atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar jogo.");
            }
        }
    }
    
    private static void excluirJogo() {
        System.out.println("\n==== Excluir Jogo ====");
        System.out.print("Informe o ID do jogo a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        
        if (jogoDAO.delete(id)) {
            System.out.println("Jogo excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir jogo.");
        }
    }
}
