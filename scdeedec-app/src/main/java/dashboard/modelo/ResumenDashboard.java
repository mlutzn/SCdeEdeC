package dashboard.modelo;

public class ResumenDashboard {

    private int totalEquipos;
    private int totalUsuarios;
    private int totalMantenimientos;
    private int totalReparaciones;
    private int reparacionesPendientes;

    public int getTotalEquipos() {
        return totalEquipos;
    }

    public void setTotalEquipos(int totalEquipos) {
        this.totalEquipos = totalEquipos;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public int getTotalMantenimientos() {
        return totalMantenimientos;
    }

    public void setTotalMantenimientos(int totalMantenimientos) {
        this.totalMantenimientos = totalMantenimientos;
    }

    public int getTotalReparaciones() {
        return totalReparaciones;
    }

    public void setTotalReparaciones(int totalReparaciones) {
        this.totalReparaciones = totalReparaciones;
    }

    public int getReparacionesPendientes() {
        return reparacionesPendientes;
    }

    public void setReparacionesPendientes(int reparacionesPendientes) {
        this.reparacionesPendientes = reparacionesPendientes;
    }
}