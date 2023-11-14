import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Pagina {
    int numeroPagina;
    int instrucao;
    int dado;
    int bitR;
    int bitM;
    int tempoEnvelhecimento;

    public Pagina(int numeroPagina, int instrucao, int dado, int tempoEnvelhecimento) {
        this.numeroPagina = numeroPagina;
        this.instrucao = instrucao;
        this.dado = dado;
        this.bitR = 0;
        this.bitM = 0;
        this.tempoEnvelhecimento = tempoEnvelhecimento;
    }
}

public class SimuladorSubstituicaoPagina {
    public static void main(String[] args) {
        List<Pagina> matrizSwap = new ArrayList<>();
        List<Pagina> matrizRam = new ArrayList<>();

        inicializarMatrizes(matrizSwap, matrizRam);

        System.out.println("Matriz SWAP no início:");
        imprimirMatriz(matrizSwap);
        System.out.println("\nMatriz RAM no início:");
        imprimirMatriz(matrizRam);

        simularExecucao(matrizSwap, matrizRam, "NRU");
        simularExecucao(matrizSwap, matrizRam, "FIFO");
        simularExecucao(matrizSwap, matrizRam, "FIFO-SC");
        simularExecucao(matrizSwap, matrizRam, "RELÓGIO");
        simularExecucao(matrizSwap, matrizRam, "WS-CLOCK");

        // Imprimir matrizes no final da simulação
        System.out.println("\nMatriz SWAP no final:");
        imprimirMatriz(matrizSwap);
        System.out.println("\nMatriz RAM no final:");
        imprimirMatriz(matrizRam);
    }

    public static void inicializarMatrizes(List<Pagina> matrizSwap, List<Pagina> matrizRam) {
        for (int i = 0; i < 100; i++) {
            int instrucao = i + 1;
            int dado = new Random().nextInt(50) + 1;
            int tempoEnvelhecimento = new Random().nextInt(9900) + 100;
            matrizSwap.add(new Pagina(i, instrucao, dado, tempoEnvelhecimento));
        }

        for (int i = 0; i < 10; i++) {
            int indiceAleatorio = new Random().nextInt(matrizSwap.size());
            matrizRam.add(new Pagina(i, matrizSwap.get(indiceAleatorio).instrucao,
                    matrizSwap.get(indiceAleatorio).dado, matrizSwap.get(indiceAleatorio).tempoEnvelhecimento));
        }
    }

    public static void simularExecucao(List<Pagina> matrizSwap, List<Pagina> matrizRam, String algoritmo) {
        for (int i = 0; i < 1000; i++) {
            if (i % 10 == 0) {
                zerarBitR(matrizRam);
            }

            int instrucao = new Random().nextInt(100) + 1;
            executarInstrucao(instrucao, matrizSwap, matrizRam, algoritmo);

            if (i % 10 == 9) {
                salvarPaginasModificadasEmSwap(matrizRam);
            }
        }
    }

    public static void executarInstrucao(int instrucao, List<Pagina> matrizSwap, List<Pagina> matrizRam, String algoritmo) {
        for (Pagina pagina : matrizRam) {
            if (pagina.instrucao == instrucao) {
                pagina.bitR = 1;

                if (new Random().nextDouble() <= 0.3) {
                    pagina.dado++;
                    pagina.bitM = 1;
                }
                return;
            }
        }

        // Se chegou aqui, a página não está na RAM. Aplicar algoritmo de substituição.
        if (algoritmo.equals("NRU")) {
            substituirNRU(matrizRam, matrizSwap);
        } else if (algoritmo.equals("FIFO")) {
            substituirFIFO(matrizRam, matrizSwap);
        } else if (algoritmo.equals("FIFO-SC")) {
            substituirFIFO_SC(matrizRam, matrizSwap);
        } else if (algoritmo.equals("RELÓGIO")) {
            substituirRelogio(matrizRam, matrizSwap);
        } else if (algoritmo.equals("WS-CLOCK")) {
            substituirWSClock(matrizRam, matrizSwap);
        }
    }

