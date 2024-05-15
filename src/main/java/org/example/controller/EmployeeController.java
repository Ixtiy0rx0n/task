package org.example.controller;

import org.apache.poi.ss.formula.functions.T;
import org.example.dto.EmployeeDTO;
import org.example.dto.PaginationResultDTO;
import org.example.enums.AppLanguage;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAll")
    public ResponseEntity<PageImpl<PaginationResultDTO>> getAllEmployees(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                         @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                                                             AppLanguage appLanguage) {
        return ResponseEntity.ok(employeeService.getAll(page,size));

    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Integer id,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                                       AppLanguage appLanguage) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.create(employeeDTO));
    }

    @GetMapping("/getByPhone/{phone}")
    public ResponseEntity<EmployeeDTO> getEmployeeByPhone(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(employeeService.getByPhone(phone));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        return ResponseEntity.ok(employeeService.update(employeeDTO));
    }
}
