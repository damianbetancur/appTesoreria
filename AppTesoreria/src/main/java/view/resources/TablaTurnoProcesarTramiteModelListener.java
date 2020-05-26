/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.PanelProcesarTramite01;
import view.PanelProcesarTurno01;

/**
 *
 * @author Ariel
 */
public class TablaTurnoProcesarTramiteModelListener implements ListSelectionListener {

    private final PanelProcesarTramite01 unPanelProcesarTramite01;

    public TablaTurnoProcesarTramiteModelListener(PanelProcesarTramite01 pantallaContenido) {
        this.unPanelProcesarTramite01 = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelProcesarTramite01.seleccionarTurno();
    }

}
