package service;

import exception.EmployeeAlreadyAddedException;
import exception.EmployeeNotFoundException;
import exception.EmployeeStorageIsFullException;
import model.Employee;
import org.springframework.stereotype.Service;
import util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private static final int employeeCount = 5;
    private final Map<String, Employee> employees = new HashMap<>();

    private static void checkUserData(String firstName, String lastName) {
        if (StringUtils.isNullOrEmpty(firstName) || StringUtils.isNullOrEmpty(lastName)) {
            throw new IllegalArgumentException();
        }
    }

    public Employee createEmployee(String firstName, String lastName, int salary, int department) {
        EmployeeService.checkUserData(firstName, lastName);
        if (employees.size() >= employeeCount) {
            throw new EmployeeStorageIsFullException();
        }
        String key = getKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        Employee employee = new Employee(firstName, lastName);
        employees.put(key, employee);
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) {
        EmployeeService.checkUserData(firstName, lastName);
        Employee employee = employees.remove(getKey(firstName, lastName));
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Employee findEmployee(String firstName, String lastName) {
        EmployeeService.checkUserData(firstName, lastName);
        String key = getKey(firstName, lastName);
        Employee employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Collection<Employee> getEmployees() {
        return employees.values();
    }

    private String getKey(String firstName, String lastName) {
        return firstName + lastName;
    }
}
