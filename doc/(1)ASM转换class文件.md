添加serialVersionUID示例：
```
public class TestAdapter {
    public static void main(String[] args) throws IOException {
        //读取JavaToASM
        ClassReader reader = new ClassReader("zgh.xxx.asm.JavaToASM");
        ClassWriter classWriter = new ClassWriter(0);
        //添加serialVersionUID
        ClassAdapter adapter = new SerialVersionUIDAdder(classWriter);
        reader.accept(adapter,0);

        //重新读取生成的带uid的字节码
        ClassReader byteReader = new ClassReader(classWriter.toByteArray());
        //打印出字节码信息
        byteReader.accept(new TraceClassVisitor( new PrintWriter(System.out)),0);
    }
}
```
结果：

```
// class version 52.0 (52)
// access flags 0x21
public class zgh/xxx/asm/JavaToASM {

  // compiled from: JavaToASM.java

  // access flags 0x2
  private Ljava/lang/Integer; i

  // access flags 0x18
  final static J serialVersionUID = 1301059175870243457

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

很明显，多了

```
// access flags 0x18
final static J serialVersionUID = 1301059175870243457
```



###### ClassAdapter

实现ClassVisitor，默认实现直接调用传入的委托的cv对象的visitXXX方法，在方法中，cv调用之前，我们可以实现自己的转换逻辑，例如示例则是在其他visitXXX方法进行属性记录，在visitEnd添加的serialVersionUID属性

```
public void visitEnd() {
        // compute SVUID and add it to the class
        if (computeSVUID && !hasSVUID) {
            try {
                cv.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                        "serialVersionUID",
                        "J",
                        null,
                        new Long(computeSVUID()));
            } catch (Throwable e) {
                throw new RuntimeException("Error while computing SVUID for "
                        + name, e);
            }
        }

        super.visitEnd();
    }
```



---
示例：移除指定名称的方法

```
public class TestRemoveMethodAdapter {

    public static void main(String[] args) throws IOException {
        //读取JavaToASM
        ClassReader reader = new ClassReader("zgh.xxx.asm.JavaToASM");
        ClassWriter classWriter = new ClassWriter(0);
        //移除指定方法
        ClassAdapter adapter = new RemoveMethodAdatper(classWriter,"getI");
        reader.accept(adapter,0);

        //重新读取生成的移除指定方法的字节码
        ClassReader byteReader = new ClassReader(classWriter.toByteArray());
        //打印出字节码信息
        byteReader.accept(new TraceClassVisitor( new PrintWriter(System.out)),0);
    }


    public static class RemoveMethodAdatper extends ClassAdapter{
        //要移除的方法名
        private String removeName;

        public RemoveMethodAdatper(ClassVisitor cv, String removeName) {
            super(cv);
            this.removeName = removeName;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (removeName.equals(name)) {
                //如果方法名称相同 return null
                return null;
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }
}
```
结果：

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
很明显 没有了getI方法