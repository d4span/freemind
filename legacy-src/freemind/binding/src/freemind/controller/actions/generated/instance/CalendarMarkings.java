package freemind.controller.actions.generated.instance;
/* CalendarMarkings...*/
import java.util.ArrayList;
public class CalendarMarkings extends XmlAction {
  /* constants from enums*/
  public void addCalendarMarking(CalendarMarking calendarMarking) {
    calendarMarkingList.add(calendarMarking);
  }

  public void addAtCalendarMarking(int position, CalendarMarking calendarMarking) {
    calendarMarkingList.add(position, calendarMarking);
  }

  public CalendarMarking getCalendarMarking(int index) {
    return (CalendarMarking)calendarMarkingList.get( index );
  }

  public void removeFromCalendarMarkingElementAt(int index) {
    calendarMarkingList.remove( index );
  }

  public int sizeCalendarMarkingList() {
    return calendarMarkingList.size();
  }

  public void clearCalendarMarkingList() {
    calendarMarkingList.clear();
  }

  public java.util.List getListCalendarMarkingList() {
    return java.util.Collections.unmodifiableList(calendarMarkingList);
  }
    protected ArrayList calendarMarkingList = new ArrayList();

} /* CalendarMarkings*/
