import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;

public class PopulationStepTwo{
    public ArrayList<IndividualStepTwo> populationsteptwo;  //arraylist of individual 
    private Random random;  //objek random
    private int maxPopulationSize;  //jumlah individual maksimum
    private int populationSize = 0;  //jumlah individu di populasi ini
    private int[][] jawaban;    // array jawaban setelah proses step satu
    private int NumberOfNoLamps;    //jumlah kotak tidak berlampu
    private LinkedList<Integer> linkedList; //linkedlist untuk menyimpan isi kotak clue tersebut

     //Constructor
    public PopulationStepTwo(Random random, int maxPopulationSize,int [][] jawaban) {
        this.populationsteptwo = new ArrayList<IndividualStepTwo>();
		this.random = random;
        this.maxPopulationSize = maxPopulationSize;
        //inisialisi variabel NumberofNoLamps (NL) untuk menghitung banyaknya kotak  -1 yang menjadi soal/clue penempatan lampu
        this.NumberOfNoLamps = 0;
        this.linkedList = new LinkedList<Integer>();
        this.jawaban = jawaban;
        //iterasi seluruh soal
        for(int i = 0; i < jawaban.length; i++){
            for(int j = 0; j < jawaban[i].length; j++){
                //jika ada kotak adalah -1, artinya kotak tersebut adalah kotak NL
                if(jawaban[i][j] == -1){
                    NumberOfNoLamps++; //maka banyaknya NL bertambah
                    linkedList.add(jawaban[i][j]); //dan catat nilai kotak tersebut di linkedlist
                }
            }
        }
    }

    public void generateRandomPopulation() {    // fungsi untuk mengenerate populasi acak
        for (int i = 0; i < this.maxPopulationSize; i++) { //looping jika kondisi i lebih kecil dari jumlah maksimal populasi
            int[] NL = new int[this.NumberOfNoLamps];       //inisialisasi array nolamps
            for(int j = 0; j < this.NumberOfNoLamps; j++){  //looping jika kondisi j lebih kecil dari jumlah no lamps
                    NL[j] = this.random.nextInt(2) ;        //generate angka antara 0 dan 1 untuk dimasukkan pada array nolamps
            }

            //membuat objek individual baru dengan array NoLamps dan array2d jawaban yang didapat dari step 1
            IndividualStepTwo individualsteptwo = new IndividualStepTwo(NL, this.jawaban, this.linkedList,this.random);
            this.addIndividualStepTwo(individualsteptwo);
        }
    }


    //fungsi untuk menambah individual baru 
    public boolean addIndividualStepTwo(IndividualStepTwo newInd) {
        if (this.populationSize >= this.maxPopulationSize){ //jika jumlah populasi sekarang lebih besar dari batas populasi,
            return false;
        } 
        this.populationsteptwo.add(newInd); //tambahkan individu kedalam populasi
        this.populationSize++;  //menambah jumlah populasi sekarang
        return true;
    }

    public IndividualStepTwo[] selectParent() {    //roulette wheel
         //dipilih 2 parent dan disimpan di array dengan panjang 2
        IndividualStepTwo[] parents = new IndividualStepTwo[2];
        //jumlah seluruh fitness dari individu
        int sumfitness = 0;
        //iterasi seluruh individu 
        for (int i = 0; i < this.populationsteptwo.size(); i++) {
             //dan jumlahkan semua fitnessnya
            sumfitness += this.populationsteptwo.get(i).getFitness();
        }
        //menentukan kemungkinan setiap individu dipilih menjadi parent
        for (int i = 0; i < this.populationsteptwo.size(); i++) {
             //kemungkinan dipilih menjadi parent adalah fitness individu tersebut dibagi jumlah fitness seluruh individu
            ((IndividualStepTwo) this.populationsteptwo.get(i)).setParentProbability(1.0 * this.populationsteptwo.get(i).getFitness() / sumfitness);
        }
         //memilih 2 parent
        for (int n = 0; n < 2; n++) {
            int i = -1; //index untuk mendapat individu dari arraylist
            double prob = this.random.nextDouble(); //kemungkinan yang dirandom
            double sum = 0.0;
            do {
                i++; //index bertambah
                sum = sum + this.populationsteptwo.get(i).getParentProbability(); //sum bertambah dengan nilai kemungkinan parent tersebut dipilih
            } while(sum < prob); //looping jika sum < prob
            //jika keluar dari loop, akan didapat suatu index i
            parents[n] = this.populationsteptwo.get(i); //individu di index tersebut akan menjadi parent
        }
        return parents;
    }

    //method untuk mendapatkan individual terbaik dari suatu populasi
    public IndividualStepTwo getBestIndividual(){
        int best = Integer.MAX_VALUE; //best awalnya adalah max value integer
        int index = 0; //index untuk iterasi dimulai dari 0
        int bestIndex = 0; //index dari individual terbaik
        //loop jika belum didapat individu dengan fitness 0 (individu terbaik) atau jika seluruh individu belum dicek
        while(best != 0 && index < this.populationsteptwo.size()){
             //jika mendapat fitness yang lebih kecil dari best, maka ubah nilai best menjadi fitness tersebut
            if(this.populationsteptwo.get(index).getFitness() < best){
                best = this.populationsteptwo.get(index).getFitness();
                bestIndex = index; //catat index dari individu terbaik
            }
            index++; //index bertambah
        }

        return this.populationsteptwo.get(bestIndex); //return individu dengan index best (individu terbaik)
    }

        //membuat populasi baru dengan elitism dengan kemungkinan terjadinya elitism adalah elitismRate
    public PopulationStepTwo generateNewPopulationWithElitism(double elitismRate){
        PopulationStepTwo newPopulation = new PopulationStepTwo(this.random, this.maxPopulationSize, this.jawaban); //membuat populasi baru
        //jika nextDouble() < elitismRate, maka terjadi elitism dan best individual populasi ini langsung dimasukkan ke populasi baru
        if (this.random.nextDouble() < elitismRate){
            newPopulation.addIndividualStepTwo(this.getBestIndividual());
        }
        //return populasi baru
        return newPopulation;
    }
   //mereturn true jika besar populasi sudah = besar populasi maksimal
    public boolean isFilled() {
        return this.maxPopulationSize == this.populationsteptwo.size();
    }

}