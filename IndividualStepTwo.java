import java.util.LinkedList;
import java.util.Random;

public class IndividualStepTwo {
    private int[] arrayNL; //berisi bilangan-bilangan kombinasi ketersediaan lampu untuk setiap NL
    private int[][] arraySoal; //soal (pada kasus ini adalah array yang didapat dari proses step 1)
    private int[][] arrayJawaban2;//jawaban
    private double parentProbability; //kemungkinan individu dipilih menjadi parent
    private LinkedList<Integer> NL; 
    private Random random;; //objek random
     //fitness terbaik adalah 0
    //jika semua lampu dari hasil random arrayNL ditempati, dan terjadi error, maka fitness akan bertambah.
    private int fitness; 

    //Constructor
    public IndividualStepTwo(int[] arrayNL, int[][] arraySoal, LinkedList<Integer> linkedList, Random r){
        this.arraySoal= arraySoal;
        this.arrayJawaban2 = new int[arraySoal.length][arraySoal.length]; //array jawaban panjangnya sama dengan array soal
        this.parentProbability = 0; //kemungkinan menjadi parent awalnya 0, dan diubah ketika sudah mendapat fitness individu dan fitness populasi
        this.random = r;

        //arrayJawaban pada awalnya sama dengan arraySoal
        for(int i = 0; i < arraySoal.length; i++){
            for(int j = 0; j < arraySoal.length; j++){
                this.arrayJawaban2[i][j] = arraySoal[i][j];
            }
        }

        this.fitness = 0; //fitness awalnya 0
        this.arrayNL = arrayNL;
        this.NL = linkedList;
        //method untuk mendapatkan fitness setiap individu
        this.setFitness();
    }

    //method untuk mengubah nilai parentProbability ketika sudah mendapat fitness individu dan fitness populasi
    public void setParentProbability(double p){
        this.parentProbability = p;
    }
    //mereturn parent probability
    public double getParentProbability(){
        return this.parentProbability;
    }
    //method untuk melakukan crossover
    //crossover dilakukan dengan membagi array Nl parent menjadi 2
    //kemudian 1/2 dari NL parent 1, dan 1/2 dari Nl parent 2 digabung untuk menjadi 1 child baru
    public IndividualStepTwo doCrossover(IndividualStepTwo other){
        int NLlength = this.arrayNL.length;//mendapatkan panjang
        int[] newNL = new int[NLlength]; //Nl baru untuk child
        //1/2 dari Nl parent 1 menjadi 1/2 dari newNBS dari 0 sampai tengah
        for(int i = 0; i < NLlength / 2; i++){
            newNL[i] = this.arrayNL[i];
        }

        //1/2 dari NL parent 2 menjadi 1/2 dari newNL dari tengah sampai akhir
        for(int i = (int) Math.ceil(NLlength/2); i < NLlength; i++){
            newNL[i] = other.arrayNL[i];
        }
        //membuat individu baru dengan NL yang baru
        IndividualStepTwo child = new IndividualStepTwo(newNL, this.arraySoal, this.NL, this.random);
        //return child tersebut
        return child;
    }
    //method untuk melakukan mutasi
    public void doMutation(){
                //looping sepanjang array NL
        for(int i = 0; i < NL.size(); i++){
                //generate antara 0 dan 1 dan dimasukkan pada arrayNL
                this.arrayNL[i] = this.random.nextInt(2);
        }
    }
    //setelah method-method placeLamp(), checkLightingOtherLamp(),heckNBSConstraint() ,dan checkMinusOne dijalankan, maka akan didapat fitness individu ini 
    private void setFitness(){
        //menaruh lampu sesuai kombinasi arrayNBS
        this.placeLamp();
        //mengecek jika lampu menyinari lampu lain
        this.checkLightingOtherLamp();
         //mengecek jika penempatan lampu tidak sesuai dengan aturan NL
        this.checkNLConstraint();
        //mengecek jika masih terdapat kotak yang -1 pada array NL
        this.checkMinusOne();
      
    }
    //mengembalikan fitness
    public int getFitness(){
        return this.fitness;
    }

