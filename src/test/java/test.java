public class test {
    public static void main(String[] args) {

        boolean[][][] a = new boolean[1000][1500][2000];

        long startTime = System.currentTimeMillis();
        System.gc();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1500; j++) {
                for (int h = 0; h < 2000; h++)
                    a[i][j][h] = false;
            }
        }
        System.gc();
        System.out.println(String.format("Time Taken Milliseconds: %d",  (int) (System.currentTimeMillis() - startTime)));

    }
}
