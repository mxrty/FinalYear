package CS3910.coursework;

import CS3910.coursework.strategy.BaselineStrategy;
import CS3910.coursework.strategy.NovelStrategy;
import CS3910.coursework.strategy.RandomStrategy;
import CS3910.coursework.strategy.Strategy;

import java.util.ArrayList;

public class Main {
    private Problem problem;
    private Strategy baselineStrategy;
    private Strategy novelStrategy;

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Main main = new Main();
            main.initialise();
            main.executeStrategy();
        }
    }

    public void initialise() {
        problem = new Problem();
        baselineStrategy = new BaselineStrategy(problem, 100);
        novelStrategy = new NovelStrategy(problem, 100);
    }

    public void executeStrategy() {
        ArrayList<Solution> initialPopulation = initialisePopulation(problem, 100);

        Solution resultBaseline = baselineStrategy.solve(new ArrayList<>(initialPopulation));
        //resultBaseline.printSolution("Baseline");

        Solution resultNovel = novelStrategy.solve(new ArrayList<>(initialPopulation));
        //resultNovel.printSolution("Novel");
        System.out.println(resultBaseline.getCost() + "," + resultNovel.getCost());
    }

    private ArrayList<Solution> initialisePopulation(Problem problem, int populationSize) {
        ArrayList<Solution> population = new ArrayList<>();
        RandomStrategy randomStrategy = new RandomStrategy();
        for (int i = 0; i < populationSize; i++) {
            Solution randomSolution = randomStrategy.solve(problem);
            population.add(randomSolution);
        }
        return population;
    }


}
