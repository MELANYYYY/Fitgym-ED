public class Sistema {
    private ListaSocio lista;
    private Validaciones val;

    public Sistema() {
        this.lista = new ListaSocio();
        this.val = new Validaciones();
    }

    public void iniciar() {
        int op = -1;
        while (op != 0) {
            System.out.println("\nMenu: FitGym. Control de socios");
            System.out.println("1. Agregar socio");
            System.out.println("2. Mostrar socios registrados");
            System.out.println("3. Membresías por vencer");
            System.out.println("4. Mostrar socios ordenados por días restantes");
            System.out.println("5. Cantidad de socios por membresía");
            System.out.println("6. Renovar membresía");
            System.out.println("7. Eliminar socios sin días");
            System.out.println("8. Ejecutar casos de prueba");
            System.out.println("0. Salir");
            op = val.validarEntero("Elige una opción (0 a 8): ",0,8);

            if (op == 1) {
                agregarSocio();
            } else if (op == 2) {
                mostrarSocios("Socios registrados",lista);
            } else if (op == 3) {
                consultarPorVencer();
            } else if (op == 4) {
                mostrarSocios("Socios ordenados por días restantes (mayor a menor)",lista.MostrarOrdenadosPorDias());
            } else if (op == 5) {
                System.out.println("\nCantidad de socios por membresía: "+lista.CantidadSociosPorMembresia());
            } else if (op == 6) {
                renovar();
            } else if (op == 7) {
                eliminarSinDias();
            } else if (op == 8) {
                ejecutarPruebas();
            } else {
                System.out.println("Saliendo del sistema....");
            }
        }
    }

    private void agregarSocio() {
        String id = val.validarId("ID del socio (ej. S01): ");
        if (existeId(id)){
            System.out.println("Ya existe un socio con ese ID.");
        } else {
            String nombre = val.validarNombre("Nombre: ");
            System.out.println("Membresía: 1.Básica  2.Premium  3.VIP");
            int m = val.validarEntero("Ingrese membresía (1 a 3): ",1,3);
            String tipo;
            if (m == 1) {
                tipo = "Básica";
            } else if (m == 2) {
                tipo = "Premium";
            } else {
                tipo = "VIP";
            }
            int dias = val.validarEntero("Días restantes (0 a 365): ",0,365);
            lista.add(new Socio(id, nombre, tipo, dias));
            System.out.println("Socio agregado.");
        }
    }

    private boolean existeId(String id) {
        return lista.existeId(id);
    }

    private void mostrarSocios(String titulo, LinkedList<Socio> socios) {
        System.out.println(titulo+" (Size: " + socios.size()+"):");
        if (socios.isEmpty()) {
            System.out.println("(vacía) No hay socios registrados.");
        } else {
            for (int i=0;i<socios.size();i++) {
                System.out.println("  "+(i+1)+". "+socios.get(i));
            }
        }
    }

    private void consultarPorVencer() {
        int limite = val.validarEntero("Límite de días (0 a 365): ",0,365);
        mostrarIds("Socios con "+limite+" días o menos",lista.MembresiasPorVencer(limite));
    }

    private void mostrarIds(String titulo,LinkedList<String> listaIds) {
        String texto = "";
        for (int i=0;i<listaIds.size();i++) {
            texto += listaIds.get(i);
            if (i<listaIds.size()-1) {
                texto = texto + ", ";
            }
        }
        if (listaIds.isEmpty()) {
            texto = "No hay socios que cumplan la condición.";
        }
        System.out.println(titulo + ": "+texto);
    }

    private void renovar() {
        if (lista.isEmpty()) {
            System.out.println("No hay socios registrados.");
        } else {
            String id = val.validarId("ID del socio: ");
            int dias = val.validarEntero("Días a añadir (0 a 365): ",0,365);
            if (lista.RenovarMembresia(id, dias)) {
                System.out.println("Membresía renovada.");
            } else {
                System.out.println("No existe un socio con ese ID.");
            }
        }
    }

    private void eliminarSinDias() {
        int eliminados = lista.EliminarSociosSinDias();
        System.out.println("Socios eliminados: "+eliminados);
    }

    private void ejecutarPruebas() {
        System.out.println("\na) Método MembresiaPorVencer");
        ListaSocio l = crear();
        mostrarIds("Límite 5 ",l.MembresiasPorVencer(5)); //Se espera S01, S03
        mostrarIds("Límite 1 ",l.MembresiasPorVencer(1)); //Se espera No hay socios que cumplan la condición.
        mostrarIds("Límite 100",l.MembresiasPorVencer(100)); //Se espera S01, S02, S03, S04, S05
        mostrarIds("Lista vacía",new ListaSocio().MembresiasPorVencer(5));//Se espera Lista vacía: No hay socios que cumplan la condición.
        System.out.println("\nb) Metodo mostrarOrdenadosPorDias");
        mostrarSocios("Datos oficiales", l.MostrarOrdenadosPorDias());
        //Se espera Lucía 60, Ana 30, María 15, Carlos 5, Pedro 2
        mostrarSocios("Original (sin cambios)", l);
        // Se espera Carlos, Ana, Pedro, María, Lucía
        ListaSocio empate = new ListaSocio();
        agregar(empate, "E1", "Uno", "Básica", 15);
        agregar(empate, "E2", "Dos", "VIP", 40);
        agregar(empate, "E3", "Tres", "Premium", 15);
        mostrarSocios("Con empate de 15 días",empate.MostrarOrdenadosPorDias());
        //Se espera Dos 40, Uno 15, Tres 15 (los empates mantienen el orden de llegada)
        mostrarSocios("Lista vacía",new ListaSocio().MostrarOrdenadosPorDias());
        //Se espera Size:0 (vacía) No hay socios registrados.

        System.out.println("\nc) Método cantidadSociosPorMembresia");
        System.out.println("Datos oficiales: "+l.CantidadSociosPorMembresia());
        //Se espera Básica 2, Premium 2, VIP 1
        System.out.println("Lista vacía: " +new ListaSocio().CantidadSociosPorMembresia());
        // Se espera las tres en 0

        System.out.println("\nd) Método renovarMembresia");
        ListaSocio r = crear();
        System.out.println("Renovar S01 +10: " +r.RenovarMembresia("S01",10));// Se  espera true
        mostrarSocios("Después de renovar", r);// Se espera Carlos con 15 días
        System.out.println("Renovar S99 +10: " + r.RenovarMembresia("S99",10));//Se espera false
        System.out.println("Lista vacía: " +new ListaSocio().RenovarMembresia("S01", 10)); //Se espera false
        
        System.out.println("\ne)Método eliminarSociosSinDias");
        ListaSocio z = new ListaSocio();
        agregar(z, "Z1","Zero", "Básica", 0);
        agregar(z, "Z2","Ichigo", "VIP", 0);
        agregar(z, "A1","Inoue", "Premium", 5);
        agregar(z, "Z3","Olivia", "Básica", 0);
        agregar(z, "Z4","Aria", "VIP", 0);
        agregar(z, "B1","Isabella", "Básica", 7);
        agregar(z, "Z5","Felicia", "Premium", 0);
        mostrarSocios("Antes (ceros al inicio, seguidos, en medio y al final)", z);
        System.out.println("Eliminados: "+ z.EliminarSociosSinDias());//Se espera: 5
        mostrarSocios("Después", z);//Se espera Inoue, Isabella, size 2

        ListaSocio todos = new ListaSocio();
        agregar(todos, "T1","Rukia", "Básica", 0);
        agregar(todos, "T2","Beto", "VIP", 0);
        agregar(todos, "T3","Pepe", "Premium", 0);
        System.out.println("Todos en 0, eliminados: "+todos.EliminarSociosSinDias()); // Se espera: 3
        mostrarSocios("Después", todos); //Se espera (vacía) No hay socios registrados.
        agregar(todos, "N1","Nuevo", "Básica",9);
        mostrarSocios("Agrego uno nuevo", todos); // Se espera Nuevo, size 1

        ListaSocio ninguno = crear();
        System.out.println("Ninguno en 0, eliminados: " +ninguno.EliminarSociosSinDias()); // Se espera: 0
        mostrarSocios("Después", ninguno); //Se espera: los 5, size 5
        System.out.println("Lista vacía, eliminados: " + new ListaSocio().EliminarSociosSinDias()); // Se espera: 0

        pruebasNegativas();
    }

    private void correcto(String caso, Exception e) {
        System.out.println("  [Correcto] "+caso+" -> "+e.getMessage());
    }

    private void falla(String caso) {
        System.out.println("  [Fallo] "+caso+" -> no lanzó excepción");
    }

    private void pruebasNegativas() {
        System.out.println("\n Casos negativos (todos deben lanzar excepción)");
        ListaSocio l = crear();

        try { l.MembresiasPorVencer(-1); falla("Límite -1"); }
        catch (IllegalArgumentException e) { correcto("Límite -1", e); }

        try { l.RenovarMembresia("S01", -5); falla("Renovar -5 días"); }
        catch (IllegalArgumentException e) { correcto("Renovar -5 días", e); }

        try { l.RenovarMembresia(null, 10); falla("Renovar con ID null"); }
        catch (IllegalArgumentException e) { correcto("Renovar con ID null", e); }

        try { l.RenovarMembresia("   ",10); falla("Renovar con ID solo espacios"); }
        catch (IllegalArgumentException e) { correcto("Renovar con ID solo espacios", e); }

        try { l.RenovarMembresia("S01",Integer.MAX_VALUE); falla("Renovar con desbordamiento de entero"); }
        catch (IllegalArgumentException e) { correcto("Renovar con desbordamiento de entero", e); }

        System.out.println("  Carlos sigue con: "+l.get(0).getDiasRestantes()+" días"); // Se espera 5 (ningún intento lo cambió)
 
        try { l.add(null); falla("Agregar un socio null"); }
        catch (IllegalArgumentException e) { correcto("Agregar un socio null", e); }

        try { l.add(new Socio("S01","Duplicado","VIP", 20)); falla("Agregar un ID repetido directamente en la lista"); }
        catch (IllegalArgumentException e) { correcto("Agregar un ID repetido directamente en la lista", e); }
 
        System.out.println("  La lista conserva " +l.size() +" socios"); // Se espera 5 (el duplicado no se agregó)

        try { new Socio("", "Ana", "Básica", 10); falla("Socio con ID vacío"); }
        catch (IllegalArgumentException e) { correcto("Socio con ID vacío", e); }

        try { new Socio("S10", "Ana", "Pro", 10); falla("Socio con membresía Pro"); }
        catch (IllegalArgumentException e) { correcto("Socio con membresía Pro", e); }

        try { new Socio("S10", "Ana", "Básica", -1); falla("Socio con días negativos"); }
        catch (IllegalArgumentException e) { correcto("Socio con días negativos", e); }
    }

    private void agregar(ListaSocio l,String id,String nombre,String tipo,int dias) {
        l.add(new Socio(id, nombre, tipo, dias));
    }
 
    private ListaSocio crear() {
        ListaSocio l = new ListaSocio();
        agregar(l, "S01", "Carlos", "Básica", 5);
        agregar(l, "S02", "Ana", "Premium", 30);
        agregar(l, "S03", "Pedro", "VIP", 2);
        agregar(l, "S04", "María", "Básica", 15);
        agregar(l, "S05", "Lucía", "Premium", 60);
        return l;
    }
    
}