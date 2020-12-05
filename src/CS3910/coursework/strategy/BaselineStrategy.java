package CS3910.coursework.strategy;

import CS3910.coursework.Problem;
import CS3910.coursework.Recipe;

import java.util.ArrayList;

public class BaselineStrategy extends Strategy {
    @Override
    public ArrayList<Recipe> solve(Problem problem) {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        if (!solveable(problem)) {
            return recipes;
        }

        return recipes;
    }
}
