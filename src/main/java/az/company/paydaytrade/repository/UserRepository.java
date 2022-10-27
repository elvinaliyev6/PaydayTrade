package az.company.paydaytrade.repository;

import az.company.paydaytrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailAndStatus(String email,Integer status);

    User findByEmailAndAccountConfirmedAndStatus(String email,Integer accountConfirmed,Integer status);

    User findByEmailAndPasswordAndAccountConfirmedAndStatus(String email,String password,Integer accountConfirmed,Integer status);

    User findByUsernameAndStatus(String username,Integer status);

    User findByUsernameAndAccountConfirmedAndStatus(String username,Integer accountConfirmed,Integer status);

    User findByUsernameAndPasswordAndAccountConfirmedAndStatus(String username,String password,Integer accountConfirmed,Integer status);

}
