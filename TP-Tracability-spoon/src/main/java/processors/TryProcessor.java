package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class TryProcessor extends AbstractProcessor<CtTry> {

    @Override
    public boolean isToBeProcessed(CtTry candidate) {
        return getClass(candidate).getSimpleName().equals("Repository");
    }

    @Override
    public void process(CtTry ctTry) {
        if (this.getMethod(ctTry).getSimpleName().equals("addProduct")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement("fetchProductByID(p.getID());\n" +
                    " LOGGER.log(Level.SEVERE, \"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"msg\\\": \\\"Produit pas ajouté\\\"}\");\n" +
                    " throw new ProductAlreadyExistsException(\"Le produit ayant l\\'id saisi existe déjà\");");
            ctTry.setBody(logMsgStatement);
        }
        if (this.getMethod(ctTry).getSimpleName().equals("deleteProductByID")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement("Product p=fetchProductByID(id);\n" +
                    " products.remove(p);\n" +
                    " LOGGER.log(Level.INFO,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"ProductId\\\":\"+id+ \"}\")");
            ctTry.setBody(logMsgStatement);
        }
         if (this.getMethod(ctTry).getSimpleName().equals("updateProduct")) {
            CtCodeSnippetStatement logMsgStatement = this.getFactory().Code().createCodeSnippetStatement("Product oldproduct=fetchProductByID(p.getID());\n" +
                    " deleteProductByID(oldproduct.getID());\n" +
                    " addProduct(p);\n" +
                    " LOGGER.log(Level.INFO,\"{ \\\"UserId\\\":\"+Main.getCurrentUser().getID()+\", \\\"UserEmail\\\":\\\"\"+Main.getCurrentUser().getEmail()+\"\\\", \\\"ProductId\\\":\"+p.getID()+ \"}\")");
            ctTry.setBody(logMsgStatement);
        }

    }

    private CtClass getClass(CtTry ctTry) {
        CtElement ctElement = ctTry;
        while(!(ctElement instanceof CtClass)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtClass) ctElement;
    }

    private CtMethod getMethod(CtTry ctTry) {
        CtElement ctElement = ctTry;
        while(!(ctElement instanceof CtMethod)) {
            if (ctElement == null)
                return null;
            ctElement = ctElement.getParent();
        }
        return (CtMethod) ctElement;
    }
}
