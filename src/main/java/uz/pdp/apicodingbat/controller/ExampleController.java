package uz.pdp.apicodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apicodingbat.entity.Example;
import uz.pdp.apicodingbat.entity.Task;
import uz.pdp.apicodingbat.payload.ApiResponse;
import uz.pdp.apicodingbat.payload.ExampleDto;
import uz.pdp.apicodingbat.payload.TaskDto;
import uz.pdp.apicodingbat.service.ExampleService;
import uz.pdp.apicodingbat.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/example")
public class ExampleController {
    @Autowired
    ExampleService exampleService;

    /**
     * Namuna qo'shadigan metod
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody ExampleDto exampleDto) {
        ApiResponse apiResponse = exampleService.add(exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Namuna qaytaradigan metod
     * @return List</ Example>
     */
    @GetMapping
    public ResponseEntity<List<Example>> get() {
        List<Example> exampleList = exampleService.get();
        return ResponseEntity.ok(exampleList);
    }


    /**
     * Namuna ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Namuna
     * Namuna bolmasa null qaytadi
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Example example = exampleService.getByID(id);
        return ResponseEntity.ok(example);
    }

    /**
     * Namuna ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse response = exampleService.delete(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }
    /**
     * Namuna ID si bo'yicha tahrirlaydigan metod
     * @param id Integer
     * @param exampleDto ExampleDto
     * @return ApiResponse
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> edit( @PathVariable Integer id,@RequestBody ExampleDto exampleDto){
        ApiResponse apiResponse = exampleService.edit(id, exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    
}
