package freemind.controller.actions.generated.instance;
/* CollaborationAction...*/
public class CollaborationAction extends XmlAction {
  /* constants from enums*/
  public static final String REQUEST_MAP_SHARING = "request_map_sharing";
  public static final String ACCEPT_MAP_SHARING = "accept_map_sharing";
  public static final String STOP_MAP_SHARING = "stop_map_sharing";
  public static final String DECLINE_MAP_SHARING = "decline_map_sharing";
  protected String user;
  protected String timestamp;
  protected String cmd;
  protected String map;
  protected String filename;
  public String getUser(){
    return user;
  }
  public String getTimestamp(){
    return timestamp;
  }
  public String getCmd(){
    return cmd;
  }
  public String getMap(){
    return map;
  }
  public String getFilename(){
    return filename;
  }
  public void setUser(String value){
    this.user = value;
  }
  public void setTimestamp(String value){
    this.timestamp = value;
  }
  public void setCmd(String value){
    this.cmd = value;
  }
  public void setMap(String value){
    this.map = value;
  }
  public void setFilename(String value){
    this.filename = value;
  }
} /* CollaborationAction*/
