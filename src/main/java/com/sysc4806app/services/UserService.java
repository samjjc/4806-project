package com.sysc4806app.services;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.Review;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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

    public Collection<User> getMostSimilarUsers(String name, long numUsers) {
        return getJaccardRelativeToUser(name).entrySet().stream().sorted(Map.Entry.<User,Float>comparingByValue().thenComparing(Map.Entry.comparingByKey(Comparator.comparing(User::getName)))).limit(numUsers).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public Map<User, Float> getJaccardRelativeToUser(String name) {
        User current = userRepository.findByName(name);
        Set<Product> lhs_set = current.getReviews().stream().map(Review::getProduct).collect(Collectors.toSet());
        return userRepository.findAll().stream().filter(x -> !x.equals(current)).collect(Collectors.toMap(Function.identity(), v -> getJaccardDistanceLHSPrebuilt(lhs_set, v)));
    }

    public float getJaccardDistance(User lhs, User rhs) {
        Set<Product> lhs_set = lhs.getReviews().stream().map(Review::getProduct).collect(Collectors.toSet());
        return getJaccardDistanceLHSPrebuilt(lhs_set, rhs);
    }

    private float getJaccardDistanceLHSPrebuilt(Set<Product> lhs_set, User rhs) {
        Set<Product> rhs_set = rhs.getReviews().stream().map(Review::getProduct).collect(Collectors.toSet());
        Set<Product> intersection = new HashSet<>(rhs_set);
        intersection.retainAll(lhs_set);
        rhs_set.addAll(lhs_set);
        if (rhs_set.size() == 0) return 1;
        return 1f-(float)intersection.size()/rhs_set.size();
    }

    public float getEuclideanDistance(User lhs, User rhs) {
        HashMap<Product, Review> lhs_map = lhs.getReviews().stream().collect(Collectors.toMap(Review::getProduct, Function.identity(), (a,b)->b, HashMap::new));
        return getEuclideanDistanceLHSPrebuilt(lhs_map, rhs);
    }

    private long getEuclideanDistanceLHSPrebuilt(HashMap<Product, Review> lhs_map, User rhs) {
        HashMap<Product, Review> rhs_map = rhs.getReviews().stream().collect(Collectors.toMap(Review::getProduct, Function.identity(), (a,b)->b, HashMap::new));
        rhs_map.keySet().retainAll(lhs_map.keySet());
        if (rhs_map.size() == 0) {
            return Long.MAX_VALUE;
        }
        long score = 0;
        for (Map.Entry<Product, Review> rhs_p: rhs_map.entrySet()){
            long dif = rhs_p.getValue().getRating()-lhs_map.get(rhs_p.getKey()).getRating();
            score += dif*dif;
        }
        return score;
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
