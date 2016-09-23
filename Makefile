# Classpath para compilar y correr el programa
CLASSPATH=$(PWD)/lib/java-cup-11a.jar:.
# Flags
JFLAGS= -g -nowarn
# Java Compiler
JAVAC=javac
# Clases necesarias para realizar el lab
SRC= \
	ViperTree.java \
	CgenMachine.java \
	CgenSupport.java \
	ConstantsTable.java \
	Environments.java \
	ListNode.java \
	Node.java \
	Semant.java \
	SemantErrors.java \
	SymbolTable.java \
	TokenConstants.java \
	Type.java \
	Utils.java \
	ViperLexer.java \
	ViperParser.java

SRC2= \
	Cycles.java \
	Graph.java

CLS= $(SRC:.java=.class)
CLS2= $(SRC2:.java=.class)

# Crea el ejecutable
semant: Makefile $(CLS)
	$(RM) semant
	echo '#!/bin/sh' >> semant
	echo 'java -classpath $(CLASSPATH) Semant $$*' >> semant
	chmod 755 semant

cycles: $(CLS2)
	$(RM) cycles
	echo '#!/bin/sh' >> cycles
	echo 'java Cycles' >> cycles
	chmod 755 cycles

# compila todos los archivos de cycles
$(CLS2): $(SRC2)
	$(JAVAC) $(JFLAGS) $(SRC2)

# Compila todos los archivos
$(CLS): $(SRC)
	$(JAVAC) $(JFLAGS) -cp $(CLASSPATH) $(SRC)
	# Se tiene que agregar ya que de esto depende el target
	touch ViperTree.class

# Crea el archivo para subir
lab7.zip: ViperTree.java
	zip lab7.zip ViperTree.java Graph.java

.PHONY: clean check submit

# Autrograder
check: semant grading.py
	python grading.py

# Submit para el GES
submit: lab7.zip
	@echo 'Creando arhivo para subir al GES ...'

# Limpiar el directorio, para subir los archivos
clean:
	$(RM) -r $(PWD)/grading
	$(RM) lab7.zip
	$(RM) *.class
	$(RM) cycles
	$(RM) semant
