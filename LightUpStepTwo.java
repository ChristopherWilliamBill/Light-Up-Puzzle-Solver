import java.util.Random;

public class LightUpStepTwo {

    private int totalGeneration;
    private Random random;
    private double crossoverRate;
    private double mutationRate;
    private int [][] jawaban;

    public LightUpStepTwo(Random random, int totalGeneration, double crossoverRate, double mutationRate,int [][]jawaban){
        this.random = random;
        this.totalGeneration = totalGeneration;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.jawaban = jawaban;
    }

    public IndividualStepTwo run(){
        PopulationStepTwo currPopulation = new PopulationStepTwo(this.random, 1000,this.jawaban);
        currPopulation.generateRandomPopulation();

        int generation = 1;

        while(!terminate(generation)){
            PopulationStepTwo newPopulation = currPopulation.generateNewPopulationWithElitism();

            while(newPopulation.isFilled() == false){
                IndividualStepTwo[] parents =  currPopulation.selectParent();

                if (this.random.nextDouble() < this.crossoverRate){
                    IndividualStepTwo child = parents[0].doCrossover(parents[1]);
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