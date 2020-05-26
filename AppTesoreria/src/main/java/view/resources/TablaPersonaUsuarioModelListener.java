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
public class TablaPersonaUsuarioModelListener implements ListSelectionListener {

    private final PanelUsuario unPanelPersona;

    public TablaPersonaUsuarioModelListener(PanelUsuario pantallaContenido) {
        this.unPanelPersona = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelPersona.seleccionarPersona();
        this.unPanelPersona.getValidador().limpiarCampo(this.unPanelPersona.getJtf_buscarPersona());
    }

}
