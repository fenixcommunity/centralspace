package java8.stream;

public class Customer {

    private int id;
    private String name;
    private long salary;
    private String designation;
    private List<Integer> numbers;
    private List<Address> address;

    public Customer(int i, String n, long s, String d) {
        this.id = i;
        this.name = n;
        this.salary = s;
        this.designation = d;
    }

    public Customer(String name, long salary) {
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public static List<Customer> getAllEmps() {
        List<Customer> customers = new ArrayList<>();
        Customer e1 = new Customer(1, "MAX", 100L, "BOSS");
        List<Integer> list = new ArrayList<>(Arrays.asList(12, 31));
        e1.setNumbers(list);
        customers.add(e1);
        Customer e2 = new Customer(2, "KAMIL", 200L, "HR");
        customers.add(e2);
        Customer e3 = new Customer(3, "PIOTREK", 300L, "CEO");
        customers.add(e3);
        Customer e4 = new Customer(4, "PIOTREK", 300L, "SCRUM");
        List<Address> list2 = new ArrayList<>(Arrays.asList(new Address("BBB"), new Address("AAA")));
        e4.setAddress(list2);
        customers.add(e4);

        return customers;
    }

    public static Map<Customer, Long> getAllEmpsMap() {
        Map<Customer, Long> customers = new HashMap<>();
        Customer e1 = new Customer(1, "MAX", 100L, "BOSS");
        List<Integer> list = new ArrayList<>(Arrays.asList(12, 31));
        e1.setNumbers(list);
        customers.put(e1, 1L);
        Customer e2 = new Customer(2, "KAMIL", 200L, "HR");
        customers.put(e2, 2L);
        Customer e3 = new Customer(3, "PIOTREK", 300L, "CEO");
        customers.put(e3, 3L);
        Customer e4 = new Customer(4, "PIOTREK", 300L, "SCRUM");
        List<Address> list2 = new ArrayList<>(Arrays.asList(new Address("BBB"), new Address("AAA")));
        e4.setAddress(list2);
        customers.put(e4, 4L);

        return customers;
    }

    @Override
    public String toString() {
        return "\n Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", designation='" + designation + '\'' +
                ", numbers='" + numbers + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
