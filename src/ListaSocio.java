public class ListaSocio extends LinkedList<Socio> {

    public ListaSocio() {
        super();
    }

    @Override
    public void add(Socio socio) {
        validarNuevo(socio);
        super.add(socio);
    }
 
    @Override
    public void add(Socio socio,int index) {
        validarNuevo(socio);
        super.add(socio, index);
    }
 
    private void validarNuevo(Socio socio) {
        if (socio == null) {
            throw new IllegalArgumentException("El socio no puede ser null.");
        }
        if (existeId(socio.getIdSocio())) {
            throw new IllegalArgumentException("No puede haber IDs repetidos en la lista, ya existe el ID "+socio.getIdSocio()+".");
        }
    }
 
    public boolean existeId(String id) {
        Nodo<Socio> actual = head;
        while (actual != null) {
            if (actual.getData().getIdSocio().equals(id)) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    public LinkedList<String> MembresiasPorVencer(int diasLimite) {
        if (diasLimite < 0) {
            throw new IllegalArgumentException("El límite de días no puede ser negativo.");
        }
        LinkedList<String> result = new LinkedList<>();
        Nodo<Socio> actual = head;
        while (actual != null) {
            Socio socio = actual.getData();
            if (socio.getDiasRestantes() <= diasLimite) {
                result.add(socio.getIdSocio());
            }
            actual = actual.getNext();
        }  
        return result;
    }

    public LinkedList <Socio> MostrarOrdenadosPorDias(){ 
        LinkedList<Socio> copia = new LinkedList<>();
        Nodo<Socio> actual = head;
        while (actual != null) {
            copia.add(actual.getData());
            actual = actual.getNext();
        }
        boolean cambio = true;
        while(cambio) {
            cambio = false;
            Nodo<Socio> cursor = copia.head;
            while (cursor != null && cursor.getNext() != null) {
                Socio socioActual = cursor.getData();
                Socio socioSiguiente = cursor.getNext().getData();
                if(socioActual.getDiasRestantes() < socioSiguiente.getDiasRestantes()) {
                cursor.setData(socioSiguiente);
                cursor.getNext().setData(socioActual);
                cambio = true;
                }
                cursor = cursor.getNext();
            }
        } 
        return copia;
    }

    public String CantidadSociosPorMembresia(){
        int basica = 0;
        int premium = 0;
        int vip = 0;
        Nodo<Socio> actual = head;
        while (actual != null) {
            Socio s = actual.getData();
            if(s.getTipoMembresia().equalsIgnoreCase("Básica")) {
                basica++;
            } else if(s.getTipoMembresia().equalsIgnoreCase("Premium")) {
                premium++;
            } else if(s.getTipoMembresia().equalsIgnoreCase("VIP")) {
                vip++;
            }
            actual = actual.getNext();
        }
        return "Básica: " +basica+ ", Premium: "+premium+", VIP: "+vip;
    }

    public boolean RenovarMembresia(String idSocio,int diasAdicionales) {
        if (diasAdicionales < 0) {
            throw new IllegalArgumentException("Los días a renovar no pueden ser negativos.");
        }
        if (idSocio == null || idSocio.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del socio no puede ser null ni vacío.");
        }
    
        Nodo<Socio> actual = head;
        while (actual != null) {
            Socio socio = actual.getData();
            if (socio.getIdSocio().equals(idSocio)) {
                long nuevosDias = (long) socio.getDiasRestantes() + diasAdicionales;
                if (nuevosDias > Integer.MAX_VALUE) { 
                    throw new IllegalArgumentException("La renovación supera el máximo de días permitido.");
                } 
                socio.setDiasRestantes((int) nuevosDias);
                return true; 
            }
            actual = actual.getNext();
        }
        return false; 
    }

    public int EliminarSociosSinDias() {
        int cont = 0;
        while(head != null && head.getData().getDiasRestantes() == 0) {
            head = head.getNext();
            cont++;
            size--;
        }
        if(head != null){
            Nodo<Socio> cursor = head;
            while(cursor.getNext() != null) {
                if(cursor.getNext().getData().getDiasRestantes() == 0) {
                    cursor.setNext(cursor.getNext().getNext());
                    cont++;
                    size--;
                }else{
                    cursor = cursor.getNext();
                }
            }
        }
    return cont;
    }
 
}