    //printArrayJawaban, printArraySoal hanya untuk menampilkan isi masing-masing array
    //semuanya dilakukan dengan memprint satu-persatu elemen array dari awal array hingga akhir array
    //terdapat if dan else hanya untuk menambah spasi untuk merapikan hasil print
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

    public void printarrayJawaban(){
        int length = this.arraySoal.length;
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban2[i][j] < 0){
                    System.out.print(this.arrayJawaban2[i][j] + "  ");
                }else{
                    System.out.print(" " + this.arrayJawaban2[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    private void placeLamp(){
        int length = this.arraySoal.length; //mendapatkan panjang array
        int NLCounter = 0; //variabel untuk iterasi arrayNl, mulai dari 0

        //iterasi sepanjang arraySoal
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arraySoal[i][j] == -1){
                    if(arrayNL[NLCounter] == 1){
                        arrayJawaban2[i][j] = 5;
                    }
                    else {
                        arrayJawaban2[i][j] = -1;
                    }
                    NLCounter++;
                }
            }
        }
    }

    private void checkLightingOtherLamp(){
        int length = this.arraySoal.length; //mendapatkan panjang array

        //iterasi seluruh arraySoal
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //jika ditemukan 5 (ada lampu)
                if(arrayJawaban2[i][j] == 5){
                    int currentColumn = j; //catat kolom
                    int currentRow = i; //catat baris

                    //looping setiap baris di kolom yang sama dari baris saat ini sampai akhir baris
                    while(currentRow < length -1){
                        //jika ditemukan 0,1,2,3,4,-5 maka keluar dari loop
                        if(arrayJawaban2[currentRow+1][j] == 0 || arrayJawaban2[currentRow+1][j] == 1 || arrayJawaban2[currentRow+1][j] == 2 || arrayJawaban2[currentRow+1][j] == 3 || arrayJawaban2[currentRow+1][j] == 4 || arrayJawaban2[currentRow+1][j] == -5){
                            break;
                        }
                        //jika ditemukan 5, artinya lampu menyinari lampu lain, maka fitness + 1 dan keluar dari loop
                        if(arrayJawaban2[currentRow+1][j] == 5){
                            this.fitness += 1;
                            break;
                        }

                        //jika sampai di sini, loop masih berjalan, berarti kotak pada baris selanjutnya berhasil disinari
                        //sehingga nilai kotak tersebut menjadi -2
                        arrayJawaban2[currentRow+1][j] = -2;

                        //lanjut ke baris berikutnya
                        currentRow++;
                    }

                    //reset currentRow, karena sebelumnya sudah ditambah
                    currentRow = i;
    
                    //looping setiap baris di kolom yang sama dari baris saat ini sampai awal baris
                    while(currentRow != 0){
                        //jika ditemukan 0,1,2,3,4,-5 maka keluar dari loop
                        if(arrayJawaban2[currentRow-1][j] == 0 || arrayJawaban2[currentRow-1][j] == 1 || arrayJawaban2[currentRow-1][j] == 2 || arrayJawaban2[currentRow-1][j] == 3 || arrayJawaban2[currentRow-1][j] == 4 || arrayJawaban2[currentRow-1][j] == -5){
                            break;
                        }
                        //jika ditemukan 5, artinya lampu menyinari lampu lain, maka fitness + 1 dan keluar dari loop
                        if(arrayJawaban2[currentRow-1][j] == 5){
                            this.fitness += 1;
                            break;
                        }
                        //jika sampai di sini, loop masih berjalan, berarti kotak pada baris selanjutnya berhasil disinari
                        //sehingga nilai kotak tersebut menjadi -2
                        arrayJawaban2[currentRow-1][j] = -2;

                        //lanjut ke baris berikutnya
                        currentRow--;
                    }

                    //Lakukan hal yang sama, tetapi kali ini untuk mengecek kolom
                    while(currentColumn < length - 1){
                        if(arrayJawaban2[i][currentColumn + 1] == 0 || arrayJawaban2[i][currentColumn + 1] == 1 || arrayJawaban2[i][currentColumn + 1] == 2 || arrayJawaban2[i][currentColumn + 1] == 3 || arrayJawaban2[i][currentColumn + 1] == 4 || arrayJawaban2[i][currentColumn + 1] == -5){
                            break;
                        }
                        if(arrayJawaban2[i][currentColumn + 1] == 5){
                            this.fitness += 1;
                            break;
                        }
                        arrayJawaban2[i][currentColumn+1] = -2;
                        currentColumn++;
                    }

                    currentColumn = j;

                    while(currentColumn != 0){
                        if(arrayJawaban2[i][currentColumn - 1] == 0 || arrayJawaban2[i][currentColumn - 1] == 1 || arrayJawaban2[i][currentColumn - 1] == 2 || arrayJawaban2[i][currentColumn - 1] == 3 || arrayJawaban2[i][currentColumn - 1] == 4 || arrayJawaban2[i][currentColumn - 1] == -5){
                            break;
                        }
                        if(arrayJawaban2[i][currentColumn - 1] == 5){
                            this.fitness += 1;
                            break;
                        }
                        arrayJawaban2[i][currentColumn - 1] = -2;
                        currentColumn--;
                    }
                }
            }
        }
    }

    public void checkMinusOne(){
        for(int i = 0;i< arrayJawaban2.length;i++){ //iterasi seluruh array jawaban
            for(int j = 0;j< arrayJawaban2.length;j++){
                if(arrayJawaban2[i][j] == -1){  //jika terdapat -1 pada array jawaban
                    this.fitness+=1;    //menambah nilai fitness
                }
            }
        }
    }

    private void checkNLConstraint(){
        int length = this.arraySoal.length; //mendapatkan panjang array

        //iterasi seluruh array jawaban
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //JIKA I DAN J ADA DI TENGAH
                if(i > 0 && j > 0 && i != length - 1 && j != length - 1){
                    //Jika arraySoal = 0 dan di sekitarnya terdapat kotak bernilai 5 (ada lampu), maka fitness + 1
                    if(this.arrayJawaban2[i][j] == 0){
                        if(arrayJawaban2[i][j+1] == 5){
                            this.fitness += 1;     
                        }
                        if(arrayJawaban2[i][j-1] == 5){
                            this.fitness += 1;   
                        }
                        if(arrayJawaban2[i+1][j] == 5){
                            this.fitness += 1;                           
                        }
                        if(arrayJawaban2[i-1][j] == 5){
                            this.fitness += 1;    
                        }
                    }
                    //Jika arraySoal = 1 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        //jika ditemukan lampu di sekitar kotak, maka count1 bertambah 1
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count1++;
                        }
                        //jika terdapat lebih dari 1 lampu
                        if(count1 > 1){
                            this.fitness += (count1 - 1); //fitness bertambah sesuai banyaknya lampu yang kelebihan
                        }else if(count1 < 1){
                            this.fitness += 1; //jika tidak terdapat lampu, fitness + 1
                        }
                    }
                    //Jika arraySoal = 2 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i-1][j] == 5){
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
                    //Jika arraySoal = 3 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban2[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i-1][j] == 5){
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
                    //Jika arraySoal = 4 maka seharusnya ada lampu di semua sisinya
                    else if(this.arrayJawaban2[i][j] == 4){
                        //jika tidak ada lampu di salah satu sisi (kotak tidak bernilai 5), maka fitness bertambah 1
                        if(this.arrayJawaban2[i][j+1] != 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j-1] != 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i+1][j] != 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i-1][j] != 5){
                            this.fitness += 1;
                        }
                    }
                }
                //JIKA I DAN J DI POJOK KIRI ATAS
                //Karena i dan j ada di pojok, maka hanya perlu mengecek 0, 1, dan 2, karena tidak mungkin ada 3 dan 4 di pojok
                //Jumlah maksimal lampu di sekitar jika i dan j ada di pojok adalah 2
                else if(i == 0 && j == 0){
                    //Jika arraySoal = 0 maka seharusnya tidak ada lampu di semua sisinya
                    if(this.arrayJawaban2[i][j] == 0){
                        //Jika ditemukan lampu di salah satu sisi, maka fitness + 1
                        if(this.arrayJawaban2[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    //Jika arraySoal = 1 maka cek banyaknya lampu di semua sisinya
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count1++;
                        }
                        //Jika banyaknya lampu bukan 1, berarti antara terdapat 2 lampu, atau 0 lampu.
                        //Maka fitness + 1 (selisih 1 dengan 2 dan 0 adalah 1)
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    //Jika arraySoal = 2 maka cek banyaknya lampu di semua sisinya
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count2++;
                        } 
                        if(this.arrayJawaban2[i][j+1] == 5){
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
                    if(this.arrayJawaban2[i][j] == 0){
                        if(this.arrayJawaban2[i+1][j] == 5){
                            this.fitness += 1;
                        } 
                        if(this.arrayJawaban2[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count1++;
                        }
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count2++;
                        }
                        if(count2 != 2){
                            this.fitness += (2 - count2);
                        }
                    }
                }
                //JIKA I DAN J DI POJOK KANAN BAWAH
                else if(i == length - 1 && j == length - 1){
                    if(this.arrayJawaban2[i][j] == 0){
                        if(this.arrayJawaban2[i-1][j] == 5){
                            this.fitness += 1;
                        } 
                        if(this.arrayJawaban2[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count1++;
                        }
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count2++;
                        }
                        if(count2 != 2){
                            this.fitness += (2 - count2);
                        }
                    }
                }
                //JIKA I DAN J DI POJOK KIRI BAWAH
                else if(i == length - 1 && j == 0){
                    if(this.arrayJawaban2[i][j] == 0){
                        if(this.arrayJawaban2[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count1++;
                        }
                        if(count1 != 1){
                            this.fitness++;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count2++;
                        } 
                        if(this.arrayJawaban2[i][j+1] == 5){
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
                    //Jika arraySoal = 0 maka tidak boleh ada lampu di sekitarnya
                    if(this.arrayJawaban2[i][j] == 0){
                        //Jika ditemukan lampu, maka fitness + 1
                        if(this.arrayJawaban2[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    //Jika arraySoal = 1 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
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
                    else if(this.arrayJawaban2[i][j] == 2){
                        //Jika arraySoal = 2 maka cek jumlah lampu di sekitarnya
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
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
                    //Jika arraySoal = 3 maka cek jumlah lampu di sekitarnya
                    else if(this.arrayJawaban2[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
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
                    if(this.arrayJawaban2[i][j] == 0){
                        if(this.arrayJawaban2[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count1++;
                        } 
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count1++;
                        }

                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count2++;
                        } 
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count3++;
                        }
                        if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
                //JIKA I DI TENGAH, J DI KIRI
                else if(j == 0 && i < length - 1 && i > 0){
                    if(this.arrayJawaban2[i][j] == 0){
                        if(this.arrayJawaban2[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count1++;
                        }

                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j+1] == 5){
                            count3++;
                        }

                        if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
                //JIKA I DI TENGAH, J DI KANAN
                else if(j == length - 1 && i < length - 1 && i > 0){
                    if(this.arrayJawaban2[i][j] == 0){
                        if(this.arrayJawaban2[i-1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            this.fitness += 1;    
                        } 
                        if(this.arrayJawaban2[i][j-1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 1){
                        int count1 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count1++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count1++;
                        }

                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 2){
                        int count2 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += 1;
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban2[i][j] == 3){
                        int count3 = 0; //variabel untuk mengecek jumlah lampu di sekitar kotak
                        if(this.arrayJawaban2[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban2[i][j-1] == 5){
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


    

}