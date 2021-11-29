import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;

public class PopulationStepTwo{
    public ArrayList<IndividualStepTwo> populationsteptwo;
    private Random random;
    private int maxPopulationSize;
    private int populationSize = 0;
    private int[][] jawaban;
    private int NumberOfNoLamps;

    public PopulationStepTwo(Random random, int maxPopulationSize,int [][] jawaban) {
        this.populationsteptwo = new ArrayList<IndividualStepTwo>();
		this.random = random;
        this.maxPopulationSize = maxPopulationSize;
        //inisialisi variabel NumberOfBlackSquares (NBS) untuk menghitung banyaknya kotak  0 1 2 3 4 yang menjadi soal/clue penempatan lampu
        this.NumberOfNoLamps = 0;
        
        
        //iterasi seluruh soal
        for(int i = 0; i < jawaban.length; i++){
            for(int j = 0; j < jawaban[i].length; j++){
                //jika ada kotak selain -1, artinya kotak tersebut adalah kotak NBS
                if(jawaban[i][j] == -1){
                    NumberOfNoLamps++; //maka banyaknya NBS bertambah
                }
            }
        }
    }

    public void generateRandomPopulation() {
        for (int i = 0; i < this.maxPopulationSize; i++) {
            int[] NL = new int[this.NumberOfNoLamps];
            for(int j = 0; j < this.NumberOfNoLamps; j++){
                    NL[j] = this.random.nextInt(3 - 1) + 1;
            }

            //membuat objek individual baru dengan array NumberedBlackSquare dan array2d soal yang didapat dari file txt
            IndividualStepTwo individualsteptwo = new IndividualStepTwo(NL, this.jawaban, this.random);
            this.addIndividualStepTwo(individualsteptwo);
        }
    }

    public boolean addIndividualStepTwo(IndividualStepTwo newInd) {
        if (this.populationSize >= this.maxPopulationSize){
            return false;
        } 
        this.populationsteptwo.add(newInd);
        this.populationSize++;
        return true;
    }

    public Individual[] selectParent() {    //roulette wheel
        Individual[] parents = new Individual[2];

        int sumfitness = 0;
        for (int i = 0; i < this.populationsteptwo.size(); i++) {
            sumfitness += this.populationsteptwo.get(i).getFitness();
        }

        for (int i = 0; i < this.populationsteptwo.size(); i++) {
            ((Individual) this.populationsteptwo.get(i)).setParentProbability(1.0 * this.populationsteptwo.get(i).getFitness() / sumfitness);
        }
        for (int n = 0; n < 2; n++) {
            int i = -1;
            double prob = this.random.nextDouble();
            double sum = 0.0;
            do {
                i++;
                sum = sum + this.populationsteptwo.get(i).getParentProbability();
            } while(sum < prob);
            parents[n] = this.populationsteptwo.get(i);
        }
        return parents;
    }

    public Individual getBestIndividual(){
        int best = Integer.MAX_VALUE;
        int index = 0;
        int bestIndex = 0;

        while(best != 0 && index < this.populationsteptwo.size()){
            if(this.populationsteptwo.get(index).getFitness() < best){
                best = this.populationsteptwo.get(index).getFitness();
                bestIndex = index;
            }
            index++;
        }

        return this.populationsteptwo.get(bestIndex);
    }

    public Population generateNewPopulationWithElitism(){
        Population newPopulation = new Population(this.random, this.maxPopulationSize);
        newPopulation.addIndividual(this.getBestIndividual());

        return newPopulation;
    }

    public boolean isFilled() {
        return this.maxPopulationSize == this.populationsteptwo.size();
    }

}