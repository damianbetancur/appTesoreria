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

/**
 *
 * @author Ariel
 */
public class TablaPersonaModelListener implements ListSelectionListener {

    private final PanelPersona unPanelPersona;

    public TablaPersonaModelListener(PanelPersona pantallaContenido) {
        this.unPanelPersona = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelPersona.seleccionarPersona();        
        this.unPanelPersona.getValidador().habilitarBoton(true, this.unPanelPersona.getJbtn_eliminar(), new Color(30, 132, 73), Color.WHITE, null, null);
        this.unPanelPersona.getValidador().habilitarBoton(true, this.unPanelPersona.getJbtn_modificar(), new Color(30, 132, 73), Color.WHITE, null, null);
        this.unPanelPersona.getValidador().limpiarCampo(this.unPanelPersona.getJtf_buscarPersona());
    }

}
