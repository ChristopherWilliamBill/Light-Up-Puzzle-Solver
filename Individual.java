import java.util.LinkedList;
import java.util.Random;

public class Individual {
    private int[] arrayNBS; //berisi bilangan-bilangan kombinasi penempatan lampu untuk setiap NBS
    private int[][] arraySoal; 
    private int[][] arrayJawaban;
    private double parentProbability;
    private LinkedList<Integer> NBS;
    private Random random;

    //fitness terbaik adalah 0
    //jika semua lampu dari hasil random arrayNBS ditempati, dan terjadi error, maka fitness akan bertambah.
    private int fitness; 

    // YANG HARUS DICEK:
    // 1. lampu menyinari lampu lain -> fitness + 1
    //
    // 2. mengecek aturan NBS:
    // hanya boleh ada 1 lampu di sekitar kotak 1, ada 2 lampu di sekitar kotak 2, dst.
    // contoh:
    // ada 4 lampu di sekitar kotak 2 -> fitness + 2
    // ada 1 lampu di sekitar kotak 3 -> fitness + 2

    public Individual(int[] arrayNBS, int[][] arrayS, LinkedList<Integer> linkedList, Random r){
        this.arraySoal = arrayS;
        this.arrayJawaban = new int[arrayS.length][arrayS.length];
        this.parentProbability = 0;
        this.random = r;

        //arrayJawaban pada awalnya sama dengan arraySoal
        for(int i = 0; i < arraySoal.length; i++){
            for(int j = 0; j < arraySoal.length; j++){
                this.arrayJawaban[i][j] = arraySoal[i][j];
            }
        }

        this.arrayNBS = arrayNBS;
        this.fitness = 0; //fitness awalnya 0

        this.NBS = linkedList;

        //method untuk mendapatkan fitness setiap individu
        this.setFitness();  
    }

    public void setParentProbability(double p){
        this.parentProbability = p;
    }

    public double getParentProbability(){
        return this.parentProbability;
    }

    public Individual doCrossover(Individual other){
        int NBSlength = this.arrayNBS.length;
        int[] newNBS = new int[NBSlength];

        for(int i = 0; i < NBSlength / 2; i++){
            newNBS[i] = this.arrayNBS[i];
        }

        for(int i = (int) Math.ceil(NBSlength/2); i < NBSlength; i++){
            newNBS[i] = other.arrayNBS[i];
        }

        Individual child = new Individual(newNBS, this.arraySoal, this.NBS, this.random);

        return child;
    }

    public void doMutation(){
        for(int i = 0; i < NBS.size(); i++){
            if(this.NBS.get(i) == 3 || this.NBS.get(i) == 1){
                this.arrayNBS[i] = this.random.nextInt(5 - 1) + 1;
            }
        }
    }

    private void setFitness(){
        //menaruh lampu sesuai kombinasi arrayNBS
        this.placeLamp();
        //mengecek jika lampu menyinari lampu lain
        this.checkLightingOtherLamp();
        //mengecek jika penempatan lampu tidak sesuai dengan aturan NBS
        this.checkNBSConstraint();
    }

    //mengembalikan fitness
    public int getFitness(){
        return this.fitness;
    }

    public void printNBS(){
        for(int i = 0; i < arrayNBS.length; i++){
            System.out.print(arrayNBS[i] + " ");
        }
        System.out.println();
    }

