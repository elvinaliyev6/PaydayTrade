package az.company.paydaytrade;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(
        info = @Info(
                title = "Payday Trade API",
                description = "Payday trade web services",
                version = "v1"
        )
)
public class PaydayTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaydayTradeApplication.class, args);
    }

}
