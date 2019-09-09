package com.testtasks.texthandler;

import com.testtasks.texthandler.models.ProcessedText;
import com.testtasks.texthandler.repositories.ProcessedTextRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/processed_texts")
public class FileUploadController {
    private final ProcessedTextRepository repository;

    public FileUploadController(ProcessedTextRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ProcessedText> getAllProcessedTexts() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProcessedText getProcessedTextById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ProcessedText createProcessedText(@RequestParam("file") MultipartFile file) {
        ProcessedText processedText = new ProcessedText(ObjectId.get(), reformString(readFile(file)));
        repository.save(processedText);
        return processedText;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ProcessedText modifyProcessedTextById(@PathVariable("id") ObjectId id, @Valid @RequestBody ProcessedText processedText) {
        processedText.set_id(id);
        repository.save(processedText);
        return processedText;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteProcessedTextById(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
        return "Deleted!";
    }

    private String readFile(MultipartFile file) {
        String result = "";
        try {
            result = new BufferedReader(new InputStreamReader(file.getInputStream())).lines().parallel().collect(Collectors.joining("\n"));
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    private String[] reformString(String text) {
        return text.split("\n");
    }
}
