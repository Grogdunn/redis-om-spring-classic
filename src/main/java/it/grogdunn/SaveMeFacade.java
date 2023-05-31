package it.grogdunn;

import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Transactional
public class SaveMeFacade {

    private final SaveMeRepository repo;

    public SaveMeFacade(SaveMeRepository repo) {
        this.repo = repo;
    }

    public void generateScrapData() {
        IntStream.range(0, 10).forEach(i -> repo.save(SaveMe.create()));
    }
}
