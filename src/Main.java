public class Main {
    public static void main(String[] args) {
        Database db = new Database("./database");
        db.set("1", "Foo");
        db.set("1", "Bar");
        String data  = db.get("1").orElse("");
        System.out.println(data);
    }
}