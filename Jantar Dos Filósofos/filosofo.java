import java.util.concurrent.locks.Lock;

class filosofo implements Runnable {
    private final int id;
    private final Lock garfoEsquerdo;
    private final Lock garfoDireito;
    private volatile boolean running;

    public filosofo(int id, Lock garfoEsquerdo, Lock garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
        this.running = true;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep(1000); // Simula o tempo que o filósofo gasta pensando
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comendo.");
        Thread.sleep(1000); // Simula o tempo que o filósofo gasta comendo
    }

    public void parar() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                pensar();

                garfoEsquerdo.lock();
                System.out.println("Filósofo " + id + " pegou o garfo esquerdo.");

                garfoDireito.lock();
                System.out.println("Filósofo " + id + " pegou o garfo direito.");

                comer();

                garfoEsquerdo.unlock();
                System.out.println("Filósofo " + id + " soltou o garfo esquerdo.");

                garfoDireito.unlock();
                System.out.println("Filósofo " + id + " soltou o garfo direito.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}