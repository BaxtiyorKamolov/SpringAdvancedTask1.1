package uz.pdp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Company;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CompanyDto;
import uz.pdp.service.CompanyService;

import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/api/company")
    public ResponseEntity<List<Company>> getAllCompany() {
        List<Company> companyList = companyService.getAllCompany();
        return ResponseEntity.ok(companyList);
    }

    @GetMapping("/api/company/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {
        Company companyById = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyById);
    }

    @PostMapping("/api/company")
    public ResponseEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    @PutMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id,
                                                   @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    @DeleteMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
}
