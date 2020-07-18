package org.benf.cfr.reader.util.output;

import org.benf.cfr.reader.bytecode.analysis.loc.HasByteCodeLoc;
import org.benf.cfr.reader.bytecode.analysis.types.JavaRefTypeInstance;
import org.benf.cfr.reader.bytecode.analysis.types.JavaTypeInstance;
import org.benf.cfr.reader.bytecode.analysis.types.MethodPrototype;
import org.benf.cfr.reader.entities.Method;
import org.benf.cfr.reader.mapping.ObfuscationMapping;
import org.benf.cfr.reader.state.TypeUsageInformation;

import java.io.BufferedOutputStream;

/*
 * NB: This interface is NOT an externally visible one, and is subject to change.
 *
 * Please don't implement this (it's public because Java6's crappy access protection
 * means that subpackage visibility is required.).
 *
 * If you find yourself tempted to implement this, please see https://www.benf.org/other/cfr/api
 */
public interface Dumper extends MethodErrorCollector {
    /*
     * A dumper is initialised with knowledge of the types, so that two
     * dumpers can dump the same code with different import shortening.
     */
    TypeUsageInformation getTypeUsageInformation();

    ObfuscationMapping getObfuscationMapping();

    Dumper label(String s, boolean inline);

    void enqueuePendingCarriageReturn();

    Dumper removePendingCarriageReturn();

    Dumper keyword(String s);

    Dumper operator(String s);

    Dumper separator(String s);

    Dumper literal(String s, Object o);

    Dumper print(String s);

    Dumper methodName(String s, MethodPrototype p, boolean special, boolean defines);

    Dumper packageName(JavaRefTypeInstance t);

    Dumper identifier(String s, Object ref, boolean defines);

    Dumper print(char c);

    Dumper newln();

    Dumper endCodeln();

    void indent(int diff);

    int getIndentLevel();

    void close();

    @Override
    void addSummaryError(Method method, String s);

    boolean canEmitClass(JavaTypeInstance type);

    Dumper fieldName(String name, JavaTypeInstance owner, boolean hiddenDeclaration, boolean isStatic, boolean defines);

    Dumper withTypeUsageInformation(TypeUsageInformation innerclassTypeUsageInformation);

    Dumper comment(String s);

    Dumper beginBlockComment(boolean inline);

    Dumper endBlockComment();

    class CannotCreate extends RuntimeException {
        CannotCreate(String s) {
            super(s);
        }

        CannotCreate(Throwable throwable) {
            super(throwable);
        }

        @Override
        public String toString() {
            return "Cannot create dumper " + super.toString();
        }
    }

    int getOutputCount();

//////////////

    Dumper dump(JavaTypeInstance javaTypeInstance, TypeContext typeContext);

    Dumper dump(JavaTypeInstance javaTypeInstance);

    Dumper dump(Dumpable d);

    int getCurrentLine();

    void informBytecodeLoc(HasByteCodeLoc loc);

    // TODO : I probably want something more structured here, but this will do for now.
    BufferedOutputStream getAdditionalOutputStream(String description);
}
