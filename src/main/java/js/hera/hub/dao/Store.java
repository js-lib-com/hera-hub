package js.hera.hub.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;

import com.jslib.api.json.Json;
import com.jslib.api.json.JsonException;
import com.jslib.api.log.Log;
import com.jslib.api.log.LogFactory;
import com.jslib.lang.GType;
import com.jslib.util.Classes;

import js.hera.hub.Application;

public class Store<T>
{
  private static final Log log = LogFactory.getLog(Store.class);

  private final Application app;
  private final Dao dao;
  private final Json json;
  private final String name;

  private final Class<T> type;

  private SortedMap<Integer, T> store;

  public Store(Application app, Dao dao, String storeName, Class<T> type)
  {
    this.app = app;
    this.dao = dao;
    this.json = Classes.loadService(Json.class);
    this.name = storeName;
    this.type = type;
  }

  public void load()
  {
    File storeFile = app.getAppFile(name);
    if(!storeFile.exists()) {
      store = new TreeMap<Integer, T>();
      return;
    }

    try (Reader reader = new FileReader(storeFile)) {
      store = json.parse(reader, new GType(SortedMap.class, Integer.class, type));
      log.debug("Store |%s| loaded.", name);
    }
    catch(IllegalArgumentException | JsonException | ClassCastException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if(store == null) {
      store = new TreeMap<Integer, T>();
    }
  }

  public void save()
  {
    try (Writer writer = new FileWriter(app.getAppFile(name))) {
      json.stringify(writer, store);
      log.debug("Store |%s| saved.", name);
    }
    catch(IOException e) {
      log.error(e);
    }
  }

  public synchronized T get(int id)
  {
    T t = store.get(id);
    if(t instanceof PostLoad) {
      ((PostLoad)t).postLoad(dao);
    }
    return t;
  }

  public synchronized void put(int id, T t)
  {
    if(id == 0) {
      id = store.isEmpty() ? 1 : store.lastKey() + 1;
      Classes.setFieldValue(t, "id", id);
    }
    store.put(id, t);
    save();
  }

  public synchronized void remove(int id)
  {
    store.remove(id);
    save();
  }

  public synchronized void clear()
  {
    store.clear();
    save();
  }

  /**
   * Return an immutable snapshot of this store values.
   * 
   * @return this store values.
   */
  public synchronized List<T> values()
  {
    List<T> values = new ArrayList<T>(store.values());
    for(T value : values) {
      if(value instanceof PostLoad) {
        ((PostLoad)value).postLoad(dao);
      }
    }
    return Collections.unmodifiableList(values);
  }

  public synchronized List<T> values(Predicate<T> predicate)
  {
    List<T> list = new ArrayList<T>();
    for(T item : store.values()) {
      if(predicate.test(item)) {
        list.add(item);
      }
    }
    return list;
  }

  public synchronized T value(Predicate<T> predicate)
  {
    for(T item : store.values()) {
      if(predicate.test(item)) {
        return item;
      }
    }
    return null;
  }
}
