package org.example.scdeedec;

import org.example.scdeedec.componentes.equipos.modelo.EquipoComputo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SCdeEdeCApplicationTests {

    @Test
    void permiteCrearUnEquipoComputo() {

        EquipoComputo equipo = new EquipoComputo();

        equipo.setTipo("Laptop");
        equipo.setMarca("Dell");
        equipo.setModelo("Latitude 5420");
        equipo.setNumeroSerie("PRUEBA-001");

        assertEquals("Laptop", equipo.getTipo());
        assertEquals("Dell", equipo.getMarca());
        assertEquals("Latitude 5420", equipo.getModelo());
        assertEquals("PRUEBA-001", equipo.getNumeroSerie());
    }
}