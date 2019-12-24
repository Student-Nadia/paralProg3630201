package spbstu;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import static java.lang.Thread.sleep;

public class Main {
    public Integer nThreads = 0;
    public ExecutorService execService = null;
    public List<Future<Double>> listFutures = new ArrayList<>();

    public Main(Integer nThreads) {
        this.nThreads = nThreads;
        if (nThreads>0)
            execService = Executors.newFixedThreadPool(nThreads);
    }

    public void StartCalculation(Integer N) {
        for(int i=0; i<nThreads; i++)
            listFutures.add(execService.submit(() -> CalculatePi(N)));
    }

    public boolean allDone() {
        boolean isDone = true;
        for(Future<Double> f: listFutures) isDone = isDone && f.isDone();
        return isDone;
    }

    public void StopCalculation() {
        if (execService!=null)
            execService.shutdown();
    }

    public double CalculatePi(Integer N){
        Integer hitCount = 0;
        Random rnd = new Random(); // SecureRandom();
        for(int i = 0; i < N; i++) {
            double x = rnd.nextDouble();
            double y = rnd.nextDouble();
            if(x*x+y*y<=1.0)
                hitCount++;
        }
        return (4.0*hitCount/N);
    }

    public static void main(String[] args) throws Exception {
        Main Calc = new Main(10);
        Calc.StartCalculation(100000000);
        System.out.print("calculating");
        while (!Calc.allDone()) {
            System.out.print(".");
            sleep(150);
        }
        System.out.println();
        Double sumPi = 0.0;
        for(Future<Double> f: Calc.listFutures) {
            sumPi += f.get();
            System.out.println("F.get()=" + f.get());
        }
        System.out.println("--------------------");
        System.out.println("calculated Pi = "+sumPi/Calc.nThreads);
        Calc.StopCalculation();
    }
}
