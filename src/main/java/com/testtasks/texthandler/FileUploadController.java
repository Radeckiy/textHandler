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
                sliceTextIntoStrings(readFile(file).replace("#", "").trim()),
                getSectionsFromStrings(sliceTextIntoStrings(readFile(file))));
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
                ArrayList<Integer> stringsBodyIndices = new ArrayList<>(), nestedSectionsIndices = new ArrayList<>();
                while (getNestedLevel(strings[i + k]) >= n + 1) {
                    if (getNestedLevel(strings[i + k]) == n + 1) {
                        stringsBodyIndices.add(i + k);
                    }
                    k++;
                }
                if(stringsBodyIndices.size() > 0 || n == 1)
                    result.add(new Section(result.size(), i, stringsBodyIndices, nestedSectionsIndices));
            }
        }

        for(int i = 0; i < result.size()-1; i++) {
            int k = 1;
            if (result.get(i).getStringsBodyIndices().contains(result.get(i + k).getSectionTitleStringIndex())) {
                ArrayList<Integer> nestedSectionsIndices = new ArrayList<>();
                while(result.get(i).getStringsBodyIndices().contains(result.get(i + k).getSectionTitleStringIndex())) {
                    nestedSectionsIndices.add(i + k);
                    k++;
                }
                result.get(i).setNestedSectionsIndices(nestedSectionsIndices);
            }
        }
        return result;
    }

    private Integer getNestedLevel(String str) {
        int k = 0;
        if (str.charAt(0) == '#') {
            while (str.charAt(k) == '#') {
                k++;
            }
        }
        return k;
    }

    private String[] sliceTextIntoStrings(String text) {
        return text.split("\n");
    }
}
