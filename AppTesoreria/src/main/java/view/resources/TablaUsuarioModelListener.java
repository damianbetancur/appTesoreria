/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.awt.Color;
import view.PanelUsuario;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Ariel
 */
public class TablaUsuarioModelListener implements ListSelectionListener {

    private final PanelUsuario unPanelUsuario;

    public TablaUsuarioModelListener(PanelUsuario pantallaContenido) {
        this.unPanelUsuario = pantallaContenido;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // obtenemos la escuela seleccionada
        this.unPanelUsuario.seleccionarUsuario();        
        this.unPanelUsuario.getValidador().habilitarBoton(true, this.unPanelUsuario.getJbtn_eliminar(), new Color(30, 132, 73), Color.WHITE, null, null);
        this.unPanelUsuario.getValidador().habilitarBoton(true, this.unPanelUsuario.getJbtn_modificar(), new Color(30, 132, 73), Color.WHITE, null, null);
        this.unPanelUsuario.getValidador().limpiarCampo(this.unPanelUsuario.getJtf_buscarPersona());
    }

}
