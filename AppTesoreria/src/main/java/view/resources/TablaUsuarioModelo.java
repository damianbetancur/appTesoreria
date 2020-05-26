/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import model.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class TablaUsuarioModelo extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "NickName", "Persona Asociada", "Empleado Asociado", "Tipo Usuario"};
    private List<Usuario> usuarios;

    public TablaUsuarioModelo() {
        usuarios = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return usuarios == null ? 0 : usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retorno = null;
        Usuario usuario = usuarios.get(rowIndex);

        switch (columnIndex) {
            case 0:
                retorno = rowIndex;
                break;
            case 1:
                retorno = usuario.getNombre();
                break;
            case 2:
                if (usuario.getUnaPersona()!=null) {
                    retorno = usuario.getUnaPersona().getDni();
                }else{
                    retorno ="Sin Asignar";
                }
                break;
            case 3:
                if (usuario.getUnEmpleado() != null) {
                    retorno = usuario.getUnEmpleado().getDni();
                }else{
                    retorno ="Sin Asignar";
                }

                break;
            case 4:
                if (usuario.getUnEmpleado() != null) {
                    retorno = usuario.getTipoUsuario().getDescripcion();
                }
                break;
        }

        return retorno;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario obtenerUsuarioEn(int fila) {
        return usuarios.get(fila);
    }

    public int buscarFilaUsuario(Usuario usuarioBuscada) {
        int fila = 0;
        int contador = 0;
        for (Usuario usuarioRecorrido : usuarios) {
            contador = contador + 1;
            if (usuarioBuscada.getId() == usuarioRecorrido.getId()) {
                fila = contador;
            }
        }
        return fila;
    }

}
