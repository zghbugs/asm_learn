package zgh.xxx.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/8/7.
 */
public class ReaderClass {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader("zgh.xxx.asm.JavaToASM");
        reader.accept(new TraceClassVisitor( new PrintWriter(System.out)),0);
    }
}


