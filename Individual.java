public class Individual {
    private int[][] array;
    private int fitness;

    /*

    BENTUK ARRAY SOAL:
    -1  -5  -1  -1   3   -1  -1
    -1   0  -1  -1  -1   -5   0
     1  -1  -1  -1  -1   -1  -1
    -1  -1  -1  -5  -1   -1  -1
    -1  -1  -1  -1  -1   -1   1
     1  -5  -1  -1  -1   -5  -1
    -1  -1   1  -1  -1    0  -1


    BENTUK ARRAY JAWABAN:
         j   j   j   j   j    j   j
    i    5  -5   4   5   3    5   4
    i    4   0   4   4   5   -5   0
    i    1   4   5   4   4    4   4
    i    5   4   4  -5   4    5   4
    i    4   5   4   4   4    4   1
    i    1  -5   4   4   4   -5   5
    i    5   4   1   5   4    4   4

    */

    public Individual(int[][] array){
        this.fitness = 0;
        this.array = array;
    }

    public void setFitness(){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){

                //JIKA KETEMU 0, TIDAK BOLEH ADA LAMPU DI SEKITARNYA
                if(this.array[i][j] == 0){

                    //JIKA I DAN J BUKAN DI UJUNG
                    if(i != 0 && i != this.array.length && j != 0 && j != this.array[i].length){
                        //JIKA SEKELILINGNYA ADALAH 0 (BENER) MAKA FITNESS += 1
                        if(this.array[i+1][j] == 0 && this.array[i-1][j] == 0 && this.array[i][j+1] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I DI UJUNG ATAS DAN J DI TENGAH
                    else if(i == 0 && j != 0 && j != this.array[i].length){
                        if(this.array[i+1][j] == 0 && this.array[i][j+1] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I DI UJUNG BAWAH DAN J DI TENGAH
                    else if(i == this.array.length && j != 0 && j != this.array[i].length){
                        if(this.array[i-1][j] == 0 && this.array[i][j+1] == 0 && this.array[i][j-1] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA J DI UJUNG KIRI DAN I DI TENGAH
                    else if(j == 0 && i != 0 && i != this.array.length){
                        if(this.array[i][j+1] == 0 && this.array[i+1][j] == 0 && this.array[i-1][j] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA J DI UJUNG KANAN DAN I DI TENGAH
                    else if(j == this.array[i].length && i != 0 && i != this.array.length){
                        if(this.array[i][j-1] == 0 && this.array[i+1][j] == 0 && this.array[i-1][j] == 0){
                            this.fitness += 1;
                        }else{
                            this.fitness -= 1;
                        }
                    }

                    //JIKA I J DI UJUNG KIRI ATAS

                    //JIKA I J DI UJUNG KANAN ATAS

                    //JIKA I J DI UJUNG KIRI BAWAH

                    //JIKA I J DI UJUNG KANAN BAWAH
                }
            }
        } 
    }
}
