/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.LineaDeMovimiento;

/**
 *
 * @author Ariel
 */
public class TablaLineasDeMovimientoModelo extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "Concepto", "Monto", "Credito", "Debito"};
    private List<LineaDeMovimiento> lineasDeMovimientos;

    public TablaLineasDeMovimientoModelo() {
        lineasDeMovimientos = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return lineasDeMovimientos == null ? 0 : lineasDeMovimientos.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retorno = null;
        LineaDeMovimiento lineaDeMovimiento = lineasDeMovimientos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                retorno = rowIndex;
                break;
            case 1:
                retorno = lineaDeMovimiento.getUnConcepto().getDescripcion();
                break;
            case 2:
                retorno = lineaDeMovimiento.getMonto();
                break;
            case 3:
                retorno = lineaDeMovimiento.getUnConcepto().getUnTipoMovimiento().getDescripcion();
                break;
            case 4:                
                if (lineaDeMovimiento.getUnConcepto().getUnTipoMovimiento().getDescripcion().equals("Credito")) {
                    retorno = lineaDeMovimiento.getUnConcepto().getUnTipoMovimiento().getDescripcion();
                }
                
                break;
           
        }

        return retorno;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    public void setLineasDeMovimientos(List<LineaDeMovimiento> lineasDeMovimientos) {
        this.lineasDeMovimientos = lineasDeMovimientos;
    }

   
    

}
