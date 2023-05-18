package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsByHomeNumber(String homeNumber);

    boolean existsByHomeNumberAndIdNot(String homeNumber, Integer id);
}
