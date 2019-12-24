package spbstu;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

public class MainTest {

    @org.junit.Test
    public void startCalculation() throws ExecutionException, InterruptedException {
        Main testPi = new Main(2);
        testPi.StartCalculation(10000);
        for(Future<Double> f: testPi.listFutures) {
            System.out.println("F.get()=" + f.get());
            assertTrue(Math.abs(f.get() - Math.PI) < 0.05);
        }
        testPi.StopCalculation();
    }

    @org.junit.Test
    public void calculatePi() {
        Main testPi = new Main(0);
        for (int i = 0; i < 10; i++) {
            double myPi = testPi.CalculatePi(10000);
            System.out.println("Pi=" + myPi+" ("+Math.abs(myPi - Math.PI)+")");
            assertTrue(Math.abs(myPi - Math.PI) < 0.05);
        }
    }

}