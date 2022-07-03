package vjvm.interpreter.instruction.comparisons;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.var;
import vjvm.interpreter.instruction.Instruction;
import vjvm.runtime.JThread;
import vjvm.runtime.OperandStack;
import vjvm.runtime.ProgramCounter;
import vjvm.runtime.classdata.MethodInfo;

import java.util.function.BiFunction;
import java.util.function.Function;

public class LCMP extends Instruction {
  private final String name;
  private final BiFunction<Long, Long, Integer> compareFunc;
  private final Function<OperandStack, Long> popFunc;

  public LCMP(ProgramCounter programCounter, MethodInfo methodInfo) {
    this.name = "lcmp";
    this.compareFunc = Long::compare;
    this.popFunc = OperandStack::popLong;
  }

  @Override
  public void run(JThread thread) {
    var stack = thread.top().stack();
    var value2 = popFunc.apply(stack);
    var value1 = popFunc.apply(stack);
    stack.pushInt(compareFunc.apply(value1, value2));
  }

  @Override
  public String toString() {
    return name;
  }
}
