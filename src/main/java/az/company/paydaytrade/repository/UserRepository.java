package az.company.paydaytrade.repository;

import az.company.paydaytrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailAndStatus(String email,Integer status);

    User findByUsernameAndStatus(String username,Integer status);

}
