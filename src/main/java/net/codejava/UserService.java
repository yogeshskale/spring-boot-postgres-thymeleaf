package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser getUser(String name, String password){
        return  appUserRepository.findByNameAndPassword(name,password);
    }

}
