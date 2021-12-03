import java.util.Random;

public class LightUpStepTwo {

    private int totalGeneration;     //total generasi
    private Random random;          //objek random
    private double crossoverRate;  //kemungkinan terjadinya crossover
    private double mutationRate;   //kemungkinan terjadinya mutasi
    private int [][] jawaban;       //array jawaban yang didapat dari pengerjaan step 1
    private double elitismRate;      //kemungkinan terjadinya elitism

    //constructor
    public LightUpStepTwo(Random random, int totalGeneration, double crossoverRate, double mutationRate,int [][]jawaban, double elitismRate){
        this.random = random;
        this.totalGeneration = totalGeneration;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.jawaban = jawaban;
        this.elitismRate = elitismRate;
    }

     //method untuk mendapatkan individual terbaik
    public IndividualStepTwo run(){
        //membuat populasi pertama
        PopulationStepTwo currPopulation = new PopulationStepTwo(this.random, 1000,this.jawaban);
        currPopulation.generateRandomPopulation();
        //menyatakan jumlah generasi
        int generation = 1;
        //loop jika jumlah generasi belum mencapai batas
        while(!terminate(generation)){
            //buat populasi baru dengan elitism
            PopulationStepTwo newPopulation = currPopulation.generateNewPopulationWithElitism(this.elitismRate);
            //loop jika populasi yang baru belum terisi sampai jumlah individu
            while(newPopulation.isFilled() == false){
                 //memilih 2 parents
                IndividualStepTwo[] parents =  currPopulation.selectParent();
                 //jika nextDouble() < crossoverRate maka akan terjadi crossOver
                if (this.random.nextDouble() < this.crossoverRate){
                    //mendapatkan child dari hasil crossover parents
                    IndividualStepTwo child = parents[0].doCrossover(parents[1]);
                    //jika nextDouble() < mutationRate maka akan terjadi mutation
                    if (this.random.nextDouble() < this.mutationRate) {
                        child.doMutation();
                    }
                    //mengisi populasi yang baru dengan child yang tadi dibuat.
                    newPopulation.addIndividualStepTwo(child);
                }
            }
            //sampai sini, populasi yang baru sudah terbentuk dan jumlah generasi bertambah
            generation++;
            currPopulation = newPopulation;  // populasi yang baru menjadi populasi 

            //jika didapat individu dengan fitness = 0, maka sudah didapat individu terbaik dan algoritma dapat berhenti
            if(currPopulation.getBestIndividual().getFitness() == 0){
                 break;
            }
        }
        //mengembalikan individu terbaik
        return currPopulation.getBestIndividual();
    }
    
    public boolean terminate(int generation){   //fungsi untuk melakukan terminasi dengan parameter generasi masukkan dari user
        if (generation >= this.totalGeneration){    //jika generation lebih besar sama dengan total generasi yang diinginkan,maka
            return true;                           
        }else{
            return false;                          
        }
    }
}