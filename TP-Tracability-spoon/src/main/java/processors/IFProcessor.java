package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class IFProcessor extends AbstractProcessor<CtIf> {

    @Override
    public boolean isToBeProcessed(CtIf candidate) {

        return getClass(candidate).getSimpleName().equals("Repository");
    }

    @Override
    public void process(CtIf ctIf) {
        System.out.println("if0");
        if (this.getMethod(ctIf).getSimpleName().equals("fetchExpensiveProducts")) {
            System.out.println("if1");
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement("LOGGER.log(Level.INFO,\"{ \"UserId\":\"+Main.getCurrentUser().getID()+\", \"UserEmail\":\"\"+Main.getCurrentUser().getEmail()+\"\", \"ProductId\":\"+product.getID()+ \"}\")" +
                    "expensiveProducts.add(product)");
            ctIf.setThenStatement(logMsgStatement);
        }
    }

    private CtClass getClass(CtIf ctIf) {
        CtElement ctElement = ctIf;
        while(!(ctElement instanceof CtClass)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtClass) ctElement;
    }

    private CtMethod getMethod(CtIf ctIf) {
        CtElement ctElement = ctIf;
        while(!(ctElement instanceof CtMethod)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtMethod) ctElement;
    }
}
