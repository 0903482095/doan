package com.hoangnt.service.impl;

import com.hoangnt.entity.User;
import com.hoangnt.model.AccountDTO;
import com.hoangnt.model.InformationUser;
import com.hoangnt.repository.UserRepository;
import com.hoangnt.service.AdminService;
import com.hoangnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<InformationUser> getAllUser(String role) {
        List<InformationUser> informationUsers=new ArrayList<>();
        List<User> users=userRepository.findByRoleName(role);
        users.forEach(user->{
            InformationUser informationUser = new InformationUser();
            informationUser.setId(user.getId());
            informationUser.setEmail(user.getEmail());
            informationUser.setFullName(user.getFullName());
            informationUser.setPhone(user.getPhone());
            informationUser.setGender(user.getGender());
            informationUser.setImageURL(user.getImageURL());

            informationUsers.add(informationUser);
        });
        return informationUsers;
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
