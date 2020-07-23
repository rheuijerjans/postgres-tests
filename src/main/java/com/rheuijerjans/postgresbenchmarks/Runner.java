package com.rheuijerjans.postgresbenchmarks;

import com.rheuijerjans.postgresbenchmarks.multicolumnindex.one.OneFetcher;
import com.rheuijerjans.postgresbenchmarks.multicolumnindex.one.OneLoader;
import com.rheuijerjans.postgresbenchmarks.multicolumnindex.two.TwoFetcher;
import com.rheuijerjans.postgresbenchmarks.multicolumnindex.two.TwoLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Runner implements CommandLineRunner {

    private final OneLoader oneLoader;
    private final OneFetcher oneFetcher;
    private final TwoLoader twoLoader;
    private final TwoFetcher twoFetcher;

    public Runner(OneLoader oneLoader, OneFetcher oneFetcher, TwoLoader twoLoader, TwoFetcher twoFetcher) {
        this.oneLoader = oneLoader;
        this.oneFetcher = oneFetcher;
        this.twoLoader = twoLoader;
        this.twoFetcher = twoFetcher;
    }

    @Override
    public void run(String... args) throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(4);

        final CompletableFuture<Void> a = CompletableFuture.runAsync(twoLoader::createData, executorService);
        final CompletableFuture<Void> b = CompletableFuture.runAsync(twoLoader::createData, executorService);
        final CompletableFuture<Void> c = CompletableFuture.runAsync(twoLoader::createData, executorService);
        final CompletableFuture<Void> d = CompletableFuture.runAsync(twoLoader::createData, executorService);

        CompletableFuture.allOf(a, b, c, d).join();


        final CompletableFuture<Void> a1 = CompletableFuture.runAsync(oneLoader::createData, executorService);
        final CompletableFuture<Void> b1 = CompletableFuture.runAsync(oneLoader::createData, executorService);
        final CompletableFuture<Void> c1 = CompletableFuture.runAsync(oneLoader::createData, executorService);
        final CompletableFuture<Void> d1 = CompletableFuture.runAsync(oneLoader::createData, executorService);
        CompletableFuture.allOf(a1, b1, c1, d1).join();

        twoFetcher.fetch();
        oneFetcher.fetch();
    }

}
