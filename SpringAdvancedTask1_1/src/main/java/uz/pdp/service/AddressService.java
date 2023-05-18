package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Address;
import uz.pdp.payload.AddressDto;
import uz.pdp.payload.ApiResponse;
import uz.pdp.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

import static io.vavr.control.Option.ofOptional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        boolean existsByHomeNumber = addressRepository.existsByHomeNumber(addressDto.getHomeNumber());
        if (existsByHomeNumber) {
            return new ApiResponse("Bunday manzil mavjud", false);
        }
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Manzil saqlandi", true);
    }

    public ApiResponse editAddress(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new ApiResponse("Bunday manzil mavjud emas", false);
        }
        if (optionalAddress.get().getHomeNumber().equals(addressDto.getHomeNumber())) {
            return new ApiResponse("Bunday uy raqamli address mavjud", false);
        }
        ofOptional(optionalAddress)
                .peek(address -> address.setHomeNumber(addressDto.getHomeNumber()))
                .peek(address -> address.setStreet(addressDto.getStreet()))
                .peek(addressRepository::save);
        return new ApiResponse("Manzil tahrirlandi", true);
    }

    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Manzil o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Manzil mavjud emas", false);
        }
    }
}
