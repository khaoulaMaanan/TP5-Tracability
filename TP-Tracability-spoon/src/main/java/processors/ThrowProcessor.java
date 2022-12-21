package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class ThrowProcessor extends AbstractProcessor<CtThrow> {
    @Override
    public boolean isToBeProcessed(CtThrow candidate) {
        return getClass(candidate).getSimpleName().equals("Repository");
    }

    @Override
    public void process(CtThrow ctThrow) {
        if (this.getMethod(ctThrow).getSimpleName().equals("fetchProductByID")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement(" LOGGER.log(Level.SEVERE,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"msg\\\":\"+Produit non existant+ \"}\")");
            ctThrow.insertBefore(logMsgStatement);
        } else if (this.getMethod(ctThrow).getSimpleName().equals("addProduct")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement(" LOGGER.log(Level.SEVERE,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"msg\\\":\"+Produit pas ajouté+ \"}\")");
            ctThrow.insertBefore(logMsgStatement);
        }else if (this.getMethod(ctThrow).getSimpleName().equals("deleteProductByID")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement(" LOGGER.log(Level.SEVERE,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"msg\\\":\"+Produit pas supprimé+ \"}\")");
            ctThrow.insertBefore(logMsgStatement);
        }else if (this.getMethod(ctThrow).getSimpleName().equals("updateProduct")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement(" LOGGER.log(Level.SEVERE,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"msg\\\":\"+Produit pas ajouté+ \"}\")");
            ctThrow.insertBefore(logMsgStatement);
        }
    }

    private CtClass getClass(CtThrow ctThrow) {
        CtElement ctElement = ctThrow;
        while(!(ctElement instanceof CtClass)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtClass) ctElement;
    }

    private CtMethod getMethod(CtThrow ctThrow) {
        CtElement ctElement = ctThrow;
        while(!(ctElement instanceof CtMethod)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtMethod) ctElement;
    }
}
