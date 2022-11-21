package ua.kpi.fict.multinewscw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.User;
import ua.kpi.fict.multinewscw.repositories.UserRepo;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void save(User user) {
        userRepo.save(user);
    }

    public void saveById(Long userId) {
        User user = userRepo.findById(userId).get();
        userRepo.save(user);
    }

    public User findById(long id) {
        return userRepo.findById(id).get();
    }

    public List<User> findAll(){
        return (List<User>) userRepo.findAll();
    }


}
