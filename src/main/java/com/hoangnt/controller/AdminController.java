package com.hoangnt.controller;

import com.hoangnt.config.TokenProvider;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    private TokenProvider tokenProvider;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin/info/users/{role}")
    public ResponseEntity<Response<List<InformationUser>>> getInfoUser(@PathVariable String role) {

        List<InformationUser> informationUsers = adminService.getAllUser(role);
        Response<List<InformationUser>> response = new Response<>();
        ResponseData<List<InformationUser>> responseData = new ResponseData<>();
        responseData.setAccountInfo(informationUsers);
        response.setStatus("OK");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()));
        response.setData(responseData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin/delete/users/{id}")
    public ResponseEntity<Response<Void>> deleteUser(@PathVariable int id) {

        adminService.deleteUser(id);
        Response<Void> response = new Response<>();
        ResponseData<Void> responseData = new ResponseData<>();
        response.setStatus("OK");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()));
        response.setData(responseData);

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
