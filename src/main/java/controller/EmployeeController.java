package controller;

import exception.EmployeeNotFoundException;
import model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> messageNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сотрудник не найден.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> messageIllegalArgument() {
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Неверный ввод данных. Не указаны имя или фамилия.");
    }

    @GetMapping
    public Collection<Employee> allEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/add")
    public Employee add(@RequestParam("firstName") String firstName,
                        @RequestParam("lastName") String lastName,
                        @RequestParam("salary") int salary,
                        @RequestParam("departmentId") int department) {
        return employeeService.createEmployee(firstName, lastName, salary, department);
    }

    @GetMapping("/remove")
    public Employee remove(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName) {
        return employeeService.removeEmployee(firstName, lastName);
    }

    @GetMapping("/find")
    public Employee find(@RequestParam("firstName") String firstName,
                         @RequestParam("lastName") String lastName) {
        return employeeService.findEmployee(firstName, lastName);
    }
}
