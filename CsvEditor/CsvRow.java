package application;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class CsvRow {
	private final List<SimpleStringProperty> columns;

    public CsvRow() {
        this.columns = new ArrayList<>();
    }
    public CsvRow(List<SimpleStringProperty> columns) {
        this.columns = columns;
    }
    public List<SimpleStringProperty> getColumns() {
        return columns;
    }
    public void addColumn(int index, String value) {
        if (index <= columns.size()) {
            columns.add(index, new SimpleStringProperty(value));
        }
    }
    public void addColumn(int index) {
        addColumn(index, "");
    }
    public boolean isEmpty() {
        if (columns == null || columns.isEmpty()) {
            return true;
        }
        for (SimpleStringProperty prop : columns) {
            if (!prop.getValueSafe().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        return "CsvRow: " + columns;
    }
    public void removeColumn(int index) {
        if (index < columns.size()) {
            columns.remove(index);
        }
    }
}
