package modelo;

public class Recomendacion {
    private int clienteId;
    private int clienteRecomendadoId;
    private double porcentaje;

    public Recomendacion() {
    }

    public Recomendacion(int clienteId, int clienteRecomendadoId, double porcentaje) {
        this.clienteId = clienteId;
        this.clienteRecomendadoId = clienteRecomendadoId;
        this.porcentaje = porcentaje;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getClienteRecomendadoId() {
        return clienteRecomendadoId;
    }

    public void setClienteRecomendadoId(int clienteRecomendadoId) {
        this.clienteRecomendadoId = clienteRecomendadoId;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
   
}
