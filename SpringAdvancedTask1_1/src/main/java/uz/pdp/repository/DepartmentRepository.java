package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    List<Department> findAllByCompanyId(Integer id);
}