    public static void substituirNRU(List<Pagina> matrizRam, List<Pagina> matrizSwap) {
        // Implemente o algoritmo NRU
        // Este é um exemplo básico, você deve ajustar conforme necessário
        for (Pagina pagina : matrizRam) {
            if (pagina.bitR == 0 && pagina.bitM == 0) {
                substituirPagina(pagina, matrizSwap);
                return;
            }
        }
        for (Pagina pagina : matrizRam) {
            if (pagina.bitR == 0 && pagina.bitM == 1) {
                substituirPagina(pagina, matrizSwap);
                return;
            }
        }
        for (Pagina pagina : matrizRam) {
            if (pagina.bitR == 1 && pagina.bitM == 0) {
                substituirPagina(pagina, matrizSwap);
                return;
            }
        }
        for (Pagina pagina : matrizRam) {
            if (pagina.bitR == 1 && pagina.bitM == 1) {
                substituirPagina(pagina, matrizSwap);
                return;
            }
        }
    }

    public static void substituirFIFO(List<Pagina> matrizRam, List<Pagina> matrizSwap) {
        // Implemente o algoritmo FIFO
        Pagina paginaSubstituir = matrizRam.remove(0);
        substituirPagina(paginaSubstituir, matrizSwap);
        matrizRam.add(paginaSubstituir);
    }

    public static void substituirFIFO_SC(List<Pagina> matrizRam, List<Pagina> matrizSwap) {
        // Implemente o algoritmo FIFO-SC
        // Este é um exemplo básico, você deve ajustar conforme necessário
        Pagina paginaSubstituir = null;
        for (Pagina pagina : matrizRam) {
            if (pagina.bitR == 0) {
                paginaSubstituir = pagina;
                break;
            } else {
                pagina.bitR = 0;
            }
        }
        if (paginaSubstituir == null) {
            paginaSubstituir = matrizRam.remove(0);
        }
        substituirPagina(paginaSubstituir, matrizSwap);
        matrizRam.add(paginaSubstituir);
    }

    public static void substituirRelogio(List<Pagina> matrizRam, List<Pagina> matrizSwap) {
        // Implemente o algoritmo RELÓGIO
        // Este é um exemplo básico, você deve ajustar conforme necessário
        Pagina paginaSubstituir = null;
        while (true) {
            Pagina pagina = matrizRam.remove(0);
            if (pagina.bitR == 0) {
                paginaSubstituir = pagina;
                break;
            } else {
                pagina.bitR = 0;
                matrizRam.add(pagina);
            }
        }
        substituirPagina(paginaSubstituir, matrizSwap);
        matrizRam.add(paginaSubstituir);
    }

    public static void substituirWSClock(List<Pagina> matrizRam, List<Pagina> matrizSwap) {
        // Implemente o algoritmo WS-CLOCK
        // Este é um exemplo básico, você deve ajustar conforme necessário
        Pagina paginaSubstituir = null;
        while (true) {
            Pagina pagina = matrizRam.remove(0);
            if (pagina.bitR == 0 && pagina.tempoEnvelhecimento > 2000) {
                paginaSubstituir = pagina;
                break;
            } else {
                if (pagina.bitR == 1) {
                    pagina.tempoEnvelhecimento = 0;
                    pagina.bitR = 0;
                }
                matrizRam.add(pagina);
            }
        }
        substituirPagina(paginaSubstituir, matrizSwap);
        matrizRam.add(paginaSubstituir);
    }

    public static void substituirPagina(Pagina pagina, List<Pagina> matrizSwap) {
        // Lógica para substituir a página na RAM pela página da matriz SWAP
        int indiceAleatorio = new Random().nextInt(matrizSwap.size());
        Pagina novaPagina = matrizSwap.get(indiceAleatorio);
        pagina.numeroPagina = novaPagina.numeroPagina;
        pagina.instrucao = novaPagina.instrucao;
        pagina.dado = novaPagina.dado;
        pagina.bitR = 0;
        pagina.bitM = 0;
        pagina.tempoEnvelhecimento = novaPagina.tempoEnvelhecimento;
    }

    public static void zerarBitR(List<Pagina> matrizRam) {
        for (Pagina pagina : matrizRam) {
            pagina.bitR = 0;
        }
    }

    public static void salvarPaginasModificadasEmSwap(List<Pagina> matrizRam) {
        for (Pagina pagina : matrizRam) {
            if (pagina.bitM == 1) {
                // Implemente a lógica para salvar páginas com Bit M = 1 em SWAP
                pagina.bitM = 0;
            }
        }
    }

    public static void imprimirMatriz(List<Pagina> matriz) {
        for (Pagina pagina : matriz) {
            System.out.println("N: " + pagina.numeroPagina +
                    " I: " + pagina.instrucao +
                    " D: " + pagina.dado +
                    " R: " + pagina.bitR +
                    " M: " + pagina.bitM +
                    " T: " + pagina.tempoEnvelhecimento);
        }
    }
}

