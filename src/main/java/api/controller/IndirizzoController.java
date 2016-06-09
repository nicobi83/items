package api.controller;

import api.model.Indirizzo;
import api.service.IndirizzoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marco-Giovanni-Nicola Muahahah (BFF) on 18/05/2016.
 */


@RestController
@RequestMapping("indirizzo")
public class IndirizzoController {


    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    IndirizzoService service;

    @PostConstruct
    void init()
    {
       if(this.service.toString()!= "")
       {
           logger.info("non è vuoto");
       }
    }
//PostConstruct per verificare se il service iniettato non è vuoto

    @RequestMapping(value = {"/", ""},
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createIndirizzo(UriComponentsBuilder builder, @RequestBody Indirizzo indirizzo) {
        Indirizzo out = service.add(indirizzo);
        if (out == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/indirizzo/{id}").buildAndExpand(out.getId()).toUri());
        ResponseEntity<Indirizzo> response = new ResponseEntity<>(null, headers, HttpStatus.CREATED);
        return response;
    }

    @RequestMapping(value = {"/{id}"},
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getIndirizzo(@PathVariable("id") String id) {
        Indirizzo out = service.getById(id);

        ResponseEntity<?> response = new ResponseEntity<>(out, null, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = {"/{id}"},
      method = RequestMethod.PUT,
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> updateIndirizzo(@RequestBody Indirizzo indirizzo, @PathVariable("id") String id) {
        service.update(id, indirizzo);

        ResponseEntity<?> response = new ResponseEntity<>(null, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = {"/{id}"},
      method = RequestMethod.DELETE,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> deleteIndirizzo(@PathVariable("id") String id) {
        service.delete(id);

        ResponseEntity<?> response = new ResponseEntity<>(null, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = {"/", ""},
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Set<Indirizzo>> getIndirizzi(@RequestParam Map<String, String> queryParamsMap) {
        List<Indirizzo> lst = service.findItems(queryParamsMap);
        Set<Indirizzo> indirizzi = new HashSet<>();
        indirizzi.addAll(lst);
        return new ResponseEntity<Set<Indirizzo>>(indirizzi, null, HttpStatus.OK);
    }


}
