public final class LampCombination{
    int num;
    int [] [] array;
    int row;
    int col;
    public LampCombination(int num,int[][]array,int row,int col){
        this.num = num;
        this.array = array;
    }

    public void above(){
        this.array[row-1][col] = 5;
    }

    public void under(){
        this.array[row+1][col] = 5;
    }

    public void left (){
        this.array[row][col-1] = 5;
    }

    public void right(){
        this.array[row][col+1] = 5;
    }

    

}