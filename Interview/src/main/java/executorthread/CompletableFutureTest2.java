package executorthread;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Auther: baozi
 * @Date: 2019/4/26 16:09
 * @Description:
 */
public class CompletableFutureTest2 {
    public static void main(String[] args) {
        CompletableFuture<Integer>
                f0 = CompletableFuture
                .supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        return "ds";
                    }
                })
                .thenApply(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String r) {
                        System.out.println(r);
                        return 10;
                    }
                })
                .exceptionally(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable e) {
//                        e.printStackTrace();
                        return 0;
                    }
                });
        System.out.println(f0.join());

    }
}
