package az.company.paydaytrade.service.impl;

import az.company.paydaytrade.dto.response.RespStatus;
import az.company.paydaytrade.dto.response.RespStock;
import az.company.paydaytrade.dto.response.Response;
import az.company.paydaytrade.entity.Order;
import az.company.paydaytrade.entity.Stock;
import az.company.paydaytrade.entity.User;
import az.company.paydaytrade.enums.StatusAndAccountConfirmed;
import az.company.paydaytrade.exception.ExceptionConstants;
import az.company.paydaytrade.exception.PaydayTradeException;
import az.company.paydaytrade.repository.OrderRepository;
import az.company.paydaytrade.repository.StockRepository;
import az.company.paydaytrade.repository.UserRepository;
import az.company.paydaytrade.service.StockService;
import az.company.paydaytrade.util.Converter;
import az.company.paydaytrade.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MailService mailService;

    @Override
    public Response<List<RespStock>> getStockList() {

        Response<List<RespStock>> response = new Response<>();

        try {
            List<RespStock> stockList = stockRepository.findAllByStatus(StatusAndAccountConfirmed.ACTIVE.getValue())
                    .stream()
                    .map(stock -> Converter.convertFromStock(stock))
                    .collect(Collectors.toList());

            if (stockList.isEmpty())
                throw new PaydayTradeException(ExceptionConstants.STOCK_NOT_FOUND, "Stock didn't find!");

            response.setT(stockList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public Response buyStock(String username, String stockName, String count) {

        Response response = new Response();

        try {
            User user = userRepository.findByUsernameAndStatus(username, StatusAndAccountConfirmed.ACTIVE.getValue());
            Stock stock = stockRepository.findByNameAndStatus(stockName, StatusAndAccountConfirmed.ACTIVE.getValue());
            Order order = orderRepository.findByUserAndStockAndStatus(user, stock, StatusAndAccountConfirmed.ACTIVE.getValue());

            if (stockName == null || username == null || count == null)
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");

            if (user == null)
                throw new PaydayTradeException(ExceptionConstants.USERNAME_NOT_FOUND, "User didn't find!");

            if (stock == null)
                throw new PaydayTradeException(ExceptionConstants.STOCK_NOT_FOUND, "Stock didn't find!");

            if (order == null) {
                if (Integer.valueOf(count) > user.getBalance())
                    throw new PaydayTradeException(ExceptionConstants.INSUFFICIENT_FOUND, "Insufficient found!");

                user.setBalance(user.getBalance() - (Integer.valueOf(count) * stock.getPrice()));
                userRepository.save(user);

                Order resOrder = Order.builder()
                        .user(user)
                        .stock(stock)
                        .status(1)
                        .count(Integer.valueOf(count))
                        .value(Integer.valueOf(count) * stock.getPrice())
                        .build();

                orderRepository.save(resOrder);
                mailService.sendEmail(user.getEmail(),
                        "Buy Stock",
                        "  elvinaliyevinfo@gmail.com"
                        , "Stock Name: " + stock.getName() + ", Share Amount: " + count + " , Price: " + Integer.valueOf(count) * stock.getPrice());
                response.setStatus(RespStatus.getSuccessMessage());
            } else {
                if (Integer.valueOf(count) * stock.getPrice() > user.getBalance()) {
                    throw new PaydayTradeException(ExceptionConstants.INSUFFICIENT_FOUND, "Insufficient found !");
                }
                user.setBalance(user.getBalance() - (Integer.valueOf(count) * stock.getPrice()));
                userRepository.save(user);
                order.setValue((order.getValue() + Integer.valueOf(count)) * stock.getPrice());
                order.setCount(order.getCount() + Integer.valueOf(count));
                orderRepository.save(order);

                response.setStatus(RespStatus.getSuccessMessage());
                mailService.sendEmail(user.getEmail(),
                        "By Stock",
                        "elvinaliyevinfo@gmail.com"
                        , "Stock Name: " + stock.getName() + ", Share Amount: " + count + " , Price: " + Integer.valueOf(count) * stock.getPrice());
            }
        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public Response sellStock(String username, String stockName, String count) {
        Response response = new Response();

        try {
            User user = userRepository.findByUsernameAndStatus(username, StatusAndAccountConfirmed.ACTIVE.getValue());
            Stock stock = stockRepository.findByNameAndStatus(stockName, StatusAndAccountConfirmed.ACTIVE.getValue());
            Order order = orderRepository.findByUserAndStockAndStatus(user, stock, StatusAndAccountConfirmed.ACTIVE.getValue());

            if (stockName == null || username == null || count == null)
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");

            if (user == null)
                throw new PaydayTradeException(ExceptionConstants.USERNAME_NOT_FOUND, "User didn't find!");

            if (stock == null)
                throw new PaydayTradeException(ExceptionConstants.STOCK_NOT_FOUND, "Stock didn't find!");

            if (order == null)
                throw new PaydayTradeException(ExceptionConstants.ORDER_NOT_FOUND, "This user didn't have " + stockName);

            user.setBalance(user.getBalance() + (Integer.valueOf(count) * stock.getPrice()));
            userRepository.save(user);

            if (order.getCount() < Integer.valueOf(count))
                throw new PaydayTradeException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");

            order.setCount(order.getCount() - Integer.valueOf(count));
            order.setValue(order.getValue() - (Integer.valueOf(count) * stock.getPrice()));
            orderRepository.save(order);


            response.setStatus(RespStatus.getSuccessMessage());
            mailService.sendEmail(user.getEmail()
                    , "Sell Order", "elvinaliyevinfo@gmail.com"
                    , "Stock name: " + stock.getName() + ", Count: " + order.getCount() + ", Price: " + Integer.valueOf(count) * stock.getPrice());

        } catch (PaydayTradeException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}
