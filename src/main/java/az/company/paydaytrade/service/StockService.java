package az.company.paydaytrade.service;

import az.company.paydaytrade.dto.response.RespStock;
import az.company.paydaytrade.dto.response.Response;

import java.util.List;

public interface StockService {

    Response<List<RespStock>> getStockList();

    Response buyStock(String username,String stockName,String count);

    Response sellStock(String username,String stockName,String count);
}
