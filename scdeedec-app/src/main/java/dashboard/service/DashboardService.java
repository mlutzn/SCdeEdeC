package dashboard.service;

import dashboard.dao.DashboardDAO;
import dashboard.modelo.ResumenDashboard;

import java.sql.SQLException;

public class DashboardService {

    private final DashboardDAO dashboardDAO;

    public DashboardService() {
        this.dashboardDAO = new DashboardDAO();
    }

    public ResumenDashboard obtenerResumen() throws SQLException {
        return dashboardDAO.obtenerResumen();
    }
}