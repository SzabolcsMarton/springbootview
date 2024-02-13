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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public User getOneUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User getOneUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersOrderedByName() {
        return userRepository.findAllUserOrderedByName();
    }

    public List<String> getAllUsersName() {
        List<User> users = userRepository.findAll();
        List<String> names = new ArrayList<>();
        for (User actual : users) {
            if (!names.contains(actual.getName())) {
                names.add(actual.getName());
            }
        }
        return names.stream().sorted().toList();
    }

    public void addRole(Long id, Role role) {
        User user = getOneUserById(id);
        user.addRole(role);
        userRepository.save(user);
    }

    public void removeRole(Long id, Role role) {
        User user = getOneUserById(id);
        user.removeRole(role);
        userRepository.save(user);
    }

    public String getUserRolesPrint(Long userId){
        List<Role> roles = this.getOneUserById(userId).getRoles();
        StringBuilder stBuilder = new StringBuilder();
        for (int i = 0; i < roles.size(); i++){
            stBuilder.append(roles.get(i).getRole());
            if(i != roles.size() - 1){
                stBuilder.append(", ");
            }
        }
        return stBuilder.toString();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User saveUserFromDto(UserRegisterDto userRegisterDto) {
        checkPasswordsAreSame(userRegisterDto);
        isUserExist(userRegisterDto.getEmail());
        User parsedUser = new User();
        parsedUser.setName(userRegisterDto.getName());
        parsedUser.setEmail(userRegisterDto.getEmail());
        parsedUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        parsedUser.setRegistered(LocalDate.now());
        return userRepository.save(parsedUser);
    }

    public boolean registerUser(AddressRegisterDto addressRegisterDto) {
        User userToRegister = getOneUserById(addressRegisterDto.getUserId());
        Address addressToRegister = saveAddressFromDto(addressRegisterDto, userToRegister.getName());
        Role role = roleRepository.findByRole("ROLE_USER");
        userToRegister.addRole(role);
        userToRegister.setAddress(addressToRegister);
        User user = userRepository.save(userToRegister);
        return true;

    }

    public String addAdminRole(Long userId){
        User user = this.getOneUserById(userId);
        Role admin = roleRepository.findByRole("ROLE_ADMIN");
        if(user.getRoles().contains(admin)){
            return "Már rendelkezik admin joggal!";
        }
        this.addRole(userId, admin);
        return "Admin jog sikeressen megadva!";
    }

    public String removeAdminRole(Long userId){
        User user = this.getOneUserById(userId);
        Role admin = roleRepository.findByRole("ROLE_ADMIN");
        if(user.getRoles().contains(admin)){
            this.removeRole(userId, admin);
            return "Admin jog sikeressen megvonva!";
        }
        return "Nem rendelkezik admin joggal!";
    }


    private void isUserExist(String email) {
        Optional<User> checkEmployee = userRepository.findByEmail(email);
        if (checkEmployee.isPresent()) {
            throw new UserAlreadyRegisteredException("User email is already registered: " + email);
        }
    }

    private Address saveAddressFromDto(AddressRegisterDto addressRegisterDto, String name) {
        Address parsedAddress = new Address();
        parsedAddress.setName(name);
        parsedAddress.setCity(addressRegisterDto.getCity());
        parsedAddress.setStreet(addressRegisterDto.getStreet());
        parsedAddress.setHouseNumber(addressRegisterDto.getHouseNumber());
        parsedAddress.setZip(addressRegisterDto.getZip());
        parsedAddress.setPhone(addressRegisterDto.getPhone());
        return addressRepository.save(parsedAddress);
    }

    private void checkPasswordsAreSame(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getPassword2())) {
            throw new IllegalArgumentException("A két jelszó nem egyezik!");
        }
    }



}
