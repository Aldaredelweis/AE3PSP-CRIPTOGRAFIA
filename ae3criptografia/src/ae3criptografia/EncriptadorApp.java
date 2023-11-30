package ae3criptografia;

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class EncriptadorApp {
    private static final int MAX_INTENTOS = 3;
    private static final Map<String, String> usuarios = new HashMap<>();

    public static void main(String[] args) {
        // Inicializar usuarios
        usuarios.put("usuario1", hashearContrasena("contrasena1"));
        usuarios.put("usuario2", hashearContrasena("contrasena2"));
        usuarios.put("usuario3", hashearContrasena("contrasena3"));

        // Pedir credenciales al usuario
        boolean autenticado = autenticarUsuario();
        if (!autenticado) {
            System.out.println("Demasiados intentos. La aplicación se detendrá.");
            return;
        }

        // Menú principal
        int opcion;
        String fraseEncriptada = null;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\nMenú:");
            System.out.println("1. Salir del programa");
            System.out.println("2. Encriptar frase");
            System.out.println("3. Desencriptar frase");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.println("¡Hasta luego!");
                    break;
                case 2:
                    System.out.print("Introduzca la frase a encriptar: ");
                    String frase = scanner.nextLine();
                    fraseEncriptada = encriptarFrase(frase);
                    System.out.println("Frase encriptada correctamente.");
                    break;
                case 3:
                    if (fraseEncriptada == null) {
                        System.out.println("No hay frase encriptada.");
                    } else {
                        String fraseDesencriptada = desencriptarFrase(fraseEncriptada);
                        System.out.println("Frase desencriptada: " + fraseDesencriptada);
                    }
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }

        } while (opcion != 1);

        scanner.close();
    }

    private static boolean autenticarUsuario() {
        Scanner scanner = new Scanner(System.in);

        for (int intentos = 0; intentos < MAX_INTENTOS; intentos++) {
            System.out.print("Introduzca su nombre de usuario: ");
            String usuario = scanner.nextLine();

            System.out.print("Introduzca su contraseña: ");
            String contrasena = scanner.nextLine();

            if (validarCredenciales(usuario, contrasena)) {
                System.out.println("¡Bienvenido, " + usuario + "!");
                return true;
            } else {
                System.out.println("Credenciales incorrectas. Intento " + (intentos + 1) + " de " + MAX_INTENTOS);
            }
        }

        return false;
    }

    private static boolean validarCredenciales(String usuario, String contrasena) {
        String contrasenaHasheada = usuarios.get(usuario);
        return contrasenaHasheada != null && contrasenaHasheada.equals(hashearContrasena(contrasena));
    }

    private static String encriptarFrase(String frase) {
        // Implementar lógica de encriptación simétrica aquí
        // Por simplicidad, en este ejemplo simplemente devolvemos la frase en mayúsculas
        return frase.toUpperCase();
    }

    private static String desencriptarFrase(String fraseEncriptada) {
        // Implementar lógica de desencriptación simétrica aquí
        // Por simplicidad, en este ejemplo simplemente devolvemos la frase en minúsculas
        return fraseEncriptada.toLowerCase();
    }

    private static String hashearContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(contrasena.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
