package ru.javawebinar.topjava.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.DAO.MealEmbeddedRepository;
import ru.javawebinar.topjava.DAO.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);

    private final Repository<Meal> repository = new MealEmbeddedRepository();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("redirect to meals");
        List<MealTo> meals = MealsUtil.filteredByStreams(repository.getAll(), null, null, MealsUtil.getCaloriesPerDay());
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("delete meal");
        int id = Integer.valueOf(request.getParameter("id"));
        repository.delete(id);
        List<MealTo> meals = MealsUtil.filteredByStreams(repository.getAll(), null, null, MealsUtil.getCaloriesPerDay());
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO update meal logic
        ObjectMapper mapper = new ObjectMapper();
        MealTo mealTo = mapper.readValue(request.getInputStream(), MealTo.class);
        Meal meal = MealsUtil.createEntity(mealTo);
        if (repository.getById(meal.getId()) != null) {
            repository.update(meal);
        } else {
            repository.create(meal);
        }
    }
}
