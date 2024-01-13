package ch.d4span.freemind.presentation;

public interface DrawableNode {

  boolean isLeft();

  void setLeft(boolean isLeft);

  void setShiftY(int y);

  int getShiftY();

  int calcShiftY();

  void setVGap(int i);

  int getVGap();

  void setHGap(int i);

  int getHGap();
}
