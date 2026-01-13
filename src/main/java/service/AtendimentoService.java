package service;

import concurrency.FilaPrioridade;
import concurrency.RecursosHospitalares;
import mode.Paciente;

public class AtendimentoService implements Runnable {

    private final FilaPrioridade fila;

    public AtendimentoService(FilaPrioridade fila) {
        this.fila = fila;
    }

    @Override
    public void run() {
        try {
            while (true) {

                // Aguarda consultório disponível
                RecursosHospitalares.ocuparConsultorio();
                RecursosHospitalares.ocuparMedico();

                // Retira o paciente da fila
                Paciente paciente = fila.retirar();

                if (paciente == null) {
                    // Se não tiver paciente, libera o consultório
                    RecursosHospitalares.liberarConsultorio();
                    RecursosHospitalares.liberarMedico();
                    continue;
                }

                System.out.println(
                Thread.currentThread().getName() + " atendeu paciente " + paciente.getId()
                );

                Thread.sleep(1500);

                // Libera após o atendimento
                RecursosHospitalares.liberarConsultorio();
                RecursosHospitalares.liberarMedico();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}