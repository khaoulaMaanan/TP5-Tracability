package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class CatchProcessor extends AbstractProcessor<CtCatch> {

    @Override
    public boolean isToBeProcessed(CtCatch candidate) {
        return getClass(candidate).getSimpleName().equals("Repository");
    }

    @Override
    public void process(CtCatch ctCatch) {
        if (this.getMethod(ctCatch).getSimpleName().equals("addProduct")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement("LOGGER.log(Level.INFO,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"ProductId\\\":\"+p.getID()+ \"}\") \n this.products.add(product) ");
            ctCatch.setBody(logMsgStatement);

        }
    }

    private CtClass getClass(CtCatch ctCatch) {
        CtElement ctElement = ctCatch;
        while(!(ctElement instanceof CtClass)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtClass) ctElement;
    }

    private CtMethod getMethod(CtCatch ctCatch) {
        CtElement ctElement = ctCatch;
        while(!(ctElement instanceof CtMethod)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtMethod) ctElement;
    }
}
