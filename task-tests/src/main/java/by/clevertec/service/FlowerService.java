package by.clevertec.service;

import by.clevertec.domain.Flower;
import by.clevertec.entity.FlowerEntity;
import by.clevertec.exception.FlowerNotFoundException;
import by.clevertec.mapper.FlowerMapper;
import by.clevertec.mapper.FlowerMapperImpl;
import by.clevertec.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository repository;
    private final FlowerMapper mapper;

    public FlowerService() {
        this(new FlowerRepository(), new FlowerMapperImpl());
    }

    public FlowerService(FlowerRepository repository) {
        this(repository, new FlowerMapperImpl());
    }

    public FlowerService(FlowerMapper mapper) {
        this(new FlowerRepository(), mapper);
    }

    public Flower createFlower(Flower flower) {
        return mapper.toDomain(repository.createFlower(mapper.toEntity(flower)));
    }

    public Flower getFlowerById(UUID uuid) {
        return mapper.toDomain(repository.getFlowerById(uuid));
    }

    public List<Flower> getFlowers() {
        return mapper.toDomains(repository.getFlowers());
    }

    public Flower updateFlower(UUID uuid, Flower flower) {
        return mapper.toDomain(repository.updateFlower(uuid, mapper.toEntity(flower)));
    }

    public void deleteFlower(UUID uuid) {
        repository.deleteFlower(uuid);
    }
}
