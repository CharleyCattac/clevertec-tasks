package by.clevertec.repository;

import by.clevertec.entity.FlowerEntity;
import by.clevertec.exception.FlowerNotFoundException;

import java.util.List;
import java.util.UUID;

public class FlowerRepository {

    private static final List<FlowerEntity> db = List.of(
            FlowerEntity.builder()
                    .id(UUID.randomUUID())
                    .commonName("Hetch Hetchy Monkeyflower")
                    .plantFamily("Scrophulariaceae")
                    .build(),
            FlowerEntity.builder()
                    .id(UUID.randomUUID())
                    .commonName("Yellow Pond-lily")
                    .plantFamily("Nymphaeaceae")
                    .build(),
            FlowerEntity.builder()
                    .id(UUID.randomUUID())
                    .commonName("Napali Kona Peperomia")
                    .plantFamily("Piperaceae")
                    .build()
    );

    public FlowerEntity createFlower(FlowerEntity flowerEntity) {
        this.deleteFlower(flowerEntity.getId());
        db.add(flowerEntity);
        return flowerEntity;
    }

    public FlowerEntity getFlowerById(UUID uuid) {
        return db.stream()
                .filter(flower -> flower.getId().equals(uuid))
                .findFirst()
                .orElseThrow(FlowerNotFoundException::new);
    }

    public List<FlowerEntity> getFlowers() {
        return List.copyOf(db);
    }

    public FlowerEntity updateFlower(UUID uuid, FlowerEntity flowerEntity) {
        return this.createFlower(flowerEntity.setId(uuid));
    }

    public void deleteFlower(UUID uuid) {
        this.getFlowerById(uuid);
        db.removeIf(flower -> flower.getId().equals(uuid));
    }
}
