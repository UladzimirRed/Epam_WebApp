package by.epam.training.command.customer;

import by.epam.training.command.ActionCommand;
import by.epam.training.entity.Order;
import by.epam.training.exception.ServiceException;
import by.epam.training.service.impl.CustomerServiceImpl;
import by.epam.training.command.JspAddress;
import by.epam.training.command.JspAttribute;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Confirm rate command.
 */
public class ConfirmRateCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        Integer rate = Integer.parseInt(request.getParameter(JspAttribute.RATE));
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(JspAttribute.ORDER);
        int orderId = order.getOrderId();
        String courierLogin = order.getUser().getLogin();
        try {
            CustomerServiceImpl service = new CustomerServiceImpl();
            double currentRating = service.showCurrentCourierRating(courierLogin);
            double updatedRating = service.updateRating(courierLogin, currentRating, rate);
            service.updateOrderStatusToRated(orderId);
            session.setAttribute(JspAttribute.LOGIN, courierLogin);
            session.setAttribute(JspAttribute.USER_RATING, updatedRating);
            return JspAddress.THANK_YOU_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            return JspAddress.ERROR_PAGE;
        }
    }
}
