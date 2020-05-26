/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Empleado;

/**
 *
 * @author Ariel
 */
public class TablaEmpleadoModelo extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "Nombre", "Apellido", "DNI", "Tipo Empleado", "Area"};
    private List<Empleado> empleados;

    public TablaEmpleadoModelo() {
        empleados = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return empleados == null ? 0 : empleados.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retorno = null;
        Empleado empleado = empleados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                retorno = rowIndex;
                break;
            case 1:
                retorno = empleado.getNombre();
                break;
            case 2:
                retorno = empleado.getApellido();
                break;
            case 3:
                retorno = empleado.getDni();
                break;
            case 4:
                retorno = empleado.getUnTipoEmpleado().getDescripcion();
                break;
            case 5:
                retorno = empleado.getUnAreaA().getNombre();
                break;
        }

        return retorno;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Empleado obtenerEmpleadoEn(int fila) {
        return empleados.get(fila);
    }

    public int buscarFilaEmpleado(Empleado empleadoBuscado) {
        int fila = 0;
        int contador = 0;
        for (Empleado empleadoRecorrido : empleados) {
            contador = contador + 1;
            if (empleadoBuscado.getId() == empleadoRecorrido.getId()) {
                fila = contador;
            }
        }
        return fila;
    }

}
