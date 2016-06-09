package api.controller;

import api.model.Persona;
import api.service.PersonaService;
import org.apache.commons.lang.StringUtils;
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
 * Created by AlbertoBortolato on 19/05/2016.
 */

@RestController
@RequestMapping("persona")
public class PersonaController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    PersonaService service;

    @PostConstruct
    public void init() {
        if (service != null) logger.info("ciao");
    }

    @RequestMapping(value = {"/", ""},
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> insertPersona(UriComponentsBuilder builder, @RequestBody Persona persona) {
        Persona out = service.add(persona);
        if (out == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/persona/{id}").buildAndExpand(out.getId()).toUri());
        ResponseEntity<Persona> response = new ResponseEntity<>(null, headers, HttpStatus.CREATED);
        return response;
    }

    @RequestMapping(value = {"/{id}"},
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getPersona(@PathVariable("id") String id_email) {
        Persona out;
        if ( id_email.contains("@"))
        {
            out = service.getByEmail(id_email);
        } else {
            out = service.getById(id_email);
        }
        ResponseEntity<?> response = new ResponseEntity<>(out, null, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = {"/{id:.+}"},
      method = RequestMethod.PUT,
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    public ResponseEntity<?> updatePersona(@RequestBody Persona persona, @PathVariable("id") String sId) {

       /* String[] arr = StringUtils.split(id, "-|-");
        if (arr != null && arr.length > 1) {
            // abbiamo un nome e cognome
            Persona tmp = service.getByNameSurname(arr[0], arr[1]);
            if (tmp != null && tmp.getId() != null) {
                id = tmp.getId().toString();
            }
        }*/

        if (!StringUtils.isBlank(sId)) {
            try {
                Integer id = Integer.parseInt(sId);
                if(id > 0) {
                    service.update(sId, persona);
                    ResponseEntity<?> response = new ResponseEntity<>(null, HttpStatus.OK);
                    return response;
                }
            }catch (Exception ex) {

            }
            service.update(sId, persona);
        }

        ResponseEntity<?> response = new ResponseEntity<>(null, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = {"/{id}"},
      method = RequestMethod.DELETE,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> deletePersona(@PathVariable("id") String id_email) {

        if ( id_email.contains("@"))
        {
            service.delete(id_email);
        } else {
            service.delete(id_email);
        }



        ResponseEntity<?> response = new ResponseEntity<>(null, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = {"/", ""},
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Set<Persona>> getPersone(@RequestParam Map<String, String> queryParamsMap) {
        List<Persona> lst = service.findItems(queryParamsMap);
        Set<Persona> persone = new HashSet<>();
        persone.addAll(lst);
        return new ResponseEntity<Set<Persona>>(persone, null, HttpStatus.OK);
    }


}
