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

/**
 *
 * @author Ariel
 */
public class TablaPersonaModelo extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "Nombre", "Apellido", "DNI", "Tipo Persona"};
    private List<Persona> personas;

    public TablaPersonaModelo() {
        personas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return personas == null ? 0 : personas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retorno = null;
        Persona persona = personas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                retorno = rowIndex;
                break;
            case 1:
                retorno = persona.getNombre();
                break;
            case 2:
                retorno = persona.getApellido();
                break;
            case 3:
                retorno = persona.getDni();
                break;
            case 4:
                retorno = persona.getUnTipoPersona().getDescripcion();
                break;
        }

        return retorno;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
    
    
    public Persona obtenerPersonaEn (int fila) {
        return personas.get(fila);
    }
    
    public int buscarFilaPersona(Persona personaBuscada){
        int fila = 0;
        int contador = 0;
        for (Persona personaRecorrido : personas) {
            contador = contador +1;
            if (personaBuscada.getId()==personaRecorrido.getId()) {
                fila = contador;
            }
        }
        return fila;
    }

}
