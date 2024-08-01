package org.freemind.tree;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.EventListener;

public class EventListenerList implements Serializable {
  private static final Object[] NULL_ARRAY = new Object[0];
  protected transient volatile Object[] listenerList;

  public EventListenerList() {
    this.listenerList = EventListenerList.NULL_ARRAY;
  }

  public Object[] getListenerList() {
    return this.listenerList;
  }

  public <T extends EventListener> EventListener[] getListeners(Class<T> t) {
    Object[] lList = this.listenerList;
    int n = this.getListenerCount(lList, t);
    var result = (EventListener[]) Array.newInstance(t, n);
    int j = 0;

    for (int i = lList.length - 2; i >= 0; i -= 2) {
      if (lList[i] == t) {
        var tmp = (EventListener) lList[i + 1];
        result[j++] = tmp;
      }
    }

    return result;
  }

  public int getListenerCount() {
    return this.listenerList.length / 2;
  }

  public int getListenerCount(Class<?> t) {
    Object[] lList = this.listenerList;
    return this.getListenerCount(lList, t);
  }

  private int getListenerCount(Object[] list, Class<?> t) {
    int count = 0;

    for (int i = 0; i < list.length; i += 2) {
      if (t == (Class) list[i]) {
        ++count;
      }
    }

    return count;
  }

  public synchronized <T extends EventListener> void add(Class<T> t, T l) {
    if (l != null) {
      if (!t.isInstance(l)) {
        String var10002 = String.valueOf(l);
        throw new IllegalArgumentException(
            "Listener " + var10002 + " is not of type " + String.valueOf(t));
      } else {
        if (this.listenerList == EventListenerList.NULL_ARRAY) {
          this.listenerList = new Object[] {t, l};
        } else {
          int i = this.listenerList.length;
          Object[] tmp = new Object[i + 2];
          System.arraycopy(this.listenerList, 0, tmp, 0, i);
          tmp[i] = t;
          tmp[i + 1] = l;
          this.listenerList = tmp;
        }
      }
    }
  }

  public synchronized <T extends EventListener> void remove(Class<T> t, T l) {
    if (l != null) {
      if (!t.isInstance(l)) {
        String var10002 = String.valueOf(l);
        throw new IllegalArgumentException(
            "Listener " + var10002 + " is not of type " + String.valueOf(t));
      } else {
        int index = -1;

        for (int i = this.listenerList.length - 2; i >= 0; i -= 2) {
          if (this.listenerList[i] == t && this.listenerList[i + 1].equals(l)) {
            index = i;
            break;
          }
        }

        if (index != -1) {
          Object[] tmp = new Object[this.listenerList.length - 2];
          System.arraycopy(this.listenerList, 0, tmp, 0, index);
          if (index < tmp.length) {
            System.arraycopy(this.listenerList, index + 2, tmp, index, tmp.length - index);
          }

          this.listenerList = tmp.length == 0 ? EventListenerList.NULL_ARRAY : tmp;
        }
      }
    }
  }

  private void writeObject(ObjectOutputStream s) throws IOException {
    Object[] lList = this.listenerList;
    s.defaultWriteObject();

    for (int i = 0; i < lList.length; i += 2) {
      Class<?> t = (Class) lList[i];
      EventListener l = (EventListener) lList[i + 1];
      if (l instanceof Serializable) {
        s.writeObject(t.getName());
        s.writeObject(l);
      }
    }

    s.writeObject((Object) null);
  }

  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    this.listenerList = EventListenerList.NULL_ARRAY;
    s.defaultReadObject();

    Object listenerTypeOrNull;
    while (null != (listenerTypeOrNull = s.readObject())) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      EventListener l = (EventListener) s.readObject();
      String name = (String) listenerTypeOrNull;
      throw new UnsupportedOperationException();
      // ReflectUtil.checkPackageAccess(name);
      // Class<EventListener> tmp = Class.forName(name, true, cl);
      // this.add(tmp, l);
    }
  }

  @Override
  public String toString() {
    Object[] lList = this.listenerList;
    String s = "EventListenerList: ";
    s = s + lList.length / 2 + " listeners: ";

    for (int i = 0; i <= lList.length - 2; i += 2) {
      s = s + " type " + ((Class) lList[i]).getName();
      s = s + " listener " + String.valueOf(lList[i + 1]);
    }

    return s;
  }
}
