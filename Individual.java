import java.util.Random;

public class Individual {
    private int[] arrayNBS;
    private int[][] arraySoal;
    private int[][] arrayJawaban;
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

    /*
        0 1 2 3 4 : jumlah lampu yang ada di sekitar array[i][j] tersebut
        -5 : tembok
         5 : lampu
         6 : tidak ada lampu   
    */


    // YANG HARUS DICEK:
    // light bulb illuminating another light bulb
    // violation of a numbered black squares constraint

    public Individual(int[] arrayNBS, int[][] arrayS){
        this.arraySoal = arrayS;
        this.arrayJawaban = new int[arrayS.length][arrayS.length];

        for(int i = 0; i < arraySoal.length; i++){
            for(int j = 0; j < arraySoal.length; j++){
                this.arrayJawaban[i][j] = arraySoal[i][j];
            }
        }

        this.arrayNBS = arrayNBS;
        this.fitness = 0;
        this.placeLamp();
        this.checkLightingOtherLamp();
        this.checkNBSConstraint();
    }

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

    private void checkNBSConstraint(){
        int length = this.arraySoal.length;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                //JIKA I DAN J ADA DI TENGAH
                if(i > 0 && j > 0 && i != length - 1 && j != length - 1){
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
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0;
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
                        if(count1 > 1){
                            this.fitness += (count1 - 1);
                        }else if(count1 < 1){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 2){
                        int count2 = 0;
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
                        if(count2 > 2){
                            this.fitness += (count2 - 2);
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0;
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
                        if(count3 > 3){
                            this.fitness += 1;
                        }else if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 4){
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
                else if(i == 0 && j == 0){
                    if(this.arrayJawaban[i][j] == 0){
                        if(this.arrayJawaban[i+1][j] == 5){
                            this.fitness += 1;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            this.fitness += 1;
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0;
                        if(this.arrayJawaban[i+1][j] == 5){
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
                        int count2 = 0;
                        if(this.arrayJawaban[i+1][j] == 5){
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
                        int count1 = 0;
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
                        int count2 = 0;
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
                        int count1 = 0;
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
                        int count2 = 0;
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
                        int count1 = 0;
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
                        int count2 = 0;
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
                else if(i == 0 && j < length - 1 && j > 0){
                    if(this.arrayJawaban[i][j] == 0){
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
                    else if(this.arrayJawaban[i][j] == 1){
                        int count1 = 0;
                        if(this.arrayJawaban[i+1][j] == 5){
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
                        int count2 = 0;
                        if(this.arrayJawaban[i+1][j] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count2++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count2++;
                        }

                        if(count2 > 2){
                            this.fitness += (count2 - 2);
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0;
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }

                        if(count3 > 3){
                            this.fitness += 1;
                        }else if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
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
                        int count1 = 0;
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
                        int count2 = 0;
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
                            this.fitness += (count2 - 2);
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0;
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }
                        if(count3 > 3){
                            this.fitness += 1;
                        }else if(count3 < 3){
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
                        int count1 = 0;
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
                        int count2 = 0;
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
                            this.fitness += (count2 - 2);
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0;
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j+1] == 5){
                            count3++;
                        }

                        if(count3 > 3){
                            this.fitness += 1;
                        }else if(count3 < 3){
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
                        int count1 = 0;
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
                        int count2 = 0;
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
                            this.fitness += (count2 - 2);
                        }else if(count2 < 2){
                            this.fitness += (2 - count2);
                        }
                    }
                    else if(this.arrayJawaban[i][j] == 3){
                        int count3 = 0;
                        if(this.arrayJawaban[i-1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i+1][j] == 5){
                            count3++;
                        }
                        if(this.arrayJawaban[i][j-1] == 5){
                            count3++;
                        }

                        if(count3 > 3){
                            this.fitness += 1;
                        }else if(count3 < 3){
                            this.fitness += (3 - count3);
                        }
                    }
                }
            }
        }
    }

    private void checkLightingOtherLamp(){
        int length = this.arraySoal.length;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban[i][j] == 5){
                    int currentColumn = j;
                    int currentRow = i;

                    while(currentRow < length -1){
                        if(arrayJawaban[currentRow+1][j] == 0 || arrayJawaban[currentRow+1][j] == 1 || arrayJawaban[currentRow+1][j] == 2 || arrayJawaban[currentRow+1][j] == 3 || arrayJawaban[currentRow+1][j] == 4){
                            break;
                        }
                        if(arrayJawaban[currentRow+1][j] == -5){
                            break;
                        }
                        if(arrayJawaban[currentRow+1][j] == 5){
                            this.fitness += 1;
                            break;
                        }
                        arrayJawaban[currentRow+1][j] = -2;
                        currentRow++;
                    }

                    currentColumn = j;
                    currentRow = i;
    
                    while(currentRow != 0){
                        if(arrayJawaban[currentRow-1][j] == 0 || arrayJawaban[currentRow-1][j] == 1 || arrayJawaban[currentRow-1][j] == 2 || arrayJawaban[currentRow-1][j] == 3 || arrayJawaban[currentRow-1][j] == 4){
                            break;
                        }
                        if(arrayJawaban[currentRow-1][j] == -5){
                            break;
                        }
                        if(arrayJawaban[currentRow-1][j] == 5){
                            this.fitness += 1;
                            break;
                        }
                        arrayJawaban[currentRow-1][j] = -2;
                        currentRow--;
                    }

                    currentColumn = j;
                    currentRow = i;

                    while(currentColumn < length - 1){
                        if(arrayJawaban[i][currentColumn + 1] == 0 || arrayJawaban[i][currentColumn + 1] == 1 || arrayJawaban[i][currentColumn + 1] == 2 || arrayJawaban[i][currentColumn + 1] == 3 || arrayJawaban[i][currentColumn + 1] == 4){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn + 1] == -5){
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
                    currentRow = i;

                    while(currentColumn != 0){
                        if(arrayJawaban[i][currentColumn - 1] == 0 || arrayJawaban[i][currentColumn - 1] == 1 || arrayJawaban[i][currentColumn - 1] == 2 || arrayJawaban[i][currentColumn - 1] == 3 || arrayJawaban[i][currentColumn - 1] == 4){
                            break;
                        }
                        if(arrayJawaban[i][currentColumn - 1] == -5){
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

    private void checkTembok(){
        int length = this.arraySoal.length;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arrayJawaban[i][j] == 5 && arraySoal[i][j] == -5){
                    this.fitness += 1;
                    this.arrayJawaban[i][j] = -5;
                }
            }
        }
    }


    private void placeLamp(){
        int length = this.arraySoal.length;
        int NBSCounter = 0;

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(arraySoal[i][j] == 0){
                    NBSCounter++;
                }else if(arraySoal[i][j] == 4){
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
                    NBSCounter++;
                }else if(arraySoal[i][j] == 1){
                    if(arrayNBS[NBSCounter] == 1){
                        try {
                            this.arrayJawaban[i-1][j] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    else if(arrayNBS[NBSCounter] == 2){
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
                    }
                    else if(arrayNBS[NBSCounter] == 4){
                        try {
                            this.arrayJawaban[i][j-1] = 5;
                        }catch (Exception e){
                            fitness += 1;
                        }
                    }
                    NBSCounter++;
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
        this.checkTembok();

    }
}
