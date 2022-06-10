package vjvm.classloader;

import lombok.var;
import lombok.Getter;
import lombok.SneakyThrows;
import vjvm.classloader.searchpath.ClassSearchPath;
import vjvm.runtime.JClass;
import vjvm.vm.VMContext;
import vjvm.utils.UnimplementedError;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class JClassLoader implements Closeable {
  private final JClassLoader parent;
  private final ClassSearchPath[] searchPaths;
  private final HashMap<String, JClass> definedClass = new HashMap<>();
  @Getter
  private final VMContext context;

  public JClassLoader(JClassLoader parent, ClassSearchPath[] searchPaths, VMContext context) {
    this.context = context;
    this.parent = parent;
    this.searchPaths = searchPaths;
  }

  /**
   * Load class
   *
   * If a class is found, construct it using the data returned by ClassSearchPath.findClass and return it.
   *
   * Otherwise, return null.
   */
  public JClass loadClass(String descriptor) {
    assert descriptor.charAt(descriptor.length() - 1) ==';';

    //parent
    JClass jClass;
    if(parent != null && (jClass = parent.loadClass(descriptor)) != null){
      return jClass;
    }

    if((jClass = definedClass.get(descriptor)) != null){
      return jClass;
    }

    //self
    var name = descriptor.substring(1, descriptor.length() - 1);
    for(var p : searchPaths){
      var iStream = p.findClass(name);
      if(iStream != null){
        return defineNonArrayClass(descriptor, iStream);
      }
    }

    return null;
    // To construct a JClass, use the following constructor
    // return new JClass(new DataInputStream(istream_from_file), this);
  }

  @Override
  @SneakyThrows
  public void close() {
    for (var s : searchPaths)
      s.close();
  }

  public JClass defineNonArrayClass(String descriptor, InputStream data){
    var ret = new JClass(new DataInputStream(data), this);

    definedClass.put(descriptor, ret);
    return ret;
  }
}
