package com.sysc4806app.services;

import com.sysc4806app.model.User;
import com.sysc4806app.repos.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getFollowers(String name) {
        return userRepository.findByName(name).getFollowers();
    }

    public Collection<User> getFollowing(String name) {
        return userRepository.findByName(name).getFollowing();
    }

    public Collection<User> getMostPopularUsers(long numUsers) {
        return userRepository.findAll().stream().sorted(Comparator.comparingInt((User x) -> x.getFollowers().size()).reversed()).limit(numUsers).collect(Collectors.toList());
    }

    public int getDegreeOfSeparationNumber(User root, User target,Collection<User> visited){
        if (root.equals(target)){
            return 0;
        }
        int DOS = 1;
        Collection<User> following = root.getFollowing();
        for( User user : following){
            visited.add(user);
            if (user.equals(target)){
                return DOS;
            }else if (!visited.contains(user)){
                getDegreeOfSeparationNumber( root, target, visited);
            }
        }


        return -1;
    }
}
