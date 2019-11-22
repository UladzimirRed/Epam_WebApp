package by.epam.training.service.impl;

import by.epam.training.dao.impl.CourierDaoImpl;
import by.epam.training.entity.Order;
import by.epam.training.entity.User;
import by.epam.training.exception.DaoException;
import by.epam.training.exception.ServiceException;
import by.epam.training.service.CourierService;

import java.util.List;

public class CourierServiceImpl implements CourierService {
    private CourierDaoImpl courierDao = new CourierDaoImpl();

    @Override
    public List<Order> showAvailableDelivery(User courier) throws ServiceException {
        try {
            return courierDao.selectAvailableDelivery(courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateOrderStatus(int orderId, User courier) throws ServiceException {
        try {
            courierDao.changeOrderStatus(orderId, courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> showProcessingDelivery(User courier) throws ServiceException {
        try {
            return courierDao.selectProcessingDelivery(courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
