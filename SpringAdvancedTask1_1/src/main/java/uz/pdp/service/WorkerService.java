package uz.pdp.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Address;
import uz.pdp.entity.Department;
import uz.pdp.entity.Worker;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.WorkerDto;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    DepartmentService departmentService;

    public List<Worker> getWorkers() {
        List<Worker> workers = workerRepository.findAll();
        return workers;
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public ApiResponse addWorker(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("Bunday phoneNumber mavjud", false);
        }
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        Address address = addressService.getAddressById(workerDto.getAddressId());
        worker.setAddress(address);
        List<Department> departmentAllByIds = departmentService.getDepartmentAllByIds(workerDto.getDepartmentIds());
        worker.setDepartments(departmentAllByIds);

        workerRepository.save(worker);
        return new ApiResponse("Mijoz qoshildi", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return new ApiResponse("Bunday phoneNumber mavjud", false);
        }
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty()) {
            return new ApiResponse("Bunday phoneNumber mavjud emas", false);
        }
        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        Address addressById = addressService.getAddressById(workerDto.getAddressId());
        worker.setAddress(addressById);
        List<Department> departmentAllByIds = departmentService.getDepartmentAllByIds(workerDto.getDepartmentIds());
        worker.setDepartments(departmentAllByIds);

        workerRepository.save(worker);
        return new ApiResponse("Mijoz tahrirlandi", true);
    }

    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Mijoz ochirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Mijoz mavjud emas", false);
        }

    }
}
