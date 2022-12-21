package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class ForProcessor extends AbstractProcessor<CtFor> {

    @Override
    public boolean isToBeProcessed(CtFor candidate) {
        return getClass(candidate).getSimpleName().equals("Repository");
    }

    @Override
    public void process(CtFor ctFor) {
        if (this.getMethod(ctFor).getSimpleName().equals("displayAllProducts")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement("LOGGER.log(Level.INFO,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\",\\\"UserEmail\\\": \\\"\"+Main.getCurrentUser().getEmail()+\"\\\"}\")");
            ctFor.insertBefore(logMsgStatement);
        }

    }

    private CtClass getClass(CtFor ctFor) {
        CtElement ctElement = ctFor;
        while(!(ctElement instanceof CtClass)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtClass) ctElement;
    }

    private CtMethod getMethod(CtFor ctFor) {
        CtElement ctElement = ctFor;
        while(!(ctElement instanceof CtMethod)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtMethod) ctElement;
    }
}
