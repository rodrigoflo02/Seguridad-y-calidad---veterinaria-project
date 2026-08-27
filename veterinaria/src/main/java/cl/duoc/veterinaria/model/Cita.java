package cl.duoc.veterinaria.model;

public class Cita {
    private String fecha;
    private String hora;
    private String motivo_consulta;
    private String veterinario;

    public Cita(String fecha, String hora, String motivo_consulta, String veterinario) {
        this.fecha = fecha;
        this.hora = hora;
        this.motivo_consulta = motivo_consulta;
        this.veterinario = veterinario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo_consulta() {
        return motivo_consulta;
    }

    public void setMotivo_consulta(String motivo_consulta) {
        this.motivo_consulta = motivo_consulta;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}