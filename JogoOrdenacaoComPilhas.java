import java.util.*;

class Pilha {
    LinkedList<Integer> elementos = new LinkedList<>();

    public boolean estaVazia() {
        return elementos.isEmpty();
    }

    public void empilhar(int dado) {
        elementos.push(dado);
    }

    public int desempilhar() {
        if (!estaVazia()) {
            return elementos.pop();
        }
        return -1;
    }

    public int espiar() {
        if (!estaVazia()) {
            return elementos.peek();
        }
        return -1;
    }

    public void imprimir() {
        for (int dado : elementos) {
            System.out.print(dado + " ");
        }
        System.out.println();
    }
}

public class JogoOrdenacaoComPilhas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("\uD83D\uDD0B Jogo das pilhas! \uD83D\uDD0B");
        System.out.println("");

        boolean jogando = true;
        while (jogando) {
            System.out.print("Digite o tamanho das pilhas: ");
            int tamanhoPilha = scanner.nextInt();

            Pilha pilha1 = new Pilha();
            Pilha pilha2 = new Pilha();
            Pilha pilha3 = new Pilha();

            for (int i = 0; i < tamanhoPilha; i++) {
                pilha1.empilhar(random.nextInt(100) + 1);
            }

            boolean crescente = escolherOrdem(scanner);

            int movimentos = 0;
            boolean resolvido = false;

            while (!resolvido) {
                System.out.println("\nPilha 1: ");
                pilha1.imprimir();
                System.out.println("Pilha 2: ");
                pilha2.imprimir();
                System.out.println("Pilha 3: ");
                pilha3.imprimir();

                System.out.println("\nMenu de Opções:");
                System.out.println("0 - Sair do Jogo");
                System.out.println("1 - Movimentar");
                System.out.println("2 - Solução Automática");

                int escolha = scanner.nextInt();

                switch (escolha) {
                    case 0:
                        System.out.println("Jogo encerrado.");
                        return;
                    case 1:
                        movimentos = movimentarPilhas(scanner, pilha1, pilha2, pilha3, crescente, movimentos);
                        break;
                    case 2:
                        solucaoAutomatica(pilha1, pilha2, pilha3, crescente);
                        break;
                    default:
                        System.out.println("Escolha inválida. Tente novamente.");
                }

                if (pilhaEstaOrdenada(pilha1, crescente)) {
                    System.out.println("Pilha 1 está ordenada. Jogo encerrado.");
                    break;
                }
            }

            // Perguntar ao jogador se ele deseja reiniciar o jogo
            System.out.print("Deseja reiniciar o jogo (S/N)? ");
            String reiniciar = scanner.next().toUpperCase();
            if (!reiniciar.equals("S")) {
                jogando = false;
            }
        }
        scanner.close();
        System.out.println("Jogo encerrado.");
    }

    private static boolean escolherOrdem(Scanner scanner) {
        System.out.print("Deseja ordem crescente (1) ou decrescente (2): ");
        int escolhaOrdem = scanner.nextInt();
        return escolhaOrdem == 1;
    }

    private static boolean pilhaEstaOrdenada(Pilha pilha, boolean crescente) {
        LinkedList<Integer> copiaElementos = new LinkedList<>(pilha.elementos);

        if (crescente) {
            Collections.sort(copiaElementos);
        } else {
            Collections.sort(copiaElementos, Collections.reverseOrder());
        }

        return pilha.elementos.equals(copiaElementos);
    }

    private static int movimentarPilhas(Scanner scanner, Pilha pilha1, Pilha pilha2, Pilha pilha3, boolean crescente, int movimentos) {
        System.out.print("Informe a pilha de origem (1, 2, 3): ");
        int pilhaOrigem = scanner.nextInt();
        System.out.print("Informe a pilha de destino (1, 2, 3): ");
        int pilhaDestino = scanner.nextInt();

        Pilha origem = obterPilhaPorNumero(pilhaOrigem, pilha1, pilha2, pilha3);
        Pilha destino = obterPilhaPorNumero(pilhaDestino, pilha1, pilha2, pilha3);

        int valorMovido = origem.desempilhar();
        if (valorMovido != -1 && (destino.estaVazia() || valorMovido < destino.espiar())) {
            destino.empilhar(valorMovido);
            movimentos++;
        } else {
            System.out.println("Movimento inválido. Tente novamente.");
        }
        return movimentos;
    }

    private static Pilha obterPilhaPorNumero(int numeroPilha, Pilha pilha1, Pilha pilha2, Pilha pilha3) {
        switch (numeroPilha) {
            case 1:
                return pilha1;
            case 2:
                return pilha2;
            case 3:
                return pilha3;
            default:
                System.out.println("Número de pilha inválido. Usando a Pilha 1.");
                return pilha1;
        }
    }

    private static void solucaoAutomatica(Pilha pilhaOrigem, Pilha pilhaAuxiliar, Pilha pilhaDestino, boolean crescente) {
        int totalMovimentos = resolverJogo(pilhaOrigem.estaVazia() ? 0 : pilhaOrigem.elementos.size(), pilhaOrigem, pilhaAuxiliar, pilhaDestino, crescente);
        System.out.println("Solução Automática - Ordenação concluída em " + totalMovimentos + " movimentos.");
    }

    private static int resolverJogo(int n, Pilha pilhaOrigem, Pilha pilhaAuxiliar, Pilha pilhaDestino, boolean crescente) {
        if (n == 0) {
            return 0;
        }

        int movimentos = resolverJogo(n - 1, pilhaOrigem, pilhaDestino, pilhaAuxiliar, crescente);

        int valorMovido = pilhaOrigem.desempilhar();
        pilhaDestino.empilhar(valorMovido);
        movimentos++;

        movimentos += resolverJogo(n - 1, pilhaAuxiliar, pilhaOrigem, pilhaDestino, crescente);

        return movimentos;
    }
}
