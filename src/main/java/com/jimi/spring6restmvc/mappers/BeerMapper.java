package com.jimi.spring6restmvc.mappers;

import com.jimi.spring6restmvc.entities.Beer;
import com.jimi.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
