
public class Main {
    public static void main(String[] args) {
      
        HashTable ht = new HashTable(new HashTable.DivisionHashFunction());

        System.out.println("Inserting values one by one:");

        ht.insert(1, 100);
        ht.insert(2, 200);
        ht.insert(1, 150);  
        ht.insert(2, 250);  
        ht.insert(3, 300);
        ht.insert(4, 400);
    }
}
