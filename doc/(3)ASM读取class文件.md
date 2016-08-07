
示例代码：
```
public class ReaderClass {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader("zgh.xxx.asm.JavaToASM");
        reader.accept(new TraceClassVisitor( new PrintWriter(System.out)),0);
    }
}
```
运行结果：

```
// class version 52.0 (52)
// access flags 0x21
public class zgh/xxx/asm/JavaToASM {

  // compiled from: JavaToASM.java

  // access flags 0x2
  private Ljava/lang/Integer; i

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 6 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this Lzgh/xxx/asm/JavaToASM; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x1
  public getI()Ljava/lang/Integer;
   L0
    LINENUMBER 10 L0
    ALOAD 0
    GETFIELD zgh/xxx/asm/JavaToASM.i : Ljava/lang/Integer;
    ARETURN
   L1
    LOCALVARIABLE this Lzgh/xxx/asm/JavaToASM; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x1
  public setI(Ljava/lang/Integer;)V
   L0
    LINENUMBER 13 L0
    ALOAD 0
    ALOAD 1
    PUTFIELD zgh/xxx/asm/JavaToASM.i : Ljava/lang/Integer;
   L1
    LINENUMBER 14 L1
    RETURN
   L2
    LOCALVARIABLE this Lzgh/xxx/asm/JavaToASM; L0 L2 0
    LOCALVARIABLE i Ljava/lang/Integer; L0 L2 1
    MAXSTACK = 2
    MAXLOCALS = 2
}
```

###### ClassReader
解析编译过的class的字节数组，通过accept方法设置ClassVisitor实例，然后解析class的字节数组，根据对应的段调用调用ClassVisitor的visitXXX方法


###### TraceClassVisitor
实现ClassVisitor接口，调用visitXXX方法是把相应的参数信息放到buf里并在调用visitEnd时把buf传递给PrintWriter 调用print方法

