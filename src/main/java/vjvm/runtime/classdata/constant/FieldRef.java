package vjvm.runtime.classdata.constant;

import lombok.SneakyThrows;
import sun.jvm.hotspot.runtime.ClassConstants;
import vjvm.runtime.JClass;

import java.io.DataInput;

public class FieldRef extends Constant{
  private final int classIndex;
  private final int nameAneTypeIndex;
  private final JClass self;
  private String name;
  private NameAndTypeConstant nameAndType;


  @SneakyThrows
  FieldRef(DataInput input, JClass jClass){
    classIndex = input.readUnsignedShort();
    nameAneTypeIndex = input.readUnsignedShort();
    this.self = jClass;
  }

  public String className(){
    if(name == null){
      name = ((ClassRef)self.constantPool().constant(classIndex)).name();
    }
    return name;
  }

  public NameAndTypeConstant getNameAndType(){
    if(nameAndType == null){
      nameAndType = (NameAndTypeConstant) self.constantPool().constant(nameAneTypeIndex);
    }
    return nameAndType;
  }

  @Override
  public String toString(){
    return String.format("Fieldref: %s.%s:%s", name, nameAndType.name(), nameAndType.type());
  }
}
