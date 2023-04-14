package com.jimi.spring6restmvc.controllers;

import com.jimi.spring6restmvc.model.BeerDTO;
import com.jimi.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;



    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){

        beerService.patchBeerById(beerId,beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId")UUID beerId){
        if(!beerService.deleteById(beerId)){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDTO beer){

        if(beerService.updateBeerById(beerId, beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer){

        BeerDTO saverBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + saverBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id){

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

}



//    private final TesterService testerService;

//    @PostMapping("/api/v1/tester")
//    public ResponseEntity handlePostTester(@RequestBody TesterDTO dto) throws IOException, IllegalAccessException {
//
//        String currentDir = System.getProperty("user.dir");
//
//        String filment = makeFiler(dto);
//
//
//        Path filePath = Paths.get(currentDir, "./src/scripts", "facts.rdf");
//        System.out.println(filePath);
//        FileWriter fileWriter = new FileWriter(filePath.toFile());
//        fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
//                "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
//                "        xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
//                "        xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
//                "        xmlns:lc=\"http://informatika.ftn.uns.ac.rs/legal-case.rdf#\">\n" +
//                "    <lc:case rdf:about=\"http://informatika.ftn.uns.ac.rs/legal-case.rdf#case01\">\n" +
//                "        <lc:name>case 01</lc:name>\n" +
//                "        <lc:defendant>Cvele</lc:defendant>\n" +
//
//                filment +
////                "\t<lc:ubistvo_na_mah>no</lc:ubistvo_na_mah>\n" +
//
//                "    </lc:case>\n" +
//                "</rdf:RDF>");
//        fileWriter.close();
//
//        System.out.println();
//        System.out.println(dto.getName());
//        System.out.println(dto.getId());
//        System.out.println(dto.getStyle());
//        System.out.println(dto.getVersion());
//        System.out.println(dto.getSurname());
//        TesterDTO savertester = testerService.saveNewTester(dto);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "/api/v1/tester/" + savertester.getId().toString());
//
//        return new ResponseEntity(headers, HttpStatus.CREATED);
//    }
//
//    public String makeFiler(TesterDTO dto) throws IllegalAccessException {
//        //                "\t<lc:ubistvo_na_mah>no</lc:ubistvo_na_mah>\n" +
//
//        Class cls = dto.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        for(Field field : fields){
//            field.setAccessible(true);
//            if(field.getType().getName().equals("java.lang.String")){
//                System.out.println("ovo je string");
//                Object value = field.get(dto);
//                if(value == null)
//                if(value.equals("no") || value == null){
//                    System.out.println("atribut: " + field.getName() + "\tvrednost: " + value + "\tno");
//                }else{
//                    System.out.println("atribut: " + field.getName() + "\tvrednost: " + value + "\tyes");
//                }
//            }
//
//
//
//        }
//
//        return "";
//    }
