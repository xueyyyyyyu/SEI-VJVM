package vjvm.classloader.searchpath;

import lombok.var;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.jar.JarFile;

public class JarSearchPath extends ClassSearchPath {
  private JarFile file;

  @SneakyThrows
  public JarSearchPath(String name){
    try {
      file = new JarFile(name);
    } catch (FileNotFoundException e){
      file = null;
    }
  }

  @Override
  @SneakyThrows
  public InputStream findClass(String name){
    if(file == null)
      return null;

    var entry = file.getEntry(name + ".class");
    return entry == null ? null : file.getInputStream(entry);
  }

  @Override
  @SneakyThrows
  public void close(){
    if(file != null)
      file.close();
  }

}
