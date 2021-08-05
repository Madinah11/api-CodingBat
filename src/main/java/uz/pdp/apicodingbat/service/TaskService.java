package uz.pdp.apicodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apicodingbat.entity.Category;
import uz.pdp.apicodingbat.entity.Language;
import uz.pdp.apicodingbat.entity.Task;
import uz.pdp.apicodingbat.payload.ApiResponse;
import uz.pdp.apicodingbat.payload.TaskDto;
import uz.pdp.apicodingbat.repository.CategoryRepository;
import uz.pdp.apicodingbat.repository.LanguageRepository;
import uz.pdp.apicodingbat.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LanguageRepository languageRepository;

    /**
     * Vazifa qo'shadigan metod
     * @return ApiResponse
     */
    public ApiResponse add(TaskDto taskDto) {
        boolean existsByName = taskRepository.existsByName(taskDto.getName());
        if (existsByName)
            return new ApiResponse("Bunday vazifa bazada mavjud ",false);
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageID());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Bunday til topilmadi ",false);
        Language language = optionalLanguage.get();
        Task task=new Task(null,taskDto.getName(),taskDto.getCondition(),taskDto.getSolution(),taskDto.getHint(),taskDto.getMethod(),taskDto.isHasStar(),language);
        taskRepository.save(task);
        return new ApiResponse("Yangi vazifa saqlandi", true);
    }

    /**
     * Vazifa listini qaytaradigan metod
     * @return List<Task>
     */
    public List<Task> get() {
        List<Task> taskList = taskRepository.findAll();
        return taskList;
    }

    /**
     * Vazifa  ID si bo'yicha qaytaradigan metod
     * @param id Integer
     * @return Vazifa
     * Vazifa bo'lmasa null qaytadi
     */
    public Task getByID(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    /**
     * Vazifa ID si bo'yicha udalit qiladigan metod
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Vazifa o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Vazifa  topilmadi", false);
        }
    }

    /**
     * Vazifa ID si bo'yicha tahrirlaydigan metod
     * @param id      Integer
     * @param taskDto TaskDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, TaskDto taskDto) {
        boolean existsByName = taskRepository.existsByName(taskDto.getName());
        if (existsByName)
            return new ApiResponse("Bunday vazifa bazada mavjud ",false);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Bunday vazifa topilmadi ",false);
        Task editingTask = optionalTask.get();
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageID());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Bunday til topilmadi ",false);
        Language language = optionalLanguage.get();
        editingTask.setCondition(taskDto.getCondition());
        editingTask.setHint(taskDto.getHint());
        editingTask.setName(taskDto.getName());
        editingTask.setHasStar(taskDto.isHasStar());
        editingTask.setLanguage(language);
        editingTask.setSolution(taskDto.getSolution());
        editingTask.setMethod(taskDto.getMethod());
        taskRepository.save(editingTask);
        return new ApiResponse("Tanlangan til turi tahrirlandi",true);
    }
}
