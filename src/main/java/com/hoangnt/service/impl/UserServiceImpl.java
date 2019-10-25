package com.hoangnt.service.impl;

import com.hoangnt.entity.Account;
import com.hoangnt.entity.Role;
import com.hoangnt.entity.User;
import com.hoangnt.model.AccountDTO;
import com.hoangnt.model.InformationUser;
import com.hoangnt.repository.SocialRepository;
import com.hoangnt.repository.UserRepository;
import com.hoangnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service("manager")
public class UserServiceImpl implements UserService<AccountDTO, InformationUser>, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    SocialRepository socialRepository;

    @Override
    public InformationUser getInfoUser(AccountDTO accountDTO) {

        User user = userRepository.findByUsername(accountDTO.getUsername());
        InformationUser informationUser = new InformationUser();
        informationUser.setId(user.getId());
        informationUser.setEmail(user.getEmail());
        informationUser.setFullName(user.getFullName());
        informationUser.setPhone(user.getPhone());
        informationUser.setGender(user.getGender());
        informationUser.setImageURL(user.getImageURL());
        return informationUser;
    }

    @Override
    public User addUser(InformationUser informationUser) {
        User user;

        if (informationUser.getId() > 0) {
            user = userRepository.findById(informationUser.getId()).get();
//            user.setImageURL(informationUser.getImageURL());
            if(informationUser.getImageURL()!=null){
                user.setImageURL(informationUser.getImageURL());
                return userRepository.save(user);
            }

        } else {
            user = new User();
            Account account=new Account();
            account.setUsername(informationUser.getEmail());
            account.setPassword(encoder.encode(informationUser.getPassword()));
            user.setAccount(account);
            user.setEmail(informationUser.getEmail());
        }
        user.setFullName(informationUser.getFullName());
        user.setGender(informationUser.getGender());
        user.setPhone(informationUser.getPhone());
        user.setRole(new Role(2));
        return userRepository.save(user);

    }


    @Override
    public InformationUser getById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            InformationUser informationUser = new InformationUser();
            informationUser.setId(user.getId());
            informationUser.setEmail(user.getEmail());
            informationUser.setFullName(user.getFullName());
            informationUser.setPhone(user.getPhone());
            informationUser.setGender(user.getGender());
            informationUser.setImageURL(user.getImageURL());
            informationUser.setPassword(user.getAccount().getPassword());
            return informationUser;
        }
        return null;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (username.contains("@")) {
            user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Invalid ");
            }
            return new org.springframework.security.core.userdetails.User(username, user.getAccount().getPassword(),
                    getAuthority(user));
        } else {
            user = socialRepository.findByProviderUserId(username).getAccount().getUser();
            return new org.springframework.security.core.userdetails.User(username,
                    "$2a$10$vh2a4ik6tOX/dgQtXJvLaeFUrX9jLVmXHKg6X3TqMchEZgi5Sfk32", getAuthority(user));
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }


}
