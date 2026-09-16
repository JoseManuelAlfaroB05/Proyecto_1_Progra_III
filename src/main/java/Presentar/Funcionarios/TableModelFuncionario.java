package Presentar.Funcionarios;

import Recursos.User;
import java.util.List;

public class TableModelFuncionario extends javax.swing.table.AbstractTableModel {
    private List<User> rows;
    private int[] cols;
    private String[] colNames;

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;

    public TableModelFuncionario(int[] cols, List<User> rows) {
        this.cols = cols;
        this.rows = rows;
        initColNames();
    }

    private void initColNames() {
        colNames = new String[3];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[TELEFONO] = "Teléfono";
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[cols[col]];
    }

    @Override
    public Object getValueAt(int row, int col) {
        User funcionario = rows.get(row);

        switch (cols[col]) {
            case ID:
                return funcionario.getVarId();
            case NOMBRE:
                return funcionario.getVarNombre();
            case TELEFONO:
                return funcionario.getVarTelefono();
            default:
                return "";
        }
    }
}