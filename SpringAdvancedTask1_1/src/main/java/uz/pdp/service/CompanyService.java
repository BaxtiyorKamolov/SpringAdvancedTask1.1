package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Address;
import uz.pdp.entity.Company;
import uz.pdp.entity.Department;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CompanyDto;
import uz.pdp.repository.CompanyRepository;
import uz.pdp.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final DepartmentRepository departmentRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (existsByCorpName) {
            return new ApiResponse("Bunday corpName mavjud", false);
        }
        Company company = new Company();
        Address address = addressService.getAddressById(companyDto.getAddressId());
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(address);

        companyRepository.save(company);
        return new ApiResponse("Company saved", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        boolean existsByCorpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (existsByCorpNameAndIdNot) {
            return new ApiResponse("Bunday corpName mavjud", false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Bunday corpName mabjud emas", false);
        }
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Address address = addressService.getAddressById(companyDto.getAddressId());
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Tahrirlandi", true);
    }

    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Mijoz ochirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Mijoz mavjud emas", false);
        }
    }
}