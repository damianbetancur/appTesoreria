/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.PanelProcesarTurno04;

/**
 *
 * @author Ariel
 */
public class TablaEmpleadoProcesarTurnoModelListener implements ListSelectionListener {

    private final PanelProcesarTurno04 unPanelProcesarTurno04;

    public TablaEmpleadoProcesarTurnoModelListener(PanelProcesarTurno04 pantallaContenido) {
        this.unPanelProcesarTurno04 = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelProcesarTurno04.seleccionarPersona();                
        this.unPanelProcesarTurno04.getValidador().limpiarCampo(this.unPanelProcesarTurno04.getJtf_buscarPersona());
    }

}
