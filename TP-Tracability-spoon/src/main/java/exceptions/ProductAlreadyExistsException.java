package exceptions;

public class ProductAlreadyExistsException extends  Exception{
    public ProductAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
}
