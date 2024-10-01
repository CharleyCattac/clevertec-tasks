package by.clevertec.mapper;

import by.clevertec.domain.Flower;
import by.clevertec.entity.FlowerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FlowerMapper {

    List<Flower> toDomains(List<FlowerEntity> flowerEntities);

    Flower toDomain(FlowerEntity flowerEntity);

    FlowerEntity toEntity(Flower flower);
}
