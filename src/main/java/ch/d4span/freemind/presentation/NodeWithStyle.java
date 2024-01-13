package ch.d4span.freemind.presentation;

public interface NodeWithStyle {

  String getBareStyle();

  NodeStyle getStyle();

  void setStyle(NodeStyle style);

  boolean hasStyle();
}
