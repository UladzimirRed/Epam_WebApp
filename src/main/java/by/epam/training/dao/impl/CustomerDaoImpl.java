package by.epam.training.dao.impl;

import by.epam.training.connection.ConnectionPool;
import by.epam.training.connection.ProxyConnection;
import by.epam.training.dao.BaseDao;
import by.epam.training.entity.Order;
import by.epam.training.entity.OrderStatus;
import by.epam.training.entity.Transport;
import by.epam.training.entity.User;
import by.epam.training.exception.DaoException;
import by.epam.training.util.SqlRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements BaseDao<User> {

    private final ConnectionPool pool;

    public CustomerDaoImpl() {
        pool = ConnectionPool.getInstance();
    }

    public void makeOrder(Order order) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SqlRequest.SQL_MAKE_NEW_ORDER);
            preparedStatement.setString(1, order.getSubject());
            preparedStatement.setInt(2, order.getUser().getId());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.setInt(4, order.getDistance());
            preparedStatement.setObject(5, Transport.getCodeByTransport(order.getTransport()));
            preparedStatement.setBoolean(6, order.getRate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            pool.releaseConnection(connection);
        }
    }

    public List<Order> selectCurrentDelivery(int userId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        List<Order> orders = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SqlRequest.SQL_FIND_CUSTOMER_ORDER);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = createCustomerDeliveryFromQueryResult(resultSet);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            close(preparedStatement);
            pool.releaseConnection(connection);
        }
    }

    private Order createCustomerDeliveryFromQueryResult(ResultSet resultSet) throws SQLException {
        User user = new User();
        Order order = null;
        user.setLogin(resultSet.getString(3));
        String userLogin = user.getLogin();
        order = new Order(resultSet.getInt(1), resultSet.getString(2), new User(userLogin),
                resultSet.getDouble(4), resultSet.getInt(5), resultSet.getBoolean(6),
                Transport.getTransportByString(resultSet.getString(7)),
                OrderStatus.getOrderStatusByString(resultSet.getString(8)));
        return order;
    }
}
