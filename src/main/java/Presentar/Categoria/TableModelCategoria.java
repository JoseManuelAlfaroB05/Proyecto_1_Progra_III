package Presentar.Categoria;

import Recursos.CategoriaRecurso;
import java.util.List;

public class TableModelCategoria extends javax.swing.table.AbstractTableModel {
    private List<CategoriaRecurso> rows;
    private int[] cols;
    private String[] colNames;

    public static final int ID = 0;
    public static final int DESCRIPCION = 1;

    public TableModelCategoria(int[] cols, List<CategoriaRecurso> rows) {
        this.cols = cols;
        this.rows = rows;
        initColNames();
    }

    private void initColNames() {
        colNames = new String[2];
        colNames[ID] = "ID";
        colNames[DESCRIPCION] = "Descripción";
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
        CategoriaRecurso categoria = rows.get(row);
        switch (cols[col]) {
            case ID:
                return categoria.getVarId();
            case DESCRIPCION:
                return categoria.getDescripcion();
            default:
                return "";
        }
    }

    public CategoriaRecurso getCategoria(int rowIndex) {
        return rows.get(rowIndex);
    }
}