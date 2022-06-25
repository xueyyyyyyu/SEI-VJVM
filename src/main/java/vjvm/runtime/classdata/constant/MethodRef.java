package vjvm.runtime.classdata.constant;

import lombok.SneakyThrows;
import lombok.var;
import sun.tools.jconsole.ProxyClient;
import vjvm.runtime.JClass;
import vjvm.runtime.classdata.MethodInfo;

import java.io.DataInput;

public class MethodRef extends Constant{
  private final int classIndex;
  private final int nameAndTypeIndex;
  private final JClass self;
  private final boolean interface_;

  private String className;
  private NameAndTypeConstant nameAndType;

  @SneakyThrows
  MethodRef(DataInput input, JClass jClass, boolean interface_){
    classIndex = input.readUnsignedShort();
    nameAndTypeIndex = input.readUnsignedShort();
    this.self = jClass;
    this.interface_ = interface_;
  }

  public String getClassName(){
    if(className == null){
      className = ((ClassRef)self.constantPool().constant(classIndex)).name();
    }
    return className;
  }

  public NameAndTypeConstant nameAndType(){
    if(nameAndType == null){
      nameAndType = (NameAndTypeConstant) self.constantPool().constant(nameAndTypeIndex);
    }
    return nameAndType;
  }


  @Override
  public String toString(){
    if(!interface_) {
      return String.format("Methodref: %s.%s:%s", className, nameAndType.name(), nameAndType.type());
    }
    else {
      return String.format("InterfaceMethodref: %s.%s:%s", className, nameAndType.name(), nameAndType.type());
    }
  }
}
