@echo off
echo === Compiling HelloWorld Example ===
javac examples/org/benf/cfr/examples/HelloWorld.java
echo.

echo === Running HelloWorld Example ===
java -cp examples org.benf.cfr.examples.HelloWorld
echo.

echo === Decompiling HelloWorld Class File with CFR ===
java -jar target/cfr-0.153-SNAPSHOT.jar examples/org/benf/cfr/examples/HelloWorld.class
echo.

echo === Example Complete === 