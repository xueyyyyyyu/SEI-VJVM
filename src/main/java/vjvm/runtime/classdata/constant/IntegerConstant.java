package vjvm.runtime.classdata.constant;

import lombok.var;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.DataInput;

public class IntegerConstant extends Constant {
  private final int value;

  @SneakyThrows
  IntegerConstant(DataInput input) {
    value = input.readInt();
  }

  public Integer value(){
    return value;
  }

  @Override
  public String toString() {
    return String.format("Integer: %d", value());
  }
}
