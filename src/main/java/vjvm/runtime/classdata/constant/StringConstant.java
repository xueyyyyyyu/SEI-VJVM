package vjvm.runtime.classdata.constant;

import lombok.SneakyThrows;
import org.apache.commons.text.StringEscapeUtils;
import vjvm.runtime.JClass;

import java.io.DataInput;

public class StringConstant extends Constant{
  private final JClass self;
  private final int index;
  private UTF8Constant utf8;

  @SneakyThrows
  StringConstant(DataInput input, JClass jClass){
    index = input.readUnsignedShort();
    this.self = jClass;
  }

  public UTF8Constant utf8(){
    if(utf8 == null){
      utf8 = (UTF8Constant) self.constantPool().constant(index);
    }
    return utf8;
  }

  @Override
  public String toString(){
    return String.format("String: \"%s\"", StringEscapeUtils.escapeJava(utf8().value()));
  }
}
