package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.poi.ss.formula.functions.T;
import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.dto.PaginationResultDTO;
import org.example.enums.AppLanguage;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    // http://localhost:8080/swagger-ui/index.html -> Swagger

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Get all Employees", description = "Barcha hodimlar (pagination)")
    @GetMapping("/getAll")
    public ResponseEntity<PageImpl<PaginationResultDTO>> getAllEmployees(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                         @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                                                         AppLanguage appLanguage) {
        return ResponseEntity.ok(employeeService.getAll(page, size));

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id", description = "Id bo'yicha hodim")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Integer id,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                                       AppLanguage appLanguage) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete employee by id", description = "Id bo'yicha hodimlarni o'chirish")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Create employee", description = "Hodim yaratish")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody CreateEmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.create(employeeDTO));
    }

    @GetMapping("/getByPhone/{phone}")
    @Operation(summary = "Get employee by phone", description = "Telefon raqam bo'yicha hodim")
    public ResponseEntity<EmployeeDTO> getEmployeeByPhone(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(employeeService.getByPhone(phone));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update employee", description = "Hodim ma'lumotlarini o'zgartirish")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        return ResponseEntity.ok(employeeService.update(employeeDTO));
    }


    @GetMapping("/download-excel")
    @Operation(summary = "Employee excel download", description = "Barcha hodimlarning ma'lumotlari")
    public ResponseEntity<byte[]> downloadStudentFilter(@RequestParam(value = "lang", defaultValue = "uz") AppLanguage lang) throws IOException {

        byte[] excelBytes = employeeService.filterDownload();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentType("application/vnd.ms-excel");
        headers.setContentDispositionFormData("attachment", "employee.xlsx");
        return new ResponseEntity<>(excelBytes, headers, 200);
    }
}
