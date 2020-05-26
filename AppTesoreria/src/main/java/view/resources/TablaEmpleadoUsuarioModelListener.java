/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.PanelUsuario;

/**
 *
 * @author Ariel
 */
public class TablaEmpleadoUsuarioModelListener implements ListSelectionListener {

    private final PanelUsuario unPanelEmpleado;

    public TablaEmpleadoUsuarioModelListener(PanelUsuario pantallaContenido) {
        this.unPanelEmpleado = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelEmpleado.seleccionarEmpleado();
        this.unPanelEmpleado.getValidador().limpiarCampo(this.unPanelEmpleado.getJtf_buscarPersona());
    }

}
