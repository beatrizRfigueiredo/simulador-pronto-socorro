package concurrency;

import mode.Paciente;
import java.util.LinkedList;
import java.util.Queue;

public class FilaPrioridade {

    private final Queue<Paciente> vermelhos = new LinkedList<>();
    private final Queue<Paciente> amarelos = new LinkedList<>();
    private final Queue<Paciente> verdes = new LinkedList<>();

    //Variáveis para controle de Starvation
    private int contadorVermelhos = 0;
    private final int LIMITE_STARVATION = 3;

    // Adiciona um paciente na fila correspondente à sua prioridade
    public synchronized void adicionar(Paciente paciente) {
        switch (paciente.getPrioridade()) {
            case VERMELHO -> vermelhos.add(paciente);
            case AMARELO -> amarelos.add(paciente);
            case VERDE -> verdes.add(paciente);
        }
        imprimirEstadoFila(
        "Apos entrada do paciente " + paciente.getId()+ " (" + paciente.getPrioridade() + ")");
        
        // Notifica médicos que estejam aguardando
        notifyAll();
    }

    // Retira um paciente respeitando a prioridade
    public synchronized Paciente retirar() throws InterruptedException {
        while (vermelhos.isEmpty() &&
               amarelos.isEmpty() &&
               verdes.isEmpty()) {
            wait();
        }

        imprimirEstadoFila("Antes da retirada pelo medico");

        // Verifica se pode furar a prioridade vermelha
        boolean furarFila = (contadorVermelhos >= LIMITE_STARVATION) &&
                (!amarelos.isEmpty() || !verdes.isEmpty());

        Paciente escolhido;
        if (!vermelhos.isEmpty() && !furarFila) {
            escolhido = vermelhos.poll();
            contadorVermelhos++; // Conta +1 vermelho seguido
        }
        else if (!amarelos.isEmpty()) {
            escolhido = amarelos.poll();
            contadorVermelhos = 0; // resetar
            if(furarFila) System.out.println("Anti-Starvation: Pulo no amarelo");
        }
        else {
        escolhido = verdes.poll();
        contadorVermelhos = 0; // resetar
        if(furarFila) System.out.println("Anti-Starvation: Pulo pro verde");
        }

        imprimirEstadoFila(
        "Apos retirada do paciente " + escolhido.getId()
        );
        return escolhido;
    }

    // Imprime o estado atual das filas
    private void imprimirEstadoFila(String momento) {
        System.out.println("----- ESTADO DA FILA (" + momento + ") -----");

        System.out.print("VERMELHO: ");
        imprimirFila(vermelhos);

        System.out.print("AMARELO : ");
        imprimirFila(amarelos);

        System.out.print("VERDE   : ");
        imprimirFila(verdes);

        System.out.println("-----------------------------------------");
    }

    // Imprime os pacientes de uma fila
    private void imprimirFila(Queue<Paciente> fila) {
        if (fila.isEmpty()) {
            System.out.println("vazia");
            return;
        }

        for (Paciente p : fila) {
            System.out.print(p.getId() + " ");
        }
        System.out.println();
    }
    // fila vazia para a mensagem
    public synchronized boolean estaVazia() {
        return vermelhos.isEmpty()
                && amarelos.isEmpty()
                && verdes.isEmpty();
    }
}