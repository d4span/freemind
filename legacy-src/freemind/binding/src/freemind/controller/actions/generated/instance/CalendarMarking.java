package freemind.controller.actions.generated.instance;
/* CalendarMarking...*/
public class CalendarMarking {
  /* constants from enums*/
  public static final String NEVER = "never";
  public static final String YEARLY = "yearly";
  public static final String YEARLY_EVERY_NTH_DAY = "yearly_every_nth_day";
  public static final String YEARLY_EVERY_NTH_WEEK = "yearly_every_nth_week";
  public static final String YEARLY_EVERY_NTH_MONTH = "yearly_every_nth_month";
  public static final String MONTHLY = "monthly";
  public static final String MONTHLY_EVERY_NTH_DAY = "monthly_every_nth_day";
  public static final String MONTHLY_EVERY_NTH_WEEK = "monthly_every_nth_week";
  public static final String WEEKLY = "weekly";
  public static final String WEEKLY_EVERY_NTH_DAY = "weekly_every_nth_day";
  public static final String DAILY = "daily";
  protected String name;
  protected String color;
  protected long startDate;
  protected long endDate;
  protected String repeatType;
  protected int repeatEachNOccurence;
  protected int firstOccurence;
  public String getName(){
    return name;
  }
  public String getColor(){
    return color;
  }
  public long getStartDate(){
    return startDate;
  }
  public long getEndDate(){
    return endDate;
  }
  public String getRepeatType(){
    return repeatType;
  }
  public int getRepeatEachNOccurence(){
    return repeatEachNOccurence;
  }
  public int getFirstOccurence(){
    return firstOccurence;
  }
  public void setName(String value){
    this.name = value;
  }
  public void setColor(String value){
    this.color = value;
  }
  public void setStartDate(long value){
    this.startDate = value;
  }
  public void setEndDate(long value){
    this.endDate = value;
  }
  public void setRepeatType(String value){
    this.repeatType = value;
  }
  public void setRepeatEachNOccurence(int value){
    this.repeatEachNOccurence = value;
  }
  public void setFirstOccurence(int value){
    this.firstOccurence = value;
  }
} /* CalendarMarking*/