    public void printArrayJawaban(){
        int length = this.arraySoal.length;
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban[i][j] < 0){
                    System.out.print(this.arrayJawaban[i][j] + "  ");
                }else{
                    System.out.print(" " + this.arrayJawaban[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    public void printArraySoal(){
        int length = this.arraySoal.length;
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arraySoal[i][j] < 0){
                    System.out.print(this.arraySoal[i][j] + "  ");
                }else{
                    System.out.print(" " + this.arraySoal[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    //printNBS, printArrayJawaban, printArraySoal hanya untuk melihat isi masing-masing array
    //semuanya dilakukan dengan memprint satu-persatu elemen array dari awal array hingga akhir array
    //terdapat if dan else hanya untuk menambah spasi untuk merapikan hasil print


    private void checkNBSConstraint(){
        int length = this.arraySoal.length; //mendapatkan panjang array

        //iterasi seluruh array jawaban
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //JIKA I DAN J ADA DI TENGAH
                if(i > 0 && j > 0 && i != length - 1 && j != length - 1){
                    //Jika arrayJawaban = 0 dan di sekitarnya terdapat kotak bernilai 5 (ada lampu), maka fitness + 1
                    if(this.arrayJawaban[i][j] == 0){
                        if(arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;     
                        }
                        if(arrayJawaban[i][j-1] == 5){
                            this.fitness += 1;   
                        }
                        if(arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;                           
                        }
                        if(arrayJawaban[i-1][j] == 5){
                            this.fitness += 1;    
                        }
                    }
                    //Jika arrayJawaban = 1 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        //jika ditemukan lampu di sekitar kotak, maka count1 bertambah 1
                        if(this.arrayJawaban[i][j+1] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i-1][j] == 5){
                            count1++;
                        }
                        //jika terdapat lebih dari 1 lampu
                        if(count1 > 1){
                            this.fitness += (count1 - 1); //fitness bertambah sesuai banyaknya lampu yang kelebihan
                        }else if(count1 < 1){
                            this.fitness += 1; //jika tidak terdapat lampu, fitness + 1
                        }
                    }
                    //Jika arrayJawaban = 2 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i-1][j] == 5){
                            count2++;
                        }
                        //jika terdapat lebih dari 2 lampu
                        if(count2 > 2){
                            this.fitness += (count2 - 2); //fitness bertambah sesuai banyaknya lampu yang kelebihan
                        //jika terdapat kurang dari 2 lampu
                        }else if(count2 < 2){
                            this.fitness += (2 - count2); //fitness bertambah sesuai banyaknya lampu yang kekurangan
                        }
                    }
                    //Jika arrayJawaban = 3 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        //jika terdapat lebih dari 3 lampu
                        if(count3 > 3){
                            this.fitness += 1; //fitness bertambah 1, karena berarti ada 4 lampu (kelebihan 1 lampu)
                        //jika terdapat kurang dari 3 lampu
                        }else if(count3 < 3){
                            this.fitness += (3 - count3); //fitness bertambah sesuai banyaknya lampu yang kekurangan
                        }
                    }
                    //Jika arrayJawaban = 4 maka seharusnya ada lampu di semua sisinya
                    else if(this.arrayJawaban[i][j] == 4){
                        //jika tidak ada lampu di salah satu sisi (kotak tidak bernilai 5), maka fitness bertambah 1
                        if(this.arrayJawaban[i][j+1] != 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j-1] != 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i+1][j] != 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i-1][j] != 5){
                            this.fitness += 1;
                        }
                    }
                }
                //JIKA I DAN J DI POJOK KIRI ATAS
                //Karena i dan j ada di pojok, maka hanya perlu mengecek 0, 1, dan 2, karena tidak mungkin ada 3 dan 4 di pojok
                //Jumlah maksimal lampu di sekitar jika i dan j ada di pojok adalah 2
                else if(i == 0 && j == 0){
                    //Jika arrayJawaban = 0 maka seharusnya tidak ada lampu di semua sisinya
                    if(this.arrayJawaban[i][j] == 0){
                        //Jika ditemukan lampu di salah satu sisi, maka fitness + 1
                        if(this.arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    //Jika arrayJawaban = 1 maka cek banyaknya lampu di semua sisinya
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count1++;
                        }
                        //Jika banyaknya lampu bukan 1, berarti antara terdapat 2 lampu, atau 0 lampu.
                        //Maka fitness + 1 (selisih 1 dengan 2 dan 0 adalah 1)
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    //Jika arrayJawaban = 2 maka cek banyaknya lampu di semua sisinya
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        } 
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        }
                        //Jika banyaknya lampu bukan 2, berarti fitness + selisih kurangnya lampu
                        if(count2 != 2){
                            this.fitness += (2 - count2);
                        }
                    }
                }
                //Lakukan hal yang sama untuk setiap pojokan, yang berbeda hanya index pengecekannya
                //JIKA I DAN J DI POJOK KANAN ATAS
                else if(i == 0 && j == length - 1){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;
                        } 
                        if(this.arrayJawaban[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count1++;
                        }
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }
                        if(count2 != 2){
                            this.fitness += (2 - count2);
                        }
                    }
                }
                //JIKA I DAN J DI POJOK KANAN BAWAH
                else if(i == length - 1 && j == length - 1){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i-1][j] == 5){
                            this.fitness += 1;
                        } 
                        if(this.arrayJawaban[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count1++;
                        }
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }
                        if(count2 != 2){
                            this.fitness += (2 - count2);
                        }
                    }
                }
                //JIKA I DAN J DI POJOK KIRI BAWAH
                else if(i == length - 1 && j == 0){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count1++;
                        }
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count2++;
                        } 
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        }
                        if(count2 != 2){
                            this.fitness += (2 - count2);
                        }
                    }
                }
                //JIKA I DI ATAS, J DI TENGAH
                //Karena i atau j ada di ujung dan yang lainnya ada di pojok, 
                //maka hanya perlu mengecek 0, 1, 2, dan 3 karena tidak mungkin ada 4
                //jumlah maksimal lampu di sekitar i j adalah 3
                else if(i == 0 && j < length - 1 && j > 0){
                    //Jika arrayJawaban = 0 maka tidak boleh ada lampu di sekitarnya
                    if(this.arrayJawaban[i][j] == 0){
                        //Jika ditemukan lampu, maka fitness + 1
                        if(this.arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    //Jika arrayJawaban = 1 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count1++;
                        }
                        //Jika terdapat lebih dari 1 lampu, maka kemungkinannya adalah 2 atau 3
                        //maka fitness + selisih jumlah lampu yang kebanyakan
                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                        //jika tidak ditemukan lampu, maka fitness + 1
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        //Jika arrayJawaban = 2 maka cek jumlah lampu di sekitarnya
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }
                        //jika terdapat lebih dari 2 lampu (terdapat 3 lampu) maka fitness + 1
                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            //jika jumlah lampu kurang dari 2, maka fitness + selisih lampu yang kekurangan
                            this.fitness += (2 - count2);
                        }
                    }
                    //Jika arrayJawaban = 3 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }

                        //jika terdapat kurang dari 3 lampu maka fitness + selisih lampu yang kekurangan
                        if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
                //Lakukan hal yang sama untuk setiap i j di mana salah satunya ada di ujung dan yang lainnya ada di tengah, yang berbeda hanya index pengecekannya
                //JIKA I DI BAWAH, J DI TENGAH
                else if(i == length - 1 && j < length - 1 && j > 0){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count1++;
                        } 
                        if(this.arrayJawaban[i][j-1] == 5){
                            count1++;
                        }

                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        } 
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }
                        if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
                //JIKA I DI TENGAH, J DI KIRI
                else if(j == 0 && i < length - 1 && i > 0){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count1++;
                        }

                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }

                        if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
                //JIKA I DI TENGAH, J DI KANAN
                else if(j == length - 1 && i < length - 1 && i > 0){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;    
                        } 
                        if(this.arrayJawaban[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count1++;
                        }

                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }

                        if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
            }
        }
    }

    private void checkLightingOtherLamp(){
        int length = this.arraySoal.length; //mendapatkan panjang array

        //iterasi seluruh arrayJawaban
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //jika ditemukan 5 (ada lampu)
                if(arrayJawaban[i][j] == 5){
                    int currentColumn = j; //catat kolom
                    int currentRow = i; //catat baris

                    //looping setiap baris di kolom yang sama dari baris saat ini sampai akhir baris
                    while(currentRow < length -1){
                        //jika ditemukan 0,1,2,3,4,-5 maka keluar dari loop
                        if(arrayJawaban[currentRow+1][j] == 0 || arrayJawaban[currentRow+1][j] == 1 || arrayJawaban[currentRow+1][j] == 2 || arrayJawaban[currentRow+1][j] == 3 || arrayJawaban[currentRow+1][j] == 4 || arrayJawaban[currentRow+1][j] == -5){
                            break;
                        }
                        //jika ditemukan 5, artinya lampu menyinari lampu lain, maka fitness + 1 dan keluar dari loop
                        if(arrayJawaban[currentRow+1][j] == 5){
                            this.fitness += 1;
                            break;
                        }

                        //jika sampai di sini, loop masih berjalan, berarti kotak pada baris selanjutnya berhasil disinari
                        //sehingga nilai kotak tersebut menjadi -2
                        arrayJawaban[currentRow+1][j] = -2;

                        //lanjut ke baris berikutnya
                        currentRow++;
                    }

                    //reset currentRow, karena sebelumnya sudah ditambah
                    currentRow = i;
    
                    //looping setiap baris di kolom yang sama dari baris saat ini sampai awal baris
                    while(currentRow != 0){
                        //jika ditemukan 0,1,2,3,4,-5 maka keluar dari loop
                        if(arrayJawaban[currentRow-1][j] == 0 || arrayJawaban[currentRow-1][j] == 1 || arrayJawaban[currentRow-1][j] == 2 || arrayJawaban[currentRow-1][j] == 3 || arrayJawaban[currentRow-1][j] == 4 || arrayJawaban[currentRow-1][j] == -5){
                            break;
                        }
                        //jika ditemukan 5, artinya lampu menyinari lampu lain, maka fitness + 1 dan keluar dari loop
                        if(arrayJawaban[currentRow-1][j] == 5){
                            this.fitness += 1;
                            break;
                        }
                        //jika sampai di sini, loop masih berjalan, berarti kotak pada baris selanjutnya berhasil disinari
                        //sehingga nilai kotak tersebut menjadi -2
                        arrayJawaban[currentRow-1][j] = -2;

                        //lanjut ke baris berikutnya
                        currentRow--;
                    }

                    //Lakukan hal yang sama, tetapi kali ini untuk mengecek kolom
                    while(currentColumn < length - 1){
                        if(arrayJawaban[i][currentColumn + 1] == 0 || arrayJawaban[i][currentColumn + 1] == 1 || arrayJawaban[i][currentColumn + 1] == 2 || arrayJawaban[i][currentColumn + 1] == 3 || arrayJawaban[i][currentColumn + 1] == 4 || arrayJawaban[i][currentColumn + 1] == -5){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn + 1] == 5){
                            this.fitness += 1;
                            break;
                        }
                        arrayJawaban[i][currentColumn+1] = -2;
                        currentColumn++;
                    }

                    currentColumn = j;

                    while(currentColumn != 0){
                        if(arrayJawaban[i][currentColumn - 1] == 0 || arrayJawaban[i][currentColumn - 1] == 1 || arrayJawaban[i][currentColumn - 1] == 2 || arrayJawaban[i][currentColumn - 1] == 3 || arrayJawaban[i][currentColumn - 1] == 4 || arrayJawaban[i][currentColumn - 1] == -5){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn - 1] == 5){
                            this.fitness += 1;
                            break;
                        }
                        arrayJawaban[i][currentColumn - 1] = -2;
                        currentColumn--;
                    }
                }
            }
        }
    }

    private void checkNabrak(){
        int length = this.arraySoal.length; //mendapatkan panjang array

        //iterasi arrayJawaban
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //jika pada arrayJawaban, ditemukan 5 (ada lampu di kotak tersebut)
                //tetapi pada arraySoal, kotak tersebut adalah -5 (tembok), atau  0, 1, 2, 3, 4 (clue), maka fitness + 1 dan mengembalikan kotak tersebut sesuai kotak pada soal
                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == -5){
                    this.fitness += 1; 
                    this.arrayJawaban[i][j] = -5; 
                }

                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == 0){
                    this.fitness += 1; 
                    this.arrayJawaban[i][j] = 0; 
                }

                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == 1){
                    this.fitness += 1; 
                    this.arrayJawaban[i][j] = 1; 
                }

                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == 2){
                    this.fitness += 1; 
                    this.arrayJawaban[i][j] = 2; 
                }

                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == 3){
                    this.fitness += 1; 
                    this.arrayJawaban[i][j] = 3; 
                }

                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == 4){
                    this.fitness += 1; 
                    this.arrayJawaban[i][j] = 4; 
                }
            }
        }
    }

    private void placeLamp(){
        int length = this.arraySoal.length; //mendapatkan panjang array
        int NBSCounter = 0; //variabel untuk iterasi arrayNBS, mulai dari 0

        //menggunakan try catch untuk menaruh lampu, jika lampu ditaruh di luar batas index, maka fitness + 1

        //iterasi sepanjang arraySoal
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //jika ditemukan 0, maka NBSCounter++
                //Hal ini dikarenakan nilai arrayNBS jika kotak bernilai 0 pasti 1, sehingga dapat dilewat
                if(arraySoal[i][j] == 0){
                    NBSCounter++;
                }
                //jika ditemukan 4, maka mengubah semua nilai arrayJawaban di sekelilingnya menjadi 5 (lampu)
                else if(arraySoal[i][j] == 4){
                    try {
                        this.arrayJawaban[i][j+1] = 5;
                    }catch (Exception e){
                        fitness += 1;
                    }
                    try {
                        this.arrayJawaban[i][j-1] = 5;
                    }catch (Exception e){
                        fitness += 1;
                    }
                    try {
                        this.arrayJawaban[i+1][j] = 5;
                    }catch (Exception e){
                        fitness += 1;
                    }
                    try {
                        this.arrayJawaban[i-1][j] = 5;
                    }catch (Exception e){
                        fitness += 1;
                    }
                    //lanjut ke index arrayNBS selanjutnya
                    NBSCounter++;
                }else if(arraySoal[i][j] == 1){
                    //jika ditemukan 1, maka mengubah nilai arrayJawaban di sekelilingnya menjadi 5 (lampu) tergantung kombinasi yang didapat dari arrayNBS
                    //jika arrayNBS bernilai 1, taruh lampu di atas kotak
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    //jika arrayNBS bernilai 2, taruh lampu di kanan kotak
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    //jika arrayNBS bernilai 3, taruh lampu di bawah kotak
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    //jika arrayNBS bernilai 4, taruh lampu di kiri kotak
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    //lanjut ke index arrayNBS selanjutnya
                    NBSCounter++;
                //Lanjutkan untuk semua arraySoal yang bernilai 2 dan 3 dengan prinsip yang sama.
                //Untuk arraySoal yang bernilai 2, ada 6 kombinasi penempatan lampu
                //Untuk arraySoal yang bernilai 3, ada 4 kombinasi penempatan lampu
                //Kombinasi penempatan lampu ada di file KombinasiNBS.txt
                } else if (arraySoal[i][j] == 2){
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }

                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 5){
                        try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 6){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    NBSCounter++;

                } else if (arraySoal[i][j] == 3){
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 3){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i+1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                        try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }try {
                            this.arrayJawaban[i][j+1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    NBSCounter++;
                }
            }
        }

        //Karena penempatan lampu dilakukan tanpa mengecek jika lampu bertabrakan dengan tembok / clue lainnya
        //maka perlu dipanggil method checkNabrak untuk menanganinya
        this.checkNabrak();
    }
}