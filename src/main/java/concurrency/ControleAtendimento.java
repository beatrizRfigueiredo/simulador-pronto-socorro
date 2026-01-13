package concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ControleAtendimento {

    private static final Semaphore inicioAtendimento = new Semaphore(0);
    private static final AtomicInteger triados = new AtomicInteger(0);
    private static int totalMedicos;

    public static void configurarMedicos(int qtdMedicos) {
        totalMedicos = qtdMedicos;
    }

    public static void pacienteTriado() {
        int total = triados.incrementAndGet();

        // Quando atingir 2 pacientes, libera TODOS os médicos
        if (total == 2) {
            inicioAtendimento.release(totalMedicos);
        }
    }

    // Faz o médico aguardar até que o atendimento seja liberado
    public static void aguardarInicio() throws InterruptedException {
        inicioAtendimento.acquire();
    }
}