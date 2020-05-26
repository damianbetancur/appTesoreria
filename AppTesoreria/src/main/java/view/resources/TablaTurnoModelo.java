/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Turno;

/**
 *
 * @author Ariel
 */
public class TablaTurnoModelo extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "Hora Turno", "Estado Turno", "Tipo Atención", "Tipo Tramite"};
    private List<Turno> turnos;

    public TablaTurnoModelo() {
        turnos = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return turnos == null ? 0 : turnos.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retorno = null;
        Turno turno = turnos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                retorno = rowIndex;
                break;
            case 1:
                retorno = turno.getUnaHoraTurno().getDescripcion();
                break;
            case 2:
                retorno = turno.getUnEstadoTurno().getNombre();
                break;
            case 3:
                retorno = turno.getUnTipoAtencion().getNombre();
                break;
            case 4:
                retorno = turno.getUnTipoTramite().getNombre();
                break;
        }

        return retorno;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
    
    
    public Turno obtenerTurnoEn (int fila) {
        return turnos.get(fila);
    }
    
    public int buscarFilaTurno(Turno turnoBuscado){
        int fila = 0;
        int contador = 0;
        for (Turno turnoRecorrido : turnos) {
            contador = contador +1;
            if (turnoBuscado.getId()==turnoRecorrido.getId()) {
                fila = contador;
            }
        }
        return fila;
    }

}
