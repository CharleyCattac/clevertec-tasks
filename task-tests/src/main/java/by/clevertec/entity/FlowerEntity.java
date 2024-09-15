package by.clevertec.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Builder
@Accessors(chain = true)
public class FlowerEntity {

    private UUID id;
    private String commonName;
    private String plantFamily;
}
