package com.hoangnt.service;

import com.hoangnt.model.InformationUser;

import java.util.List;

public interface AdminService {
    List<InformationUser> getAllUser(String role);

    void deleteUser(int id);

}
