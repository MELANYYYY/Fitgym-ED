import java.util.Scanner;

public class Validaciones {
    Scanner in = new Scanner(System.in);

    
    public int validarEntero(String mensaje,int min,int max) {
        int num = min - 1;
        boolean val = false;
        while (!val) {
            System.out.print(mensaje);
            String entrada = in.nextLine().trim();
            try {
                num = Integer.parseInt(entrada);
                if (num >= min && num <=max) {
                    val = true;
                } else {
                    System.out.println("Debe estar entre "+min+" y "+max+".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
        return num;
    }

    
    public String validarNombre(String mensaje) {
        String texto = "";
        boolean val = false;
        while (!val) {
            System.out.print(mensaje);
            texto = in.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            } else if (!soloLetrasYEspacios(texto)) {
                System.out.println("Solo se permiten letras y espacios.");
            } else {
                val = true;
            }
        }
        return texto;
    }

    public String validarId(String mensaje) {
        String texto = "";
        boolean val = false;
        while (!val) {
            System.out.print(mensaje);
            texto = in.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El ID no puede estar vacío.");
            } else if (texto.length() > 10) {
                System.out.println("El ID no puede tener más de 10 caracteres.");
            } else if (!soloLetrasYNumeros(texto)) {
                System.out.println("Solo se permiten letras y números (sin espacios ni símbolos).");
            } else {
                val = true;
            }
        }
        return texto.toUpperCase();
    }

    private boolean soloLetrasYEspacios(String texto) {
        boolean v = true;
        for (int i=0;i<texto.length();i++) {
            char c = texto.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                v = false;
            }
        }
        return v;
    }

    private boolean soloLetrasYNumeros(String texto) {
        boolean v = true;
        for (int i=0;i<texto.length();i++) {
            if (!Character.isLetterOrDigit(texto.charAt(i))) {
                v = false;
            }
        }
        return v;
    }
}