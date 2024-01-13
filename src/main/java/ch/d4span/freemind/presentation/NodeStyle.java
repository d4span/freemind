package ch.d4span.freemind.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NodeStyle {
  BUBBLE("bubble"),
  FORK("fork"),
  COMBINED("combined"),
  AS_PARENT("as_parent");

  private final String style;
}
