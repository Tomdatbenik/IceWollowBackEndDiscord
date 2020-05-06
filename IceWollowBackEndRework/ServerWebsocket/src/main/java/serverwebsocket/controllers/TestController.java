package serverwebsocket.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path= "test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @GetMapping(path = "/get")
    public String test()
    {
        return "Test";
    }
}
