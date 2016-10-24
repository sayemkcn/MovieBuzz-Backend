package net.toracode.moviedb.services;

import net.toracode.moviedb.entities.User;
import net.toracode.moviedb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sayemkcn on 10/25/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Transactional(readOnly = true)
    public User getUserByAccountId(String accountId) {
        return this.userRepo.findByAccountId(accountId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User saveUser(User user) {
        return this.userRepo.save(user);
    }

}
