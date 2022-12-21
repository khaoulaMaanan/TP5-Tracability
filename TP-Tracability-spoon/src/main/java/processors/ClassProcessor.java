package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;



import java.util.Set;


public class ClassProcessor extends AbstractProcessor<CtClass> {


    @Override
    public boolean isToBeProcessed(CtClass ctClass) {
        return ctClass.getSimpleName().equals("Repository");
    }

    @Override
    public void process(CtClass ctClass) {
            System.out.println("repository");
            if (ctClass.getField("LOGGER") == null && ctClass.getField("fileHandler") == null) {
                // Attributes
                final CtTypeReference<java.util.logging.Logger> loggerTypeRef = getFactory().Code().createCtTypeReference(java.util.logging.Logger.class);
                final CtField<java.util.logging.Logger> loggerField = getFactory().Core().createField();
                loggerField.addModifier(ModifierKind.PRIVATE);
                loggerField.addModifier(ModifierKind.STATIC);
                loggerField.addModifier(ModifierKind.FINAL);
                loggerField.setType(loggerTypeRef);
                loggerField.setSimpleName("LOGGER");

                final CtTypeReference<java.util.logging.Handler> handlerTypeRef = getFactory().Code().createCtTypeReference(java.util.logging.Handler.class);
                final CtField<java.util.logging.Handler> fileHandlerField = getFactory().Core().createField();
                fileHandlerField.addModifier(ModifierKind.PRIVATE);
                fileHandlerField.setType(handlerTypeRef);
                fileHandlerField.setSimpleName("fileHandler");

                ctClass.addFieldAtTop(fileHandlerField);
                ctClass.addFieldAtTop(loggerField);

                // Constructor
                Set<CtConstructor> ctConstructors = ctClass.getConstructors();
                String initLogger = String.format("this.LOGGER = java.util.logging.Logger.getLogger(%s.class.getName());", ctClass.getSimpleName());
                String tryCatchFileHandler = "try {\n" +
                        "   fileHandler  = new java.util.logging.FileHandler(\"Repository.log\");\n" +
                        "   LOGGER.addHandler(fileHandler);\n" +
                        "   fileHandler.setLevel(java.util.logging.Level.ALL);\n" +
                        "   LOGGER.setLevel(java.util.logging.Level.ALL);\n" +
                        "} catch (java.io.IOException e) {\n" +
                        "   LOGGER.log(java.util.logging.Level.SEVERE, \"Error occur in FileHandler.\", e);\n" +
                        "}";
                for (CtConstructor ctConstructor : ctConstructors) {
                    ctConstructor.getBody().addStatement(this.getFactory().Code().createCodeSnippetStatement(initLogger));
                    ctConstructor.getBody().addStatement(this.getFactory().Code().createCodeSnippetStatement(tryCatchFileHandler));
                }
            }

        }

}


