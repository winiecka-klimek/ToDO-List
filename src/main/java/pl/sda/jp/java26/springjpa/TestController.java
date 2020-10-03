package pl.sda.jp.java26.springjpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }

    @PostMapping("/import")
    public String initialImport() {
        TaskDao taskDao = new TaskDao();
        taskDao.importInitialData();
        return "OK";
    }
}

