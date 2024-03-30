package com.ynmio.IdCreater.Controllers;

import com.ynmio.IdCreater.dao.IdDataRepository;
import com.ynmio.IdCreater.model.IdData;
import com.ynmio.IdCreater.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class Output {

    @Autowired
    IdDataRepository idDataRepository;

    @GetMapping("/")
    public String showId(Model model){
        model.addAttribute("title","Home");
        return "outputId";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "searchForm"; // Return the name of your Thymeleaf template for the search form
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam Integer userId) {
        Optional<IdData> idDataOptional = idDataRepository.findById(userId);
        if (idDataOptional.isPresent()) {
            IdData idData = idDataOptional.get();
            String idHolderName = idData.getName();
            String idPosition=idData.getPosition();
            double idContact=idData.getContact();
            String idBloodGroup=idData.getBloodGroup();
            Integer idNumber=idData.getId();

            model.addAttribute("idHolderName", idHolderName);
            model.addAttribute("idNumber",idNumber);
            model.addAttribute("idNum",idNumber);
            model.addAttribute("idPosition",idPosition);
            model.addAttribute("idContact",idContact);
            model.addAttribute("idBloodGroup",idBloodGroup);
            return "outputId"; // Return the name of your Thymeleaf template to display the search results
        } else {
            // Handle case where IdData with given ID is not found
            return "error"; // Return the name of your Thymeleaf template for displaying errors
        }
    }

    @PostMapping("updatingDetails")
    public String updateData(@ModelAttribute("input") IdData idData, @RequestParam("image")MultipartFile multipartFile, HttpSession httpSession) throws IOException {

        if(!multipartFile.isEmpty()){
            String fileName ="wr.jpg";
//                    StringUtils.cleanPath(multipartFile.getOriginalFilename());
            idData.setPhoto(fileName);
            idDataRepository.save(idData);
            String upload="src/main/resources/static/images/"+idData.getId();
            FileUploadUtil.saveFile(upload,fileName,multipartFile);
        }
        else {
            if(idData.getPhoto().isEmpty()){
                idData.setPhoto(null);
                idDataRepository.save(idData);
            }
        }
        idDataRepository.save(idData);
        return "Input";
    }

    @GetMapping("test")
    public String test(){
        return "test";
    }
    @GetMapping("/input")
    public String input(){
        return "Input";
    }
}
