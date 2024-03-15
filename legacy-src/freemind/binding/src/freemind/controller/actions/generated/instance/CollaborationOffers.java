package freemind.controller.actions.generated.instance;
/* CollaborationOffers...*/
import java.util.ArrayList;
public class CollaborationOffers extends CollaborationActionBase {
  /* constants from enums*/
  protected boolean isSingleOffer;
  public boolean getIsSingleOffer(){
    return isSingleOffer;
  }
  public void setIsSingleOffer(boolean value){
    this.isSingleOffer = value;
  }
  public void addCollaborationMapOffer(CollaborationMapOffer collaborationMapOffer) {
    collaborationMapOfferList.add(collaborationMapOffer);
  }

  public void addAtCollaborationMapOffer(int position, CollaborationMapOffer collaborationMapOffer) {
    collaborationMapOfferList.add(position, collaborationMapOffer);
  }

  public CollaborationMapOffer getCollaborationMapOffer(int index) {
    return (CollaborationMapOffer)collaborationMapOfferList.get( index );
  }

  public void removeFromCollaborationMapOfferElementAt(int index) {
    collaborationMapOfferList.remove( index );
  }

  public int sizeCollaborationMapOfferList() {
    return collaborationMapOfferList.size();
  }

  public void clearCollaborationMapOfferList() {
    collaborationMapOfferList.clear();
  }

  public java.util.List getListCollaborationMapOfferList() {
    return java.util.Collections.unmodifiableList(collaborationMapOfferList);
  }
    protected ArrayList collaborationMapOfferList = new ArrayList();

} /* CollaborationOffers*/
