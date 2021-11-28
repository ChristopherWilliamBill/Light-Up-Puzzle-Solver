import java.util.Random;

public class LightUp {

    private int totalGeneration;
    private Random random;
    private double crossoverRate;
    private double mutationRate;

    public LightUp(Random random, int totalGeneration, double crossoverRate, double mutationRate){
        this.random = random;
        this.totalGeneration = totalGeneration;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }

    public Individual run(){
        Population currPopulation = new Population(this.random, 10000);
        currPopulation.generateRandomPopulation();

        int generation = 1;

        while(!terminate(generation)){
            Population newPopulation = currPopulation.generateNewPopulationWithElitism();

            while(newPopulation.isFilled() == false){
                Individual[] parents =  currPopulation.selectParent();

                if (this.random.nextDouble() < this.crossoverRate){
                    Individual child = parents[0].doCrossover(parents[1]);
                    if (this.random.nextDouble() < this.mutationRate) {
                        child.doMutation();
                    }
                    newPopulation.addIndividual(child);
                }
            }
            generation++;
            currPopulation = newPopulation;
        }

        return currPopulation.getBestIndividual();
    }
    
    public boolean terminate(int generation){
        if (generation >= this.totalGeneration){
            return true;
        }else{
            return false;
        }
    }
}
