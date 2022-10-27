package az.company.paydaytrade.repository;

import az.company.paydaytrade.entity.Order;
import az.company.paydaytrade.entity.Stock;
import az.company.paydaytrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByUserAndStockAndStatus(User user, Stock stock,Integer status);

}
