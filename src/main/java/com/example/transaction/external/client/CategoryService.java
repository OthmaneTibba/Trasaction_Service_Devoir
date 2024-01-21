package com.example.transaction.external.client;

import com.example.transaction.dto.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@FeignClient("http://localhost:8088/api/category")
public interface CategoryService {



    @GetMapping
    @ResponseBody
    public List<Category> getAll();
    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Category> getByName(@PathVariable("name") String name) ;
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Category> getById(@PathVariable("id") int id);
}
