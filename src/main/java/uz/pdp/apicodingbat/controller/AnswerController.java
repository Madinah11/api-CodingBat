package uz.pdp.apicodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apicodingbat.entity.Answer;
import uz.pdp.apicodingbat.entity.Task;
import uz.pdp.apicodingbat.payload.AnswerDto;
import uz.pdp.apicodingbat.payload.ApiResponse;
import uz.pdp.apicodingbat.payload.TaskDto;
import uz.pdp.apicodingbat.service.AnswerService;
import uz.pdp.apicodingbat.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    /**
     * Javob qo'shadigan metod
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody AnswerDto answerDto) {
        ApiResponse apiResponse = answerService.add(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Javob qaytaradigan metod
     * @return List</ Answer>
     */
    @GetMapping
    public ResponseEntity<List<Answer>> get() {
        List<Answer> answerList = answerService.get();
        return ResponseEntity.ok(answerList);
    }


    /**
     * Javobni ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Javob
     * Javob bolmasa null qaytadi
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Answer answer = answerService.getByID(id);
        return ResponseEntity.ok(answer);
    }

    /**
     * Javobni ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse response = answerService.delete(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }
    /**
     * Javobni ID si bo'yicha tahrirlaydigan metod
     * @param id Integer
     * @param answerDto AnswerDto
     * @return ApiResponse
     */
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> edit( @PathVariable Integer id,@RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.edit(id, answerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    
}
