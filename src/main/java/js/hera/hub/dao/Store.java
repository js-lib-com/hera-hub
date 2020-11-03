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

import js.json.Json;
import js.json.JsonException;
import js.lang.GType;
import js.log.Log;
import js.log.LogFactory;
import js.tiny.container.core.AppContext;
import js.util.Classes;

public class Store<T>
{
  private static final Log log = LogFactory.getLog(Store.class);

  private final AppContext context;
  private final Json json;
  private final String name;

  private final Class<T> type;

  private SortedMap<Integer, T> store;

  public Store(AppContext context, String storeName, Class<T> type)
  {
    this.context = context;
    this.json = context.getInstance(Json.class);
    this.name = storeName;
    this.type = type;
  }

  public void load() throws JsonException, ClassCastException, IOException
  {
    File storeFile = context.getAppFile(name);
    if(!storeFile.exists()) {
      store = new TreeMap<Integer, T>();
      return;
    }

    try (Reader reader = new FileReader(storeFile)) {
      store = json.parse(reader, new GType(SortedMap.class, Integer.class, type));
      log.debug("Store |%s| loaded.", name);
    }

    if(store == null) {
      store = new TreeMap<Integer, T>();
    }
  }

  public void save()
  {
    try (Writer writer = new FileWriter(context.getAppFile(name))) {
      json.stringify(writer, store);
      log.debug("Store |%s| saved.", name);
    }
    catch(IOException e) {
      log.error(e);
    }
  }

  public synchronized T get(int id)
  {
    return store.get(id);
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
    return Collections.unmodifiableList(new ArrayList<T>(store.values()));
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

  public static interface Predicate<T>
  {
    boolean test(T t);
  }
}
