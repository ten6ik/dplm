package bstu.dplm.dao;

import bstu.dplm.model.game.Quiz;
import org.springframework.stereotype.Repository;

@Repository
public class QuizDao extends GenericDao<Quiz>
{
    protected QuizDao()
    {
        super(Quiz.class);
    }
}