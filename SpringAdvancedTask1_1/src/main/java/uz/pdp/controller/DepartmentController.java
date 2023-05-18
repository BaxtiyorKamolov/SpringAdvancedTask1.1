package uz.pdp.controller;

import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Department;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.DepartmentDto;
import uz.pdp.service.DepartmentService;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/api/department")
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/api/department/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id) {
        Department departmentById = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentById);
    }

    @GetMapping("/api/department/companyId/{id}")
    public ResponseEntity<List<Department>> getAllByCompanyId(@PathVariable Integer id) {
        List<Department> departmentList = departmentService.getAllByCompanyId(id);
        return ResponseEntity.ok(departmentList);
    }

    @PostMapping("/api/department")
    public ResponseEntity<ApiResponse> addDepartment(@RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    @PutMapping("/api/department/{id}")
    public ResponseEntity<ApiResponse> editDepartment(@PathVariable Integer id,
                                                      @Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    @DeleteMapping("/api/department/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}
