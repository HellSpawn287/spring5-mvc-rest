package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCustomers() {
        Customer jim = new Customer();
        jim.setFirstname("John");
        jim.setLastname("Jameson");

        Customer jack = new Customer();
        jack.setFirstname("Jack");
        jack.setLastname("Nicholson");

        Customer peter = new Customer();
        peter.setFirstname("Peter");
        peter.setLastname("Parker");

        customerRepository.save(jim);
        customerRepository.save(jack);
        customerRepository.save(peter);

        System.out.println("Data Loaded: " +
                "\n numbers of customers = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded: " +
                "\n numbers of categories = " + categoryRepository.count());
    }

}
