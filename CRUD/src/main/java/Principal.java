import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        JogoDAO jogoDAO = new JogoDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Listar Jogos");
            System.out.println("2 - Inserir Jogo");
            System.out.println("3 - Excluir Jogo");
            System.out.println("4 - Atualizar Jogo");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    jogoDAO.listar().forEach(System.out::println);
                    break;
                case 2:
                    inserirJogo(scanner, jogoDAO);
                    break;
                case 3:
                    excluirJogo(scanner, jogoDAO);
                    break;
                case 4:
                    atualizarJogo(scanner, jogoDAO);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void inserirJogo(Scanner scanner, JogoDAO jogoDAO) {
        System.out.println("Inserir novo Jogo:");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Desenvolvedor: ");
        String desenvolvedor = scanner.nextLine();

        System.out.print("Plataforma: ");
        String plataforma = scanner.nextLine();

        System.out.print("Ano de Lançamento: ");
        int anoLancamento = scanner.nextInt();

        System.out.print("Disponível (true/false): ");
        boolean disponivel = scanner.nextBoolean();

        Jogo jogo = new Jogo(0, titulo, desenvolvedor, plataforma, anoLancamento, disponivel);
        if (jogoDAO.inserir(jogo)) {
            System.out.println("Jogo inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir jogo.");
        }
    }

    private static void excluirJogo(Scanner scanner, JogoDAO jogoDAO) {
        System.out.print("Digite o ID do jogo que deseja excluir: ");
        int id = scanner.nextInt();

        if (jogoDAO.excluir(id)) {
            System.out.println("Jogo excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir jogo.");
        }
    }

    private static void atualizarJogo(Scanner scanner, JogoDAO jogoDAO) {
        System.out.print("Digite o ID do jogo que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Novo Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Novo Desenvolvedor: ");
        String desenvolvedor = scanner.nextLine();

        System.out.print("Nova Plataforma: ");
        String plataforma = scanner.nextLine();

        System.out.print("Novo Ano de Lançamento: ");
        int anoLancamento = scanner.nextInt();

        System.out.print("Disponível (true/false): ");
        boolean disponivel = scanner.nextBoolean();

        Jogo jogo = new Jogo(id, titulo, desenvolvedor, plataforma, anoLancamento, disponivel);
        if (jogoDAO.atualizar(jogo)) {
            System.out.println("Jogo atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar jogo.");
        }
    }
}
