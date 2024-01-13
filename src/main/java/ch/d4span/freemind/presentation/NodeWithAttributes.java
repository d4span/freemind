package ch.d4span.freemind.presentation;

import freemind.modes.attributes.Attribute;
import java.util.List;

public interface NodeWithAttributes {

  List<String> getAttributeKeyList();

  List<Attribute> getAttributes();

  int getAttributeTableLength();

  Attribute getAttribute(int pPosition);

  String getAttribute(String pKey);

  int getAttributePosition(String key);

  void setAttribute(int pPosition, Attribute pAttribute);

  void insertAttribute(int pPosition, Attribute pAttribute);

  int addAttribute(Attribute pAttribute);

  void removeAttribute(int pPosition);
}
