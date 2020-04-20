all: compile

compile: 
	java -jar ./jtb132di.jar -te minijava.jj
	java -jar ./javacc5.jar minijava-jtb.jj
	javac Main.java

clean:
	rm -f *.class *~
	rm  JavaCharStream.java MiniJavaParser* minijava-jtb.jj Token* ParseException.java
	rm -rf syntaxtree/ visitor/

execute:
	java Main ./Tests/test1.java