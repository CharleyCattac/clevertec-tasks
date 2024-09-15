package by.clevertec.service;

import by.clevertec.domain.Flower;
import by.clevertec.entity.FlowerEntity;
import by.clevertec.exception.FlowerNotFoundException;
import by.clevertec.mapper.FlowerMapper;
import by.clevertec.repository.FlowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlowerServiceTest {

    @Mock
    private FlowerRepository flowerRepository;
    @Mock
    private FlowerMapper flowerMapper;
    @InjectMocks
    private FlowerService flowerService;

    @Test
    void shouldCreateFlower() {
        UUID uuid = UUID.randomUUID();
        Flower expectedDomain = Flower
                .builder()
                .id(uuid)
                .commonName("Barnacle Lichen")
                .plantFamily("Thelotremataceae")
                .build();
        FlowerEntity expectedEntity = FlowerEntity
                .builder()
                .id(uuid)
                .commonName("Barnacle Lichen")
                .plantFamily("Thelotremataceae")
                .build();
        Mockito
                .when(flowerRepository.createFlower(expectedEntity))
                .thenReturn(expectedEntity);
        Mockito
                .when(flowerMapper.toEntity(expectedDomain))
                .thenReturn(expectedEntity);
        Mockito
                .when(flowerMapper.toDomain(expectedEntity))
                .thenReturn(expectedDomain);
        // when
        Flower actualDomain = flowerService.createFlower(expectedDomain);
        // then
        assertEquals(expectedDomain, actualDomain);
    }

    @Test
    void shouldGetFlowerById_whenFlowerPresent() {
        // given
        UUID uuid = UUID.randomUUID();
        Flower expectedDomain = Flower
                .builder()
                .id(uuid)
                .commonName("Jointleaf Rush")
                .plantFamily("Juncaceae")
                .build();
        FlowerEntity expectedEntity = FlowerEntity
                .builder()
                .id(uuid)
                .commonName("Jointleaf Rush")
                .plantFamily("Juncaceae")
                .build();
        Mockito
                .when(flowerRepository.getFlowerById(uuid))
                .thenReturn(expectedEntity);
        Mockito
                .when(flowerMapper.toDomain(expectedEntity))
                .thenReturn(expectedDomain);
        // when
        Flower actualDomain = flowerService.getFlowerById(uuid);
        // then
        assertEquals(expectedDomain, actualDomain);
    }

    @Test
    void shouldThrowFlowerNotFoundException_whenFlowerNotPresent() {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito
                .when(flowerRepository.getFlowerById(uuid))
                .thenThrow(FlowerNotFoundException.class);
        // when
        Exception actualException =
                assertThrows(FlowerNotFoundException.class, () -> flowerService.getFlowerById(uuid));
        // then
        assertEquals(FlowerNotFoundException.class, actualException.getClass());
    }

    @Test
    void shouldGetFlowers_givenNonEmptyList() {
        List<FlowerEntity> expectedEntities = List.of(FlowerEntity.builder().build());
        List<Flower> expectedDomains = List.of(Flower.builder().build());
        Mockito
                .when(flowerRepository.getFlowers())
                .thenReturn(expectedEntities);
        Mockito
                .when(flowerMapper.toDomains(expectedEntities))
                .thenReturn(expectedDomains);
        // when
        List<Flower> actualDomains = flowerService.getFlowers();
        // then
        assertFalse(actualDomains.isEmpty());
    }

    @Test
    void shouldNotGetFlowers_givenEmptyList() {
        // given
        List<FlowerEntity> expectedEntities = new ArrayList<>();
        List<Flower> expectedDomains = new ArrayList<>();
        Mockito
                .when(flowerRepository.getFlowers())
                .thenReturn(expectedEntities);
        Mockito
                .when(flowerMapper.toDomains(expectedEntities))
                .thenReturn(expectedDomains);
        // when
        List<Flower> actualDomains = flowerService.getFlowers();
        // then
        assertTrue(actualDomains.isEmpty());
    }

    @Test
    void shouldUpdateFlower_whenFlowerPresent() {
        // given
        UUID uuid = UUID.randomUUID();
        Flower expectedDomain = Flower
                .builder()
                .id(uuid)
                .commonName("Jointleaf Rush")
                .plantFamily("Juncaceae")
                .build();
        FlowerEntity expectedEntity = FlowerEntity
                .builder()
                .id(uuid)
                .commonName("Jointleaf Rush")
                .plantFamily("Juncaceae")
                .build();
        Mockito
                .when(flowerMapper.toEntity(expectedDomain))
                .thenReturn(expectedEntity);
        Mockito
                .when(flowerRepository.updateFlower(uuid, expectedEntity))
                .thenReturn(expectedEntity);
        Mockito
                .when(flowerMapper.toDomain(expectedEntity))
                .thenReturn(expectedDomain);
        // when
        Flower actualDomain = flowerService.updateFlower(uuid, expectedDomain);
        // then
        assertEquals(expectedDomain, actualDomain);
    }

    @Test
    void shouldUpdateFlower_whenNoFlowerPresent() {
        // given
        UUID uuid = UUID.randomUUID();
        Flower expectedDomain = Flower
                .builder()
                .id(uuid)
                .commonName("Jointleaf Rush")
                .plantFamily("Juncaceae")
                .build();
        FlowerEntity expectedEntity = FlowerEntity
                .builder()
                .id(uuid)
                .commonName("Jointleaf Rush")
                .plantFamily("Juncaceae")
                .build();
        Mockito
                .when(flowerMapper.toEntity(expectedDomain))
                .thenReturn(expectedEntity);
        Mockito
                .when(flowerRepository.updateFlower(uuid, expectedEntity))
                .thenThrow(FlowerNotFoundException.class);
        // when
        Exception actualException =
                assertThrows(FlowerNotFoundException.class, () -> flowerService.updateFlower(uuid, expectedDomain));
        // then
        assertEquals(FlowerNotFoundException.class, actualException.getClass());
    }

    @Test
    void shouldDeleteFlower() {
        // given

        // when

        // then

    }
}