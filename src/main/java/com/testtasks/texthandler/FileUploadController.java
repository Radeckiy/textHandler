package com.testtasks.texthandler;

import com.testtasks.texthandler.models.ProcessedText;
import com.testtasks.texthandler.models.Section;
import com.testtasks.texthandler.repositories.ProcessedTextRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        ProcessedText processedText = new ProcessedText(ObjectId.get(),
                Arrays.stream(sliceTextIntoStringsSections(readFile(file))).map(s -> s.replace("#", "")).toArray(String[]::new),
                getSectionsFromStrings(sliceTextIntoStringsSections(readFile(file))));
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

    private ArrayList<Section> getSectionsFromStrings(String[] strings) {
        ArrayList<Section> result = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            int n = getNestedLevel(strings[i]), k = 1;
            if (n >= 1) {
                ArrayList<Integer> nestedSectionsIndices = new ArrayList<>();
                while (i + k <= strings.length - 1 && getNestedLevel(strings[i + k]) >= n + 1) {
                    if (getNestedLevel(strings[i + k]) == n + 1) {
                        nestedSectionsIndices.add(i + k);
                    }
                    k++;
                }
                result.add(new Section(result.size(), i, nestedSectionsIndices));
            }
        }
        return result;
    }

    private Integer getNestedLevel(String str) {
        int k = 0;
        if (str.trim().charAt(0) == '#') {
            while (str.trim().charAt(k) == '#') {
                k++;
            }
        }
        return k;
    }

    private String[] sliceTextIntoStringsSections(String text) {
        //return Arrays.stream(text.split("#")).map(String::trim).filter(s -> s.length() > 0).toArray(String[]::new);

        String[] stringsArray = text.split("\n");
        ArrayList<String> result = new ArrayList<>();
        StringBuilder s = new StringBuilder();

        for (String string : stringsArray) {
            if (string.length() > 0 && string.trim().charAt(0) == '#') {
                if (s.toString().length() > 0)
                    result.add(s.toString().trim());
                s = new StringBuilder(string);
            }
            else
                s.append("\n").append(string);
        }
        if (s.toString().length() > 0)
            result.add(s.toString().trim());

        return result.toArray(new String[0]);
    }
}
