public class Penguin extends Bird {
    @Override void fly(){
         throw new UnsupportedOperationException("Can't fly"); 
    }
}