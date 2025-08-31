public class OrderController {
    void create(String id){
        SqlOrderRepository repo = new SqlOrderRepository(); // hard dependency
        repo.save(id);
        System.out.println("Created order: " + id);
    }
}