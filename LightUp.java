import java.util.Random;

public class LightUp {

    private int totalGeneration; //total generasi
    private Random random; //objek random
    private double crossoverRate; //kemungkinan terjadinya crossover
    private double mutationRate; //kemungkinan terjadinya mutasi
    private double elitismRate; //kemungkinan terjadinya elitism

    //Constructor
    public LightUp(Random random, int totalGeneration, double crossoverRate, double mutationRate, double elitismRate){
        this.random = random;
        this.totalGeneration = totalGeneration;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitismRate = elitismRate;
    }

    //method untuk mendapatkan individual terbaik
    public Individual run(){

        //membuat populasi pertama
        Population currPopulation = new Population(this.random, 1000);
        currPopulation.generateRandomPopulation();

        //menyatakan jumlah generasi
        int generation = 1;

        //loop jika jumlah generasi belum mencapai batas
        while(!terminate(generation)){
            //buat populasi baru dengan elitism
            Population newPopulation = currPopulation.generateNewPopulationWithElitism(this.elitismRate);

            //loop jika populasi yang baru belum terisi sampai jumlah individu
            while(newPopulation.isFilled() == false){
                //memilih 2 parents
                Individual[] parents =  currPopulation.selectParent();

                //jika nextDouble() < crossoverRate maka akan terjadi crossOver
                if (this.random.nextDouble() < this.crossoverRate){
                    //mendapatkan child dari hasil crossover parents
                    Individual child = parents[0].doCrossover(parents[1]);

                    //jika nextDouble() < mutationRate maka akan terjadi mutation
                    if (this.random.nextDouble() < this.mutationRate) {
                        child.doMutation();
                    }

                    //mengisi populasi yang baru dengan child yang tadi dibuat.
                    newPopulation.addIndividual(child);
                }
            }

            //sampai sini, populasi yang baru sudah terbentuk dan jumlah generasi bertambah
            generation++;
            currPopulation = newPopulation; // populasi yang baru menjadi populasi 
            
            //jika didapat individu dengan fitness = 0, maka sudah didapat individu terbaik dan algoritma dapat berhenti
            if(currPopulation.getBestIndividual().getFitness() == 0){
                break;
            }
        }

        //mengembalikan individu terbaik
        return currPopulation.getBestIndividual();
    }
    
    //method untuk mengecek apakah jumlah generasi sudah melebihi batas total generasi atau belum
    //mereturn true jika sudah
    //mereturn false jika belum
    public boolean terminate(int generation){
        if (generation >= this.totalGeneration){
            return true;
        }else{
            return false;
        }
    }
}
