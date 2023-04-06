package com.lightdev.app.shtm;

import javax.swing.Icon;

public interface TextResources extends UIResources {
    String getString(String var1);

    default Icon getIcon(String pKey) {
        throw new UnsupportedOperationException();
    }
}