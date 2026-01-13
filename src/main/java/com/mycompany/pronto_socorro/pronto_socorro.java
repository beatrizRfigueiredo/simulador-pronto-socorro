package com.mycompany.pronto_socorro;

import concurrency.*;
import mode.Paciente;
import service.*;
import java.util.Scanner;

public class pronto_socorro {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o numero de pacientes:");
        int qtdPacientes = sc.nextInt();

        System.out.println("Digite o numero de medicos:");
        int qtdMedicos = sc.nextInt();

        System.out.println("Digite o numero de consultorios:");
        int qtdConsultorios = sc.nextInt();

        System.out.println("Digite o numero de enfermeiros (triagem):");
        int qtdEnfermeiros = sc.nextInt();

        // Avisos
        if (qtdMedicos == 0) {
            System.out.println("[AVISO] Nenhum médico disponível no hospital.");
        }

        if (qtdConsultorios == 0) {
            System.out.println("[AVISO] Nenhum consultório disponível no hospital.");
        }

        if (qtdEnfermeiros == 0) {
            System.out.println("[AVISO] Nenhum enfermeiro disponível para triagem.");
        }

        if (qtdPacientes == 0) {
            System.out.println("[AVISO] Nenhum paciente para atendimento.");
        }

        // Configura os recursos compartilhados do hospital
        RecursosHospitalares.configurar(qtdConsultorios, qtdEnfermeiros, qtdMedicos);

        FilaPrioridade fila = new FilaPrioridade();
        ProntoSocorroService prontoSocorro = new ProntoSocorroService(fila);
        ProntoSocorroService.iniciar(qtdPacientes);

        // Criação das threads de pacientes
        for (int i = 1; i <= qtdPacientes; i++) {
            Paciente paciente = new Paciente(i);
            new Thread(() -> {
                try {
                    prontoSocorro.receberPaciente(paciente);
                } catch (InterruptedException ex) {
                    System.getLogger(pronto_socorro.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }).start();
        }
        // Criação das threads de médicos
        for (int i = 0; i < qtdMedicos; i++) {
            new Thread(
                    new AtendimentoService(fila),
                    "Medico-" + (i + 1)
            ).start();
        }
        while (true) {
            if (fila.estaVazia() && ProntoSocorroService.acabouTudo()) {
                System.out.println("\n[AVISO] Todos os pacientes foram atendidos.");
                break;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}