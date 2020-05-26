/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.PanelEmpleado;

/**
 *
 * @author Ariel
 */
public class TablaEmpleadoModelListener implements ListSelectionListener {

    private final PanelEmpleado unPanelEmpleado;

    public TablaEmpleadoModelListener(PanelEmpleado pantallaContenido) {
        this.unPanelEmpleado = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelEmpleado.seleccionarEmpleado();        
        this.unPanelEmpleado.getValidador().habilitarBoton(true, this.unPanelEmpleado.getJbtn_eliminar(), new Color(30, 132, 73), Color.WHITE, null, null);
        this.unPanelEmpleado.getValidador().habilitarBoton(true, this.unPanelEmpleado.getJbtn_modificar(), new Color(30, 132, 73), Color.WHITE, null, null);
        this.unPanelEmpleado.getValidador().limpiarCampo(this.unPanelEmpleado.getJtf_buscarPersona());
    }

}
