import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class jantarDosFilosofos {
    public static void main(String[] args) {
        Lock[] garfos = new Lock[5];

        for (int i = 0; i < 5; i++) {
            garfos[i] = new ReentrantLock();
        }

        filosofo[] filosofos = new filosofo[5];

        for (int i = 0; i < 5; i++) {
            filosofos[i] = new filosofo(i, garfos[i], garfos[(i + 1) % 5]);
            new Thread(filosofos[i]).start();
        }

        // Simulação de execução por um período de tempo
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Encerrando as threads dos filósofos
        for (int i = 0; i < 5; i++) {
            filosofos[i].parar();
        }
    }
}