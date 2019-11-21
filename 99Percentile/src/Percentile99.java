import java.io.Console;
import java.util.Arrays;

public class Percentile99 {
   public static void main(String[] args)
    {
//        double latencies[] = { 3, 6, 7, 8, 8, 9, 10, 13, 15, 16, 20 };
        double latencies[] = {43, 54, 56, 61, 62, 66, 68, 69, 69, 70, 71, 72, 77, 78, 79, 85, 87, 88, 89, 93, 95, 96, 98, 99, 909};

        System.out.println(Percentile(latencies,20));
        System.out.println(Percentile(latencies, 50));
        System.out.println(Percentile(latencies, 75));
        System.out.println(Percentile(latencies, 100));


    }

    public static double Percentile(double[] latencies, double Percentile)
    {
        Arrays.sort(latencies);
        double[] tee=Arrays.copyOfRange(latencies,latencies.length-12,latencies.length);
        for (int i=0;i<tee.length;i++){
            System.out.print(tee[i]+",");
        }
        System.out.println();
        int index = (int)Math.ceil((Percentile / 100) * latencies.length);
        return latencies[index-1];
    }
}





