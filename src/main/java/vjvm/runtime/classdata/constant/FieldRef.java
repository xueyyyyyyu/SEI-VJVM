package vjvm.runtime.classdata.constant;

import vjvm.runtime.JClass;

import java.io.DataInput;

public class FieldRef extends Constant{
  private final int classIndex;
  private final int nameAneTypeIndex;
  private final JClass self;

  FieldRef(DataInput input, JClass jClass){
    classIndex = input.readUnsignedShort();
    nameAneTypeIndex = input.readUnsignedShort();
    this.self = jClass;
  }
}
