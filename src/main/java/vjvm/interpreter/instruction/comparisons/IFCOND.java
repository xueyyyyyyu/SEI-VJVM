package vjvm.interpreter.instruction.comparisons;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.var;
import vjvm.interpreter.instruction.Instruction;
import vjvm.runtime.JThread;
import vjvm.runtime.OperandStack;
import vjvm.runtime.ProgramCounter;
import vjvm.runtime.Slots;
import vjvm.runtime.classdata.MethodInfo;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class IFCOND extends Instruction{
  private BiPredicate<Integer, Integer> pred;
  private final String name;
  private final int offset;

  private IFCOND(BiPredicate<Integer, Integer> pred, String name, ProgramCounter pc) {
    this.pred = pred;
    this.name = name;
    this.offset = pc.short_() - 3;
  }

  public static IFCOND IFEQ(ProgramCounter pc, MethodInfo method) {
    return new IFCOND(Objects::equals,"ifeq", pc);
  }

  public static IFCOND IFNE(ProgramCounter pc, MethodInfo method) {
    return new IFCOND((x, y) -> !x.equals(y),"ifne", pc);
  }

  public static IFCOND IFLT(ProgramCounter pc, MethodInfo method) {
    return new IFCOND((x, y) -> x < y,"iflt", pc);
  }

  public static IFCOND IFGE(ProgramCounter pc, MethodInfo method) {
    return new IFCOND((x, y) -> x >= y,"ifge", pc);
  }

  public static IFCOND IFGT(ProgramCounter pc, MethodInfo method) {
    return new IFCOND((x, y) -> x > y,"ifgt", pc);
  }

  public static IFCOND IFLE(ProgramCounter pc, MethodInfo method) {
    return new IFCOND((x, y) -> x <= y,"ifle", pc);
  }

  @Override
  public void run(JThread thread) {
    var stack = thread.top().stack();
    var value = stack.popInt();
    var pc = thread.pc();
    if(pred.test(value, 0))
      pc.move(offset);
  }

  @Override
  public String toString(){
    return String.format("%s %d", name, offset);
  }
}
