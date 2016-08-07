package zgh.xxx.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.SerialVersionUIDAdder;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/8/7.
 */
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
