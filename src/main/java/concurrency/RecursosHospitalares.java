package concurrency;

import java.util.concurrent.Semaphore;

public class RecursosHospitalares {

    // Semáforo que controla a quantidade de consultórios disponíveis
    private static Semaphore consultorios;
    private static Semaphore enfermeiros;
    private static Semaphore medicos;

    // Guarda os valores
    private static int totalConsultorios;
    private static int totalEnfermeiros;
    private static int totalMedicos;

    public static void configurar(int qtdConsultorios,int qtdEnfermeiros, int qtdMedicos) {
        totalConsultorios = qtdConsultorios;
        totalEnfermeiros = qtdEnfermeiros;
        totalMedicos = qtdMedicos;

        consultorios = new Semaphore(qtdConsultorios);
        enfermeiros = new Semaphore(qtdEnfermeiros);
        medicos = new Semaphore(qtdMedicos);
    }

    public static boolean semConsultorios() {
        return totalConsultorios == 0;
    }

    public static boolean semEnfermeiros() {
        return totalEnfermeiros == 0;
    }
    public static boolean semMedicos() {
        return totalMedicos == 0;
    }
    // Ocupa um consultório (bloqueia se não houver disponível)
    public static void ocuparConsultorio() throws InterruptedException {
        consultorios.acquire();
    }

    public static void ocuparEnfermeiro() throws InterruptedException {
        enfermeiros.acquire();
    }
    public static void liberarEnfermeiro() {
        enfermeiros.release();
    }
    // Libera um consultório após o atendimento
    public static void liberarConsultorio() {
        consultorios.release();
    }
    public static void ocuparMedico() throws InterruptedException {
        medicos.acquire();
    }

    public static void liberarMedico() {
        medicos.release();
    }
}