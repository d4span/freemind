package freemind.controller.actions.generated.instance;
/* AttributeTableProperties...*/
import java.util.ArrayList;
public class AttributeTableProperties extends XmlAction {
  /* constants from enums*/
  public void addTableColumnOrder(TableColumnOrder tableColumnOrder) {
    tableColumnOrderList.add(tableColumnOrder);
  }

  public void addAtTableColumnOrder(int position, TableColumnOrder tableColumnOrder) {
    tableColumnOrderList.add(position, tableColumnOrder);
  }

  public TableColumnOrder getTableColumnOrder(int index) {
    return (TableColumnOrder)tableColumnOrderList.get( index );
  }

  public void removeFromTableColumnOrderElementAt(int index) {
    tableColumnOrderList.remove( index );
  }

  public int sizeTableColumnOrderList() {
    return tableColumnOrderList.size();
  }

  public void clearTableColumnOrderList() {
    tableColumnOrderList.clear();
  }

  public java.util.List getListTableColumnOrderList() {
    return java.util.Collections.unmodifiableList(tableColumnOrderList);
  }
    protected ArrayList tableColumnOrderList = new ArrayList();

} /* AttributeTableProperties*/
