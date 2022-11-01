package ru.javawebinar.topjava.web;

import com.fasterxml.jackson.databind.JsonNode;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MealServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);

    private final Repository<Meal> repository = new MealEmbeddedRepository();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("redirect to meals");
        if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
            doDelete(request, response);
        } else if (request.getParameter("action") != null && request.getParameter("action").equals("update")) {
            request.getRequestDispatcher("/updateMeal.jsp").forward(request, response);
        }
        else {
            List<MealTo> meals = MealsUtil.filteredByStreams(repository.getAll(), null, null, MealsUtil.getCaloriesPerDay());
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("delete meal");
        int id = Integer.parseInt(request.getParameter("id"));
        repository.delete(id);
        List<MealTo> meals = MealsUtil.filteredByStreams(repository.getAll(), null, null, MealsUtil.getCaloriesPerDay());
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO update meal logic
        request.setCharacterEncoding("UTF-8");
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        while(reader.ready()) {
            sb.append(reader.readLine());
        }
        reader.close();
        String decodedString = URLDecoder.decode(sb.toString(), "UTF-8");
        String[] paramsArray = decodedString.split("\\&");

        int id = Integer.parseInt(paramsArray[3].split("=")[1]);
        String dateTime = paramsArray[0].split("=")[1];
        String description = paramsArray[1].split("=")[1];
        int calories = Integer.parseInt(paramsArray[2].split("=")[1]);
        MealTo mealTo = new MealTo();
        mealTo.setId(id);
        mealTo.setDateTime(dateTime);
        mealTo.setDescription(description);
        mealTo.setCalories(calories);
        Meal meal = MealsUtil.createEntity(mealTo);
        if (repository.getById(meal.getId()) != null) {
            repository.update(meal);
        } else {
            repository.create(meal);
        }
        List<MealTo> meals = MealsUtil.filteredByStreams(repository.getAll(), null, null, MealsUtil.getCaloriesPerDay());
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
