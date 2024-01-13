package ch.d4span.freemind.presentation;

import java.awt.Color;
import java.awt.Font;

public interface FormattedTextNode {
  boolean isBold();

  boolean isItalic();

  boolean isUnderlined();

  boolean isStrikethrough();

  Font getFont();

  void setFont(Font font);

  Integer getFontSize();

  void setFontSize(Integer fontSize);

  String getFontFamilyName();

  void setColor(Color color);

  Color getColor();

  Color getBackgroundColor();

  void setBackgroundColor(Color color);
}
