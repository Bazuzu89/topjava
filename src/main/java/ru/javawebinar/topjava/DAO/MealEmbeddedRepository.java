package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealEmbeddedRepository implements Repository<Meal> {
    private static final ConcurrentHashMap<Integer, Meal> mealRepository = new ConcurrentHashMap<>();
    private static AtomicInteger lastCreatedId;

    static {
        mealRepository.put(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealRepository.put(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealRepository.put(3, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealRepository.put(4, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealRepository.put(5, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealRepository.put(6, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealRepository.put(7, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        lastCreatedId = new AtomicInteger(7);
    }


    @Override
    public int create(Meal meal) {
        int id = lastCreatedId.addAndGet(1);
        meal.setId(id);
        mealRepository.put(id, meal);
        return id;
    }

    @Override
    public Meal getById(int id) {
        return mealRepository.get(id);
    }

    @Override
    public int update(Meal meal) {
        if (meal.equals(mealRepository.get(meal.getId()))) {
            return 0;
        } else {
            mealRepository.put(meal.getId(), meal);
            return 1;
        }
    }

    @Override
    public void delete(int id) {
        mealRepository.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealRepository.values());
    }
}
