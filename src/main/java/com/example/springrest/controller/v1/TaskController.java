package com.example.springrest.controller.v1;

import java.util.List;
import com.example.springrest.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.springrest.model.Task;

@RequestMapping("/api/v1/tasks")
// finalなフィールドに対して自動でコンストラクタを生成する
@RequiredArgsConstructor
// Rest用のコントローラであることを宣言
@RestController
public class TaskController {

  private final TaskRepository repository;
  @Operation(summary = "タスクを全件取得します")
  @GetMapping("/")
  public List<Task> findAll() {
    return repository.findAll();
  }

  @Operation(summary = "タスクを登録します")
  @PostMapping("/")
  public Task save(@RequestBody Task task) {
    return repository.save(task);
  }

  @Operation(summary = "タスクを1件取得します")
  @GetMapping("/{id}")
  public Task findById(@PathVariable Long id) {
    return repository.findById(id).get();
  }

  @Operation(summary = "タスクを更新します")
  @PostMapping("/{id}")
  public Task save(@RequestBody Task newTask, @PathVariable Long id) {

    return repository.findById(id).map(task -> {
      task.setName(newTask.getName());
      task.setCompleted(newTask.getCompleted());
      return repository.save(task);

    }).orElseGet(() -> {

      newTask.setId(id);
      return repository.save(newTask);
    });
  }

  @Operation(summary = "タスクを削除します")
  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
