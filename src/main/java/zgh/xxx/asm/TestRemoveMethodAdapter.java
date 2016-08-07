package zgh.xxx.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.SerialVersionUIDAdder;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/8/7.
 */
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
