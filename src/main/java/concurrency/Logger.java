package concurrency;

public class Logger {
    // Garante que apenas uma thread por vez escreva no console
    public static synchronized void log(String mensagem) {
        System.out.println(mensagem);
    }
}
