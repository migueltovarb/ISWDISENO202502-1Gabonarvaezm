package com.motriz.config;

import com.motriz.dto.FabricaDTO;
import com.motriz.dto.PlantaDTO;
import com.motriz.dto.VehiculoDTO;
import com.motriz.model.Planta;
import com.motriz.model.Fabrica;
import com.motriz.service.FabricaService;
import com.motriz.service.PlantaService;
import com.motriz.service.VehiculoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class DataBootstrap {

    @Bean
    CommandLineRunner initMongo(MongoTemplate mongoTemplate,
                                FabricaService fabricaService,
                                PlantaService plantaService,
                                VehiculoService vehiculoService) {
        return args -> {
            if (!mongoTemplate.collectionExists("vehiculos")) {
                mongoTemplate.createCollection("vehiculos");
            }
            if (!mongoTemplate.collectionExists("plantas")) {
                mongoTemplate.createCollection("plantas");
            }
            if (!mongoTemplate.collectionExists("fabricas")) {
                mongoTemplate.createCollection("fabricas");
            }

            boolean needsSeed = mongoTemplate.getCollection("fabricas").countDocuments() == 0;
            if (needsSeed) {
                FabricaDTO f1 = new FabricaDTO(null, "Aurora Mobility", "Colombia");
                Fabrica fabrica = fabricaService.crearFabrica(f1);

                PlantaDTO p1 = new PlantaDTO(null, "Nodo Central", "Bogotá", fabrica.getId());
                PlantaDTO p2 = new PlantaDTO(null, "Nodo Pacífico", "Cali", fabrica.getId());
                Planta planta1 = plantaService.crearPlanta(p1);
                Planta planta2 = plantaService.crearPlanta(p2);

                VehiculoDTO v1 = new VehiculoDTO(null, "Kia", "Rio", "Pirelli Cinturato", 4, planta1.getId());
                VehiculoDTO v2 = new VehiculoDTO(null, "Renault", "Stepway", "Goodyear Assurance", 4, planta1.getId());
                VehiculoDTO v3 = new VehiculoDTO(null, "Mazda", "CX-30", "Michelin Primacy", 5, planta2.getId());
                vehiculoService.crearVehiculo(v1);
                vehiculoService.crearVehiculo(v2);
                vehiculoService.crearVehiculo(v3);
            }
        };
    }
}