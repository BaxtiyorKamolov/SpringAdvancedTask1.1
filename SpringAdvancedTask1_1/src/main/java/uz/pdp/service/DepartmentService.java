package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Company;
import uz.pdp.entity.Department;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.DepartmentDto;
import uz.pdp.repository.CompanyRepository;
import uz.pdp.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyService companyService;


    private final CompanyRepository companyRepository;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public List<Department> getDepartmentAllByIds(List<Integer> ids) {
        return departmentRepository.findAllById(ids);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if (existsByName) {
            return new ApiResponse("Bunday department name mavjud", false);
        }
        Department department = new Department();
        Company company = companyService.getCompanyById(departmentDto.getCompanyId());
        department.setName(departmentDto.getName());
        department.setCompany(company);

        departmentRepository.save(department);
        return new ApiResponse("Mijoz qoshildi", true);
    }

    public List<Department> getAllByCompanyId(Integer companyId) {
        return departmentRepository.findAllByCompanyId(companyId);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if (existsByNameAndIdNot) {
            return new ApiResponse("Bunday name mavjud", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Bunday name mavjud emas", false);
        }
        Department department = optionalDepartment.get();
        Company company = companyService.getCompanyById(departmentDto.getCompanyId());
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Mijoz tahrirlandi", true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Mijoz ochirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Mijoz mavjud emas", false);
        }
    }
}
