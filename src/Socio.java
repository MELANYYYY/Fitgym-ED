public class Socio {
    private String idSocio;
    private String nombre;
    private String tipoMembresia;
    private int diasRestantes;
  
    public Socio(String idSocio, String nombre, String tipoMembresia, int diasRestantes) {
        if (idSocio == null || idSocio.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null ni vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null ni vacío.");
        }
        if (tipoMembresia == null || !(tipoMembresia.equalsIgnoreCase("Básica") || tipoMembresia.equalsIgnoreCase("Premium") || tipoMembresia.equalsIgnoreCase("VIP"))) {
            throw new IllegalArgumentException("La membresía debe ser Básica, Premium o VIP.");
        }
        if (diasRestantes < 0) {
            throw new IllegalArgumentException("Los días restantes no pueden ser negativos.");
        }
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.tipoMembresia = tipoMembresia;
        this.diasRestantes = diasRestantes;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public int getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(int diasRestantes) {

        if (diasRestantes < 0) {
            throw new IllegalArgumentException("Los días restantes no pueden ser negativos.");
        }
        this.diasRestantes = diasRestantes;
    }

    @Override 
    public String toString() {
        return "ID: "+idSocio+", Nombre: "+nombre+", Tipo Membresia: "+tipoMembresia+", Dias Restantes: "+diasRestantes;
    } 
}
