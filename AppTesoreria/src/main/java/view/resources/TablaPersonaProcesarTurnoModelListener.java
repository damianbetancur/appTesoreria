/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.awt.Color;
import view.PanelPersona;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.PanelProcesarTurno01;

/**
 *
 * @author Ariel
 */
public class TablaPersonaProcesarTurnoModelListener implements ListSelectionListener {

    private final PanelProcesarTurno01 unPanelProcesarTurno01;

    public TablaPersonaProcesarTurnoModelListener(PanelProcesarTurno01 pantallaContenido) {
        this.unPanelProcesarTurno01 = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelProcesarTurno01.seleccionarPersona();                
        this.unPanelProcesarTurno01.getValidador().limpiarCampo(this.unPanelProcesarTurno01.getJtf_buscarPersona());
    }

}
