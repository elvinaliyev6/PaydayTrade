package az.company.paydaytrade.repository;

import az.company.paydaytrade.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

    List<Stock> findAllByStatus(Integer status);

    Stock findByNameAndStatus(String name,Integer status);

}
