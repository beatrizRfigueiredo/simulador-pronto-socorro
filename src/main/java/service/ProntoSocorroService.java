package service;

import concurrency.FilaPrioridade;
import concurrency.RecursosHospitalares;
import mode.Paciente;

public class ProntoSocorroService {

    private final TriagemService triagemService;
    private final FilaPrioridade fila;
    private static int pacientesRestantes;

    public static synchronized void iniciar(int totalPacientes) {
        pacientesRestantes = totalPacientes;
    }

    public static synchronized void pacienteFinalizado() {
        pacientesRestantes--;
    }

    public static synchronized boolean acabouTudo() {
        return pacientesRestantes == 0;
    }

    // Recebe a fila usada pelo sistema
    public ProntoSocorroService(FilaPrioridade fila) {
        this.triagemService = new TriagemService();
        this.fila = fila;
    }

    public void receberPaciente(Paciente paciente) throws InterruptedException {

        RecursosHospitalares.ocuparEnfermeiro();
        try {
            Thread.sleep(1500); // 1 segundo
            triagemService.triar(paciente);
        } finally {
            RecursosHospitalares.liberarEnfermeiro();
        }
        fila.adicionar(paciente);
        pacienteFinalizado();
    }
}