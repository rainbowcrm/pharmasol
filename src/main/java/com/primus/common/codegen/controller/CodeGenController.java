package com.primus.common.codegen.controller;

import com.primus.common.Logger;
import com.techtrade.rads.framework.ui.controls.UIText;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/codegenerate")
@RestController
public class CodeGenController {



    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        UIText test = new UIText("id");
        return "{Hello World}";

    }

    @RequestMapping( value = "/generate/{entity}/mode/{mode}", method = RequestMethod.GET)
    public ResponseEntity<?> generate(@PathVariable("entity") String entity, @PathVariable("mode") String mode) {
        CodeGenerator codeGenerator  = new CodeGenerator() ;
        try {
            codeGenerator.generate(entity, mode);
            return new ResponseEntity<String>("success", HttpStatus.OK);
        }catch (Exception ex) {
            Logger.logException("Error while generating code " ,  this.getClass(),ex );
            return new ResponseEntity<String>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

}
