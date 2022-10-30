package az.company.paydaytrade.controller;

import az.company.paydaytrade.dto.response.RespStock;
import az.company.paydaytrade.dto.response.Response;
import az.company.paydaytrade.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@Tag(name = "Stock services",description = "stock services")
public class StockController {

    private final StockService stockService;

    @GetMapping("/")
    public Response<List<RespStock>> getStockList() {
        return stockService.getStockList();
    }

    @PostMapping("/buyStock")
    public Response buyStock(@RequestParam String username, @RequestParam String stockName, @RequestParam String count) {
        return stockService.buyStock(username, stockName, count);
    }

    @PutMapping("/sellStock")
    public Response sellStock(@RequestParam String username, @RequestParam String stockName, @RequestParam String count) {
        return stockService.sellStock(username, stockName, count);
    }

}
