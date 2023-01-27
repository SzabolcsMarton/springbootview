package com.springbootView.springbootview.services;

import com.springbootView.springbootview.dto.AddressRegisterDto;
import com.springbootView.springbootview.dto.UserRegisterDto;
import com.springbootView.springbootview.exception.UserAlreadyRegisteredException;
import com.springbootView.springbootview.exception.UserNotFoundException;
import com.springbootView.springbootview.model.Address;
import com.springbootView.springbootview.model.Role;
import com.springbootView.springbootview.model.User;
import com.springbootView.springbootview.repositories.AddressRepository;
import com.springbootView.springbootview.repositories.RoleRepository;
import com.springbootView.springbootview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AddressRepository addressRepository, UserRepository userRepository, RoleRepository repository, PasswordEncoder passwordEncoder) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.roleRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getOneUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addRole(Long id, Role role){
        User user = getOneUserById(id);
        user.addRole(role);
    }

    public void removeRole(Long id, Role role){
        User user = getOneUserById(id);
        user.removeRole(role);
    }

    public User saveUserFromDto(UserRegisterDto userRegisterDto){
        checkPasswordsAreSame(userRegisterDto);
       isUserExist(userRegisterDto.getEmail());
        User parsedUser = new User();
        parsedUser.setName(userRegisterDto.getName());
        parsedUser.setEmail(userRegisterDto.getEmail());
        parsedUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        parsedUser.setRegistered(new Date());
        return userRepository.save(parsedUser);
    }

    public boolean registerUser(AddressRegisterDto addressRegisterDto){
        User userToRegister = getUser(addressRegisterDto.getUserId());
        Address addressToRegister = saveAddressFromDto(addressRegisterDto, userToRegister.getName());
        Role role = roleRepository.findByRole("ROLE_USER");
        userToRegister.addRole(role);
        userToRegister.setAddress(addressToRegister);
        User user = userRepository.save(userToRegister);
        return user != null;

    }

    private void isUserExist(String email){
        Optional<User> checkEmployee = userRepository.findByEmail(email);
        if(checkEmployee.isPresent()) {
            throw new UserAlreadyRegisteredException("User email is already registered: "+ email);
        }
    }

    private User getUser(Long userId){
        Optional<User> user= userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("Nem található a keresett user, id: " + userId);
        }
        return user.get();
    }

    private Address saveAddressFromDto(AddressRegisterDto addressRegisterDto, String name){
        Address parsedAddress = new Address();
        parsedAddress.setName(name);
        parsedAddress.setCity(addressRegisterDto.getCity());
        parsedAddress.setStreet(addressRegisterDto.getStreet());
        parsedAddress.setHouseNumber(addressRegisterDto.getHouseNumber());
        parsedAddress.setZip(addressRegisterDto.getZip());
        parsedAddress.setPhone(addressRegisterDto.getPhone());
        return addressRepository.save(parsedAddress);
    }

    private void checkPasswordsAreSame(UserRegisterDto userRegisterDto){
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getPassword2())){
            throw new IllegalArgumentException("A két jelszó nem egyezik!");
        }
    }

}
