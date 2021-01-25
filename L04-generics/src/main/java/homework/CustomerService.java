package homework;


import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static java.util.Comparator.comparingLong;

public class CustomerService {

    private final NavigableMap<Customer, String> customersMap = new TreeMap<>(comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> firstEntry = customersMap.firstEntry();
        if (firstEntry == null) {
            return null;
        }
        return new SimpleImmutableEntry<>(new Customer(firstEntry.getKey()), firstEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> nextEntry = customersMap.higherEntry(customer);
        if (nextEntry == null) {
            return null;
        }
        return new SimpleImmutableEntry<>(new Customer(nextEntry.getKey()), nextEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }
}
